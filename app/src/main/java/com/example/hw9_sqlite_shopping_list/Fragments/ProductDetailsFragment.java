package com.example.hw9_sqlite_shopping_list.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hw9_sqlite_shopping_list.Helpers.DaoManager;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ListModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ProductModelDao;
import com.example.hw9_sqlite_shopping_list.Models.ListModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModelWithDetails;
import com.example.hw9_sqlite_shopping_list.Models.TypeModel;
import com.example.hw9_sqlite_shopping_list.R;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ProductDetailsFragment extends Fragment {
    private static final String PRODUCT_ID = "product_id";

    public static ProductDetailsFragment getInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID, id);

        ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
        productDetailsFragment.setArguments(bundle);
        return productDetailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.product_details_fragment, container, false);

        TextView productNameTextView = view.findViewById(R.id.product_name);
        TextView countTextView = view.findViewById(R.id.product_count);
        TextView typeTextView = view.findViewById(R.id.product_type);
        TextView listNameTextView = view.findViewById(R.id.list_name);
        TextView descriptionTextView = view.findViewById(R.id.list_description);
        TextView creationDateTextView = view.findViewById(R.id.creation_date);

        if(getArguments() != null) {
            int id = getArguments().getInt(PRODUCT_ID);

            ProductModelDao productModelDao = new DaoManager(getContext()).getProductModelDao();
            CompletableFuture<ProductModelWithDetails> futureProduct =
                    CompletableFuture.supplyAsync(() -> productModelDao.getAllProductsWithDetails()
                            .stream()
                            .filter(pmwd -> pmwd.productModel.getId() == id)
                            .collect(Collectors.toList()).get(0));

            try  {
                ProductModelWithDetails productModelWithDetails = futureProduct.get();
                ProductModel productModel = productModelWithDetails.productModel;
                TypeModel typeModel = productModelWithDetails.typeModel;
                ListModel listModel = productModelWithDetails.listModel;

                String productName = "Product Name: " + productModel.getName();
                String count = "Quantity: " + productModel.getCount();
                String type = "Type: " + typeModel.getLabel();
                String listName = "List Name: " + listModel.getName();
                String creationDate = "Creation Date: " + new Date(listModel.getDate() * 1000L);
                String description = listModel.getDescription();

                productNameTextView.setText(productName);
                countTextView.setText(count);
                typeTextView.setText(type);
                listNameTextView.setText(listName);
                creationDateTextView.setText(creationDate);
                descriptionTextView.setText(description);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
