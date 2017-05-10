package com.example.dutchman.dailycostscalculation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.dutchman.dailycostscalculation.objects.MonthList;
import com.example.dutchman.dailycostscalculation.objects.MyObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dutchman on 5/13/16.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private Context context;


    public MyDBHandler(Context context) {
        super(context, MyObject.DB.NAME, null, MyObject.DB.VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(MyObject.DB.TB.PRODUCT_INFO.CREATE_TB);
        db.execSQL(MyObject.DB.TB.PRICE_INFO.CREATE_TB);
        db.execSQL(MyObject.DB.TB.CATAGORY_INFO.CREATE_TB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(MyObject.DB.TB.PRODUCT_INFO.DROP_TB);
        db.execSQL(MyObject.DB.TB.PRICE_INFO.DROP_TB);
        db.execSQL(MyObject.DB.TB.CATAGORY_INFO.DROP_TB);

        onCreate(db);

    }


    public boolean addCatagory(SQLiteDatabase db, String catagory){

        ContentValues values = new ContentValues();

        long res = -1;

        values.put(MyObject.DB.TB.CATAGORY_INFO.KEY_NAME, catagory);
        try {

            res = db.insert(MyObject.DB.TB.CATAGORY_INFO.NAME, null, values);

        } catch (Exception e){

        }

        if(res > 0)
            return true;
        else
            return false;

    }

    public boolean addCatagorys(String[] catagorys){

        SQLiteDatabase db = getWritableDatabase();

        boolean isCatagoryInserted = false;

        for(String catagory : catagorys){


            isCatagoryInserted = addCatagory(db,catagory);

            if(!isCatagoryInserted)
                break;

        }

        db.close();

        return isCatagoryInserted;

    }

    public List<String> getCatagoryList(){

        List<String> catagoryList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(MyObject.DB.TB.CATAGORY_INFO.SELECT_SQL, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){


            catagoryList.add(cursor.getString(1));

            cursor.moveToNext();
        }

        Log.d("DBCATA",catagoryList.size()+"");

        cursor.close();
        db.close();

        return catagoryList;


    }



    public boolean addProduct(Product product){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        boolean isProductInserted;
        boolean isPriceInserted;

        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_MONTH, product.getMonth());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_YEAR, product.getYear());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_DATE,product.getDate());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_TIME,product.getTime());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_CATEGORY, product.getCategory());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_PRICE, product.getPrice());

        long res = db.insert(MyObject.DB.TB.PRODUCT_INFO.NAME,null,values);


        if(res > 0)
            isProductInserted = true;
        else
            isProductInserted = false;

        db.close();

        if(isDateExists(product.getDate())) {
            isPriceInserted = updatePrice(product.getMonth(), product.getYear(), product.getDate());
        }else
            isPriceInserted = addPrice(product.getMonth(), product.getYear(),product.getDate(),product.getPrice());


        return (isProductInserted && isPriceInserted);

    }



    public boolean updateProduct(Product product){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        boolean isProductInserted;
        boolean isPriceInserted = false;

        Log.d("MyDB",product.getDate()+" "+product.getTime()+" "+product.getPrice());

        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_MONTH, product.getMonth());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_YEAR, product.getYear());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_DATE,product.getDate());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_TIME,product.getTime());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_CATEGORY, product.getCategory());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_PRICE, product.getPrice());

        long res = db.update(MyObject.DB.TB.PRODUCT_INFO.NAME, values, MyObject.DB.TB.PRODUCT_INFO.KEY_DATE+" = ? AND "+MyObject.DB.TB.PRODUCT_INFO.KEY_TIME+ " = ?", new String[]{product.getDate(),product.getTime()});


        if(res > 0)
            isProductInserted = true;
        else
            isProductInserted = false;

        db.close();

        if(isProductInserted) {
            isPriceInserted = updatePrice(product.getMonth(), product.getYear(), product.getDate());
        }


        return (isProductInserted && isPriceInserted);

    }

    public boolean deleteProduct(Product product){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        boolean isProductInserted;
        boolean isPriceInserted = false;

        Log.d("MyDB",product.getDate()+" "+product.getTime()+" "+product.getPrice());

        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_MONTH, product.getMonth());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_YEAR, product.getYear());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_DATE,product.getDate());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_TIME,product.getTime());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_CATEGORY, product.getCategory());
        values.put(MyObject.DB.TB.PRODUCT_INFO.KEY_PRICE, product.getPrice());

        int res = db.delete(MyObject.DB.TB.PRODUCT_INFO.NAME, MyObject.DB.TB.PRODUCT_INFO.KEY_DATE+" = ? AND "+MyObject.DB.TB.PRODUCT_INFO.KEY_TIME+ " = ?", new String[]{product.getDate(),product.getTime()});



        if(res > 0)
            isProductInserted = true;
        else
            isProductInserted = false;

        db.close();

        if(isProductInserted) {
            isPriceInserted = updatePrice(product.getMonth(), product.getYear(), product.getDate());
        }


        return (isProductInserted && isPriceInserted);

    }



    public List<String> getMonthListFromProduct(String year){

        List<String> monthList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRODUCT_INFO.SELECT_SQL_MONTHS, new String[]{year});

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){



            monthList.add(cursor.getString(0));

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return monthList;


    }

    public List<String> getYearListFromProduct(){

        List<String> yearList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRODUCT_INFO.SELECT_SQL_YEARS, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){


            yearList.add(cursor.getString(0));

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return yearList;


    }


    public List<Product> getListProduct(){

        Product product = null;
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRODUCT_INFO.SELECT_SQL, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            product = new Product(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getDouble(6));
            productList.add(product);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return productList;
    }

    public List<Product> getListProduct(String month, String year){

        Product product = null;
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRODUCT_INFO.SELECT_SQL_MONTH_YEAR, new String[]{month,year});

        cursor.moveToFirst();



        while(!cursor.isAfterLast()) {

            product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getDouble(6));
            productList.add(product);
            Log.d("MYDB", product.getDate() + " " + product.getPrice());

            cursor.moveToNext();

        }





        cursor.close();
        db.close();

        return productList;
    }



    public List<Product> getCurrentListProduct(String month, String year){

        Product product = null;
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Log.d("MYDB", month+" "+year);

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRODUCT_INFO.SELECT_SQL_MONTH_YEAR, new String[]{month,year});


        if(cursor != null)
        {

            //int id    = cursor.getInt(0);
            String d_month = cursor.getString(1);
            String d_year = cursor.getString(2);
            String date = cursor.getString(3);
            String time = cursor.getString(4);
            String catagory = cursor.getString(5);
            double cost = cursor.getDouble(6);

            product = new Product(d_month, d_year, date, time, catagory, cost);

            productList.add(product);

            Log.d("MYDB", " "+d_month+" "+d_year+" "+catagory+" "+cost);


        } else{
            Log.d("MYDB", "Cursor NULL");
        }

        cursor.close();
        db.close();

        return productList;
    }



    public List<Product> getLastProduct(){

        Product product = null;
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRODUCT_INFO.SELECT_SQL, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            if(cursor.isLast()) {
                //product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getDouble(4));
                product = new Product(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getDouble(6));
                productList.add(product);
            }
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return productList;
    }


    private boolean updatePrice(String month, String year, String date) {


        ContentValues values = new ContentValues();

        values.put(MyObject.DB.TB.PRICE_INFO.KEY_MONTH,month);
        values.put(MyObject.DB.TB.PRICE_INFO.KEY_YEAR,year);
        values.put(MyObject.DB.TB.PRICE_INFO.KEY_DATE, date);
        values.put(MyObject.DB.TB.PRICE_INFO.KEY_PRICE, getProductPriceInDate(date));

        SQLiteDatabase db = getWritableDatabase();

        long res = db.update(MyObject.DB.TB.PRICE_INFO.NAME,values,MyObject.DB.TB.PRICE_INFO.KEY_DATE+" = ?",new String[]{date});

        db.close();

        if(res > -1)
            return true;
        else
            return false;

    }

    public boolean addPrice(String month, String year, String date,double price){


        ContentValues values = new ContentValues();

        values.put(MyObject.DB.TB.PRICE_INFO.KEY_MONTH,month);
        values.put(MyObject.DB.TB.PRICE_INFO.KEY_YEAR,year);
        values.put(MyObject.DB.TB.PRICE_INFO.KEY_DATE, date);
        values.put(MyObject.DB.TB.PRICE_INFO.KEY_PRICE, price);

        SQLiteDatabase db = getWritableDatabase();

        long res = db.insert(MyObject.DB.TB.PRICE_INFO.NAME, null, values);

        db.close();

        if(res > 0)
            return true;
        else
            return false;

    }

    public double getProductPriceInDate(String date){

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRODUCT_INFO.SELECT_SPECEFIC_DATE, new String[]{date});

        cursor.moveToFirst();


        double totalPrice = 0.0;

        if(cursor.moveToFirst()){

            totalPrice = cursor.getDouble(0);

        }

        cursor.close();
        db.close();

        return totalPrice;
    }

    public List<Price> getTotalPrice(){
        Price price = null;
        List<Price> priceList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        //String sql = "SELECT * FROM "+TB_PRICEINFO+" WHERE 1";

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRICE_INFO.SELECT_SQL, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            price = new Price(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getDouble(4));
            priceList.add(price);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return priceList;
    }

    public Price getTotalPrice(String month, String year){
        Price price = null;
        List<Price> priceList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRICE_INFO.SELECT_SQL_MONTH_YEAR, new String[]{month, year});

        cursor.moveToFirst();

        if(cursor.moveToFirst()){

            //price = new Price(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getDouble(4));
            price = new Price(cursor.getDouble(0));
            //priceList.add(price);
            //cursor.moveToNext();
        }

        Log.d("DBHandler",price.getPrice()+" total");

        cursor.close();
        db.close();

        return price;
    }


    public List<Price> getLastTotalPrice(){
        Price price = null;
        List<Price> priceList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRICE_INFO.SELECT_SQL, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            if(cursor.isLast()) {
                //price = new Price(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2));
                price = new Price(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getDouble(4));
                priceList.add(price);
            }
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return priceList;
    }


    public boolean isDateExists(String date){

        SQLiteDatabase db = getWritableDatabase();


        Cursor cursor = db.rawQuery(MyObject.DB.TB.PRICE_INFO.SELECT_IS_DATE_EXIST, null);


        boolean isExist;

        if(cursor.getCount() > 0)
            isExist = true;
        else
            isExist = false;

        cursor.close();
        db.close();

        return isExist;
    }

}
