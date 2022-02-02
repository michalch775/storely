package com.example.storelyServer.category;

import com.example.storelyServer.itemTemplate.ItemTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private Set<ItemTemplate> itemTemplates = new HashSet<>();

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<ItemTemplate> getItemTemplates() {
        return itemTemplates;
    }

    public Category() {
    }
}
