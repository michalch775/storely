package com.example.storelyServer.rental;

import com.example.storelyServer.item.Item;
import com.example.storelyServer.item.ItemRepository;
import com.example.storelyServer.templates.ResponseCode;
import com.example.storelyServer.templates.ResponseId;
import com.example.storelyServer.user.User;
import com.example.storelyServer.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Klasa obslugujaca logike wypozyczen (Spring Service)
 * @author Michał Chruścielski
 */
@Service
public class RentalService {
    private final RentalRepository rentalRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Autowired
    public RentalService(RentalRepository rentalRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    /**
     * zwraca wypozyczenia
     * @return <code>List<Rental></code> lista wypozyczen
     */
    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }


    /**
     * dodaje nowe wypozyczenie
     * @param email
     * @param code
     * @param quantity
     * @return <code>Long</code> id wypozyczenia
     */
    public Long addNewRental(String email, Long code, Integer quantity){

        Item item = itemRepository.findItemByCode(code)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Przedmiot nie istnieje"));

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono uzytkowika"));

        if(!item.getItemTemplate().getGroups().contains(user.getGroup())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Uzytkownik nie jest w dozwolonej grupie");
        }


        //Rental rental = new Rental(quantity, user, item );

        List<Rental> rentalsByCode = rentalRepository
                .findByItem_code(item.getCode());

        //int sum = rentalsByCode.stream().mapToInt(Integer::intValue).sum();

        if(!rentalsByCode.isEmpty()){
            Predicate<Rental> wasReturned = x -> x.getReturnDate() == null;

            var result = rentalsByCode.stream().filter(wasReturned)
                    .collect(Collectors.toList());

            if(item.getItemTemplate().isReturnable()){
                if(result.isEmpty()){
                    return rentalRepository.save(new Rental(user, item, quantity)).getId();
                }
                else{
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Ten przedmiot jest juz wypozyczony");
                }
            }
            else{
                if(item.getQuantity() >= quantity){
                    item.setQuantity(item.getQuantity() - quantity);
                    itemRepository.save(item);
                    return rentalRepository.save(new Rental(user, item, quantity)).getId();
                }
                else{
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Brak wystarczajacej ilosci na magazynie");
                }
            }
        }
        else{

            if(!item.getItemTemplate().isReturnable() && item.getQuantity()>=quantity){
                item.setQuantity(item.getQuantity() - quantity);
                itemRepository.save(item);
                return rentalRepository.save(new Rental(user, item, quantity)).getId();
            }
            else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Brak wystarczajacej ilosci na magazynie");
            }
        }
    }

    /*
    public List<ResponseCode> addNewRentalGroup(String userEmail, List<RentalListPostDto> list){


        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono uzytkowika"));

        List<ResponseCode> responseCodeList = new ArrayList<>();
        for (RentalListPostDto dto : list){

            List<Rental> rentalsByCode = rentalRepository
                .findByItem_code(dto.getCode());
            Optional<Item> item = itemRepository.findItemByCode(dto.getCode());
            if(!item.isPresent()){
                responseCodeList.add(new ResponseCode(dto.getCode(),"Przedmiot nie istnieje"));
                continue;
            }

            if(item.get().getItemTemplate().getGroups().contains(user.getGroup())){
                if(!rentalsByCode.isEmpty()){
                    Predicate<Rental> wasReturned = x -> x.getReturnDate() == null;

                    var result = rentalsByCode.stream().filter(wasReturned)
                            .collect(Collectors.toList());
                    if(item.get().getItemTemplate().isReturnable()){
                        if(result.isEmpty()){
                            Rental rental = new Rental(user, item.get(), true, -1);
                            responseCodeList.add(new ResponseCode(dto.getCode(), rentalRepository.save(rental).getId()));
                        }
                        else{
                            responseCodeList.add(new ResponseCode(dto.getCode(),"Przedmiot jest wypozyczony"));
                        }
                    }
                    else{
                        int sum = 0;
                        for(int i=0; i<rentalsByCode.size(); i++){
                            sum+=rentalsByCode.get(i).getQuantity();
                        }

                        if(item.get().getQuantity() > 0
                                && item.get().getQuantity() >= dto.getQuantity()){

                            item.get().setQuantity(item.get().getQuantity() - dto.getQuantity());
                            Rental rental = new Rental(user, item.get(), false, dto.getQuantity());
                            rental.setReturnDate(LocalDateTime.now());
                            itemRepository.save(item.get());
                            responseCodeList.add(new ResponseCode(dto.getCode(), rentalRepository.save(rental).getId()));
                        }
                        else{
                            responseCodeList.add(new ResponseCode(dto.getCode(),"Brak wystarcajacej ilosci na magazynie"));
                        }
                    }
                }
                else{
                    if(item.get().getItemTemplate().isReturnable()){
                        item.get().setQuantity(item.get().getQuantity() - dto.getQuantity());
                        Rental rental = new Rental(user, item.get(), false, dto.getQuantity());
                        rental.setReturnDate(LocalDateTime.now());
                        responseCodeList.add(new ResponseCode(dto.getCode(), rentalRepository.save(rental).getId()));
                    }
                    else{
                        Rental rental = new Rental(user, item.get(), true, -1);
                        responseCodeList.add(new ResponseCode(dto.getCode(), rentalRepository.save(rental).getId()));
                    }

                }
            }
            else{
                responseCodeList.add(new ResponseCode(dto.getCode(),"Nie mozesz wypozyczyc tego przedmiotu"));
            }

        }
        return responseCodeList;


    }

    public void returnRentalGroup(String userEmail, List<ResponseId> list){//TODO obsluga grupowych zwrotow
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono uzytkowika"));

        List<ResponseCode> responseCodeList = new ArrayList<>();
        for (ResponseId idObj : list){
            Rental rental = rentalRepository.findById(idObj.getId())
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Wypozyczenie nie istnieje"));
            if(rental.getUser().getId()==user.getId()){
                rental.setReturnDate(LocalDateTime.now());
            }
            else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wypozyczenie zsotalo zrobione przez innego uzytkownika");
            }

        }



       // return responseCodeList;
    }
     */


    /**
     * zwraca wypozyczenia uzytkownika
     * @param email email uzytkownika, ktorego wypozyczenia zwraca
     * @return <code>List<Rental></code> lista wypozyczen
     */
    public List<Rental> getRentalsByUserEmail(String email){
        return rentalRepository.findByUser_email(email);

    }


    /**
     * zamyka wypozyczenie, czyli robi zwrot przedmiotu
     * przy udanym zwrocie nie zwraca nic, a zapytanie zwraca <code>HTTP 200</code>
     * @param id id wypozyczenia do zamkniecia
     */
    public void closeRental(Long id){
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Wypozyczenie nie istnieje"));
        if(rental.getReturnDate()==null) {
            rental.setReturnDate(LocalDateTime.now());
        }
        else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Wypozyczenie zostalo juz zakonczone");
        }

        rentalRepository.save(rental);

    }

    /**
     * pobiera wypozyczenie po id
     * @param id id wypozyczenia
     * @return <code>Rental</code> wypozyczenie
     */
    public Rental getRentalsById(Long id){
        return rentalRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Wypozyczenie nie istnieje"));
    }
}
