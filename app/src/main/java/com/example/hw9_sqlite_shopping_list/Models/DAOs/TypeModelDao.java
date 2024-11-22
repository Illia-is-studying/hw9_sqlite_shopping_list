package com.example.hw9_sqlite_shopping_list.Models.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hw9_sqlite_shopping_list.Models.TypeModel;
import java.util.List;

@Dao
public interface TypeModelDao {
    @Insert
    void insert(TypeModel model);

    @Update
    void update(TypeModel model);

    @Delete
    void delete(TypeModel model);

    @Query("SELECT * FROM types")
    List<TypeModel> getAllTypes();

    @Query("SELECT * FROM types WHERE id = :id")
    TypeModel getTypeById(int id);
}
