package com.example.smartRecordServer.item;

import com.example.smartRecordServer.itemTemplate.ItemTemplate;
import com.example.smartRecordServer.itemTemplate.ItemTemplateService;
import com.example.smartRecordServer.rental.Rental;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
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

    @JsonIgnore
    private Long code;


    @Column(name = "itemTemplateId", insertable = false, updatable = false)
    private Long itemTemplateId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemTemplateId", referencedColumnName = "id")
    private ItemTemplate itemTemplate;

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private Set<Rental> rentals = new HashSet<>();

    public Item(Integer quantity, Long code, ItemTemplate itemTemplate) {
        this.quantity = quantity;
        this.code = code;
        this.itemTemplate = itemTemplate;
    }

    public Item(Long id, Integer quantity, Long code, Long itemTemplateId, ItemTemplate itemTemplate, Set<Rental> rentals) {
        this.id = id;
        this.quantity = quantity;
        this.code = code;
        this.itemTemplateId = itemTemplateId;
        this.itemTemplate = itemTemplate;
        this.rentals = rentals;
    }

    public Item() {
    }

    public Long getItemTemplateId() {
        return itemTemplateId;
    }

    public void setItemTemplateId(Long itemTemplateId) {
        this.itemTemplateId = itemTemplateId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }

    public void setItemTemplate(ItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    public Set<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Set<Rental> rentals) {
        this.rentals = rentals;
    }
}