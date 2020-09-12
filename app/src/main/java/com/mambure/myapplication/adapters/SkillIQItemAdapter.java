package com.mambure.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mambure.myapplication.R;
import com.mambure.myapplication.models.SkillsIQItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkillIQItemAdapter extends RecyclerView.Adapter<SkillIQItemAdapter.ViewHolder> {
    private List<SkillsIQItem> list;

    public SkillIQItemAdapter() {
        this.list = new ArrayList<>();
    }

    public void addData(List<SkillsIQItem> items) {
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

        public void bind(final SkillsIQItem model) {
            txtName.setText(model.getName());
            txtCountry.setText(model.getCountry());
            txtValue.setText(String.valueOf(model.getScore()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skilliq_leader, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SkillsIQItem item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}