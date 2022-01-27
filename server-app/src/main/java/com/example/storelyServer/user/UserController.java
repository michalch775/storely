package com.example.storelyServer.user;

import com.example.storelyServer.item.Item;
import com.example.storelyServer.item.ItemService;
import com.example.storelyServer.rental.Rental;
import com.example.storelyServer.rental.RentalService;
import com.example.storelyServer.templates.ResponseId;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @PostMapping("rental")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_WAREHOUSEMAN')")
    public ResponseId<Long> addNewUserRental(@RequestBody ObjectNode objectNode){

        if(!objectNode.has("code")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Long code = objectNode.path("code").asLong();
        Integer quantity = objectNode.path("quantity").asInt(1);

        Item item = itemService.getItemByCode(code);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.getUserByEmail(authentication.getName());
        Rental rental = new Rental(quantity, user, item );

        if(item.getItemTemplate().getGroups().contains(user.getGroup())){
            return new ResponseId<Long>(rentalService.addNewRental(rental));
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Uzytkownik nie jest w dozwolonej grupie");
        }

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
