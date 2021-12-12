package com.example.smartRecordServer.rental;

import com.example.smartRecordServer.category.Category;
import com.example.smartRecordServer.item.Item;
import com.example.smartRecordServer.user.User;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.ui.Model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public class Rental {
    @Id
    @SequenceGenerator(
            name = "rental_sequence",
            sequenceName = "rental_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rental_sequence"
    )
    private Long id;

    private LocalDateTime rentDate = LocalDateTime.now();
    private LocalDateTime returnDate;
    private boolean isReturnable;
    private Integer quantity;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId", referencedColumnName = "id")
    private Item item;

    public Rental(Integer quantity, User user, Item item) {
        this.quantity = quantity;
        this.user = user;
        this.item = item;
    }

    public Rental(Long id, LocalDateTime rentDate, LocalDateTime returnDate, boolean isReturnable, Integer quantity, User user, Item item) {
        this.id = id;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.isReturnable = isReturnable;
        this.quantity = quantity;
        this.user = user;
        this.item = item;
    }

    public Rental() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getRentDate() {
        return rentDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public boolean isReturnable() {
        return isReturnable;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public User getUser() {
        return user;
    }

    public Item getItem() {
        return item;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRentDate(LocalDateTime rentDate) {
        this.rentDate = rentDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturnable(boolean returnable) {
        isReturnable = returnable;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
