package com.example.smartRecordServer.item;

import com.example.smartRecordServer.itemTemplate.ItemTemplate;
import com.example.smartRecordServer.rental.Rental;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Item {
    @Id
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    private Long id;
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="itemId", referencedColumnName = "id")
    private ItemTemplate itemTemplate;

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private Set<Rental> rentals = new HashSet<>();



    public Item(Integer quantity, ItemTemplate itemTemplate) {
        this.quantity = quantity;
        this.itemTemplate = itemTemplate;
    }

    public Item(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }
}
