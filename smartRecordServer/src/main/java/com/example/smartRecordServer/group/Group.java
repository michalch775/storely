package com.example.smartRecordServer.group;


import com.example.smartRecordServer.item.Item;
import com.example.smartRecordServer.itemTemplate.ItemTemplate;
import com.example.smartRecordServer.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="appGroup")
public class Group {
    @Id
    @SequenceGenerator(
            name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "group_sequence"
    )

    private Long id;
    private String name;

    @ManyToMany(
            mappedBy = "groups"
    )
    private Set<ItemTemplate> items = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "id")
    private Set<User> users = new HashSet<>();

    public Group(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<ItemTemplate> getItems() {
        return items;
    }
}
