package com.example.smartRecordServer.templates;

public class ReturnId<T> {
    private T id;

    public ReturnId(T toReturn) {
        this.id = toReturn;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
