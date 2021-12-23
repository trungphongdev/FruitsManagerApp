package com.example.qunlcahnghoaqu;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qunlcahnghoaqu.Database.DBHelper;

import java.util.ArrayList;

public class CalcData extends AppCompatActivity implements View.OnClickListener {
    TextView tvCountStaff, tvCountFruit, tvResult,tvTotalPrice;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_data);
        dbHelper = new DBHelper(this);
        tvCountStaff = (TextView) findViewById(R.id.textViewCountStaff);
        tvCountFruit = (TextView) findViewById(R.id.textViewCountFruits);
        tvTotalPrice = (TextView) findViewById(R.id.textViewTotalprice);
        tvTotalPrice.setOnClickListener(this);
        tvResult = (TextView) findViewById(R.id.textViewResult);
        tvCountStaff.setOnClickListener(this);
        tvCountFruit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textViewCountFruits) {
            tvResult.setText("");
            Cursor res = dbHelper.countVeget();
            ArrayList<String> arrayList = new ArrayList<>();
            res.moveToFirst();
            while (res.isAfterLast()==false) {
                int count = res.getInt(0);
                String category = res.getString(1);
                arrayList.add(category+count+"\n");
                res.moveToNext();
                Log.d("name",category + count);
            }
            for (int i = 0 ; i< arrayList.size();i++) {
                tvResult.append(arrayList.get(i));
        }
        }
        if(v.getId() == R.id.textViewCountStaff) {
            tvResult.setText("The number of Staff is :");
            Cursor res = dbHelper.countStaff();
            res.moveToFirst();
            if(res != null) {
                while (!res.isAfterLast()) {
                    String name = res.getString(0);
                    tvResult.append(name);
                    res.moveToNext();
                }
            }
            else {
                Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.textViewTotalprice) {
            tvResult.setText("");
            Cursor res = dbHelper.totalPrice();
            res.moveToFirst();
            if(res!=null) {
                while (!res.isAfterLast()) {
                    int total = res.getInt(0);
                    String name = res.getString(1);
                    tvResult.append(name + total +"\n");
                    res.moveToNext();
                }
            }
        }
    }

}