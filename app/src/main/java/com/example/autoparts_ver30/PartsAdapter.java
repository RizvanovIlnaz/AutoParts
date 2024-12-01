package com.example.autoparts_ver30;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PartsAdapter extends RecyclerView.Adapter<PartsAdapter.PartViewHolder> {

    private final Context context;
    private final ArrayList<Part> partsList;

    // Конструктор для передачи данных
    public PartsAdapter(Context context, ArrayList<Part> partsList) {
        this.context = context;
        this.partsList = partsList;
    }

    @NonNull
    @Override
    public PartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_part, parent, false);
        return new PartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartViewHolder holder, int position) {
        Part part = partsList.get(position);

        // Установка значений для элементов View
        holder.partName.setText(part.getName());
        holder.partCarModel.setText(part.getCarModel());
        holder.partPrice.setText(String.format("%s руб.", part.getPrice()));

        // Загрузка изображения (если используется URL)
        Glide.with(context)
                .load(part.getPhoto())
                .placeholder(R.drawable.placeholder) // Плейсхолдер
                .into(holder.partImage);
    }

    @Override
    public int getItemCount() {
        return partsList != null ? partsList.size() : 0; // Защита от NPE
    }

    // ViewHolder для управления элементами RecyclerView
    static class PartViewHolder extends RecyclerView.ViewHolder {
        ImageView partImage;
        TextView partName, partCarModel, partPrice;

        public PartViewHolder(@NonNull View itemView) {
            super(itemView);
            partImage = itemView.findViewById(R.id.partImage);
            partName = itemView.findViewById(R.id.partName);
            partCarModel = itemView.findViewById(R.id.partCarModel);
            partPrice = itemView.findViewById(R.id.partPrice);
        }
    }
}
