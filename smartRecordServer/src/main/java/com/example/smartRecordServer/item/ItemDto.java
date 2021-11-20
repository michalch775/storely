package com.example.smartRecordServer.item;

import com.example.smartRecordServer.itemTemplate.ItemTemplate;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ItemDto {

    private Integer quantity;
    private Long code;
    private Long itemTemplateId;
    private ItemTemplate itemTemplate;

    public ItemDto() {
    }

    public ItemDto( Integer quantity, Long code, Long itemTemplateId, ItemTemplate itemTemplate) {
        this.quantity = quantity;
        this.code = code;
        this.itemTemplateId = itemTemplateId;
        this.itemTemplate = itemTemplate;
    }

    public ItemDto(Integer quantity, Long code, Long itemTemplateId) {
        this.quantity = quantity;
        this.code = code;
        this.itemTemplateId = itemTemplateId;
    }

    public ItemDto(Integer quantity, Long code, ItemTemplate itemTemplate) {
        this.quantity = quantity;
        this.code = code;
        this.itemTemplate = itemTemplate;
    }



    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getItemTemplateId() {
        return itemTemplateId;
    }

    public void setItemTemplateId(Long itemTemplateId) {
        this.itemTemplateId = itemTemplateId;
    }

    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }

    public void setItemTemplate(ItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }
}
