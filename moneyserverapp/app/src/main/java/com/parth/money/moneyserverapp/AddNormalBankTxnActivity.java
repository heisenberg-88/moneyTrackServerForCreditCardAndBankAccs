package com.parth.money.moneyserverapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.parth.money.moneyserverapp.Model.bankResponseEntity;
import com.parth.money.moneyserverapp.NetworkUtils.RetrofitUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNormalBankTxnActivity extends AppCompatActivity {

    List<String> months = Arrays.asList(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    );

    static boolean incomingMoneyCHECKBOX = false;
    static boolean outgoingMoneyCHECKBOX = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_add_normal_txn);

        bankResponseEntity entityToPost = new bankResponseEntity();

        //MONTH
        Spinner monthSpinner = findViewById(R.id.bankmonthSpinner);
        ArrayAdapter<String> monthsadapter = new ArrayAdapter<>(this,  R.layout.my_spinner, months);
        monthsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthsadapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entityToPost.setBanktxnBillingMonth(monthSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //YEAR
        Spinner yearSpinner = findViewById(R.id.bankyearSpinner);
        List<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearsadapter = new ArrayAdapter<>(this, R.layout.my_spinner, years);
        yearsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearsadapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entityToPost.setBanktxnBillingYear(yearSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Bank Acc Name
        Spinner bankAccNameSpinner = findViewById(R.id.bankAccNameSpinner);
        List<String> bankAccNames = Arrays.asList(
                "Bank Of Maharashtra OLD", "HDFC Bank Salary Account"
        );
        ArrayAdapter<String> bankAccNamesAdapter = new ArrayAdapter<>(this, R.layout.my_spinner, bankAccNames);
        bankAccNameSpinner.setAdapter(bankAccNamesAdapter);
        bankAccNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                entityToPost.setBankAccName(bankAccNameSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //TXNDATE
        EditText txnDatefield = findViewById(R.id.banktxnDatefield);
        entityToPost.setBanktxnBillingDate("1");
        txnDatefield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isNumeric(s.toString())){
                    entityToPost.setBanktxnBillingDate(s.toString());
                }else{
                    txnDatefield.setError("Only Numeric Date Values Allowed");
                }
            }
        });

        //TXNDETAILS
        EditText txnDetailsField = findViewById(R.id.banktxnDetailsField);
        entityToPost.setBanktxnDetails("X");
        txnDetailsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                entityToPost.setBanktxnDetails(s.toString());
            }
        });


        //INCOMING / OUTGOING TXNS
        CheckBox incomingCheckbox = findViewById(R.id.incomingCheckbox);
        CheckBox outgoingCheckbox = findViewById(R.id.outgoingCheckbox);
        incomingCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    outgoingCheckbox.setChecked(false);
                    incomingMoneyCHECKBOX = true;
                    outgoingMoneyCHECKBOX = false;
                }else{
                    incomingMoneyCHECKBOX = false;
                }
            }
        });
        outgoingCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    incomingCheckbox.setChecked(false);
                    incomingMoneyCHECKBOX = false;
                    outgoingMoneyCHECKBOX = true;
                }else{
                    outgoingMoneyCHECKBOX = false;
                }
            }
        });


        //TXNAMOUNT
        EditText txnAmountsField = findViewById(R.id.banktxnAmountField);
        entityToPost.setBanktxnAmount(new BigDecimal(0));
        txnAmountsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isNumeric(s.toString())){
                    BigDecimal tempAmt = new BigDecimal(s.toString());
                    tempAmt = tempAmt.abs();
                    entityToPost.setBanktxnAmount(tempAmt);
                }else{
                    txnAmountsField.setError("Only Numeric Values Allowed");
                }

            }
        });


        // TXN SequenceNo
        EditText txnSeqNoField = findViewById(R.id.bankTxnSeqNofield);
        entityToPost.setBankTxnSeqNumOrder(new BigDecimal("0.0"));
        txnSeqNoField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isNumeric(s.toString())){
                    entityToPost.setBankTxnSeqNumOrder(new BigDecimal(s.toString()));
                }else{
                    txnSeqNoField.setError("Only Numeric Values Allowed");
                }
            }
        });


        // POST txn
        Button postTxnButton = findViewById(R.id.bankPOSTtxnbtn);
        postTxnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!entityToPost.getBanktxnAmount().toString().isEmpty() && !entityToPost.getBanktxnDetails().toString().isEmpty() ){
                    if(incomingMoneyCHECKBOX==false && outgoingMoneyCHECKBOX==false){
                        showDialogForNoinNoOut("Income or Expense checkbox must be checked!");
                    }else{
                        BigDecimal currAmt = entityToPost.getBanktxnAmount();
                        if(incomingMoneyCHECKBOX==false && outgoingMoneyCHECKBOX==true){
                            currAmt = currAmt.negate();
                            entityToPost.setBanktxnAmount(currAmt);
                        }
                        BanktxnPOSTER(entityToPost);
                    }
                }else{
                    txnDetailsField.setError("Valid txn details required");
                    txnAmountsField.setError("Valid txn amount required");
                }
            }
        });

        // EXIT txn
        Button cancelPostTxn = findViewById(R.id.bankCANCELtxnbtn);
        cancelPostTxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNormalBankTxnActivity.this, BankActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }



    public void BanktxnPOSTER(bankResponseEntity entityToPost){
        Call<bankResponseEntity> responseFromRetrofit = RetrofitUtils.getInstance().getApiService().postBankNormalTxn(entityToPost);
        responseFromRetrofit.enqueue(new Callback<bankResponseEntity>() {
            @Override
            public void onResponse(Call<bankResponseEntity> call, Response<bankResponseEntity> response) {
                if(response.code()==200){
                    if(response.body()==null){
                        showDialog("FAIL: DB lastUsed year/month mismatch - txn unsuccessful");
                    }else{
                        showDialog("SUCCESS: 200 - OK - txn successful");
                    }
                } else if (response.code()==500) {
                    showDialog("FAIL: 500 - Internal server error - txn unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<bankResponseEntity> call, Throwable t) {

            }
        });
    }


    private void showDialog(String dialogData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNormalBankTxnActivity.this);
        builder.setMessage(dialogData)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!dialogData.startsWith("FAIL:")){
                            Intent intent = new Intent(AddNormalBankTxnActivity.this, BankActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showDialogForNoinNoOut(String dialogData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNormalBankTxnActivity.this);
        builder.setMessage(dialogData)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}