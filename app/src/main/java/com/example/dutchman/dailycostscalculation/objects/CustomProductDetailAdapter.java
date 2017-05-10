package com.example.dutchman.dailycostscalculation.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dutchman.dailycostscalculation.Product;
import com.example.dutchman.dailycostscalculation.R;

import java.util.List;

/**
 * Created by dutchman on 2/16/17.
 */

public class CustomProductDetailAdapter extends ArrayAdapter<Product> {

    //        private TextView tvCMMRPName,tvCMMRPMealNo;
//
    private Context context;

    public CustomProductDetailAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CustomProductDetailAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_product_row,null);
        }

        Product product = getItem(position);

        TextView tvCustomProductRowDate = (TextView) v.findViewById(R.id.tvCustomProductRowDate);
        TextView tvCustomProductRowTime  = (TextView) v.findViewById(R.id.tvCustomProductRowTime);

        TextView tvCustomProductCatagory   = (TextView) v.findViewById(R.id.tvCustomProductCatagory);

        TextView tvCustomProductCost   = (TextView) v.findViewById(R.id.tvCustomProductCost);


        tvCustomProductRowDate.setText(product.getDate());
        tvCustomProductRowTime.setText(product.getTime());
        tvCustomProductCatagory.setText(product.getCategory());
        tvCustomProductCost.setText(product.getPrice()+"");



        return v;
    }


}