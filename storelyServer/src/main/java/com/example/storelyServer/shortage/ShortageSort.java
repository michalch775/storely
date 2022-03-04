package com.example.storelyServer.shortage;

import org.hibernate.search.engine.search.sort.dsl.SortOrder;

public enum ShortageSort {
    ADDED,
    QUANTITY,
    NAME,
    CRITICAL,
    COVER,
    AVG;


    private String value;


    public String getValue(){
        switch (this){
            case QUANTITY:
                return "quantity";
            case COVER:
                return "cover";
            case NAME:
                return "name_sort";
            case CRITICAL:
                return "criticalQuantity";
            case AVG:
                return "averageRentals";
            case ADDED:
            default:
                return "added";
        }
    }

    public SortOrder getOrder(){
        switch (this){
            case ADDED:
            case QUANTITY:
            case NAME:
                return SortOrder.DESC;
            case CRITICAL:
            case AVG:
            case COVER:
            default:
                return SortOrder.ASC;
        }
    }
}
