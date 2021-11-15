package com.example.smartRecordServer.itemTemplate;

import com.example.smartRecordServer.category.Category;
import com.example.smartRecordServer.group.Group;
import com.example.smartRecordServer.item.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
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
    private boolean isReturnable;
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

    public ItemTemplate(String name,
                        String description,
                        boolean isReturnable,
                        Time timeLimit,
                        Integer criticalQuantity,
                        Category category,
                        Set<Group> groups,
                        Set<Item> items) {
        this.name = name;
        this.description = description;
        this.isReturnable = isReturnable;
        this.timeLimit = timeLimit;
        this.criticalQuantity = criticalQuantity;
        this.category = category;
        this.groups = groups;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isReturnable() {
        return isReturnable;
    }

    public Time getTimeLimit() {
        return timeLimit;
    }

    public Integer getCriticalQuantity() {
        return criticalQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public Set<Item> getItems() {
        return items;
    }
}