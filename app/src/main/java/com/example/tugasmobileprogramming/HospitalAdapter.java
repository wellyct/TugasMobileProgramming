package com.example.tugasmobileprogramming;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {
    private final List<Model> hospitals;
    private final List<Model> filteredHospitals;
    private final Context context;

    public HospitalAdapter(List<Model> hospitals, Context context) {
        this.hospitals = hospitals;
        this.filteredHospitals = new ArrayList<>(hospitals);
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query) {
        filteredHospitals.clear();
        if (query.isEmpty()) {
            filteredHospitals.addAll(hospitals);
        } else {
            String filterText = query.toLowerCase();
            for (Model hospital : hospitals) {
                if (hospital.getName().toLowerCase().contains(filterText) || hospital.getAddress().toLowerCase().contains(filterText)) {
                    filteredHospitals.add(hospital);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rumah_sakit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model hospital = filteredHospitals.get(position);

        holder.tvName.setText(hospital.getName());
        holder.tvAddress.setText(hospital.getAddress());

        holder.itemView.setOnClickListener(v -> {
            String name = hospital.getName();
            String address = hospital.getAddress();
            String region = hospital.getRegion();
            String phone = hospital.getPhone();
            String province = hospital.getProvince();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("address", address);
            intent.putExtra("region", region);
            intent.putExtra("phone", phone);
            intent.putExtra("province", province);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredHospitals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }
    }
}

