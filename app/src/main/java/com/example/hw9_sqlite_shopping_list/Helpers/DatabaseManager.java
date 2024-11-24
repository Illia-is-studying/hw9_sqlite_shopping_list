package com.example.hw9_sqlite_shopping_list.Helpers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.hw9_sqlite_shopping_list.Models.DAOs.ListModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ProductModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.TypeModelDao;
import com.example.hw9_sqlite_shopping_list.Models.ListModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModel;
import com.example.hw9_sqlite_shopping_list.Models.TypeModel;

import java.util.Date;
import java.util.concurrent.Executors;

@Database(entities = {ListModel.class, TypeModel.class, ProductModel.class}, version = 5)
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
