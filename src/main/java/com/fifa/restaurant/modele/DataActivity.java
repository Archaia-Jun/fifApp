package com.fifa.restaurant.modele;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fifa.restaurant.R;

public class DataActivity extends AppCompatActivity {

    private TextView textData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Pour le RGPD
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        textData = (TextView) findViewById(R.id.textViewRGDP);


        }


}


