package com.example.cleanarchitecture.task.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanarchitecture.R;
import com.example.cleanarchitecture.common.event.OnItemClickListener;
import com.example.cleanarchitecture.data.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hwang on 2018-03-26.
 *
 * Description :
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private List<Model.User> items = new ArrayList<>();

    private OnItemClickListener<Model.User> onItemClick;

    public SearchAdapter(Context context) {
        this.context = context;
    }
    protected View inflate(int resourceId, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(resourceId, viewGroup, false);
    }

    public SearchAdapter setOnItemClick(OnItemClickListener<Model.User> onItemClick) {
        this.onItemClick = onItemClick;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflate(R.layout.item_user_search_row, parent));
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setItems(List<Model.User> items) {
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtAge;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtName = itemView.findViewById(R.id.txt_name);
            this.txtAge = itemView.findViewById(R.id.txt_age);

            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClick(v, getAdapterPosition(), (items == null ? null : items.get(getAdapterPosition())));
                }
            });
        }
        public void onItemClick(View v, int position, Model.User item) {
            if (onItemClick != null) {
                onItemClick.onItemClick(v, position, item);
            }
        }
        public void bind(Model.User item) {
            txtName.setText(item.getName());
            txtAge.setText(String.format(Locale.getDefault(), "%d", item.getAge()));
        }
    }
}
