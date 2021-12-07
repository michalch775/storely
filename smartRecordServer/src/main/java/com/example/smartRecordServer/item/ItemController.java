package com.example.smartRecordServer.item;

import com.example.smartRecordServer.itemTemplate.ItemTemplate;
import com.example.smartRecordServer.itemTemplate.ItemTemplateService;
import com.example.smartRecordServer.templates.ResponseId;
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
                        @RequestParam(required = false) String byCode){
        if(byCode=="true"){
            return itemService.getItemByCode(itemId);
        }
        return itemService.getItemById(itemId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseId<Long> addItem(@RequestBody ItemPostDto itemPostDto){ //TODO: dto czy bez

        //return new ResponseId<Long>( itemService.addNewItem(item) );

        if(itemPostDto.getItemTemplateId()!=null && itemPostDto.getItemTemplate()!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        else if (itemPostDto.getItemTemplate() != null) {
            Item item = new Item(itemPostDto.getQuantity(), itemPostDto.getCode(), itemPostDto.getItemTemplate());
            return new ResponseId<Long>( itemService.addNewItem(item) );
        }
        else if (itemPostDto.getItemTemplateId() != null) {
            ItemTemplate itemTemplate= itemTemplateService.getItemTemplate(itemPostDto.getItemTemplateId());
            Item item = new Item(itemPostDto.getQuantity(), itemPostDto.getCode(), itemTemplate);
            return new ResponseId<Long>( itemService.addNewItem(item) );
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
