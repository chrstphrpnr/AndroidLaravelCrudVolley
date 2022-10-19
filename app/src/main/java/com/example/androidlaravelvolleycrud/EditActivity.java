package com.example.androidlaravelvolleycrud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    EditText edId, edHospitalName;
    Button btnUpdate;
    private int position;

    String api_url = "http://192.168.125.27:8080/api/hospitals/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edId = findViewById(R.id.ed_id);
        edHospitalName = findViewById(R.id.ed_hospital_name);
        btnUpdate = findViewById(R.id.btnUpdate);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        edId.setText(MainActivity.hospitalArrayList.get(position).getId());
        edHospitalName.setText(MainActivity.hospitalArrayList.get(position).getHospital_name());


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

    }

    private void updateData() {

        final String id = edId.getText().toString();
        final String hospital_name = edHospitalName.getText().toString();


        StringRequest request = new StringRequest(Request.Method.PUT, "http://192.168.125.27:8080/api/hospitals/"+id,
            new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);

                params.put("hospital_name", hospital_name);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditActivity.this);
        requestQueue.add(request);

    }
}