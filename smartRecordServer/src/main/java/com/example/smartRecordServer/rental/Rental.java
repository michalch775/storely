package com.example.smartRecordServer.rental;

import com.example.smartRecordServer.category.Category;
import com.example.smartRecordServer.item.Item;
import com.example.smartRecordServer.user.User;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate rentDate;
    private LocalDate returnDate;
    private boolean isReturnable;
    private Integer quantity;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itemId", referencedColumnName = "id")
    private Item item;
}
