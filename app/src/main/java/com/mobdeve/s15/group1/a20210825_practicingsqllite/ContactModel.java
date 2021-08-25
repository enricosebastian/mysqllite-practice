package com.mobdeve.s15.group1.a20210825_practicingsqllite;

public class ContactModel {
    String name, date;

    public String getName() {
        return name;
    }

    public ContactModel(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContactModel{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
