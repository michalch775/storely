package com.example.smartRecordServer.templates;

public class ResponseId<T> {
    private T id;

    public ResponseId(T toReturn) {
        this.id = toReturn;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
