package com.example.smartRecordServer.user;

import com.example.smartRecordServer.group.Group;
import com.example.smartRecordServer.itemTemplate.ItemTemplate;
import com.example.smartRecordServer.rental.Rental;
import com.example.smartRecordServer.security.ApplicationUserRole;
import com.example.smartRecordServer.security.PasswordConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="AppUser")
public class User implements UserDetails {




    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;
    private String name;
    @JsonIgnore
    private String password;
    private String surname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, updatable = false)
    private LocalDateTime registered = LocalDateTime.now();

    private ApplicationUserRole role = ApplicationUserRole.EMPLOYEE;

    private boolean isEnabled = true;


    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private Set<Rental> rentals = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="groupId", referencedColumnName = "id")
    private Group group;



    public User(String name,
                String surname,
                String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public User(String name, String surname, String email, ApplicationUserRole role, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.password=password;
    }

    public User(String name,
                String password,
                String surname,
                String email,
                ApplicationUserRole role,
                Set<Rental> rentals,
                Group group) {
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.rentals = rentals;
        this.group = group;
        this.isEnabled=true;
        this.registered=LocalDateTime.now();
    }

    public User(String name,
                String surname,
                String email,
                Set<Rental> rentals,
                Group group) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.rentals = rentals;
        this.group = group;
        this.registered=LocalDateTime.now();
        //this.password= PasswordConfig.alphaNumericString(8);TODO:password generating
    }

    public User(Long id,
                String name,
                String password,
                String surname,
                String email,
                LocalDateTime registered,
                ApplicationUserRole role,
                Set<Rental> rentals,
                Group group) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.email = email;
        this.registered = registered;
        this.role = role;
        this.isEnabled = true;
        this.rentals = rentals;
        this.group = group;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public ApplicationUserRole getRole() {
        return role;
    }

    public void setRole(ApplicationUserRole role) {
        this.role = role;
    }

    public void setIsEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
