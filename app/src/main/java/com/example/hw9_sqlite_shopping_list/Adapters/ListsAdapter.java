package com.example.hw9_sqlite_shopping_list.Adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw9_sqlite_shopping_list.Models.ListModel;
import com.example.hw9_sqlite_shopping_list.R;

import java.util.Date;
import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListsViewHolder> {
    private List<ListModel> listModels;
    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ListsAdapter(List<ListModel> listModels, OnItemClickListener clickListener) {
        this.listModels = listModels;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_item, parent, false);

        return new ListsAdapter.ListsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsViewHolder holder, int position) {
        ListModel listModel = listModels.get(position);
        int id = listModel.getId();
        String name = listModel.getName();
        long dateMilliseconds = listModel.getDate() * 1000L;
        Date date = new Date(dateMilliseconds);

        holder.shortTextView.setText(name);
        holder.extraInfo.setText(date.toLocaleString());
        holder.extraInfo.setTag(id);
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    public static class ListsViewHolder extends RecyclerView.ViewHolder {
        public TextView shortTextView;
        public TextView extraInfo;

        public ListsViewHolder(@NonNull View itemView) {
            super(itemView);
            shortTextView = itemView.findViewById(R.id.item_short_text);
            extraInfo = itemView.findViewById(R.id.item_extra_info);
        }
    }
}
