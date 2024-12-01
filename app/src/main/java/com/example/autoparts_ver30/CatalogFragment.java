package com.example.autoparts_ver30;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CatalogFragment extends Fragment {

    private View categoryButtonsContainer; // Контейнер для кнопок категорий
    private RecyclerView recyclerViewParts; // Список товаров
    private PartsAdapter adapter;
    private ArrayList<Part> partsList;
    private DatabaseHelperParts dbHelperParts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_catalog, container, false);

        // Инициализируем элементы
        categoryButtonsContainer = rootView.findViewById(R.id.categoryButtonsContainer); // Контейнер с кнопками
        recyclerViewParts = rootView.findViewById(R.id.recyclerViewParts); // RecyclerView для товаров
        recyclerViewParts.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация базы данных и списка товаров
        dbHelperParts = new DatabaseHelperParts(getContext());
        partsList = new ArrayList<>();

        // Инициализируем адаптер для RecyclerView (важно это делать один раз)
        adapter = new PartsAdapter(getContext(), partsList);
        recyclerViewParts.setAdapter(adapter);

        // Пример кнопок для категорий
        Button btnFuelSystem = rootView.findViewById(R.id.btnFuelSystem);
        btnFuelSystem.setOnClickListener(view -> loadParts("Топливная система"));

        Button btnCoolingSystem = rootView.findViewById(R.id.btnCoolingSystem);
        btnCoolingSystem.setOnClickListener(view -> loadParts("Система охлаждения"));

        Button btnBrakeSystem = rootView.findViewById(R.id.btnBrakeSystem);
        btnBrakeSystem.setOnClickListener(view -> loadParts("Тормозная система"));

        return rootView;
    }

    private void loadParts(String category) {
        // Скрываем кнопки категорий
        categoryButtonsContainer.setVisibility(View.GONE);
        recyclerViewParts.setVisibility(View.VISIBLE);

        // Загружаем список товаров из базы данных
        partsList.clear(); // Очищаем список перед загрузкой новых данных
        try {
            dbHelperParts.create_db();
            Cursor cursor = dbHelperParts.open().rawQuery(
                    "SELECT name, car_model, price, photo FROM parts WHERE category = ?",
                    new String[]{category}
            );

            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(0);
                    String carModel = cursor.getString(1);
                    double price = cursor.getDouble(2); // Используем double для цены
                    String photo = cursor.getString(3);

                    partsList.add(new Part(name, carModel, price, photo));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Ошибка загрузки данных: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Уведомляем адаптер об изменении данных
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dbHelperParts.close();
    }
}
