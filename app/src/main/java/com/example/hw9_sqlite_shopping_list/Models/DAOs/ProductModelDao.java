package com.example.hw9_sqlite_shopping_list.Models.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hw9_sqlite_shopping_list.Models.ListModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModelWithDetails;
import com.example.hw9_sqlite_shopping_list.Models.TypeModel;

import java.util.List;

@Dao
public interface ProductModelDao {
    @Insert
    void insert(ProductModel model);

    @Update
    void update(ProductModel model);

    @Delete
    void delete(ProductModel model);

    @Query("SELECT * FROM products")
    List<ProductModel> getAllProducts();

    @Query("SELECT * FROM products")
    List<ProductModelWithDetails> getAllProductsWithDetails();

    @Query("SELECT * FROM products WHERE listId = :listId")
    List<ProductModelWithDetails> getAllProductsWithDetailsByListsId(int listId);

    @Query("SELECT * FROM products WHERE id = :id")
    ProductModel getProductById(int id);

    @Query("SELECT * FROM products WHERE listId = :listId")
    List<ProductModel> getAllProductsByListsId(int listId);

    @Query("SELECT * FROM products WHERE id = :id")
    ProductModelWithDetails getProductWithDetailsById(int id);
}
