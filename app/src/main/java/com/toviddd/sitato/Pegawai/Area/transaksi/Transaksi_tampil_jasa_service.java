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
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiJasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterHistoriTransaksiTampil;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterHistoriTransaksiTampilJasaService;
import com.toviddd.sitato.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaksi_tampil_jasa_service extends AppCompatActivity implements View.OnClickListener {

    String TAG= "Transaksi tampil jasa service";
    private Button btnBelumSelesai, btnSelesai;
    private RecyclerView rvTransaksiTampilJasaService;
    private LinearLayout llBelumSelesai, llSelesai;
    private RecyclerAdapterHistoriTransaksiTampilJasaService raHistoriTransaksiTampilJasaService;
    private List<TransaksiJasaServiceDAO> listBelumSelesai= new ArrayList<>();
    private List<TransaksiJasaServiceDAO> listSelesai= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_tampil_jasa_service);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAttribute();
        buildRVTransaksiTampil(listBelumSelesai);
        tampilBelumSelesai();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_belumSelesai_tampilTransaksiJasaService:
                tampilBelumSelesai();
                break;
            case R.id.button_selesai_tampilTransaksiJasaService:
                tampilSelesai();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Transaksi_tampil_jasa_service.this, PegawaiMainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Transaksi_tampil_jasa_service.this, PegawaiMainActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    private void initAttribute()
    {
        btnBelumSelesai= findViewById(R.id.button_belumSelesai_tampilTransaksiJasaService);
        btnBelumSelesai.setOnClickListener(this);
        btnSelesai= findViewById(R.id.button_selesai_tampilTransaksiJasaService);
        btnSelesai.setOnClickListener(this);
        rvTransaksiTampilJasaService= findViewById(R.id.recyclerView_tampilTransaksiJasaService);
        llBelumSelesai= findViewById(R.id.linearLayout_belumSelesai_tampilTransaksiJasaService);
        llSelesai= findViewById(R.id.linearLayout_selesai_tampilTransaksiJasaService);
    }

    private void buildRVTransaksiTampil(List<TransaksiJasaServiceDAO> list)
    {
        raHistoriTransaksiTampilJasaService= new RecyclerAdapterHistoriTransaksiTampilJasaService(this, list);
        raHistoriTransaksiTampilJasaService.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(this);
        rvTransaksiTampilJasaService.setLayoutManager(mLayoutManager);
        rvTransaksiTampilJasaService.setItemAnimator(new DefaultItemAnimator());
        rvTransaksiTampilJasaService.setAdapter(raHistoriTransaksiTampilJasaService);
    }

    private void tampilBelumSelesai()
    {
        llBelumSelesai.setVisibility(View.VISIBLE);
        llSelesai.setVisibility(View.GONE);

        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<TransaksiJasaServiceDAO>> tDAO= apiClient.getTransaksiJasaServiceBelumSelesai();
        tDAO.enqueue(new Callback<List<TransaksiJasaServiceDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiJasaServiceDAO>> call, Response<List<TransaksiJasaServiceDAO>> response) {
                if(response.body() != null)
                {
                    buildRVTransaksiTampil(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiJasaServiceDAO>> call, Throwable t) {

            }
        });
    }

    private void tampilSelesai()
    {
        llBelumSelesai.setVisibility(View.GONE);
        llSelesai.setVisibility(View.VISIBLE);

        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<TransaksiJasaServiceDAO>> tDAO= apiClient.getTransaksiJasaServiceSelesai();
        tDAO.enqueue(new Callback<List<TransaksiJasaServiceDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiJasaServiceDAO>> call, Response<List<TransaksiJasaServiceDAO>> response) {
                if(response.body() != null)
                {
                    buildRVTransaksiTampil(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiJasaServiceDAO>> call, Throwable t) {

            }
        });
    }

}
