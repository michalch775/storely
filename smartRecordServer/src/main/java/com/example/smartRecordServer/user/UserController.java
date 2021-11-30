package com.example.smartRecordServer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

}
