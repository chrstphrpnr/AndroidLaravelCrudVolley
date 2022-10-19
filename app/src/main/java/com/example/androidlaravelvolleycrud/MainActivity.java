package com.example.androidlaravelvolleycrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout  swipeRefreshLayout;


    FloatingActionButton FloatingButton;

    ListView listView;
    MyAdapter adapter;
    public static ArrayList<Hospital> hospitalArrayList = new ArrayList<>();
    String api_url = "http://192.168.125.27:8080/api/hospitals/";

    Hospital hospital;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingButton = findViewById(R.id.FloatingButton);

        listView = findViewById(R.id.myListView);
        adapter = new MyAdapter(this,hospitalArrayList);
        listView.setAdapter(adapter);




        FloatingButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Add_Data_Activity.class)));


        listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            ProgressDialog progressDialog = new ProgressDialog(view.getContext());

            CharSequence[] dialogItem = {"View Data", "Edit Data", "Delete Data"};

            //Get single Name
            builder.setTitle(hospitalArrayList.get(position).getHospital_name());

            builder.setItems(dialogItem, (dialogInterface, i) -> {
                switch (i){
                    case 0:
                        startActivity(new Intent(getApplicationContext(),DetailActivity.class).putExtra("position", position));
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(),EditActivity.class).putExtra("position", position));
                        break;
                    case 2:
                        deleteData(hospitalArrayList.get(position).getId());
                        break;
                }
            });
            builder.create().show();
        });


        retrieveData();


        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveData();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }

    public void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.GET, api_url, response -> {

            hospitalArrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject(response);
                String succes = jsonObject.optString("success");
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                if(succes != null){
                    for (int i = 0; i <jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String id = object.getString("id");
                        String hospital_name = object.getString("hospital_name");

                        hospital = new Hospital(id,hospital_name);
                        hospitalArrayList.add(hospital);
                        adapter.notifyDataSetChanged();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void deleteData(final String id) {
        //+"{hospital}"
        StringRequest request = new StringRequest(Request.Method.DELETE, api_url+id,
                response -> {
                    if(response.equalsIgnoreCase("Data Delete Failed")){
                        Log.d("error",response);
                        Toast.makeText(MainActivity.this, "Error In Deleting", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.d("success",response);
                        Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        adapter.notifyDataSetChanged();


    }





}