package com.example.smartRecordServer.itemTemplate;

import com.example.smartRecordServer.item.Item;
import com.example.smartRecordServer.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTemplateService {
    private final ItemTemplateRepository itemTemplateRepository;

    @Autowired
    public ItemTemplateService(ItemTemplateRepository itemTemplateRepository) {
        this.itemTemplateRepository = itemTemplateRepository;
    }

    public List<ItemTemplate> getItemsTemplates() {
        return itemTemplateRepository.findAll();
    }

    public void addNewItemTemplate(ItemTemplate itemTemplate){
        System.out.println(itemTemplate);
    }
}
