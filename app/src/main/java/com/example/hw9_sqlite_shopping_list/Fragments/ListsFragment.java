package com.example.hw9_sqlite_shopping_list.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw9_sqlite_shopping_list.Adapters.ListsAdapter;
import com.example.hw9_sqlite_shopping_list.Helpers.DaoManager;
import com.example.hw9_sqlite_shopping_list.Models.DAOs.ListModelDao;
import com.example.hw9_sqlite_shopping_list.Models.ListModel;
import com.example.hw9_sqlite_shopping_list.R;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ListsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListModelDao listModelDao = new DaoManager(getContext()).getListModelDao();

        CompletableFuture<List<ListModel>> futureListNames =
                CompletableFuture.supplyAsync(listModelDao::getAllLists);

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        try {
            List<ListModel> listModels = futureListNames.get();

            TextView textView = view.findViewById(R.id.list_fragment_text_view);
            textView.setText(R.string.shoppingLists);

            ListsAdapter myListAdapter = new ListsAdapter(listModels,
                    position -> {
                        ListModel listModel = listModels.get(position);

                        ProductsFragment productsFragment =
                                ProductsFragment.getInstance(listModel.getId());
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_fragment, productsFragment)
                                .addToBackStack(null)
                                .commit();
                    });

            RecyclerView recyclerView = view.findViewById(R.id.list_fragment_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(myListAdapter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return view;
    }
}
