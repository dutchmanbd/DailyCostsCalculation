/*
 * Copyright (c): All rights reserved by EXCELLENCE ICT, 2017
 */

package com.example.dutchman.dailycostscalculation;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.dailycostscalculation.objects.CustomProductDetailAdapter;
import com.example.dutchman.dailycostscalculation.objects.MonthList;
import com.example.dutchman.dailycostscalculation.objects.MyObject;

import java.util.List;

/**
 * Created by eict on 2/20/17.
 */


public class CustomAdapterDelete {

    private Context context;

    private MyDBHandler handler;

    private List<MonthList> monthList;

    private boolean isDelete = false;

    private CustomProductDetailAdapter adapter;

    public CustomAdapterDelete(Context context, MyDBHandler handler, List<MonthList> monthList){

        this.context = context;
        this.handler = handler;
        this.monthList = monthList;

    }


    public void showDetailData(){


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //Text Title
        TextView title = new TextView(context);
        title.setText("ALL COST LIST");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setBackgroundColor(context.getResources().getColor(R.color.colorIndigo));
        title.setTextColor(context.getResources().getColor(R.color.colorPrimaryText));
        title.setTextSize(25);

        //alertDialog.setTitle(cName);
        alertDialog.setCustomTitle(title);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View convertView = (View) inflater.inflate(R.layout.custom_adapter, null);


        ListView lvCustomDailog = (ListView) convertView.findViewById(R.id.lvCustomDailog);

        Button btnCancel = (Button) convertView.findViewById(R.id.btnCancelDailog);


        //List<Product> list = handler.getListProduct();

        if(monthList.size() > 0 && monthList != null) {

           CustomProductAdapter adapter = new CustomProductAdapter(context, R.layout.custom_product_adapter, monthList);
            lvCustomDailog.setAdapter(adapter);
        }


        alertDialog.setView(convertView);

        alertDialog.setCancelable(false);

        final AlertDialog dialog = alertDialog.create();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        dialog.show();

    }


    private class CustomProductAdapter extends ArrayAdapter<MonthList> {

        //        private TextView tvCMMRPName,tvCMMRPMealNo;
//
        private Context context;

        public CustomProductAdapter(Context context, int resource) {
            super(context, resource);
        }

        public CustomProductAdapter(Context context, int resource, List<MonthList> objects) {
            super(context, resource, objects);

            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.custom_product_adapter,null);
            }

            MonthList monthList = getItem(position);

            TextView tvCustomProductMonth = (TextView) v.findViewById(R.id.tvCustomProductMonth);
            TextView tvCustomProductYear  = (TextView) v.findViewById(R.id.tvCustomProductYear);

            TextView tvCustomTotalPrice   = (TextView) v.findViewById(R.id.tvCustomTotalPrice);

            final ListView lvCustomProductDetail = (ListView) v.findViewById(R.id.lvCustomProductDetail);


            tvCustomProductMonth.setText(monthList.getMonth());
            tvCustomProductYear.setText(monthList.getYear());
            tvCustomTotalPrice.setText("TOTAL COST: "+monthList.getTatalCost());

            List<Product> list = monthList.getProducts();

            //Log.d("CustomAdapter",list.size()+"");



            if(list.size() > 0 && list != null) {

                adapter = new CustomProductDetailAdapter(context, R.layout.custom_product_row, list);
                lvCustomProductDetail.setAdapter(adapter);

                lvCustomProductDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        //Toast.makeText(context,"Clicked item: "+position, Toast.LENGTH_SHORT).show();
                        //Product product = getItem(position);

                        TextView tvCustomProductRowDate = (TextView) view.findViewById(R.id.tvCustomProductRowDate);
                        TextView tvCustomProductRowTime  = (TextView) view.findViewById(R.id.tvCustomProductRowTime);

                        TextView tvCustomProductCatagory   = (TextView) view.findViewById(R.id.tvCustomProductCatagory);

                        TextView tvCustomProductCost   = (TextView) view.findViewById(R.id.tvCustomProductCost);




                        StringBuffer sb = new StringBuffer("");
                        sb.append(tvCustomProductRowDate.getText().toString()).append(" ").append(tvCustomProductRowTime.getText().toString()).append("\n");

                        sb.append(tvCustomProductCatagory.getText().toString()).append(" ").append(tvCustomProductCost.getText().toString()).append("\n");

                        String date = tvCustomProductRowDate.getText().toString();
                        String time = tvCustomProductRowTime.getText().toString();
                        String catagory = tvCustomProductCatagory.getText().toString();
                        double cost = Double.parseDouble(tvCustomProductCost.getText().toString());

                        String[] str = date.split("-");

                        String year = str[0];
                        String month = MyObject.monthList.get(Integer.parseInt(str[1].trim()));

                        Product product = new Product(month,year,date,time,catagory,cost);

                        boolean isDel = showDeleteNotify(product);

                        //Log.d("CustomAdapterDelete",month);
                        if(isDel) {
                            adapter.remove((Product) lvCustomProductDetail.getItemAtPosition(position));
                            adapter.notifyDataSetChanged();
                        }

                    }
                });

            }




            return v;
        }


    }



    public boolean showDeleteNotify(final Product product){


        isDelete = false;

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //Text Title
        TextView title = new TextView(context);
        title.setText("Are you want to delete ?");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setBackgroundColor(context.getResources().getColor(R.color.colorIndigo));
        title.setTextColor(context.getResources().getColor(R.color.colorPrimaryText));
        title.setTextSize(25);

        //alertDialog.setTitle(cName);
        alertDialog.setCustomTitle(title);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View convertView = (View) inflater.inflate(R.layout.custom_delete_row, null);

        final TextView tvCustomDeleteMonth    = (TextView) convertView.findViewById(R.id.tvCustomDeleteMonth);
        final TextView tvCustomDeleteYear     = (TextView) convertView.findViewById(R.id.tvCustomDeleteYear);
        final TextView tvCustomDeleteDate     = (TextView) convertView.findViewById(R.id.tvCustomDeleteDate);
        final TextView tvCustomDeleteTime     = (TextView) convertView.findViewById(R.id.tvCustomDeleteTime);
        final TextView tvCustomDeleteCatagory = (TextView) convertView.findViewById(R.id.tvCustomDeleteCatagory);
        final TextView tvCustomDeleteCost     = (TextView) convertView.findViewById(R.id.tvCustomDeleteCost);


        Button btnCustomDelete = (Button) convertView.findViewById(R.id.btnCustomDelete);
        Button btnCancel      = (Button) convertView.findViewById(R.id.btnCustomDeleteCancel);

        alertDialog.setView(convertView);

        alertDialog.setCancelable(false);

        final AlertDialog dialog = alertDialog.create();

        if(product != null){


            tvCustomDeleteMonth.setText(product.getMonth());
            tvCustomDeleteYear.setText(product.getYear());
            tvCustomDeleteDate.setText(product.getDate());
            tvCustomDeleteTime.setText(product.getTime());
            tvCustomDeleteCatagory.setText(product.getCategory());
            tvCustomDeleteCost.setText(product.getPrice()+"");

            btnCustomDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String uMonth = tvCustomDeleteMonth.getText().toString().trim();
                    String uYear  = tvCustomDeleteYear.getText().toString().trim();
                    String uDate  = tvCustomDeleteDate.getText().toString().trim();
                    String uTime  = tvCustomDeleteTime.getText().toString().trim();
                    String uCatagory = tvCustomDeleteCatagory.getText().toString().trim();
                    String uCost   = tvCustomDeleteCost.getText().toString().trim();



                    if(uMonth.length() > 0){

                        if(uYear.length() > 0){

                            if(uDate.length() > 0){

                                if(uTime.length() > 0){

                                    if(uCatagory.length() > 0){

                                        if(uCost.length() > 0){

                                            //Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show();

                                            Log.d("MYDB",uMonth+" "+uYear);
                                            Product uProduct = new Product(uMonth, uYear, uDate, uTime, uCatagory, Double.parseDouble(uCost));

                                            if(handler.deleteProduct(uProduct)){

                                                isDelete = true;

                                                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();

                                                dialog.dismiss();

                                            }else
                                                Toast.makeText(context,"Delete Failed",Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }
                            }
                        }

                    }



                }
            });
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isDelete = false;
            }
        });

        dialog.show();

        return isDelete;

    }


}


