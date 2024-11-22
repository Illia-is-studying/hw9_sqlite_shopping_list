package com.example.hw9_sqlite_shopping_list.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "types")
public class TypeModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String label;
    private String rule;

    public TypeModel(String label, String rule) {
        this.label = label;
        this.rule = rule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
