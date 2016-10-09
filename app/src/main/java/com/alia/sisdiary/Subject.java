package com.alia.sisdiary;

public class Subject {
    private String name;
    public static final Subject[] subjects = {
            new Subject("Математика"),
            new Subject("Фіз-ра"),
            new Subject("Укр. мова"),
            new Subject("Біологія"),
            new Subject("Праця")
    };

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
