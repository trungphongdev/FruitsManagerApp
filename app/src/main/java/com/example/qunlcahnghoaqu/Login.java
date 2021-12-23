package com.example.qunlcahnghoaqu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qunlcahnghoaqu.Database.DBHelper;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText edtUser , edtPassword;
    Button btnLogin;
    CheckBox cbPassword;
    TextView txvWrongUser , txvWrongPassword , txvForgotPassword , txvSignUp;
    DBHelper dbHelper ;
    String name ,id,password;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        dbHelper = new DBHelper(this);
    //    dbHelper.alterTableStaff();
        initViews();
        preferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
        resetData();
        eventClick();



    }

    private void initViews() {
        edtUser = (EditText) findViewById(R.id.edittext_User);
        edtPassword = (EditText) findViewById(R.id.edittext_Password);
        btnLogin = (Button) findViewById(R.id.button_login);
        cbPassword = (CheckBox) findViewById(R.id.checkBox_Password);
        txvWrongPassword = (TextView) findViewById(R.id.textview_wrongPassword);
        txvWrongUser = (TextView) findViewById(R.id.textview_wrongUser);
        txvForgotPassword = (TextView) findViewById(R.id.textview_forgotPassword);
        txvSignUp = (TextView) findViewById(R.id.textview_signUp);
    }
    private void eventClick() {
        btnLogin.setOnClickListener(this);
        txvForgotPassword.setOnClickListener(this);
        txvSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login: {
                if (validInfor()) {
                    Cursor res = dbHelper.getDataStafffromName(edtUser.getText().toString().trim());
                    res.moveToFirst();
                    if (res != null && res.moveToFirst()) {
                        String name = res.getString(res.getColumnIndex(dbHelper.COLUMN_NAME_STAFF));
                        String pass = res.getString(res.getColumnIndex(dbHelper.COLUMN_PASSWORD_STAFF));
                        String id = res.getString(res.getColumnIndex(dbHelper.COLUMN_ID_STAFF));
                        if (edtUser.getText().toString().equals(name) && edtPassword.getText().toString().equals(pass)) {
                            Toast.makeText(this, "User Granted !!!", Toast.LENGTH_SHORT).show();
                            if (cbPassword.isChecked()) {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("UserName", name);
                                editor.putString("PassWord", pass);
                                editor.putBoolean("cb_checked", true);
                                editor.commit();

                            } else {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.remove("UserName");
                                editor.remove("PassWord");
                                editor.remove("cb_checked");
                                editor.commit();
                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Login Failed !!!", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.remove("UserName");
                            editor.remove("PassWord");
                            editor.remove("cb_checked");
                            editor.commit();
                        }
                    } else {
                        Toast.makeText(this, "Wrong all", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("UserName");
                        editor.remove("PassWord");
                        editor.remove("cb_checked");
                        editor.commit();
                    }
                }

            }
            break;
            case R.id.textview_signUp: {
                Intent intent = new Intent(getApplicationContext(), sign_in_layout.class);
                startActivity(intent);
            }
            break;
        }
    }


        private void resetData() {
            edtUser.setText(preferences.getString("UserName",""));
            edtPassword.setText(preferences.getString("PassWord",""));
            cbPassword.setChecked(preferences.getBoolean("cb_checked",false));
        }

    private boolean validInfor() {
        if(edtUser.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
            Toast.makeText(this,"Please check all field!!!",Toast.LENGTH_SHORT).show();
            edtPassword.setText("");
            edtUser.setText("");
            return false;
        }
        return true;
    }
}