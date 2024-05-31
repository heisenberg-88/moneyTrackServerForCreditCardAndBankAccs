package com.parth.money.moneyserverapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.parth.money.moneyserverapp.Model.SummaryBankModel;
import com.parth.money.moneyserverapp.R;

import java.math.BigDecimal;
import java.util.ArrayList;


public class CustomAdapterForBankSummary extends ArrayAdapter<SummaryBankModel> {

    private ArrayList<SummaryBankModel> dataSet;
    Context mContext;


    private static class ViewHolder {
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
    }

    public CustomAdapterForBankSummary(ArrayList<SummaryBankModel> data, Context context) {
        super(context, R.layout.card_view_banksummary, data);
        this.dataSet = data;
        this.mContext=context;

    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SummaryBankModel summaryModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.card_view_banksummary, parent, false);
            viewHolder.t1 = (TextView) convertView.findViewById(R.id.BankSummaryTextView1);
            viewHolder.t2 = (TextView) convertView.findViewById(R.id.BankSummaryTextView2);
            viewHolder.t3 = (TextView) convertView.findViewById(R.id.BankSummaryTextView3);
            viewHolder.t4 = (TextView) convertView.findViewById(R.id.BankSummaryTextView4);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterForBankSummary.ViewHolder) convertView.getTag();
            result=convertView;
        }

        String DataMonthandyear = summaryModel.getMonth() +"-"+summaryModel.getYear();
        BigDecimal Bank_Of_Maharashtra_OLD = summaryModel.getBank_Of_Maharashtra_OLD();
        BigDecimal HDFC_Salary_Account = summaryModel.getHdfc_Salary_Account();
        BigDecimal Amount_Total = summaryModel.getAmount_Total();

        viewHolder.t1.setText(DataMonthandyear);
        viewHolder.t2.setText("HDFC Salary Account : ₹"+ HDFC_Salary_Account.toString());
        viewHolder.t3.setText("Bank Of Maharashtra OLD : ₹"+Bank_Of_Maharashtra_OLD.toString());
        viewHolder.t4.setText("Amount_Total : ₹"+Amount_Total.toString());

        return convertView;
    }
}