package com.example.smartRecordServer.rental;

import com.example.smartRecordServer.user.User;
import com.example.smartRecordServer.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public void rent(Rental rental){
        System.out.println(rental);
    }
}
