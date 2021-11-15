package com.example.smartRecordServer.item;

import com.example.smartRecordServer.user.User;
import com.example.smartRecordServer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> show(){
        return itemService.getItems();
    }

    @PostMapping
    public void registerNewUser(@RequestBody Item item){
        itemService.addNewItem(item);
    }
}
