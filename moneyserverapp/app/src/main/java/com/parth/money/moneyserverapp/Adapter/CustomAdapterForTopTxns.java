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
import com.parth.money.moneyserverapp.Model.moneyServerCCTopTxnResponseEntity;
import com.parth.money.moneyserverapp.R;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CustomAdapterForTopTxns extends ArrayAdapter<moneyServerCCTopTxnResponseEntity> {

    private ArrayList<moneyServerCCTopTxnResponseEntity> dataSet;
    Context mContext;


    private static class ViewHolder {
        TextView t1;
        TextView t2;
    }

    public CustomAdapterForTopTxns(ArrayList<moneyServerCCTopTxnResponseEntity> data, Context context) {
        super(context, R.layout.card_view_toptxn, data);
        this.dataSet = data;
        this.mContext=context;

    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        moneyServerCCTopTxnResponseEntity moneyServerCCTopTxnResponseEntityobj = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_view_toptxn, parent, false);
            viewHolder.t1 = (TextView) convertView.findViewById(R.id.toptxnDetails);
            viewHolder.t2 = (TextView) convertView.findViewById(R.id.toptxnAmt);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        String DataTxnDetails = moneyServerCCTopTxnResponseEntityobj.getTxnDetails();
        BigDecimal DataTxnAmt = moneyServerCCTopTxnResponseEntityobj.getTxnAmount();

        viewHolder.t1.setText(DataTxnDetails);
        viewHolder.t2.setText(DataTxnAmt.toString());

        return convertView;
    }



}