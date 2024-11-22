package com.example.hw9_sqlite_shopping_list.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ProductModelWithDetails {
    @Embedded
    public ProductModel productModel;

    @Relation(parentColumn = "listId", entityColumn = "id")
    public ListModel listModel;

    @Relation(parentColumn = "typeId", entityColumn = "id")
    public TypeModel typeModel;
}
