package com.example.smartRecordServer.user;

import com.example.smartRecordServer.item.Item;
import com.example.smartRecordServer.item.ItemService;
import com.example.smartRecordServer.rental.Rental;
import com.example.smartRecordServer.rental.RentalPostDto;
import com.example.smartRecordServer.rental.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path="/user")
public class UserController {

    private final UserService userService;
    private final RentalService rentalService;
    private final ItemService itemService;

    @Autowired
    public UserController(UserService userService, RentalService rentalService, ItemService itemService) {
        this.userService = userService;
        this.rentalService = rentalService;
        this.itemService = itemService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<User> listUsers(){
        return userService.getUsers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "{userId}")
    public User getUser(@PathVariable Long userId){
        return userService.getUser(userId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void registerNewUser(@RequestBody User user){
        userService.addNewUser(user);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_WAREHOUSEMAN')")
    public void editUserInfo(@RequestBody User user){
        //TODO:inna klasa czy zostawic tak

        User userToUpdate = new User();
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userService.updateUserByEmail(authentication.getName(), userToUpdate);
    }


    @PutMapping(path = "{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void editUser(@PathVariable Long userId, @RequestBody User user){
        userService.updateUserById(userId, user);
    }

    @DeleteMapping(path = "{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @PostMapping("rental/{code}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_WAREHOUSEMAN')")
    public void addNewUserRental(@PathVariable Long code,
                                 @RequestParam Integer quantity){
        Item item = itemService.getItemByCode(code);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByEmail(authentication.getName());
        Rental rental = new Rental(quantity, user, item );
        rentalService.addNewRental(rental);
    }

    @GetMapping("rental")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_WAREHOUSEMAN')")
    public List<Rental> getUserRentals(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return rentalService.getRentalsByUserEmail(authentication.getName());
    }

    @DeleteMapping("rental/{rentalId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_WAREHOUSEMAN')")
    public void getUserRentals(@PathVariable Long rentalId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        rentalService.closeRental(rentalId);
    }

}
