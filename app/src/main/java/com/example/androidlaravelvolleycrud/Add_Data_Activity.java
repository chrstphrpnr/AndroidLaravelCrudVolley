package com.example.androidlaravelvolleycrud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Add_Data_Activity extends AppCompatActivity {

//    EditText txtFirstName, txtLastName, txtAge;
    EditText edtHospitalName;
    Button btn_insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        edtHospitalName = findViewById(R.id.edtHospitalName);
//        txtLastName = findViewById(R.id.edtLastName);
//        txtAge = findViewById(R.id.edtAge);

        btn_insert = findViewById(R.id.btnInsert);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();

            }
        });

    }

    private void insertData() {

        final String hospital_name = edtHospitalName.getText().toString().trim();
//        final String lastname = txtLastName.getText().toString().trim();
//        final String age = txtAge.getText().toString().trim();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");


        if (hospital_name.isEmpty()){
            Toast.makeText(this,"Enter Hospital Name", Toast.LENGTH_SHORT).show();
        }
//        else if (lastname.isEmpty()){
//            Toast.makeText(this,"Enter Last Name", Toast.LENGTH_SHORT).show();
//        }
//        else if (age.isEmpty()){
//            Toast.makeText(this,"Enter Age", Toast.LENGTH_SHORT).show();
//        }
//        https://volleyandroidtesting.000webhostapp.com/api/hospitals
        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.125.27:8080/api/hospitals",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("Data Inserted")){
                                Toast.makeText(Add_Data_Activity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                            else {
                                Toast.makeText(Add_Data_Activity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Data_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("hospital_name", hospital_name);

                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(Add_Data_Activity.this);
            requestQueue.add(request);


        }





    }

    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}