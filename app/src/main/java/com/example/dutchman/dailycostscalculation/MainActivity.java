package com.example.dutchman.dailycostscalculation;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.dailycostscalculation.objects.CustomProductDetailAdapter;
import com.example.dutchman.dailycostscalculation.objects.MonthList;
import com.example.dutchman.dailycostscalculation.objects.MyObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Button btnInsert;
    private Button btnView;
    private Button btnViewAll;
    private Button btnUpdate;
    private Button btnDelete;

    private TextView tvTime;
    private EditText etPrice;

    private AutoCompleteTextView sp_category;
    private Spinner spMonth;
    private Spinner spYear;
    private Spinner spDate;

    private Date date;

    private String month;
    private String year;

    private String t_Date,t_Time,s_Category;
    private double e_Price,totalPrice;

    private List<String> dateList;

    private List<String> catagoryList;

    private Product product;
    private MyDBHandler myDBHandler;

    private static boolean IS_BD_DATA_AVIALABLE = false;


    private String[] catagorys = {"Breakfast", "Lunch", "Dinner" ,"Coffee","Tea","Ice-cola","Education","Entertainment","Health", "Beauty", "Jewellery", "Party","Sports","Transport" };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateList = new ArrayList<>();

        init();

        setSpinnerMonth();
        setSpinnerYear();
        //setSpinnerDate();

        if(!IS_BD_DATA_AVIALABLE) {
            setCatagoryArrays();
            IS_BD_DATA_AVIALABLE = true;
        }

        catagoryList = myDBHandler.getCatagoryList();


        if(catagoryList.size() > 0 && catagoryList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, catagoryList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_category.setAdapter(adapter);
        }

        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




        String mmmm = new SimpleDateFormat("MMMM").format(new Date());
        String yyyy = new SimpleDateFormat("yyyy").format(new Date());
        int indexOfMonth = MyObject.monthList.indexOf(mmmm);
        int indexOfYear = MyObject.yearList.indexOf(yyyy);

        spMonth.setSelection(indexOfMonth);
        spYear.setSelection(indexOfYear);

        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {

                    if(spYear.getSelectedItemPosition() > 0) {
                        setSpinnerDate();
                    } else{
                        spYear.performClick();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {

                    if(spMonth.getSelectedItemPosition() > 0) {
                        setSpinnerDate();
                    } else{
                        spMonth.performClick();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



//        act_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position != 0) {
//                    s_Category = parent.getSelectedItem().toString();
//
//                    Toast.makeText(MainActivity.this, s_Category, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });




        //product = new Product();

        //Actions
        btnInsert.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }


    private void setCatagoryArrays(){

        myDBHandler.addCatagorys(catagorys);
    }


    private void init(){

        //Init Part
        myDBHandler = new MyDBHandler(this);

        //tvDate  = (TextView) findViewById(R.id.tvTime);

        sp_category = (AutoCompleteTextView) findViewById(R.id.sp_category);

        spMonth = (Spinner) findViewById(R.id.sp_month);

        spYear = (Spinner) findViewById(R.id.sp_year);

        spDate = (Spinner) findViewById(R.id.sp_date);

        tvTime  = (TextView) findViewById(R.id.tvTime);

        etPrice = (EditText) findViewById(R.id.etPrice);

        btnInsert  = (Button) findViewById(R.id.btnInsert);
        btnView    = (Button) findViewById(R.id.btnView);
        btnViewAll = (Button) findViewById(R.id.btnViewAll);
        btnUpdate  = (Button) findViewById(R.id.btnUpdate);
        btnDelete  = (Button) findViewById(R.id.btnDelete);

        //date
        date = new Date();

        //date and time format
        SimpleDateFormat yymmdd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hhmmss = new SimpleDateFormat("hh:mm:ss");

        String dt = yymmdd.format(date);
        String tm = hhmmss.format(date);

        //tvDate.setText(dt);
        tvTime.setText(tm);

        //spCategory = (Spinner) findViewById(R.id.sp_category);

    }

    private void setSpinnerMonth(){

        // init spinner
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this,R.layout.spinner_item, MyObject.monthList);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMonth.setAdapter(monthAdapter);

    }

    private void setSpinnerYear(){

        // init spinner
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,R.layout.spinner_item, MyObject.yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(yearAdapter);

    }

    private void setSpinnerDate(){


        dateList.clear();
        String mn = "";
        int daysInMonth = 0;

        if(spMonth.getSelectedItemPosition() > 0){

            if(spYear.getSelectedItemPosition() > 0){

                month = spMonth.getSelectedItem().toString().trim();
                year = spYear.getSelectedItem().toString().trim();

                if(month.length() > 0 && year.length() > 0) {


                    //int monthNumber = Calendar.getInstance().get(Calendar.MONTH)+1;

                    int monthNumber = MyObject.monthList.indexOf(month);


                    if(monthNumber < 10)
                        mn = "0"+monthNumber;
                    else
                        mn = monthNumber+"";

                    Calendar mycal = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(mn), 1);
                    daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

                    for(int day=1; day<=daysInMonth; day++){



                        String d = year+"-"+mn+"-";

                        if(day < 10)
                            d = d + "0"+day;
                        else
                            d = d + day;

                        dateList.add(d);

                        Log.d("MainActivity",d);

                    }
                }


            }

        }

        if(dateList.size() > 0) {
            // init spinner
            ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, dateList);

            dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spDate.setAdapter(dateAdapter);
        }


        String dd = new SimpleDateFormat("dd").format(new Date());

        String ddd = year+"-"+mn+"-"+dd;

        int indexOfDate = dateList.indexOf(ddd);

        Log.d("MainActivity","Selelct Date: "+ddd);


        spDate.setSelection(indexOfDate);

    }


    @Override
    public void onClick(View v) {


        int id = v.getId();

        switch (id){

            case R.id.btnInsert:

                insertData();

                break;

            case R.id.btnView:
                if(spMonth.getSelectedItemPosition() > 0) {

                    if(spYear.getSelectedItemPosition() > 0) {

                        String month = spMonth.getSelectedItem().toString().trim();
                        String year = spYear.getSelectedItem().toString().trim();


                        showData(month, year);

                    } else{
                        spYear.performClick();
                    }
                } else{
                    spMonth.performClick();
                }

                break;

            case R.id.btnViewAll:

                showAllData();

                break;

            case R.id.btnUpdate:
                //Toast.makeText(this,"Update ",Toast.LENGTH_SHORT).show();

                updateData();

                break;

            case R.id.btnDelete:
                //Toast.makeText(this,"Delete",Toast.LENGTH_SHORT).show();
                deleteData();
                break;
        }
    }

    private void insertData(){

        if(spMonth.getSelectedItemPosition() > 0) {

            if (spYear.getSelectedItemPosition() > 0) {

                if (spDate.getSelectedItemPosition() > 0) {


                    if (sp_category.getText().toString().trim().length() > 0) {

                        if (!etPrice.getText().toString().equals("")) {

                            month = spMonth.getSelectedItem().toString();

                            year = spYear.getSelectedItem().toString();

                            t_Date = spDate.getSelectedItem().toString();

                            s_Category = sp_category.getText().toString();

                            t_Time = tvTime.getText().toString();
                            e_Price = Double.parseDouble(etPrice.getText().toString());

                            product = new Product(month, year, t_Date, t_Time, s_Category, e_Price);

                            if (myDBHandler.addProduct(product)) {

                                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();

                                if(!catagoryList.contains(s_Category)){

                                    myDBHandler.addCatagorys(new String[]{s_Category});

                                    catagoryList.clear();

                                    catagoryList = myDBHandler.getCatagoryList();


                                    if(catagoryList.size() > 0 && catagoryList != null) {
                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, catagoryList);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        sp_category.setAdapter(adapter);
                                    }

                                }


                            } else {

                                Toast.makeText(this, "Data not inserted", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, "Please enter cost first", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this,"Add Catagory First",Toast.LENGTH_SHORT).show();
                    }
                } else{
                    spDate.performClick();
                }
            } else{
                spYear.performClick();
            }
        } else{

            spMonth.performClick();
        }


    }


    private void showData(String month, String year){


        List<MonthList> monthLists = new ArrayList<>();
        List<Product> products;

        products = myDBHandler.getListProduct(month,year);

        Price price = myDBHandler.getTotalPrice(month,year);

        MonthList monthList = new MonthList(month,year,products,price.getPrice());

        monthLists.add(monthList);

        if(monthLists.size() > 0 && products.size() > 0){

            CustomAdapter customAdapter = new CustomAdapter(this,myDBHandler,monthLists);
            customAdapter.showDetailData();

            //Log.d("MainAcitivityMonth",""+monthLists.size()+" "+products.get(0).getPrice()+" "+price.getPrice());

        } else{
            showMessage("Empty","There is no data available");

        }

    }

//    public void showData(List<Product> products){
//
//        Context context = this;
//
//        final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context);
//
//        //Text Title
//        TextView title = new TextView(context);
//        title.setText("ALL COST LIST");
//        title.setPadding(10, 10, 10, 10);
//        title.setGravity(Gravity.CENTER);
//        title.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
//        title.setTextColor(context.getResources().getColor(R.color.colorPrimaryText));
//        title.setTextSize(25);
//
//        //alertDialog.setTitle(cName);
//        alertDialog.setCustomTitle(title);
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//
//        View convertView = (View) inflater.inflate(R.layout.custom_adapter, null);
//
//
//        ListView lvCustomDailog = (ListView) convertView.findViewById(R.id.lvCustomDailog);
//
//        Button btnCancel = (Button) convertView.findViewById(R.id.btnCancelDailog);
//
//
//
//        if(products.size() > 0 && products != null) {
//
//            CustomProductDetailAdapter adapter = new CustomProductDetailAdapter(context, R.layout.custom_product_row, products);
//            lvCustomDailog.setAdapter(adapter);
//        }
//
//
//        alertDialog.setView(convertView);
//
//        alertDialog.setCancelable(false);
//
//        final android.support.v7.app.AlertDialog dialog = alertDialog.create();
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//
//
//        dialog.show();
//
//    }


    private void deleteData(){

        List<MonthList> monthLists = new ArrayList<>();
        List<Product> list;


        List<String> yearsFromProduct = myDBHandler.getYearListFromProduct();

        List<String> monthsFromProduct;
        int count = 0;

        boolean isDataAvailabe = false;

        for(String year : yearsFromProduct){

            monthsFromProduct = myDBHandler.getMonthListFromProduct(year);

            for(String month : monthsFromProduct){

                list = myDBHandler.getListProduct(month,year);

                isDataAvailabe = true;

                Price price = myDBHandler.getTotalPrice(month,year);

                MonthList monthList = new MonthList(month,year,list,price.getPrice());

                monthLists.add(monthList);
            }


        }


        if(isDataAvailabe){

            CustomAdapterDelete customAdapter = new CustomAdapterDelete(this,myDBHandler,monthLists);
            customAdapter.showDetailData();

            Log.d("MainAcitivity",""+monthLists.size());

        } else{
            showMessage("Empty","There is no data available");

        }
    }

    private void updateData(){

        List<MonthList> monthLists = new ArrayList<>();
        List<Product> list;


        List<String> yearsFromProduct = myDBHandler.getYearListFromProduct();

        List<String> monthsFromProduct;
        int count = 0;

        boolean isDataAvailabe = false;

        for(String year : yearsFromProduct){

            monthsFromProduct = myDBHandler.getMonthListFromProduct(year);

            for(String month : monthsFromProduct){

                list = myDBHandler.getListProduct(month,year);

                isDataAvailabe = true;

                Price price = myDBHandler.getTotalPrice(month,year);

                MonthList monthList = new MonthList(month,year,list,price.getPrice());

                monthLists.add(monthList);
            }


        }


        if(isDataAvailabe){

            CustomAdapterUpdate customAdapter = new CustomAdapterUpdate(this,myDBHandler,monthLists);
            customAdapter.showDetailData();

            Log.d("MainAcitivity",""+monthLists.size());

        } else{
            showMessage("Empty","There is no data available");

        }

    }

    private void showAllData(){

        List<MonthList> monthLists = new ArrayList<>();
        List<Product> list;


        List<String> yearsFromProduct = myDBHandler.getYearListFromProduct();

        List<String> monthsFromProduct;
        int count = 0;

        boolean isDataAvailabe = false;

        for(String year : yearsFromProduct){

            monthsFromProduct = myDBHandler.getMonthListFromProduct(year);

            for(String month : monthsFromProduct){

                list = myDBHandler.getListProduct(month,year);

//                stringBuilder.append(month).append("  ").append(year).append("\n\n");
//
//                for(Product product : list){
//                    stringBuilder.append("Date: ").append(product.getDate()).append("\n");
//                    stringBuilder.append("Time: ").append(product.getTime()).append("\n");
//                    stringBuilder.append("Category: ").append(product.getCategory()).append("\n");
//                    stringBuilder.append("Price: ").append(product.getPrice()).append("\n\n");
                    isDataAvailabe = true;
//                    Log.d("YEAR",product.getPrice()+"");
//                }
                //Log.d("YEAR",(++count)+" "+list.size());
                Price price = myDBHandler.getTotalPrice(month,year);

                MonthList monthList = new MonthList(month,year,list,price.getPrice());

                monthLists.add(monthList);
            }


        }


        if(isDataAvailabe){

            CustomAdapter customAdapter = new CustomAdapter(this,myDBHandler,monthLists);
            customAdapter.showDetailData();

            Log.d("MainAcitivity",""+monthLists.size());

        } else{
            showMessage("Empty","There is no data available");

        }

    }


    private void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
