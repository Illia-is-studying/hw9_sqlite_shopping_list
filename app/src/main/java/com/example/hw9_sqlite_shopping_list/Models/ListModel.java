package com.example.hw9_sqlite_shopping_list.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lists")
public class ListModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int date;

    private String name;
    private String description;

    public ListModel(String name, String description, int date) {
        this.date = date;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
