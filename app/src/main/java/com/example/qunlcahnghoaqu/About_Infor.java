package com.example.qunlcahnghoaqu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class About_Infor extends AppCompatActivity {
    TextView txvAbout;
    Button buttonAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__infor);
        txvAbout = findViewById(R.id.textview_About);
        buttonAbout = findViewById(R.id.buttonAbout);
        txvAbout.setText("THIS APP IS STORAGE FRUIT INFORMATION BASIC " +"\n" +
                "THIS APP WROTE SQLITE TO SAVE DATA " +"\n" +
                "CREATED BY TRUNG PHONG CNTT 18A3 FITHOU");
        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }
}