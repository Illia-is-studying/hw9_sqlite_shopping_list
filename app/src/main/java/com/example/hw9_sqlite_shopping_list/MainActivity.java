package com.example.hw9_sqlite_shopping_list;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hw9_sqlite_shopping_list.Fragments.ListsFragment;
import com.example.hw9_sqlite_shopping_list.Helpers.DatabaseManager;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ListModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ProductModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.TypeModelDao;
import com.example.hw9_sqlite_shopping_list.Models.ListModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModelWithDetails;
import com.example.hw9_sqlite_shopping_list.Models.TypeModel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialization();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, new ListsFragment())
                    .commit();
        }
    }

    private void initialization() {
        DatabaseManager databaseManager = DatabaseManager.getInstance(this);
        ListModelDao listModelDao = databaseManager.listModelDao();
        TypeModelDao typeModelDao = databaseManager.typeModelDao();
        ProductModelDao productModelDao = databaseManager.productModelDao();

        int[] dates = new int[3];
        dates[0] = (int) (new GregorianCalendar(2024, Calendar.SEPTEMBER, 12)
                .getTimeInMillis() / 1000);
        dates[1] = (int) (new GregorianCalendar(2024, Calendar.OCTOBER, 9)
                .getTimeInMillis() / 1000);
        dates[2] = (int) (new GregorianCalendar(2024, Calendar.OCTOBER, 27)
                .getTimeInMillis() / 1000);

        new Thread(() -> {
            ListModel listModel1 = new ListModel("List 1",
                    "Shopping at the grocery store.", dates[0]);
            ListModel listModel2 = new ListModel("List 2",
                    "Shopping at an appliance store.", dates[1]);
            ListModel listModel3 = new ListModel("List 3",
                    "Shopping at a home improvement store.", dates[2]);

            listModelDao.insert(listModel1);
            listModelDao.insert(listModel2);
            listModelDao.insert(listModel3);

            typeModelDao.insert(new TypeModel("pc", "int"));
            typeModelDao.insert(new TypeModel("kg", "float"));
            typeModelDao.insert(new TypeModel("l", "float"));

            productModelDao.insert(
                    new ProductModel(1, 2, 1,0.5f, "Product 1"));
            productModelDao.insert(
                    new ProductModel(1, 1, 0,1f, "Product 2"));
            productModelDao.insert(
                    new ProductModel(2, 1, 0,2f, "Product 3"));

            //List<ListModel> lists = listModelDao.getAllLists();
            //List<ProductModelWithDetails> products = productModelDao.getAllProductsWithDetails();
        }).start();
    }
}