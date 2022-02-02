package com.example.storelyServer.user;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EntityManager centityManager, EntityManager entityManager) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return userRepository.findUserByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Nie znaleziono uzytkowika"));
    }

    public List<User> getUserSearch(String word){
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(User.class)
                .get();
        Query foodQuery = qb.keyword().onFields("name","surname","email").matching(word).createQuery();
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(foodQuery, User.class);
        return (List<User>) fullTextQuery.getResultList();
    }


    public List<User> getUsers(Integer offset, String searchText) {

//        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(centityManager);
//        QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(User.class).get();
//        Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1).onFields("name")
//                .matching(searchText).createQuery();
//
//        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, User.class);
//
//        // execute search
//
//        List<User> users = null;
//        try {
//            users = jpaQuery.getResultList();
//        } catch (NoResultException nre) {
//            ;// do nothing
//
//        }



        List<User> users = userRepository.findAll().stream()
                .filter(x -> x.getName() != null && x.getName().contains(searchText)).collect(Collectors.toList());

        if (offset * 10 + 10 < users.size()) {
            return users.subList(offset * 10, offset * 10 + 10);
        } else if (offset * 10 < users.size()) {
            return users.subList(offset * 10, users.size());
        }
        else{
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"To już wszystko");
        }
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
