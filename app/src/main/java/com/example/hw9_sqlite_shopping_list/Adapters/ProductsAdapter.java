package com.example.hw9_sqlite_shopping_list.Adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw9_sqlite_shopping_list.Models.ProductModelWithDetails;
import com.example.hw9_sqlite_shopping_list.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyListViewHolder> {
    private List<ProductModelWithDetails> productModels;

    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ProductsAdapter(List<ProductModelWithDetails> productModels,
                           OnItemClickListener clickListener) {
        this.productModels = productModels;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item, parent, false);

        return new MyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {
        ProductModelWithDetails productModelWithDetails = productModels.get(position);
        int id = productModelWithDetails.productModel.getId();
        float count = productModelWithDetails.productModel.getCount();
        String name = productModelWithDetails.productModel.getName();
        String type = productModelWithDetails.typeModel.getLabel();
        String extraInfo = String.format("%.0f", count) + " " + type;

        holder.shortTextView.setText(name);
        holder.extraInfo.setText(extraInfo);
        holder.extraInfo.setTag(id);
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public static class MyListViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        public TextView shortTextView;
        public TextView extraInfo;

        public MyListViewHolder(@NonNull View itemView) {
            super(itemView);
            shortTextView = itemView.findViewById(R.id.item_short_text);
            extraInfo = itemView.findViewById(R.id.item_extra_info);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            menu.add(this.getAdapterPosition(), 1, 0, "Edit");
            menu.add(this.getAdapterPosition(), 2, 1, "Delete");
        }
    }

}
