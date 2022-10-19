package com.example.androidlaravelvolleycrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView tvDetailId, tvDetailHospitalName;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetailId = findViewById(R.id.txtDetailId);
        tvDetailHospitalName = findViewById(R.id.txtDetailHospitalName);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");


        tvDetailId.setText("ID: " + MainActivity.hospitalArrayList.get(position).getId());
        tvDetailHospitalName.setText("Hospital Name: " + MainActivity.hospitalArrayList.get(position).getHospital_name());


    }
}