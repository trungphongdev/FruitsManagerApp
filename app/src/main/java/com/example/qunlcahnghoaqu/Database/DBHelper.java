package com.example.qunlcahnghoaqu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Random;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="WorldFruit.sql";
    public static final String TABLE_NAME_FRUIT ="tblFruit";
    public static final String COLUMN_NAME_FRUIT ="nameFruit";
    public static final String COLUMN_ID_FRUIT ="idFruit";
    public static final String COLUMN_COUNT_FRUIT ="countFruit";
    public static final String COLUMN_PRICE_FRUIT ="priceFruit";
    public static final String COLUMN_CATEGORY_FRUIT ="categoryFruit";
    public static final String COLUMN_PRODUCER_FRUIT ="producerFruit";
    public static final String COLUMN_IMG_FRUIT ="imgFruit";

    public static final String TABLE_NAME_STAFF ="tblStaff";
    public static final String COLUMN_NAME_STAFF ="nameStaff";
    public static final String COLUMN_ID_STAFF ="idStaff";
    public static final String COLUMN_PASSWORD_STAFF ="passwordStaff";
    public static final String COLUMN_AGE_STAFF ="ageStaff";
    public static final String COLUMN_PHONE_STAFF ="phoneStaff";
    public static final String COLUMN_EMAIL_STAFF ="emailStaff";



    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TABLE_NAME_FRUIT +
                "(" + COLUMN_ID_FRUIT + " TEXT  UNIQUE PRIMARY KEY," +
                COLUMN_NAME_FRUIT + " TEXT," +
                COLUMN_COUNT_FRUIT + " INTEGER," +
                COLUMN_PRICE_FRUIT + " FLOAT," +
                COLUMN_IMG_FRUIT + " BLOB," +
                COLUMN_CATEGORY_FRUIT + " TEXT," +
                COLUMN_PRODUCER_FRUIT + " TEXT)";
        db.execSQL(query1);
        String query2 = "CREATE TABLE " + TABLE_NAME_STAFF +
                "(" + COLUMN_ID_STAFF + " TEXT  UNIQUE PRIMARY KEY, " +
                COLUMN_NAME_STAFF + " TEXT UNIQUE, " +
                COLUMN_AGE_STAFF + " INTEGER, " +
                COLUMN_PHONE_STAFF + " INTEGER UNIQUE, " +
                COLUMN_EMAIL_STAFF + " TEXT UNIQUE, " +
                COLUMN_PASSWORD_STAFF + " TEXT) ";

        db.execSQL(query2);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query1 = " DROP TABLE IF EXISTS  " + TABLE_NAME_FRUIT;
        db.execSQL(query1);
        String query2= " DROP TABLE IF EXISTS  " + TABLE_NAME_STAFF;
        db.execSQL(query2);
        onCreate(db);
    }
    public void AddFruits(String id , String name , int count , float price , byte[] img , String category , String producer){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_FRUIT,id);
        values.put(COLUMN_NAME_FRUIT,name);
        values.put(COLUMN_COUNT_FRUIT,count);
        values.put(COLUMN_PRICE_FRUIT,price);
        values.put(COLUMN_IMG_FRUIT,img);
        values.put(COLUMN_CATEGORY_FRUIT,category);
        values.put(COLUMN_PRODUCER_FRUIT,producer);
        database.insert(TABLE_NAME_FRUIT,null,values);
    }
    public Cursor getDataFruitsfromName(String name) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT *FROM " +  TABLE_NAME_FRUIT  + " WHERE " +  COLUMN_NAME_FRUIT  + "= ?" ,new String[] {name} );
        return cursor ;
    }
    public Cursor getDataStafffromName(String name) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery("SELECT * FROM  "+ TABLE_NAME_STAFF + " WHERE " + COLUMN_NAME_STAFF + "= ?", new String[] {name});
        return res;
    }
    public void deleteItem(String ID)  {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_FRUIT + " WHERE "+COLUMN_ID_FRUIT+"=?";
        database.execSQL(query,new String[] {ID.toString()});

    }
    public void updateItem(String id ,String name , int count , float price , byte[] img , String category , String producer) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_FRUIT,name);
        values.put(COLUMN_COUNT_FRUIT,count);
        values.put(COLUMN_PRICE_FRUIT,price);
        values.put(COLUMN_IMG_FRUIT,img);
        values.put(COLUMN_CATEGORY_FRUIT,category);
        values.put(COLUMN_PRODUCER_FRUIT,producer);
        database.update(TABLE_NAME_FRUIT,values,"idFruit=?",new String[]{id});
    }
    public void updateInforStaff(String id ,String name,int age , int phone , String email, String pass) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_STAFF,name);
        values.put(COLUMN_AGE_STAFF,age);
        values.put(COLUMN_PHONE_STAFF,phone);
        values.put(COLUMN_EMAIL_STAFF,email);
        values.put(COLUMN_PASSWORD_STAFF,pass);
        database.update(TABLE_NAME_STAFF,values,"idStaff=?",new String[]{id});

    }
    public Cursor readAllDataFromFruits() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = null;
        String query = " SELECT * FROM " + TABLE_NAME_FRUIT;
        if(database != null ) {
            res =database.rawQuery(query,null);
        }
        return res;
    }
    public Cursor readAllDataFromStaff() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = null;
        if(database != null ) {
          res =  database.rawQuery("SELECT * FROM " + TABLE_NAME_STAFF,null);
        }
        return res;
    }
    public void createAccount( String name , int age , String email , int phone , String password ) {
       SQLiteDatabase database = this.getWritableDatabase();
        Random random = new Random();
        int id_random = random.nextInt(999999);
          String id = String.format("%06d",id_random);
        /*
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID_STAFF,"10");
        values.put(COLUMN_NAME_STAFF,name);
        values.put(COLUMN_AGE_STAFF,age);
        values.put(COLUMN_EMAIL_STAFF,email);
        values.put(COLUMN_PHONE_STAFF,phone);
        values.put(COLUMN_PASSWORD_STAFF,password);
         database.insert(TABLE_NAME_STAFF,null,values);*/

         String query = " INSERT INTO " + TABLE_NAME_STAFF +" ( " + COLUMN_ID_STAFF + " , " + COLUMN_NAME_STAFF + " , " +
                 COLUMN_AGE_STAFF + " , "  + COLUMN_PHONE_STAFF + " , " + COLUMN_EMAIL_STAFF + " , " +
                 COLUMN_PASSWORD_STAFF + " ) " +
                 " VALUES( " +  "'"+id+"'" + " , "  +  "'"+name+"'" + " , "+age+" , "
                 +phone+" , " + "'"+email+"'" + "," + "'"+password+"'"+") " ;
         database.execSQL(query);
    }
    public Cursor countStaff() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT COUNT(" + COLUMN_NAME_STAFF + ")"  +" FROM " + TABLE_NAME_STAFF + " WHERE " +
                COLUMN_AGE_STAFF + ">20" ;
        Cursor res = database.rawQuery(query,null);
        return  res;


    }
    public Cursor countVeget() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT SUM("+COLUMN_COUNT_FRUIT+")"+","+COLUMN_CATEGORY_FRUIT+
                " FROM "+TABLE_NAME_FRUIT+
                " GROUP BY "+COLUMN_CATEGORY_FRUIT +
           " ORDER BY " + COLUMN_NAME_FRUIT + " ASC ";
        Cursor cursor =  database.rawQuery(query,null);
        return  cursor;

    }
    public  Cursor totalPrice() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT SUM("+COLUMN_COUNT_FRUIT+"*"+COLUMN_PRICE_FRUIT+")" + "," +COLUMN_NAME_FRUIT +
                " FROM "+TABLE_NAME_FRUIT+ " GROUP BY "+COLUMN_NAME_FRUIT;
       return database.rawQuery(query,null);
    }
    public void addDataStaff() {
        SQLiteDatabase database = this.getWritableDatabase();

        String query = "INSERT INTO " + TABLE_NAME_STAFF  + " ( "+ COLUMN_NAME_STAFF + "," +
                COLUMN_PASSWORD_STAFF  + " ) " + " VALUES( " +  "1" + "," + "123456789" +")"  ;
        database.execSQL(query);
    }


}
