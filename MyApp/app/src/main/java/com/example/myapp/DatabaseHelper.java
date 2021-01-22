package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="barcode.db";
    private static final String TABLE_NAME1="info";
   // private static final String TABLE_NAME2="orders";

    DatabaseHelper(Context context) {

        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE info(BNO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,BCNUMBER TEXT NOT NULL, DESCR TEXT (40))");

        insertData("159357147231","Parle-G biscuit",sqLiteDatabase);
        insertData("123654789029","Ponds Talcum powder",sqLiteDatabase);
        insertData("741852963685","Nivea cream",sqLiteDatabase);
        insertData("987456321203","Colgate toothpaste",sqLiteDatabase);

        insertData("253456789627","Kitkat", sqLiteDatabase);
        insertData("023596781696","Detergent" , sqLiteDatabase);
        insertData("124559702892","Banana chips" , sqLiteDatabase);
        insertData("123456789647","Yardley Deodrant",sqLiteDatabase);

       // sqLiteDatabase.execSQL("CREATE TABLE orders(ORDERID INTEGER PRIMARY KEY AUTOINCREMENT, ORDERED_ITEMS TEXT(300), PERS_PREF TEXT, PHONE_NO TEXT(10) NOT NULL, FROM_DATE DATE, TO_DATE DATE, TOTAL_PRICE INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        onCreate(sqLiteDatabase);
    }

    private void insertData(String number, String description, SQLiteDatabase database){
        //long res1=0;
        //SQLiteDatabase db=this.getWritableDatabase();


            ContentValues contentValues=new ContentValues();

            contentValues.put("BCNUMBER",number);
            contentValues.put("DESCR",description);
            //DatabaseHelper db ;
            database.insert("info", null , contentValues);


           // res1=db.insert(TABLE_NAME2,null,contentValues);


        //database.close();
       // return res1;

    }





    public long addUser(String pass, String name, String phone, String address,String x){
        long res=0;
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            FileInputStream fs =new FileInputStream(x);
            byte[] imgbyte =new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues contentValues=new ContentValues();

            contentValues.put("NAME",name);
            contentValues.put("PHONE",phone);
            contentValues.put("PASS",pass);
            contentValues.put("ADDRESS",address);

            contentValues.put("IMG",imgbyte);
            res=db.insert(TABLE_NAME1,null,contentValues);
            fs.close();
        }
        catch (IOException e){

            e.printStackTrace();


        }
        db.close();
        return res;
    }

    Cursor checkUser(String uno, String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res;
        try{
            res=db.rawQuery("select * from "+ TABLE_NAME1 +" where PHONE='"+uno+"' AND PASS='"+pass+"'",null);
        }
        catch (Exception  e){
            res=null;
        }
        return res;
    }
   /* public  Cursor getSepficItem(){
        return  SQLiteDatabase.rawQuery("select uno from credentials where uno=?",null);
    }
*/
    Bitmap getImage(String uno){
        SQLiteDatabase db=this.getReadableDatabase();
        Bitmap bt =null;
        Cursor cursor= db.rawQuery("select * from "+ TABLE_NAME1 +" where UNO=?",new String[]{String.valueOf(uno)});
        if(cursor.moveToNext()) {
            byte[] image = cursor.getBlob(4);
            bt = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        return bt;
    }

    ArrayList<String> getProfileInfo(String uno){
        SQLiteDatabase db=this.getWritableDatabase();
        ArrayList<String> buffer=new ArrayList<>();
        //StringBuilder buffer=new StringBuilder();
        Cursor res=db.rawQuery("Select * from "+TABLE_NAME1+" where UNO=?",new String[]{String.valueOf(uno)});
        if(res.moveToNext()){
            buffer.add("USER ID: "+res.getString(0));
            buffer.add("\nNAME: "+res.getString(1));
            buffer.add("\nPHONE: "+res.getString(3));
        }
        return buffer;
    }
}
