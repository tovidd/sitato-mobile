package com.toviddd.sitato.Pegawai.Area.transaksi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiPengadaanDAO;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterHistoriTransaksiTampil;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterTransaksiPengadaanTampil;
import com.toviddd.sitato.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaksi_pengadaan_tampil extends AppCompatActivity implements View.OnClickListener {

    String TAG= "Transaksi pengadaan tampil";
    private Button btnEsok, btnLampau;
    private RecyclerView rvTransaksiPengadaanTampil;
    private LinearLayout llEsok, llLampau;
    private RecyclerAdapterTransaksiPengadaanTampil raTransaksiPengadaanTampil;
    private List<TransaksiPengadaanDAO> listEsok= new ArrayList<>();
    private List<TransaksiPengadaanDAO> listLampau= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_pengadaan_tampil);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAttribute();
        buildRVTransaksiTampil(listEsok);
        tampilEsok();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_hariEsok_tampilTransaksiPengadaanTampil:
                tampilEsok();
                break;
            case R.id.button_hariLampau_tampilTransaksiPengadaanTampil:
                tampilLampau();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Transaksi_pengadaan_tampil.this, Transaksi_pengadaan.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Transaksi_pengadaan_tampil.this, Transaksi_pengadaan.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    private void initAttribute()
    {
        btnEsok= findViewById(R.id.button_hariEsok_tampilTransaksiPengadaanTampil);
        btnEsok.setOnClickListener(this);
        btnLampau= findViewById(R.id.button_hariLampau_tampilTransaksiPengadaanTampil);
        btnLampau.setOnClickListener(this);
        rvTransaksiPengadaanTampil= findViewById(R.id.recyclerView_tampilTransaksiPengadaanTampil);
        llEsok= findViewById(R.id.linearLayout_hariEsok_tampilTransaksiPengadaanTampil);
        llLampau= findViewById(R.id.linearLayout_hariLampau_tampilTransaksiPengadaanTampil);
    }

    private void buildRVTransaksiTampil(List<TransaksiPengadaanDAO> list)
    {
        raTransaksiPengadaanTampil= new RecyclerAdapterTransaksiPengadaanTampil(this, list);
        raTransaksiPengadaanTampil.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(this);
        rvTransaksiPengadaanTampil.setLayoutManager(mLayoutManager);
        rvTransaksiPengadaanTampil.setItemAnimator(new DefaultItemAnimator());
        rvTransaksiPengadaanTampil.setAdapter(raTransaksiPengadaanTampil);
    }

    private void tampilEsok()
    {
        llEsok.setVisibility(View.VISIBLE);
        llLampau.setVisibility(View.GONE);

        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<TransaksiPengadaanDAO>> tDAO= apiClient.getTransaksiPengadaanEsok();
        tDAO.enqueue(new Callback<List<TransaksiPengadaanDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiPengadaanDAO>> call, Response<List<TransaksiPengadaanDAO>> response) {
                if(response.body() != null)
                {
                    buildRVTransaksiTampil(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiPengadaanDAO>> call, Throwable t) {

            }
        });
    }

    private void tampilLampau()
    {
        llEsok.setVisibility(View.GONE);
        llLampau.setVisibility(View.VISIBLE);

        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<TransaksiPengadaanDAO>> tDAO= apiClient.getTransaksiPengadaanLampau();
        tDAO.enqueue(new Callback<List<TransaksiPengadaanDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiPengadaanDAO>> call, Response<List<TransaksiPengadaanDAO>> response) {
                if(response.body() != null)
                {
                    buildRVTransaksiTampil(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiPengadaanDAO>> call, Throwable t) {

            }
        });
    }

}
