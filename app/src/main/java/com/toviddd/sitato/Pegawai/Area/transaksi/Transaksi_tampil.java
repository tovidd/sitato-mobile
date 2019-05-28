package com.toviddd.sitato.Pegawai.Area.transaksi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.toviddd.sitato.DAO.ApiClient;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Cabang;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterHistoriTransaksiTampil;
import com.toviddd.sitato.R;
import com.toviddd.sitato.RecyclerAdapterHistoriTransaksiTab3;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaksi_tampil extends AppCompatActivity implements View.OnClickListener {

    String TAG= "Transaksi tampil";
    private Button btnToday, btnYesterday;
    private RecyclerView rvTransaksiTampil;
    private LinearLayout llToday, llYesterday;
    private RecyclerAdapterHistoriTransaksiTampil raHistoriTransaksiTampil;
    private List<TransaksiDAO> listToday= new ArrayList<>();
    private List<TransaksiDAO> listYesterday= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_tampil);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAttribute();
        buildRVTransaksiTampil(listToday);
        tampilHariIni();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_hariIni_tampilTransaksi:
                tampilHariIni();
                break;
            case R.id.button_kemarin_tampilTransaksi:
                tampilKemarin();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Transaksi_tampil.this, PegawaiMainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Transaksi_tampil.this, PegawaiMainActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    private void initAttribute()
    {
        btnToday= findViewById(R.id.button_hariIni_tampilTransaksi);
        btnToday.setOnClickListener(this);
        btnYesterday= findViewById(R.id.button_kemarin_tampilTransaksi);
        btnYesterday.setOnClickListener(this);
        rvTransaksiTampil= findViewById(R.id.recyclerView_tampilTransaksi);
        llToday= findViewById(R.id.linearLayout_hariIni_tampilTransaksi);
        llYesterday= findViewById(R.id.linearLayout_kemarin_tampilTransaksi);
    }

    private void buildRVTransaksiTampil(List<TransaksiDAO> list)
    {
        raHistoriTransaksiTampil= new RecyclerAdapterHistoriTransaksiTampil(this, list);
        raHistoriTransaksiTampil.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(this);
        rvTransaksiTampil.setLayoutManager(mLayoutManager);
        rvTransaksiTampil.setItemAnimator(new DefaultItemAnimator());
        rvTransaksiTampil.setAdapter(raHistoriTransaksiTampil);
    }

    private void tampilHariIni()
    {
        llYesterday.setVisibility(View.GONE);
        llToday.setVisibility(View.VISIBLE);

        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<TransaksiDAO>> tDAO= apiClient.getTransaksiToday();
        tDAO.enqueue(new Callback<List<TransaksiDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiDAO>> call, Response<List<TransaksiDAO>> response) {
                if(response.body() != null)
                {
                    buildRVTransaksiTampil(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiDAO>> call, Throwable t) {
            }
        });
    }

    private void tampilKemarin()
    {
        llToday.setVisibility(View.GONE);
        llYesterday.setVisibility(View.VISIBLE);

        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<TransaksiDAO>> tDAO= apiClient.getTransaksiYesterday();
        tDAO.enqueue(new Callback<List<TransaksiDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiDAO>> call, Response<List<TransaksiDAO>> response) {
                if(response.body() != null)
                {
                    buildRVTransaksiTampil(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiDAO>> call, Throwable t) {
            }
        });
    }

}
