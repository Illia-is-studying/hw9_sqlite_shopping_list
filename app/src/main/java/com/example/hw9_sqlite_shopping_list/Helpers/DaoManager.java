package com.example.hw9_sqlite_shopping_list.Helpers;

import android.content.Context;

import com.example.hw9_sqlite_shopping_list.Models.DAOs.ListModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ProductModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.TypeModelDao;

public class DaoManager {
    private ListModelDao listModelDao;
    private TypeModelDao typeModelDao;
    private ProductModelDao productModelDao;

    public DaoManager(Context context) {
        DatabaseManager databaseManager = DatabaseManager.getInstance(context);

        //new Thread(databaseManager::clearAllTables).start();

        listModelDao = databaseManager.listModelDao();
        typeModelDao = databaseManager.typeModelDao();
        productModelDao = databaseManager.productModelDao();
    }

    public ListModelDao getListModelDao() {
        return listModelDao;
    }

    public TypeModelDao getTypeModelDao() {
        return typeModelDao;
    }

    public ProductModelDao getProductModelDao() {
        return productModelDao;
    }
}
