package com.example.qunlcahnghoaqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    CardView cardView_AddList;
    CardView cardView_List;
    CardView cardView_Sencurity;
    CardView cardView_About;
    CardView cardView_Logout;
    CardView cardView_Calc;
    TextView txvName2, txvId2;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar mToolbar;
    ActionBarDrawerToggle barDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(mToolbar);
        navigationView.bringToFront();
        barDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,mToolbar,R.string.nav_open,R.string.nav_close);
        barDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        // Hide or show menu
      Menu menu = navigationView.getMenu();
      menu.findItem(R.id.nav_logout).setVisible(true);
      //
        setEventClick();
        getDataFromIntent();
    }
    private void initViews() {
        cardView_AddList = (CardView) findViewById(R.id.cardView_AddList);
        cardView_List =(CardView) findViewById(R.id.cardView_List);
        cardView_Calc =(CardView) findViewById(R.id.cardView_Calc);
        cardView_About =(CardView) findViewById(R.id.cardView_About);
        cardView_Sencurity =(CardView) findViewById(R.id.cardView_Cencurity);
        cardView_Logout =(CardView) findViewById(R.id.cardView_Logout);
        txvName2 = (TextView) findViewById(R.id.textView_Name2);
        txvId2 = (TextView) findViewById(R.id.textView_ID2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        cardView_Logout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }



    private void setEventClick() {
        cardView_AddList.setOnClickListener(this);
        cardView_List.setOnClickListener(this);
        cardView_Calc.setOnClickListener(this);
        cardView_About.setOnClickListener(this);
        cardView_Sencurity.setOnClickListener(this);
        cardView_Logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.cardView_AddList) {
            Intent intent = new Intent(getApplicationContext(),AddFruits.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.cardView_List) {
            Intent intent = new Intent(getApplicationContext(),ListFood.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.cardView_Cencurity) {
            Intent intent = new Intent(getApplicationContext(),sign_in_layout.class);
            intent.putExtra("infor",true);
            intent.putExtra("name",txvName2.getText().toString().trim());
            startActivity(intent);
        }
        if(v.getId()== R.id.cardView_About) {
            Intent intent = new Intent(getApplicationContext(),About_Infor.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.cardView_Calc) {
            Intent intent = new Intent(getApplicationContext(),CalcData.class);
            startActivity(intent);
        }
        if(v.getId()== R.id.cardView_Logout) {
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
        }
    }


    private  void getDataFromIntent() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            txvName2.setText(extras.getString("name"));
            txvId2.setText(extras.getString("id"));
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else  {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
              break;
            case R.id.nav_addlist:
                Intent i = new Intent(getApplicationContext(),AddFruits.class);
                startActivity(i);
                break;
            case R.id.nav_list:
                Intent intent = new Intent(getApplicationContext(),ListFood.class);
                startActivity(intent);
                break;
            case R.id.nav_cencurity:
                Intent i1 = new Intent(getApplicationContext(),sign_in_layout.class);
                i1.putExtra("infor",true);
                i1.putExtra("name",txvName2.getText().toString().trim());
                startActivity(i1);
                break;
            case R.id.nav_calc:
                Intent i2 = new Intent(getApplicationContext(),CalcData.class);
                startActivity(i2);
                break;
            case R.id.nav_about:
                Intent i3 = new Intent(getApplicationContext(),About_Infor.class);
                startActivity(i3);
                break;
            case R.id.nav_logout:
                Intent i4 = new Intent(getApplicationContext(),Login.class);
                startActivity(i4);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        navigationView.setCheckedItem(R.id.nav_home);
        return true;
    }
}