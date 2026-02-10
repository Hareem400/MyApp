package com.example.myapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int position);
    }
    private AddCityDialogListener listener;
    private City cityToEdit;
    private int position;

    public static AddCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        args.putInt("position", position);
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        boolean isEdit = false;
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city");
            position = getArguments().getInt("position");
            if (cityToEdit != null) {
                isEdit = true;
                editCityName.setText(cityToEdit.getCityName());
                editProvinceName.setText(cityToEdit.getProvinceName());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        boolean finalIsEdit = isEdit;
        return builder
                .setView(view)
                .setTitle(isEdit ? "Edit city" : "Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Update" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (finalIsEdit) {
                        cityToEdit.setCityName(cityName);
                        cityToEdit.setProvinceName(provinceName);
                        listener.editCity(cityToEdit, position);
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}
