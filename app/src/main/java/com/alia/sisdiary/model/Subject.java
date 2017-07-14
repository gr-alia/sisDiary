package com.alia.sisdiary.model;


public class Subject {
    private String name;
    private int mId;

    public Subject() {
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
