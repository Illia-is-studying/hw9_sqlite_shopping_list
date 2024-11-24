package com.example.hw9_sqlite_shopping_list.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hw9_sqlite_shopping_list.Helpers.DaoManager;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ListModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ProductModelDao;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.TypeModelDao;
import com.example.hw9_sqlite_shopping_list.Models.ListModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModel;
import com.example.hw9_sqlite_shopping_list.Models.ProductModelWithDetails;
import com.example.hw9_sqlite_shopping_list.Models.TypeModel;
import com.example.hw9_sqlite_shopping_list.R;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EditProductFragment extends Fragment {
    private static final String PRODUCT_ID = "product_id";
    private static final String ACTION = "action";

    public static EditProductFragment getInstance(int id, String action) {
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID, id);
        bundle.putString(ACTION, action);

        EditProductFragment editProductFragment = new EditProductFragment();
        editProductFragment.setArguments(bundle);
        return editProductFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.edit_product_fragment, container, false);

        TextView productNameTextView = view.findViewById(R.id.edit_fragment_product_name);
        TextView countTextView = view.findViewById(R.id.edit_fragment_product_quantity);
        Button submitButton = view.findViewById(R.id.button_add_product);

        AutoCompleteTextView typeACTextView = view.findViewById(R.id.edit_fragment_product_type);
        AutoCompleteTextView listNameACTextView = view.findViewById(R.id.edit_fragment_list_name);
        typeACTextView.setOnClickListener(v -> typeACTextView.showDropDown());
        listNameACTextView.setOnClickListener(v -> listNameACTextView.showDropDown());

        DaoManager daoManager = new DaoManager(getContext());
        ProductModelDao productModelDao = daoManager.getProductModelDao();
        TypeModelDao typeModelDao = daoManager.getTypeModelDao();
        ListModelDao listModelDao = daoManager.getListModelDao();

        CompletableFuture<String[]> typeLabelsFuture = CompletableFuture.supplyAsync(() ->
                typeModelDao.getAllTypes()
                        .stream()
                        .map(TypeModel::getLabel)
                        .toArray(String[]::new));

        CompletableFuture<String[]> listNamesFuture = CompletableFuture.supplyAsync(() ->
                listModelDao.getAllLists()
                        .stream()
                        .map(ListModel::getName)
                        .toArray(String[]::new));

        try {
            String[] listNames = listNamesFuture.get();
            String[] typeLabels = typeLabelsFuture.get();

            ArrayAdapter<String> typeLabelAdapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    typeLabels
            );
            ArrayAdapter<String> listNameAdapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    listNames
            );

            typeACTextView.setAdapter(typeLabelAdapter);
            listNameACTextView.setAdapter(listNameAdapter);

            if (getArguments() != null) {
                int id = getArguments().getInt(PRODUCT_ID);
                String action = getArguments().getString(ACTION);

                if (action.equals("add")) {
                    listNameACTextView.setText(listNames[0], false);
                    typeACTextView.setText(typeLabels[0], false);

                    submitButton.setText(R.string.save);
                } else if (action.equals("edit")) {
                    CompletableFuture<ProductModelWithDetails> productMWDFuture = CompletableFuture
                            .supplyAsync(() ->
                                    productModelDao.getProductWithDetailsById(id));

                    try {
                        ProductModelWithDetails productMWD = productMWDFuture.get();

                        productNameTextView.setText(productMWD.productModel.getName());
                        countTextView.setText(String.valueOf(productMWD.productModel.getCount()));
                        listNameACTextView.setText(productMWD.listModel.getName(), false);
                        typeACTextView.setText(productMWD.typeModel.getLabel(), false);

                        submitButton.setText(R.string.edit);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }

                submitButton.setOnClickListener((v -> {
                    String productName = productNameTextView.getText().toString();
                    String listName = listNameACTextView.getText().toString();
                    String typeLabel = typeACTextView.getText().toString();
                    float count = Float.parseFloat(countTextView.getText().toString());

                    CompletableFuture<TypeModel> typeModelFuture = CompletableFuture
                            .supplyAsync(() -> typeModelDao.getTypeByLabel(typeLabel));
                    CompletableFuture<ListModel> listModelFuture = CompletableFuture
                            .supplyAsync(() -> listModelDao.getListByName(listName));

                    try {
                        TypeModel typeModel = typeModelFuture.get();
                        ListModel listModel = listModelFuture.get();

                        if (action.equals("add")) {
                            new Thread(() ->
                                    productModelDao.insert(new ProductModel(listModel.getId(),
                                            typeModel.getId(), 0, count, productName))
                            ).start();
                        } else if (action.equals("edit")) {
                            new Thread(() -> {
                                ProductModel productModel = new ProductModel(listModel.getId(),
                                        typeModel.getId(), 0, count, productName);
                                productModel.setId(id);

                                productModelDao.update(productModel);
                            }).start();
                        }

                        ProductsFragment productsFragment = ProductsFragment
                                .getInstance(listModel.getId());

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_fragment, productsFragment)
                                .addToBackStack(null)
                                .commit();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return view;
    }
}
