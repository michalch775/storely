package com.example.storelyServer.itemView;


import com.example.storelyServer.item.ItemSortBy;
import com.example.storelyServer.itemTemplate.ItemTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/itemview")
public class itemViewController {

    private final ItemViewService itemViewService;
    private final ItemTemplateService itemTemplateService;

    @Autowired
    public itemViewController(ItemViewService itemViewService, ItemTemplateService itemTemplateService) {
        this.itemViewService = itemViewService;
        this.itemTemplateService = itemTemplateService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE','ROLE_WAREHOUSEMAN')")
    @GetMapping
    public List<ItemView> show(@RequestParam(required = false, defaultValue = "") String search,
                           @RequestParam(required = false, defaultValue = "0") Integer offset,
                           @RequestParam(required = false, defaultValue = "ADDED") ItemSortBy sort){
        return itemViewService.getItems(search, offset, sort);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "{itemId}")
    public ItemView getItem(@PathVariable Long itemId){
        return itemViewService.getItemById(itemId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "{itemId}")
    public void removeItem(@PathVariable Long itemId){
        System.out.println("123123123");
        itemTemplateService.removeItemTemplate(itemId);
    }
}
