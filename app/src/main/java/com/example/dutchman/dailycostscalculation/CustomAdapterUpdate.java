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
 * Created by dutchman on 2/20/17.
 */



public class CustomAdapterUpdate {

    private Context context;

    private MyDBHandler handler;

    private List<MonthList> monthList;

    private CustomProductDetailAdapter adapter;

    public CustomAdapterUpdate(Context context, MyDBHandler handler, List<MonthList> monthList){

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

                        showEditNotify(product);

                        adapter.notifyDataSetChanged();

                        lvCustomProductDetail.setAdapter(adapter);


                        Log.d("CustomAdapterUpdate",month);


                    }
                });

            }




            return v;
        }


    }



    public void showEditNotify(final Product product){


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        //Text Title
        TextView title = new TextView(context);
        title.setText("Upate Data");
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setBackgroundColor(context.getResources().getColor(R.color.colorIndigo));
        title.setTextColor(context.getResources().getColor(R.color.colorPrimaryText));
        title.setTextSize(25);

        //alertDialog.setTitle(cName);
        alertDialog.setCustomTitle(title);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View convertView = (View) inflater.inflate(R.layout.custom_update_row, null);

        final EditText etCustomUpdateMonth    = (EditText) convertView.findViewById(R.id.etCustomUpdateMonth);
        final EditText etCustomUpdateYear     = (EditText) convertView.findViewById(R.id.etCustomUpdateYear);
        final TextView tvCustomUpdateDate     = (TextView) convertView.findViewById(R.id.tvCustomUpdateDate);
        final TextView tvCustomUpdateTime     = (TextView) convertView.findViewById(R.id.tvCustomUpdateTime);
        final EditText etCustomUpdateCatagory = (EditText) convertView.findViewById(R.id.etCustomUpdateCatagory);
        final EditText etCustomUpdateCost     = (EditText) convertView.findViewById(R.id.etCustomUpdateCost);


        Button btnCustomUpate = (Button) convertView.findViewById(R.id.btnCustomUpdate);
        Button btnCancel      = (Button) convertView.findViewById(R.id.btnCustomUpdateCancel);

        alertDialog.setView(convertView);

        alertDialog.setCancelable(false);

        final AlertDialog dialog = alertDialog.create();

        if(product != null){


            etCustomUpdateMonth.setText(product.getMonth());
            etCustomUpdateYear.setText(product.getYear());
            tvCustomUpdateDate.setText(product.getDate());
            tvCustomUpdateTime.setText(product.getTime());
            etCustomUpdateCatagory.setText(product.getCategory());
            etCustomUpdateCost.setText(product.getPrice()+"");

            btnCustomUpate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String uMonth = etCustomUpdateMonth.getText().toString().trim();
                    String uYear  = etCustomUpdateYear.getText().toString().trim();
                    String uDate  = tvCustomUpdateDate.getText().toString().trim();
                    String uTime  = tvCustomUpdateTime.getText().toString().trim();
                    String uCatagory = etCustomUpdateCatagory.getText().toString().trim();
                    String uCost   = etCustomUpdateCost.getText().toString().trim();



                    if(uMonth.length() > 0){

                        if(uYear.length() > 0){

                            if(uDate.length() > 0){

                                if(uTime.length() > 0){

                                    if(uCatagory.length() > 0){

                                        if(uCost.length() > 0){

                                            //Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show();

                                            Log.d("MYDB",uMonth+" "+uYear);
                                            Product uProduct = new Product(uMonth, uYear, uDate, uTime, uCatagory, Double.parseDouble(uCost));
                                            if(handler.updateProduct(uProduct)){
                                                Toast.makeText(context,"Data successfully Updated",Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else{
                                                Toast.makeText(context,"Fail to update",Toast.LENGTH_SHORT).show();
                                            }
                                            
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
            }
        });

        dialog.show();

    }


}

