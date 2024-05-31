package com.parth.money.moneyserverapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.parth.money.moneyserverapp.Model.moneyServerCCResponseEntity;
import com.parth.money.moneyserverapp.R;


import java.math.BigDecimal;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<moneyServerCCResponseEntity> {

    private ArrayList<moneyServerCCResponseEntity> dataSet;
    Context mContext;


    private static class ViewHolder {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
    }

    public CustomAdapter(ArrayList<moneyServerCCResponseEntity> data, Context context) {
        super(context, R.layout.card_view_layout, data);
        this.dataSet = data;
        this.mContext=context;

    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        moneyServerCCResponseEntity moneyServerCCResponseEntityobj = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_view_layout, parent, false);
            viewHolder.t1 = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.t2 = (TextView) convertView.findViewById(R.id.textView2);
            viewHolder.t3 = (TextView) convertView.findViewById(R.id.textView3);
            viewHolder.t4 = (TextView) convertView.findViewById(R.id.textView4);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        String DataMonthandyear = moneyServerCCResponseEntityobj.getTxnBillingMonth() +"-"+moneyServerCCResponseEntityobj.getTxnBillingYear();
        String DataCCused = moneyServerCCResponseEntityobj.getTxnCCused();
        String DataTxnDetails = moneyServerCCResponseEntityobj.getTxnDetails();
        BigDecimal DataTxnAmt = moneyServerCCResponseEntityobj.getTxnAmount();

        viewHolder.t1.setText(DataMonthandyear);
        viewHolder.t2.setText(DataCCused);
        viewHolder.t3.setText(DataTxnDetails);
        viewHolder.t4.setText(DataTxnAmt.toString());

        if(moneyServerCCResponseEntityobj.getTxnIsEmi()){
            convertView.setBackgroundColor(Color.parseColor("#f4cf7a"));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }


        return convertView;
    }



}