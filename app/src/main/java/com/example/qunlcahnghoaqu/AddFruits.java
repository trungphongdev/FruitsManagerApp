package com.example.qunlcahnghoaqu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.qunlcahnghoaqu.Database.DBHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddFruits extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner spinnerCategory;
    ArrayAdapter adapter;
    String getTextSpiner ="";
    String[] arrCategory;
    EditText edtNameFruit, edtIdFruit,edtCountFruit, edtPriceFruit,edtProducerFruit;
    ImageView imgFruit;
    Button buttonSave,buttonUpdate;
    DBHelper database;
    Bitmap bitmap;
    String name_fruit ;
    String id;
    ByteArrayOutputStream outputStream;
    public  final  static  int REQUEST_CODE_CAMERA = 1;
    public  final  static  int REQUEST_CODE_GALLERY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fruits);
        database = new DBHelper(this);
        initViews();
        setSpinner();
        buttonSave.setOnClickListener(this);
        getDataIntent();

    }

    private void setSpinner() {
        arrCategory = getResources().getStringArray(R.array.category_arr);
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,arrCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        spinnerCategory.setOnItemSelectedListener(this);
    }

    private void initViews() {
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategoryFruit);
        edtIdFruit = (EditText) findViewById(R.id.editTextIDFruit);
        edtNameFruit = (EditText) findViewById(R.id.editTextNameFruit);
        edtCountFruit = (EditText) findViewById(R.id.editTextCountFruit);
        edtPriceFruit = (EditText) findViewById(R.id.editTextPriceFruit);
        edtProducerFruit = (EditText) findViewById(R.id.editTextProducer);
        imgFruit = (ImageView) findViewById(R.id.imageViewFruit);
        buttonSave = (Button) findViewById(R.id.buttonAddFruit);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgFruit.getDrawable();
         bitmap = bitmapDrawable.getBitmap();

         outputStream = new ByteArrayOutputStream();
         bitmap.compress(Bitmap.CompressFormat.PNG,1,outputStream);

    }
    public void popUpImg(View view) {
        PopupMenu popupMenu = new PopupMenu(this,view);
        getMenuInflater().inflate(R.menu.layout_menu_popup_img,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_chooseImg:
                        return true;
                    case R.id.item_openCamera:
                        ActivityCompat.requestPermissions(AddFruits.this,new String[] {Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
                        return true;
                    case R.id.item_openGallery:
                        ActivityCompat.requestPermissions(AddFruits.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
                        return true;
                    default: return false;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case  REQUEST_CODE_CAMERA: {
                if (requestCode == REQUEST_CODE_CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) {
                    Toast.makeText(this, "Camera is granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(this, "Camera is denied", Toast.LENGTH_SHORT).show();
                }
            }break;
            case  REQUEST_CODE_GALLERY: {
                if (requestCode == REQUEST_CODE_GALLERY && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) {
                    Toast.makeText(this, "Open Gallery is granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setType("image/*");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, REQUEST_CODE_GALLERY);
                } else {
                    Toast.makeText(this, "Open Gallery is denied", Toast.LENGTH_SHORT).show();
                }
            }break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case REQUEST_CODE_CAMERA:  {
            if ( requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
                bitmap = (Bitmap) data.getExtras().get("data");
                imgFruit.setImageBitmap(bitmap);
            }
            else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }break;
        case  REQUEST_CODE_GALLERY: {
            if (requestCode== REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
                Uri imgUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imgUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imgFruit.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
            }
        }break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getTextSpiner = spinnerCategory.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        if(checkvalid()) {

            byte[] data =outputStream.toByteArray();
            String id = edtIdFruit.getText().toString().trim();
            String name = edtNameFruit.getText().toString().trim();
            Integer count = Integer.parseInt(edtCountFruit.getText().toString());
            float price = Float.parseFloat(edtPriceFruit.getText().toString().trim());
            String producer = edtProducerFruit.getText().toString().trim();
            database.AddFruits(id,name,count,price, data, getTextSpiner,producer);
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            Toast.makeText(this,"ID"+id +"was added",Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else  {
            Toast.makeText(this,"Please check again somthing field",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checkvalid() {
        if(edtIdFruit.getText().toString().equals("")) {
            return false;
        }
        if(edtNameFruit.getText().toString().equals("")) {
            return false;
        }
        if(edtCountFruit.getText().toString().equals("")) {
            return false;
        }
        if(edtPriceFruit.getText().toString().equals("")) {
            return false;
        }
        if(edtProducerFruit.getText().toString().equals("")) {
            return false;
        }
        if(spinnerCategory.equals("")) {
            return false;
        }

        return true;
    }
    private void getDataIntent() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
             name_fruit = extras.getString("name_fruit");
             id =extras.getString("id");
            if(name_fruit != null) {
                Cursor cursor = database.getDataFruitsfromName(name_fruit);
                cursor.moveToFirst();
                String id = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID_FRUIT));
                String name = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME_FRUIT));
                int count  = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COUNT_FRUIT)));
                float price = Float.parseFloat(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRICE_FRUIT)));
                String category = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CATEGORY_FRUIT));
                String producer = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PRODUCER_FRUIT));
                byte[] img  = cursor.getBlob(cursor.getColumnIndex(DBHelper.COLUMN_IMG_FRUIT));
                if(!cursor.isClosed()) {
                    cursor.close();
                }
                buttonSave.setVisibility(View.INVISIBLE);

                edtIdFruit.setText(id);
                edtIdFruit.setFocusable(false);
                edtIdFruit.setClickable(false);

                edtNameFruit.setText(name);
                edtNameFruit.setFocusable(false);
                edtNameFruit.setClickable(false);

                edtCountFruit.setText(""+count);
                edtCountFruit.setFocusable(false);
                edtCountFruit.setClickable(false);

                edtPriceFruit.setText(""+price);
                edtPriceFruit.setFocusable(false);
                edtPriceFruit.setClickable(false);

                edtProducerFruit.setText(producer);
                edtProducerFruit.setFocusable(false);
                edtProducerFruit.setClickable(false);

                for(int i = 0 ;i < arrCategory.length; i++) {
                    if(category.equals(arrCategory[i])){
                        spinnerCategory.setSelection(i);
                        spinnerCategory.setFocusable(false);
                        spinnerCategory.setClickable(false);
                        spinnerCategory.setEnabled(false);
                       // spinnerCategory.setEnabled(false);
                    }

                }

                Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
                imgFruit.setImageBitmap(bitmap);
                imgFruit.setFocusable(false);
                imgFruit.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getIntent().hasExtra("name_fruit")){
            getMenuInflater().inflate(R.menu.menu_add,menu);
        return true;
        }
       else {
           return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd : {
                Intent intent = new Intent(getApplicationContext(),AddFruits.class);
                startActivity(intent);
                return  true;
            }
            case R.id.itemEdit: {
                edtNameFruit.setClickable(true);
                edtNameFruit.setFocusable(true);

                edtPriceFruit.setClickable(true);
                edtPriceFruit.setFocusable(true);

                edtProducerFruit.setClickable(true);
                edtProducerFruit.setFocusable(true);

                edtCountFruit.setClickable(true);
                edtCountFruit.setFocusable(true);

                spinnerCategory.setClickable(true);
                spinnerCategory.setFocusable(true);
                spinnerCategory.setEnabled(true);
                buttonUpdate.setVisibility(View.VISIBLE);
                updateFruit();

                return true;
            }
            case R.id.itemRemove: {
                dialogDelete();

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateFruit() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtIdFruit.getText().toString();
                String name = edtNameFruit.getText().toString();
                int count = Integer.parseInt(edtCountFruit.getText().toString());
                float price = Float.parseFloat(edtPriceFruit.getText().toString());
                byte[] img = outputStream.toByteArray();
               String producer= edtProducerFruit.getText().toString();
                database.updateItem(id,name,count,price,img,getTextSpiner,producer);
                Toast.makeText(getApplicationContext(),"Update Successfuly",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public  void dialogDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Do you wanna delete this item!!")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.deleteItem(id);
                        Toast.makeText(getApplicationContext(),"Delete Item" + name_fruit +"Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder.create();
                builder.show();
    }


}