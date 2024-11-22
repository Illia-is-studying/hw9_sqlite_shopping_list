package com.example.hw9_sqlite_shopping_list.Helpers;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hw9_sqlite_shopping_list.Models.DAOs.ListModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ProductModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.TypeModelDao;
import com.example.hw9_sqlite_shopping_list.Models.ListModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModel;
import com.example.hw9_sqlite_shopping_list.Models.TypeModel;

@Database(entities = {ListModel.class, TypeModel.class, ProductModel.class}, version = 3)
public abstract class DatabaseManager extends RoomDatabase {
    public abstract ListModelDao listModelDao();
    public abstract TypeModelDao typeModelDao();
    public abstract ProductModelDao productModelDao();

    private static DatabaseManager instance;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DatabaseManager.class,
                            "database_manager")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
