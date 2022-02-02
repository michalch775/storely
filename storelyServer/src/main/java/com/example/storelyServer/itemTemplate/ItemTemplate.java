package com.example.storelyServer.itemTemplate;

import com.example.storelyServer.category.Category;
import com.example.storelyServer.group.Group;
import com.example.storelyServer.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity
public class ItemTemplate {
    @Id
    @SequenceGenerator(
            name = "item_template_sequence",
            sequenceName = "item_template_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_template_sequence"
    )
    private Long id;
    private String name;
    private String description; //TODO: as varchar 5000 signs
    private boolean isReturnable = true;
    private Time timeLimit;
    private Integer criticalQuantity;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;


    @ManyToMany
    @JoinTable(
            name = "itemGroup",
            joinColumns = @JoinColumn(name = "groupId"),
            inverseJoinColumns = @JoinColumn(name = "itemId")
    )
    private Set<Group> groups = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private Set<Item> items = new HashSet<>();


    public ItemTemplate(Long id, String name,
                        String description, boolean isReturnable,
                        Time timeLimit, Integer criticalQuantity,
                        Category category, Set<Group> groups, Set<Item> items) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isReturnable = isReturnable;
        this.timeLimit = timeLimit;
        this.criticalQuantity = criticalQuantity;
        this.category = category;
        this.groups = groups;
        this.items = items;
    }

    public ItemTemplate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(boolean returnable) {
        isReturnable = returnable;
    }

    public Time getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Time timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getCriticalQuantity() {
        return criticalQuantity;
    }

    public void setCriticalQuantity(Integer criticalQuantity) {
        this.criticalQuantity = criticalQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}