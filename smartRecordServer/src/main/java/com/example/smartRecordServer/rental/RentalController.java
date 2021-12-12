package com.example.smartRecordServer.rental;

import com.example.smartRecordServer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/rental")
public class RentalController {

    private final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<Rental> listRentals(){
        return rentalService.getRentals();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{rentalId}")
    public Rental listRentals(@RequestParam Long rentalId){
        return rentalService.getRentalsById(rentalId);
    }


}
