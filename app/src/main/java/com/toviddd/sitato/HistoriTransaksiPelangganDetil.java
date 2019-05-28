package com.toviddd.sitato;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.toviddd.sitato.DAO.ApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiJasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Cabang;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterCabang;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterCabangSearch;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_jasaService;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_menu;
import com.toviddd.sitato.tab.adapter.ListViewAdapterCabangInformasiBengkel;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoriTransaksiPelangganDetil extends AppCompatActivity {

    private String TAG= "Histori Transaksi Pelanggan Detil";
    private int id_transaksi;

    // transaksi
    private TextView statusT, kodeT, bayarT, diskonT, tanggalT, csT, kasirT;
    private TextView subtotalTS, subtotalTJS;

    // transaksi sparepart
    private RecyclerView rvSparepart;
    private RecyclerAdapterHistoriTransaksiTab3ListSparepart adapterSparepart;
    private List<TransaksiSparepartDAO> listTransaksiSparepart= new ArrayList<>();
    private LinearLayout llTransaksiSparepart;

    // transaksi sparepart
    private RecyclerView rvJasaService;
    private RecyclerAdapterHistoriTransaksiTab3ListJasaService adapterJasaService;
    private List<TransaksiJasaServiceDAO> listTransaksiJasaService= new ArrayList<>();
    private LinearLayout llTransaksiJasaService;

    // transaksi jasa service

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.histori_transaksi_pelanggan_detil);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initAttribute();
        buildRVSparepart();
        buildRVJasaService();

        Bundle b= getIntent().getExtras();
        id_transaksi= b.getInt("id_transaksi");
        if(id_transaksi > 0)
        {
            Log.d(TAG, "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL: " +id_transaksi);
            loadDataTransaksi();
            loadDataTransaksiSparepart();
            loadDataTransaksiJasaService();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    private void initAttribute()
    {
        statusT= findViewById(R.id.textView_statusTransaksi_detilHistoriTransaksi);
        kodeT= findViewById(R.id.textView_kodeTransaksi_detilHistoriTransaksi);
        bayarT= findViewById(R.id.textView_bayarTransaksi_detilHistoriTransaksi);
        diskonT= findViewById(R.id.textView_diskonTransaksi_detilHistoriTransaksi);
        tanggalT= findViewById(R.id.textView_tanggalTransaksi_detilHistoriTransaksi);
        csT= findViewById(R.id.textView_namaCSTransaksi_detilHistoriTransaksi);
        kasirT= findViewById(R.id.textView_namaKasirTransaksi_detilHistoriTransaksi);

        // transaksi sparepart
        subtotalTS= findViewById(R.id.textView_subtotalTransaksiSparepart_detilHistoriTransaksi);
        llTransaksiSparepart= findViewById(R.id.linearLayout_transaksiSparepart_detilHistoriTransaksi);

        // transaksi jasa service
        subtotalTJS= findViewById(R.id.textView_subtotalTransaksiJasaService_detilHistoriTransaksi);
        llTransaksiJasaService= findViewById(R.id.linearLayout_transaksiJasaService_detilHistoriTransaksi);
    }

    private void buildRVSparepart() {
        rvSparepart = findViewById(R.id.recyclerView_sparepart_detilHistoriTransaksi);
//        rvSparepart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        adapterSparepart = new RecyclerAdapterHistoriTransaksiTab3ListSparepart(listTransaksiSparepart);
        rvSparepart.setLayoutManager(layoutManager);
        rvSparepart.setAdapter(adapterSparepart);
    }

    private void buildRVJasaService() {
        rvJasaService = findViewById(R.id.recyclerView_jasaService_detilHistoriTransaksi);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        adapterJasaService= new RecyclerAdapterHistoriTransaksiTab3ListJasaService(listTransaksiJasaService);
        rvJasaService.setLayoutManager(layoutManager);
        rvJasaService.setAdapter(adapterJasaService);
    }

    private void loadDataTransaksi()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        ApiClient apiClient= retrofit.create(ApiClient.class);
        Call<TransaksiDAO> tDAOCall= apiClient.getTransaksi(id_transaksi);
        tDAOCall.enqueue(new Callback<TransaksiDAO>() {
            @Override
            public void onResponse(Call<TransaksiDAO> call, Response<TransaksiDAO> response) {
                if(response.body() != null)
                {
                    TransaksiDAO t= response.body();
                    if(t.getNama_status_pengerjaan() != null)
                    {
                        statusT.setText(StringUtils.capitalize(t.getNama_status_pengerjaan().toLowerCase()));
                    }
                    else
                    {
                        statusT.setText(StringUtils.capitalize("sedang dikerjakan"));
                    }

                    if(t.getKode_transaksi() != null)
                    {
                        kodeT.setText(t.getKode_transaksi());
                    }
                    else
                    {
                        kodeT.setText(" ");
                    }
                    bayarT.setText("Rp. " +String.valueOf(t.getTotal_transaksi()));
                    diskonT.setText(String.valueOf(t.getDiskon_transaksi()));
                    tanggalT.setText(t.getCreated_at());

                    if(t.getNama_cs() != null)
                    {
                        csT.setText("CS: " +t.getNama_cs());
                    }
                    else
                    {
                        csT.setText("CS: ");
                    }

                    if(t.getNama_kasir() != null)
                    {
                        kasirT.setText("Kasir: " +t.getNama_kasir());
                    }
                    else
                    {
                        kasirT.setText("Kasir: ");
                    }
                }
                else
                {
                    //
                }
            }

            @Override
            public void onFailure(Call<TransaksiDAO> call, Throwable t) {
                //
            }
        });
    }

    private void loadDataTransaksiSparepart()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        ApiClient apiClient= retrofit.create(ApiClient.class);
        Call<List<TransaksiSparepartDAO>> tsDAOCall= apiClient.getTransaksiSparepart(id_transaksi);
        tsDAOCall.enqueue(new Callback<List<TransaksiSparepartDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiSparepartDAO>> call, Response<List<TransaksiSparepartDAO>> response) {
                if(response.body().size() > 0 && response.body() != null)
                {
                    double subtotalTransaksiSparepart= 0;
                    for(int i=0; i<response.body().size(); i++)
                    {
                        subtotalTransaksiSparepart= subtotalTransaksiSparepart + response.body().get(i).getSubtotal_transaksi_penjualan_sparepart();
                    }
                    subtotalTS.setText("Rp. " +String.valueOf(subtotalTransaksiSparepart));
                    adapterSparepart.notifyDataSetChanged();
                    adapterSparepart= new RecyclerAdapterHistoriTransaksiTab3ListSparepart(HistoriTransaksiPelangganDetil.this, response.body());
                    rvSparepart.setAdapter(adapterSparepart);
                    llTransaksiSparepart.setVisibility(View.GONE);
                }
                else
                {
                    subtotalTS.setText("Rp. 0.0" );
                    llTransaksiSparepart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiSparepartDAO>> call, Throwable t) {
            }
        });
    }

    private void loadDataTransaksiJasaService()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        ApiClient apiClient= retrofit.create(ApiClient.class);
        Call<List<TransaksiJasaServiceDAO>> tsDAOCall= apiClient.getTransaksiJasaService(id_transaksi);
        tsDAOCall.enqueue(new Callback<List<TransaksiJasaServiceDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiJasaServiceDAO>> call, Response<List<TransaksiJasaServiceDAO>> response) {
                if(response.body().size() > 0 && response.body() != null)
                {
                    double subtotalTransaksiJasaService= 0;
                    for(int i=0; i<response.body().size(); i++)
                    {
                        subtotalTransaksiJasaService= subtotalTransaksiJasaService + response.body().get(i).getSubtotal_transaksi_penjualan_jasa_service();
                    }
                    subtotalTJS.setText("Rp. " +String.valueOf(subtotalTransaksiJasaService));
                    adapterJasaService.notifyDataSetChanged();
                    adapterJasaService= new RecyclerAdapterHistoriTransaksiTab3ListJasaService(HistoriTransaksiPelangganDetil.this, response.body());
                    rvJasaService.setAdapter(adapterJasaService);
                    llTransaksiJasaService.setVisibility(View.GONE);
                }
                else
                {
                    subtotalTJS.setText("Rp. 0.0" );
                    llTransaksiJasaService.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiJasaServiceDAO>> call, Throwable t) {
            }
        });
    }
}
