package com.example.storelyServer.item;

import com.example.storelyServer.itemTemplate.ItemTemplate;
import com.example.storelyServer.rental.Rental;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.time.LocalDate.now;

@Entity
@Table
@Indexed
public class Item {

    @Id
    @SequenceGenerator(name = "item_sequence", sequenceName = "item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
    private Long id;
    private Integer quantity;

    @JsonIgnore
    @GenericField
    @Column(unique = true)
    private Long code;

    private Date added = Date.valueOf(LocalDate.now());

    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "itemTemplateId", referencedColumnName = "id")
    @IndexedEmbedded
    private ItemTemplate itemTemplate;

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private Set<Rental> rentals = new HashSet<>();


    public Item(Integer quantity, Long code, ItemTemplate itemTemplate) {
        this.quantity = quantity;
        this.code = code;
        this.itemTemplate = itemTemplate;
    }

    public Item(Long id, Integer quantity, Long code, Date added, ItemTemplate itemTemplate, Set<Rental> rentals) {
        this.id = id;
        this.quantity = quantity;
        this.code = code;
        this.added = added;
        //this.itemTemplateId = itemTemplateId;
        this.itemTemplate = itemTemplate;
        this.rentals = rentals;
    }

    public Item() {
    }

//    public Long getItemTemplateId() {
//        return itemTemplateId;
//    }

//    public void setItemTemplateId(Long itemTemplateId) {
//        this.itemTemplateId = itemTemplateId;
//    }

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