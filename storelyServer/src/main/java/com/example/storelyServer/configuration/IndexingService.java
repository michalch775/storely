package com.example.storelyServer.configuration;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class IndexingService {

    private final EntityManager em;

    @Autowired
    public IndexingService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void initiateIndexing() throws InterruptedException {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
        fullTextEntityManager.createIndexer().startAndWait();
    }
}
