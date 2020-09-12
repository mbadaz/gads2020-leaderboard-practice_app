package com.mambure.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mambure.myapplication.R;
import com.mambure.myapplication.models.HourItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HoursItemAdapter extends RecyclerView.Adapter<HoursItemAdapter.ViewHolder> {
    private List<HourItem> list;

    public HoursItemAdapter() {
        this.list = new ArrayList<>();
    }

    public void addData(List<HourItem> items) {
        list = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_value)
        TextView txtValue;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_country)
        TextView txtCountry;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final HourItem model) {
            txtName.setText(model.getName());
            txtCountry.setText(model.getCountry());
            txtValue.setText(String.valueOf(model.getHours()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_leader, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HourItem item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}