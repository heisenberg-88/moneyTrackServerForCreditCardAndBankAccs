package com.parth.money.moneyserverapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.parth.money.moneyserverapp.Adapter.CustomAdapter;
import com.parth.money.moneyserverapp.Adapter.CustomAdapterForBank;
import com.parth.money.moneyserverapp.Adapter.CustomAdapterForBankSummary;
import com.parth.money.moneyserverapp.Adapter.CustomAdapterForSummary;
import com.parth.money.moneyserverapp.Model.SummaryBankModel;
import com.parth.money.moneyserverapp.Model.SummaryModel;
import com.parth.money.moneyserverapp.Model.bankResponseEntity;
import com.parth.money.moneyserverapp.Model.moneyServerCCResponseEntity;
import com.parth.money.moneyserverapp.NetworkUtils.RetrofitUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankActivity extends AppCompatActivity {

    static String monthForQueryMainList = "COMPLETE";
    static String yearForQueryMainList = "COMPLETE";

    static BigDecimal hdfcAvlBalFLoat = new BigDecimal(0);
    static BigDecimal bomAvlBalFloat = new BigDecimal(0);

    List<String> months = Arrays.asList(
            "COMPLETE", "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
    );

    CustomAdapterForBank customAdapterForBank;
    CustomAdapterForBankSummary customAdapterForBankSummary;
    ListView mainBankList;
    Button bankrefreshButton;
    Button bankViewExitButton;
    Button bankAvlBalButton;
    Switch summarySwitch;
    ListView summaryBankList;
    Button addBankTxnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        mainBankList = (ListView)findViewById(R.id.BankMainlist);
        BankMainListPopulator(mainBankList);

        bankrefreshButton = findViewById(R.id.BankrefreshResults);
        bankViewExitButton = findViewById(R.id.BankViewExit);
        bankAvlBalButton = findViewById(R.id.BankAvLBal);
        summarySwitch = findViewById(R.id.BankSummarySwitchButton);
        summaryBankList = findViewById(R.id.BankSummarylist);
        addBankTxnButton = findViewById(R.id.BankaddNormaltxn);


        //MONTH - monthSpinnerMainAct
        Spinner monthSpinner = findViewById(R.id.BankmonthSpinnerMainAct);
        ArrayAdapter<String> monthsadapter = new ArrayAdapter<>(this,  R.layout.my_spinner, months);
        monthsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthsadapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthForQueryMainList = monthSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                monthForQueryMainList = "COMPLETE";
            }
        });

        //YEAR - yearSpinnerMainAct
        Spinner yearSpinner = findViewById(R.id.BankyearSpinnerMainAct);
        List<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        years.add("COMPLETE");
        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> yearsadapter = new ArrayAdapter<>(this, R.layout.my_spinner, years);
        yearsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearsadapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearForQueryMainList = yearSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                yearForQueryMainList = "COMPLETE";
            }
        });


        // Refill Button
        bankrefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BankMainListPopulator(mainBankList);
            }
        });

        // Bank View Exit
        bankViewExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        // Bank Avl Bal
        bankAvlBalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<BigDecimal> hdfcAvlBal = RetrofitUtils.getInstance().getApiService().getAvlBalFrombankAccName("avlBal-HDFC");
                hdfcAvlBal.enqueue(new Callback<BigDecimal>() {
                    @Override
                    public void onResponse(Call<BigDecimal> call, Response<BigDecimal> response) {
                        hdfcAvlBalFLoat = response.body();
                    }

                    @Override
                    public void onFailure(Call<BigDecimal> call, Throwable t) {

                    }
                });
                Call<BigDecimal> bomAvlBal = RetrofitUtils.getInstance().getApiService().getAvlBalFrombankAccName("avlBal-BOM");
                bomAvlBal.enqueue(new Callback<BigDecimal>() {
                    @Override
                    public void onResponse(Call<BigDecimal> call, Response<BigDecimal> response) {
                        bomAvlBalFloat = response.body();
                    }

                    @Override
                    public void onFailure(Call<BigDecimal> call, Throwable t) {

                    }
                });

                String toShowOnDialogAvlBal = "HDFC Bank Salary Account : Rs."+ hdfcAvlBalFLoat.toString() +
                        "  ~~~~~~~~~~~~ Bank Of Maharashtra OLD : Rs."+ bomAvlBalFloat.toString();
                showDialogAVLBAL(toShowOnDialogAvlBal);
            }
        });

        // Summary Switch
        summarySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    summaryBankList.setVisibility(View.VISIBLE);
                    SummaryListPopulator(summaryBankList);

                }else{
                    summaryBankList.setVisibility(View.GONE);
                }
            }
        });

        // Add Normal Bank Txn
        addBankTxnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankActivity.this, AddNormalBankTxnActivity.class);
                startActivity(intent);
            }
        });

    }


    public void SummaryListPopulator(ListView listView2){
        Call<List<SummaryBankModel>> responseFromRetrofit = RetrofitUtils.getInstance().getApiService().getAllBankDataSummary();

        responseFromRetrofit.enqueue(new Callback<List<SummaryBankModel>>() {
            @Override
            public void onResponse(Call<List<SummaryBankModel>> call, Response<List<SummaryBankModel>> response) {
                List<SummaryBankModel> responseList = response.body();
                customAdapterForBankSummary = new CustomAdapterForBankSummary((ArrayList<SummaryBankModel>) responseList,getApplicationContext());
                listView2.setAdapter(customAdapterForBankSummary);
            }

            @Override
            public void onFailure(Call<List<SummaryBankModel>> call, Throwable t) {

            }
        });
    }


    public void BankMainListPopulator(ListView listView){
        Call<List<bankResponseEntity>> responseFromRetrofit = null;
        if("COMPLETE".equalsIgnoreCase(monthForQueryMainList) || "COMPLETE".equalsIgnoreCase(yearForQueryMainList)){
            responseFromRetrofit = RetrofitUtils.getInstance().getApiService().getAllBankData();
        }else{
            responseFromRetrofit = RetrofitUtils.getInstance().getApiService().getBankDataFromMonthAndYear(monthForQueryMainList,yearForQueryMainList);
        }

        if(responseFromRetrofit==null){
            return;
        }

        responseFromRetrofit.enqueue(new Callback<List<bankResponseEntity>>() {
            @Override
            public void onResponse(Call<List<bankResponseEntity>> call, Response<List<bankResponseEntity>> response) {
                List<bankResponseEntity> responseList = response.body();
                customAdapterForBank = new CustomAdapterForBank((ArrayList<bankResponseEntity>) responseList,getApplicationContext());
                listView.setAdapter(customAdapterForBank);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        bankResponseEntity itemOnPosition = responseList.get(position);
                        String dialogData = itemOnPosition.getBanktxnBillingMonth()+"/"+itemOnPosition.getBanktxnBillingYear()
                                +" --> "+itemOnPosition.getBankAccName()+" --> "+itemOnPosition.getBanktxnDetails()+" --> Amt: "
                                +itemOnPosition.getBanktxnAmount()+" --> seqNo: "+itemOnPosition.getBankTxnSeqNumOrder();
                        showDialog(dialogData,itemOnPosition);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<bankResponseEntity>> call, Throwable t) {

            }
        });

    }


    private void showDialog(String dialogData,bankResponseEntity itemOnPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BankActivity.this);
        builder.setMessage(dialogData)
                .setCancelable(true)
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Call<String> response = RetrofitUtils.getInstance().getApiService().deleteBankNormaltxn(itemOnPosition.getBanktxnId());
                        response.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.code()==200){
                                    if(response.body()!=null){
                                        if("DELETE_ERROR".equalsIgnoreCase(response.body().toString())){
                                            showDialogAfterdelete("FAIL: DB lastUsed year/month mismatch - delete txn unsuccessful");
                                            dialog.dismiss();
                                        }else if ("DELETE_SUCCESS".equalsIgnoreCase(response.body().toString())){
                                            showDialogAfterdelete("SUCCESS: 200 - OK - delete txn successful");
                                            dialog.dismiss();
                                        }
                                    }else{
                                        showDialogAfterdelete("FAIL: 500 - null in response - delete txn unsuccessful");
                                        dialog.dismiss();
                                    }
                                } else if (response.code()==500) {
                                    showDialogAfterdelete("FAIL: 500 - Internal server error - delete txn unsuccessful");
                                    dialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showDialogAfterdelete(String dialogData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BankActivity.this);
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

    private void showDialogAVLBAL(String dialogData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BankActivity.this);
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
}