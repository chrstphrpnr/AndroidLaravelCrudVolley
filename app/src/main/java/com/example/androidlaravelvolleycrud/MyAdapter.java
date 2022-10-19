package com.example.androidlaravelvolleycrud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Hospital> {

    Context context;
    List<Hospital> arrayListHospital;

    public MyAdapter(@NonNull Context context, List<Hospital> arrayListHospital) {
        super(context, R.layout.custom_list_item, arrayListHospital);

        this.context = context;
        this.arrayListHospital = arrayListHospital;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,null,true);

        TextView tvID = (TextView)view.findViewById(R.id.txt_id);
        TextView tvHospitalName = (TextView)view.findViewById(R.id.txt_hospital_name);

        tvID.setText(arrayListHospital.get(position).getId());
        tvHospitalName.setText(arrayListHospital.get(position).getHospital_name());



        return view;
    }
}
