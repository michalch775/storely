package com.example.smartRecordServer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUser(userId);
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void registerNewUser(@RequestBody User user){
        userService.addNewUser(user);
    }
}
