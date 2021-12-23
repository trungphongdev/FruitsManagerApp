package com.example.qunlcahnghoaqu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlcahnghoaqu.Database.DBHelper;

public class sign_in_layout extends AppCompatActivity implements View.OnClickListener {
    EditText edtNameSignUp ,edtAgeSignUp , edtPhoneSignUp , edtPasswordSignUp , edtEmailSignUp, edtIdSignUp;
     Button buttonSignUp,buttonChange;
    DBHelper dbHelper;
    TextView txvSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_layout);
        dbHelper = new DBHelper(this);
        initViews();
        getIntentInfor();
        buttonSignUp.setOnClickListener(this);
        buttonChange.setOnClickListener(this);

    }
    private void initViews() {
        edtNameSignUp = (EditText) findViewById(R.id.edtName_SignUp);
        edtAgeSignUp = (EditText) findViewById(R.id.edtAge_SignUp);
        edtPhoneSignUp = (EditText) findViewById(R.id.edtPhone_SignUp);
        edtPasswordSignUp = (EditText) findViewById(R.id.edtPassword_SignUp);
        edtEmailSignUp = (EditText) findViewById(R.id.edtEmail_SignUp);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        txvSignUp = (TextView) findViewById(R.id.textview_signUp);
        edtIdSignUp = (EditText) findViewById(R.id.edtId_SignUp);
        buttonChange = (Button) findViewById(R.id.buttonChangeInfor);


    }



    private boolean validInfor() {
        if(edtIdSignUp.getText().toString().equals("")) {
        return false;
        }
        if(edtNameSignUp.getText().toString().equals("")) {
            edtNameSignUp.setFocusable(true);
            return  false;
        }
        if(edtAgeSignUp.getText().toString().equals("")) {
            edtNameSignUp.setFocusable(true);
            return  false;
        }
        if(edtPasswordSignUp.getText().toString().equals("")) {
            edtNameSignUp.setFocusable(true);
            return  false;
        }
        if(edtPhoneSignUp.getText().toString().equals("")  || edtPhoneSignUp.getText().toString().charAt(0) != '0' || edtPhoneSignUp.getText().length() !=10  )  {
            edtNameSignUp.setFocusable(true);
            return  false;
        }
        if(edtEmailSignUp.getText().toString().equals("")  || !edtEmailSignUp.getText().toString().contains("@gmail.com")) {
            edtNameSignUp.setFocusable(true);
            return  false;
        }
        return true;
    }

    private void getIntentInfor() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            if(extras.getBoolean("infor")) {

                String name = extras.getString("name");
                txvSignUp.setText(" User Information");
                Cursor res = dbHelper.getDataStafffromName(name);
                res.moveToFirst();
                String namestaff = res.getString(res.getColumnIndex(DBHelper.COLUMN_NAME_STAFF));
                String idstaff = res.getString(res.getColumnIndex(DBHelper.COLUMN_ID_STAFF));
                int agestaff = res.getInt(res.getColumnIndex(DBHelper.COLUMN_AGE_STAFF));
                int phonestaff = res.getInt(res.getColumnIndex(DBHelper.COLUMN_PHONE_STAFF));
                String passstaff = res.getString(res.getColumnIndex(DBHelper.COLUMN_PASSWORD_STAFF));
                String emailstaff = res.getString(res.getColumnIndex(DBHelper.COLUMN_EMAIL_STAFF));

                edtNameSignUp.setText(namestaff);
                edtNameSignUp.setFocusable(false);
                edtNameSignUp.setClickable(false);


                edtAgeSignUp.setText(String.valueOf(agestaff));
                edtAgeSignUp.setFocusable(false);
                edtAgeSignUp.setClickable(false);


                edtPhoneSignUp.setText(String.valueOf(phonestaff));
                edtPhoneSignUp.setFocusable(false);
                edtPhoneSignUp.setClickable(false);


                edtEmailSignUp.setText(emailstaff);
                edtEmailSignUp.setFocusable(false);
                edtEmailSignUp.setClickable(false);


                edtPasswordSignUp.setText(passstaff);
                edtPasswordSignUp.setFocusable(false);
                edtPasswordSignUp.setClickable(false);

                edtIdSignUp.setVisibility(View.VISIBLE);
                edtIdSignUp.setText(idstaff);
                edtIdSignUp.setFocusable(false);
                edtIdSignUp.setClickable(false);

                buttonSignUp.setVisibility(View.INVISIBLE);
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            if(extras.getBoolean("infor")) {
                getMenuInflater().inflate(R.menu.layout_menu_infor_user_img,menu);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ((item.getItemId() == R.id.item_ChangeInfor)) {
            edtIdSignUp.setClickable(false);
            edtNameSignUp.setClickable(true);
            edtAgeSignUp.setClickable(true);
            edtPhoneSignUp.setClickable(true);
            edtEmailSignUp.setClickable(true);
            edtPasswordSignUp.setClickable(true);


            edtNameSignUp.setFocusable(true);
            edtAgeSignUp.setFocusable(true);
            edtPhoneSignUp.setFocusable(true);
            edtEmailSignUp.setFocusable(true);
            edtPasswordSignUp.setFocusable(true);
            buttonChange.setVisibility(View.VISIBLE);



        }
        if(item.getItemId()==R.id.item_Admin) {
            Toast.makeText(this,"This is permission of Admin",Toast.LENGTH_SHORT).show();
        }
        return true;

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonSignUp) {
            if (valid()) {
                String name = edtNameSignUp.getText().toString().trim();
                int phone = Integer.parseInt(edtPhoneSignUp.getText().toString().trim());
                int age = Integer.parseInt(edtAgeSignUp.getText().toString().trim());
                String email = edtEmailSignUp.getText().toString().trim();
                String pass = edtPasswordSignUp.getText().toString().trim();
                dbHelper.createAccount(name, age, email, phone, pass);
                Toast.makeText(getApplicationContext(), name + "added inside db", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "please check some field", Toast.LENGTH_SHORT).show();
            }
        }

        if(v.getId()==R.id.buttonChangeInfor) {
            if(valid()) {
                String id = edtIdSignUp.getText().toString().trim();
                String name = edtNameSignUp.getText().toString().trim();
                int phone = Integer.parseInt(edtPhoneSignUp.getText().toString().trim());
                int age = Integer.parseInt(edtAgeSignUp.getText().toString().trim());
                String email = edtEmailSignUp.getText().toString().trim();
                String pass = edtPasswordSignUp.getText().toString().trim();

                dbHelper.updateInforStaff(id, name, age, phone, email, pass);
                Toast.makeText(getApplicationContext(), "Update Successefuly", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);

            }
                /*else {
                    Toast.makeText(getApplicationContext()," None Update",Toast.LENGTH_SHORT).show();
                }*/
        }

    }

    private boolean valid() {

        if(edtNameSignUp.getText().toString().equals("")) {
            Toast.makeText(this,"name",Toast.LENGTH_SHORT).show();
            edtNameSignUp.setFocusable(true);
            return  false;
        }
        if(edtAgeSignUp.getText().toString().equals("")) {
            Toast.makeText(this,"age",Toast.LENGTH_SHORT).show();
            edtNameSignUp.setFocusable(true);
            return  false;
        }
        if(edtPasswordSignUp.getText().toString().equals("")) {
            Toast.makeText(this,"pass",Toast.LENGTH_SHORT).show();
            edtNameSignUp.setFocusable(true);
            return  false;
        }
        if(edtPhoneSignUp.getText().toString().equals("")  || edtPhoneSignUp.getText().toString().charAt(0) != '0' || edtPhoneSignUp.getText().length() !=10  )  {
            edtNameSignUp.setFocusable(true);
            Toast.makeText(this,"phone",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(edtEmailSignUp.getText().toString().equals("")  || !edtEmailSignUp.getText().toString().contains("@gmail.com")) {
            edtNameSignUp.setFocusable(true);
            Toast.makeText(this,"email",Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }

}