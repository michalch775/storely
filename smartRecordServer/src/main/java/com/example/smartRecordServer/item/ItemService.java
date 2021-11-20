package com.example.smartRecordServer.item;

import com.example.smartRecordServer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(Long itemId){
        return itemRepository.findById(itemId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Przedmiot nie istnieje"));
    }
    public Item getItemByCode(Long code){
        return itemRepository.findItemByCode(code)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Przedmiot nie istnieje"));
    }

    public Long addNewItem(Item item){
        if(item.getId()!=null) {
            Optional<Item> itemById = itemRepository
                    .findById(item.getId());
            if (itemById.isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ten przedmiot już istnieje");
            }
        }
        return itemRepository.save(item).getId();
    }


}
