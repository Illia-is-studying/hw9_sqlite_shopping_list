package com.example.hw9_sqlite_shopping_list.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw9_sqlite_shopping_list.Adapters.ProductsAdapter;
import com.example.hw9_sqlite_shopping_list.Helpers.DaoManager;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ProductModelDao;
import com.example.hw9_sqlite_shopping_list.Models.ProductModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModelWithDetails;
import com.example.hw9_sqlite_shopping_list.R;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ProductsFragment extends Fragment {
    private static final String LIST_ID = "list_id";

    private int listId;

    public static ProductsFragment getInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(LIST_ID, id);

        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(bundle);
        return productsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProductModelDao productModelDao = new DaoManager(getContext()).getProductModelDao();

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        if (getArguments() != null) {
            listId = getArguments().getInt(LIST_ID);

            CompletableFuture<List<ProductModelWithDetails>> productFuture =
                    CompletableFuture
                            .supplyAsync(() -> productModelDao
                                    .getAllProductsWithDetailsByListsId(listId));

            try {
                List<ProductModelWithDetails> productMWD = productFuture.get();

                TextView textView = view.findViewById(R.id.list_fragment_text_view);
                if(productMWD.isEmpty()) {
                    textView.setText(R.string.productLists);
                } else {
                    textView.setText(productMWD.get(0).listModel.getName());
                }

                Button addNewButton = view.findViewById(R.id.button_add_new);
                addNewButton.setVisibility(View.VISIBLE);
                addNewButton.setOnClickListener(v -> {
                    EditProductFragment editProductFragment = EditProductFragment
                            .getInstance(0, "add");
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment, editProductFragment)
                            .addToBackStack(null)
                            .commit();
                });

                ProductsAdapter productsAdapter = new ProductsAdapter(productMWD,
                        position -> {
                            ProductModelWithDetails productModel =
                                    productMWD.get(position);

                            ProductDetailsFragment productDetailsFragment =
                                    ProductDetailsFragment
                                            .getInstance(productModel.productModel.getId());
                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.main_fragment, productDetailsFragment)
                                    .addToBackStack(null)
                                    .commit();
                        });

                RecyclerView recyclerView = view.findViewById(R.id.list_fragment_recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(productsAdapter);

                registerForContextMenu(recyclerView);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int pos = item.getGroupId();

        ProductModelDao productModelDao = new DaoManager(getContext()).getProductModelDao();
        CompletableFuture<List<ProductModel>> productFuture =
                CompletableFuture
                        .supplyAsync(() -> productModelDao
                                .getAllProductsByListsId(listId));
        ;

        ProductModel productModel = null;

        try {
            List<ProductModel> productModels = productFuture.get();
            productModel = productModels.get(pos);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }

        if (productModel != null) {
            if (item.getItemId() == 1) {
                EditProductFragment editProductFragment = EditProductFragment
                        .getInstance(productModel.getId(), "edit");
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment, editProductFragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            } else if (item.getItemId() == 2) {
                int id = productModel.getId();

                try {
                    Thread thread = new Thread(() -> {
                        ProductModel deletedProduct = productModelDao.getProductById(id);
                        productModelDao.delete(deletedProduct);
                    });

                    thread.start();
                    thread.join();

                    ProductsFragment productsFragment =
                            ProductsFragment.getInstance(productModel.getListId());
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment, productsFragment)
                            .addToBackStack(null)
                            .commit();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return true;
            }
        }

        return false;
    }
}
