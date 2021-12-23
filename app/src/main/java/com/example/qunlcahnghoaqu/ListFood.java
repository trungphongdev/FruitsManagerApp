
package com.example.qunlcahnghoaqu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.qunlcahnghoaqu.Database.DBHelper;
import com.example.qunlcahnghoaqu.Object.Fruits;
import com.example.qunlcahnghoaqu.RecyclerView.ListFruitAdapter;

import java.util.ArrayList;

public class ListFood extends AppCompatActivity {
    RecyclerView recyclerViewFruit;
    ListFruitAdapter fruitAdapter;
    DBHelper dbHelper;
    ArrayList<Fruits> listFruit;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        dbHelper = new DBHelper(this);
        recyclerViewFruit = (RecyclerView) findViewById(R.id.recyclerViewFruit);
        listFruit = new ArrayList<>();
        displayFruit();
        fruitAdapter = new ListFruitAdapter(this,listFruit);
        recyclerViewFruit.setAdapter(fruitAdapter);
        recyclerViewFruit.setLayoutManager(new LinearLayoutManager(this));
        edtSearch = (EditText) findViewById(R.id.editTextSearch);
        // dung editText set su kien sau  khi ki thay doi
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // ki tu sau khi thay doi truyen vao ham void filter
                filter(s.toString());
            }
        });
    }
    private  void displayFruit() {
        Cursor cursor = dbHelper.readAllDataFromFruits();
        cursor.moveToFirst();
        if(cursor.getCount() == 0) {
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
        }
        else {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID_FRUIT));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME_FRUIT));
                int count  = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COUNT_FRUIT)));
                Float price = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRICE_FRUIT)));
                String category = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CATEGORY_FRUIT));
                String producer = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCER_FRUIT));
                byte[] img  = cursor.getBlob(cursor.getColumnIndex(DBHelper.COLUMN_IMG_FRUIT));
                listFruit.add(new Fruits(id,name,count,price,img,category,producer));
                cursor.moveToNext();
            }
        }

    }
    // tao ra 1 arraylist de chua ket qua tim kiem , tim trong listFruit neu co chua ki tu thi add vao list
    // roi sau do goi su kien filterList trong adapter de show
    private  void filter(String text) {
        ArrayList<Fruits> filterList = new ArrayList<>();
        for(Fruits fruits : listFruit) {
            if(fruits.getIdFruit().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(fruits);
            }
        }
        fruitAdapter.filterList(filterList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item  = menu.findItem(R.id.itemSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fruitAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);

    }
}