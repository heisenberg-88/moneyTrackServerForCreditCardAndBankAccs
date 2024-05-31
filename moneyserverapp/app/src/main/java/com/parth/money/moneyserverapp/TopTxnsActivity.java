package com.parth.money.moneyserverapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.parth.money.moneyserverapp.Adapter.CustomAdapter;
import com.parth.money.moneyserverapp.Adapter.CustomAdapterForTopTxns;
import com.parth.money.moneyserverapp.Model.moneyServerCCResponseEntity;
import com.parth.money.moneyserverapp.Model.moneyServerCCTopTxnResponseEntity;
import com.parth.money.moneyserverapp.NetworkUtils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopTxnsActivity extends AppCompatActivity {

    private static CustomAdapterForTopTxns customAdapterForTopTxns;

    ListView cardviewLinearlayoutForToptxn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_txns);

        cardviewLinearlayoutForToptxn = (ListView)findViewById(R.id.toptxnList);
        TopTxnListPopulator(cardviewLinearlayoutForToptxn);
    }

    public void TopTxnListPopulator(ListView listView){
        Call<List<moneyServerCCTopTxnResponseEntity>> responseFromRetrofit = RetrofitUtils.getInstance().getApiService().getAllTopTxnData();

        responseFromRetrofit.enqueue(new Callback<List<moneyServerCCTopTxnResponseEntity>>() {
            @Override
            public void onResponse(Call<List<moneyServerCCTopTxnResponseEntity>> call, Response<List<moneyServerCCTopTxnResponseEntity>> response) {
                List<moneyServerCCTopTxnResponseEntity> responseList = response.body();
                customAdapterForTopTxns = new CustomAdapterForTopTxns((ArrayList<moneyServerCCTopTxnResponseEntity>) responseList,getApplicationContext());
                listView.setAdapter(customAdapterForTopTxns);
            }

            @Override
            public void onFailure(Call<List<moneyServerCCTopTxnResponseEntity>> call, Throwable t) {

            }
        });
    }

}