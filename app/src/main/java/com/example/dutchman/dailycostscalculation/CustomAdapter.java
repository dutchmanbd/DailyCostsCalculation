package com.example.dutchman.dailycostscalculation;

/**
 * Created by dutchman on 2/16/17.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dutchman.dailycostscalculation.objects.CustomProductDetailAdapter;
import com.example.dutchman.dailycostscalculation.objects.MonthList;

import java.util.List;

/**
 * Created by dutchman on 1/11/17.
 */


public class CustomAdapter {

    private Context context;

    private MyDBHandler handler;

    private List<MonthList> monthList;

    private CustomProductDetailAdapter adapter;

    public CustomAdapter(Context context, MyDBHandler handler, List<MonthList> monthList){

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

            ListView lvCustomProductDetail = (ListView) v.findViewById(R.id.lvCustomProductDetail);


            tvCustomProductMonth.setText(monthList.getMonth());
            tvCustomProductYear.setText(monthList.getYear());
            tvCustomTotalPrice.setText("TOTAL COST: "+monthList.getTatalCost());

            List<Product> list = monthList.getProducts();

            //Log.d("CustomAdapter",list.size()+"");



            if(list.size() > 0 && list != null) {

                adapter = new CustomProductDetailAdapter(context, R.layout.custom_product_row, list);
                lvCustomProductDetail.setAdapter(adapter);

            }


            return v;
        }


    }


}

