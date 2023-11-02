package com.example.tugasmobileprogramming;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WebView wvPeta = findViewById(R.id.wvPeta);

        wvPeta.getSettings().setJavaScriptEnabled(true);

        wvPeta.getSettings().setBuiltInZoomControls(true);
        wvPeta.getSettings().setDisplayZoomControls(false);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String region = intent.getStringExtra("region");
        String phone = intent.getStringExtra("phone");

        TextView tvName = findViewById(R.id.tvName);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvRegion = findViewById(R.id.tvRegion);
        TextView tvPhone = findViewById(R.id.tvPhone);


        tvName.setText( name);
        tvAddress.setText( address);
        tvRegion.setText( region);
        tvPhone.setText(tvPhone.getText() + phone);

        wvPeta.loadUrl("https://www.google.com/maps/search/" + name + " " + address);
    }
}