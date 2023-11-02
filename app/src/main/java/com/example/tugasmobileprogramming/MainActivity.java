package com.example.tugasmobileprogramming;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Model> hospitals;
    private HospitalAdapter adapter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etCari = findViewById(R.id.etCari);

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString().trim();
                adapter.filter(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        new Thread(() -> {
            try {
                URL url = new URL("https://dekontaminasi.com/api/id/covid19/hospitals");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                hospitals = parseJsonResponse(response.toString());

                runOnUiThread(() -> {
                    RecyclerView rvRumahSakit = findViewById(R.id.rvRumahSakit);
                    adapter = new HospitalAdapter(hospitals, this);

                    rvRumahSakit.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rvRumahSakit.setAdapter(adapter);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private List<Model> parseJsonResponse(String jsonResponse) {
        List<Model> hospitals = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                String address = jsonObject.getString("address");
                String region = jsonObject.getString("region");
                String phone = jsonObject.getString("phone");
                String province = jsonObject.getString("province");

                Model hospital = new Model(name, address, region, phone, province);
                hospitals.add(hospital);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hospitals;
    }

}