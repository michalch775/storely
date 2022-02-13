package com.example.storelyServer.item;

import com.example.storelyServer.user.User;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService  {

    private final ItemRepository itemRepository;
    private final EntityManager entityManager;

    @Autowired
    public ItemService(ItemRepository itemRepository, EntityManager entityManager) {
        this.itemRepository = itemRepository;
        this.entityManager = entityManager;
    }


    public List<Item> getItems(String word, Integer offset) {
        SearchSession searchSession = Search.session(entityManager);
        if(word.length()>0) {
            SearchResult<Item> result = searchSession.search(Item.class)
                    .where(f -> f.match()
                            .fields("itemTemplate.name", "itemTemplate.model","itemTemplate.category.name",
                                    "itemTemplate.groups.name") //TODO:dodac grupe
                            .matching(word)
                            .fuzzy(2))
                    .fetch(offset, 10);

            List<Item> hits = result.hits();

            return hits;
        }
        else{
            SearchResult<Item> result = searchSession.search(Item.class)
                    .where(f->f.matchAll())
                    .fetch(offset, 10);

            List<Item> hits = result.hits();

            return hits;
        }
        //return itemRepository.findAll();
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
