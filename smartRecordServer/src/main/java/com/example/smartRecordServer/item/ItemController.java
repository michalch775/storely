package com.example.smartRecordServer.item;

import com.example.smartRecordServer.itemTemplate.ItemTemplate;
import com.example.smartRecordServer.itemTemplate.ItemTemplateService;
import com.example.smartRecordServer.templates.ReturnId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path="/item")
public class ItemController {

    private final ItemService itemService;
    private final ItemTemplateService itemTemplateService;

    @Autowired
    public ItemController(ItemService itemService, ItemTemplateService itemTemplateService) {
        this.itemService = itemService;
        this.itemTemplateService = itemTemplateService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_WAREHOUSEMAN')")
    @GetMapping
    public List<Item> show(){
        return itemService.getItems();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_WAREHOUSEMAN')")
    @GetMapping(path = "{itemId}")
    public Item getUser(@PathVariable Long itemId,
                        @RequestParam(required = false) boolean byCode){
        if(byCode){
            return itemService.getItemByCode(itemId);
        }
        return itemService.getItemById(itemId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ReturnId<Long> addItem(@RequestBody ItemDto itemDto){ //TODO: dto czy bez
        if(itemDto.getItemTemplateId()!=null && itemDto.getItemTemplate()!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if (itemDto.getItemTemplate() != null) {
            Item item = new Item(itemDto.getQuantity(), itemDto.getCode(), itemDto.getItemTemplate());
            return new ReturnId<Long>( itemService.addNewItem(item) );
        }
        else if (itemDto.getItemTemplateId() != null) {
            ItemTemplate itemTemplate= itemTemplateService.getItemTemplate(itemDto.getItemTemplateId());
            Item item = new Item(itemDto.getQuantity(), itemDto.getCode(), itemTemplate);
            return new ReturnId<Long>( itemService.addNewItem(item) );
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
