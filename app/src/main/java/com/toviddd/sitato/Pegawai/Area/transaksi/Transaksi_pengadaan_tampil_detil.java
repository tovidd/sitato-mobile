package com.toviddd.sitato.Pegawai.Area.transaksi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toviddd.sitato.DAO.ApiClient;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiJasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiPengadaanDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiPengadaanDetilDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterPengadaanTampilDetilSparepart;
import com.toviddd.sitato.R;
import com.toviddd.sitato.RecyclerAdapterHistoriTransaksiTab3ListJasaService;
import com.toviddd.sitato.RecyclerAdapterHistoriTransaksiTab3ListSparepart;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaksi_pengadaan_tampil_detil extends AppCompatActivity {

    private String TAG= "Histori Transaksi Pelanggan Detil";
    private int id_pengadaan_sparepart;

    // pengadaan
    private TextView tanggal, supplier, noTelp, cabang;

    // transaksi sparepart
    private TextView jumlahPengadaan;
    private RecyclerView rvSparepart;
    private RecyclerAdapterPengadaanTampilDetilSparepart raSparepart;
    private List<TransaksiPengadaanDetilDAO> listPengadaanDetil= new ArrayList<>();
    private LinearLayout llPengadaanSparepart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_pengadaan_tampil_detil);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initAttribute();
        buildRVSparepart();

        Bundle b= getIntent().getExtras();
        id_pengadaan_sparepart= b.getInt("id_pengadaan_sparepart");
        if(id_pengadaan_sparepart > 0)
        {
            loadDataPengadaan();
            loadDataTransaksiSparepart();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Transaksi_pengadaan_tampil_detil.this, Transaksi_pengadaan_tampil.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Transaksi_pengadaan_tampil_detil.this, Transaksi_pengadaan_tampil.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    private void initAttribute()
    {
        tanggal= findViewById(R.id.textView_tanggal_pengadaanTampilDetil);
        supplier= findViewById(R.id.textView_supplier_pengadaanTampilDetil);
        noTelp= findViewById(R.id.textView_noTelp_pengadaanTampilDetil);
        cabang= findViewById(R.id.textView_cabang_pengadaanTampilDetil);

        // transaksi sparepart
        jumlahPengadaan= findViewById(R.id.textView_jumlahPengadaan_pengadaanTampilDetil); // 300/300
        llPengadaanSparepart= findViewById(R.id.linearLayout_sparepart_pengadaanTampilDetil);
    }

    private void buildRVSparepart() {
        rvSparepart= findViewById(R.id.recyclerView_pengadaanTampilDetil);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        raSparepart= new RecyclerAdapterPengadaanTampilDetilSparepart(listPengadaanDetil);
        rvSparepart.setLayoutManager(layoutManager);
        rvSparepart.setAdapter(raSparepart);
    }

    private void loadDataPengadaan()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<TransaksiPengadaanDAO> tDAOCall= apiClient.getTransaksiPengadaan(id_pengadaan_sparepart);
        tDAOCall.enqueue(new Callback<TransaksiPengadaanDAO>() {
            @Override
            public void onResponse(Call<TransaksiPengadaanDAO> call, Response<TransaksiPengadaanDAO> response) {
                TransaksiPengadaanDAO t= response.body();
                if(t != null)
                {
                    tanggal.setText(StringUtils.capitalize(t.getTanggal_pengadaan_sparepart()));
                    supplier.setText(t.getNama_supplier());
                    noTelp.setText(t.getNo_telepon_cabang());
                    cabang.setText(t.getNama_cabang());
                }
            }

            @Override
            public void onFailure(Call<TransaksiPengadaanDAO> call, Throwable t) {
                //
            }
        });
    }

    private void loadDataTransaksiSparepart()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<TransaksiPengadaanDetilDAO>> tpdDAOCall= apiClient.getTransaksiPengadaanDetil(id_pengadaan_sparepart);
        tpdDAOCall.enqueue(new Callback<List<TransaksiPengadaanDetilDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiPengadaanDetilDAO>> call, Response<List<TransaksiPengadaanDetilDAO>> response) {
                if(response.body().size() > 0 && response.body() != null)
                {
                    int jumlahPesan= 0;
                    int jumlahDatang= 0;
                    for(int i=0; i<response.body().size(); i++)
                    {
                        jumlahPesan= jumlahPesan + response.body().get(i).getJumlah_beli_detil_pengadaan_sparepart();
                        jumlahDatang= jumlahDatang + response.body().get(i).getJumlah_barang_datang_pengadaan_sparepart();
                    }
                    jumlahPengadaan.setText(String.valueOf(jumlahDatang) +"/" +String.valueOf(jumlahPesan) +" unit");
                    raSparepart.notifyDataSetChanged();
                    raSparepart= new RecyclerAdapterPengadaanTampilDetilSparepart(Transaksi_pengadaan_tampil_detil.this, response.body());
                    rvSparepart.setAdapter(raSparepart);
                    llPengadaanSparepart.setVisibility(View.GONE);
                }
                else
                {
                    jumlahPengadaan.setText("0/0");
                    llPengadaanSparepart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiPengadaanDetilDAO>> call, Throwable t) {
            }
        });
    }

}
