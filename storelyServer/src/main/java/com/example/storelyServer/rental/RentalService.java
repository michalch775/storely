package com.example.storelyServer.rental;

import com.example.storelyServer.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, ItemRepository itemRepository) {
        this.rentalRepository = rentalRepository;
        this.itemRepository = itemRepository;
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Long addNewRental(Rental rental){

        List<Rental> rentalsByCode = rentalRepository
                .findByItem_code(rental.getItem().getCode());

        //int sum = rentalsByCode.stream().mapToInt(Integer::intValue).sum();

        if(!rentalsByCode.isEmpty()){
            Predicate<Rental> wasReturned = x -> x.getReturnDate() == null;

            var result = rentalsByCode.stream().filter(wasReturned)
                    .collect(Collectors.toList());
            if(rental.getItem().getItemTemplate().isReturnable()){
                if(!result.isEmpty()){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Ten przedmiot jest juz wypozyczony");
                }
                else{
                    return rentalRepository.save(rental).getId();
                }
            }
            else{
                int sum =0;
                for(int i=0; i<rentalsByCode.size(); i++){
                    sum+=rentalsByCode.get(i).getQuantity();
                }

                if(rental.getItem().getQuantity() > 0
                        && rental.getItem().getQuantity() >= rental.getQuantity()){
                    rental.getItem().setQuantity(rental.getItem().getQuantity() - rental.getQuantity());
                    rental.setReturnDate(LocalDateTime.now());
                    itemRepository.save(rental.getItem());
                    return rentalRepository.save(rental).getId();
                }
                else{
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Brak wystarczajacej ilosci na magazynie");
                }
            }
        }
        else{
            return rentalRepository.save(rental).getId();
        }
    }

    public List<Rental> getRentalsByUserEmail(String email){
        return rentalRepository.findByUser_email(email);

    }

    public void closeRental(Long id){
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Wypozyczenie nie istnieje"));
        rental.setReturnDate(LocalDateTime.now());
        rentalRepository.save(rental);

    }

    public Rental getRentalsById(Long id){
        return rentalRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Wypozyczenie nie istnieje"));
    }
}
