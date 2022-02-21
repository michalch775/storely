package com.example.storelyServer.item;

public enum ItemSortBy {
    ADDED,
    QUANTITY,
    NAME;
    private String value;


    public String getValue(){
        if(this.name() == String.valueOf(this.ADDED)){
            return "added";
        }
        else if(this.name() == String.valueOf(this.QUANTITY)){
            return "quantity";
        }
        else if(this.name() == String.valueOf(this.NAME)){
            return "itemTemplate.name_sort";
        }
        else{
            return "added";
        }
    }
}
