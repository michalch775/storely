package com.example.smartRecordServer.user;

import com.example.smartRecordServer.group.Group;
import com.example.smartRecordServer.itemTemplate.ItemTemplate;
import com.example.smartRecordServer.rental.Rental;
import com.example.smartRecordServer.security.PasswordConfig;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="AppUser")
public class User implements UserDetails {




    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private String password;
    private String surname;
    private String email;
    private LocalDateTime registered;

    private List<? extends GrantedAuthority> grantedAuthorities;
    private boolean isEnabled;



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

    public User(String name,
                String password,
                String surname,
                String email,
                List<? extends GrantedAuthority> grantedAuthorities,
                Set<Rental> rentals,
                Group group) {
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.email = email;
        this.grantedAuthorities = grantedAuthorities;
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
                List<? extends GrantedAuthority>
                        grantedAuthorities, boolean isEnabled,
                Set<Rental> rentals,
                Group group) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.email = email;
        this.registered = registered;
        this.grantedAuthorities = grantedAuthorities;
        this.isEnabled = isEnabled;
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
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
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

    public LocalDateTime getRegistered() {
        return registered;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public Group getGroup() {
        return group;
    }
}
