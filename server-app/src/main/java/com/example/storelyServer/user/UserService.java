package com.example.storelyServer.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return userRepository.findUserByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono uzytkowika"));
    }



    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Uzytkownik nie istnieje"));
    }

    public User getUserByEmail(String email) throws UsernameNotFoundException{
        return userRepository.findUserByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono uzytkowika"));
    }


    public void addNewUser(User user){
        Optional<User> userByEmail = userRepository
                .findUserByEmail(user.getEmail());
        if(userByEmail.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Ten email jest już zajety");
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    public void updateUserByEmail(String email, User user){
        User userToEdit = userRepository.findUserByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono uzytkowika"));

        if(user.getEmail()!=null){
            userToEdit.setEmail(user.getEmail());
        }
        if(user.getPassword()!=null){
            userToEdit.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(user.getName()!=null){
            userToEdit.setName(user.getName());
        }
        if(user.getSurname()!=null){
            userToEdit.setSurname(user.getSurname());
        }
        if(user.getRole()!=null){
            //userToEdit.setRole(user.getRole());
        }
        if(user.getGroup()!=null){
            userToEdit.setGroup(user.getGroup());
        }

        userRepository.save(userToEdit);
    }

    public void updateUserById(Long id, User user){
        User userToEdit = userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono uzytkowika"));

        if(user.getEmail()!=null){
            userToEdit.setEmail(user.getEmail());
        }
        if(user.getPassword()!=null){
            userToEdit.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(user.getName()!=null){
            userToEdit.setName(user.getName());
        }
        if(user.getSurname()!=null){
            userToEdit.setSurname(user.getSurname());
        }
        if(user.getRole()!=null){
            //userToEdit.setRole(user.getRole());
        }
        if(user.getGroup()!=null){
            userToEdit.setGroup(user.getGroup());
        }

        userRepository.save(userToEdit);

    }

    public void deleteUser(Long userId){
        userRepository.findById(userId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Uzytkownik nie istnieje"));
        userRepository.deleteById(userId);
    }



}
