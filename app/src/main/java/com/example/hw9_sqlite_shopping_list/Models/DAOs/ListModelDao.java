package com.example.hw9_sqlite_shopping_list.Models.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hw9_sqlite_shopping_list.Models.ListModel;

import java.util.List;

@Dao
public interface ListModelDao {
    @Insert
    void insert(ListModel model);

    @Update
    void update(ListModel model);

    @Delete
    void delete(ListModel model);

    @Query("SELECT * FROM lists")
    List<ListModel> getAllLists();

    @Query("SELECT * FROM lists WHERE id = :id")
    ListModel getListById(int id);

    @Query("SELECT * FROM lists WHERE name = :name")
    ListModel getListByName(String name);
}