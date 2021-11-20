package com.example.smartRecordServer.templates;

public class ReturnVariable<T> {
    private T toReturn;

    public ReturnVariable(T toReturn) {
        this.toReturn = toReturn;
    }

    public T getToReturn() {
        return toReturn;
    }

    public void setToReturn(T toReturn) {
        this.toReturn = toReturn;
    }
}
