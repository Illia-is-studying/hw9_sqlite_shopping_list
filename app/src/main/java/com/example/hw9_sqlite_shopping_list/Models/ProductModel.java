package com.example.hw9_sqlite_shopping_list.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "products",
        foreignKeys = {
                @ForeignKey(
                        entity = ListModel.class,
                        parentColumns = "id",
                        childColumns = "listId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(
                        entity = TypeModel.class,
                        parentColumns = "id",
                        childColumns = "typeId",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class ProductModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int listId;
    private int typeId;
    private int checked;

    private float count;
    private String name;

    public ProductModel(int listId, int typeId, int checked, float count, String name) {
        this.listId = listId;
        this.typeId = typeId;
        this.checked = checked;
        this.count = count;
        this.name = name;
    }


    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
