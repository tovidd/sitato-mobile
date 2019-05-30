package com.toviddd.sitato.Pegawai.Area.transaksi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiPengadaanDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiPengadaanDetilDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterSparepartTransaksiSparepartSearch;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterTransaksiPengadaanSparepart;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterTransaksiSparepart;
import com.toviddd.sitato.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaksi_pengadaan_detil extends AppCompatActivity implements View.OnClickListener {

    private Button kosongkanKolom, hapus, ubah, simpan;
    private EditText namaSparepart, jumlahBeliSparepart;
    private TextView deleteSearchSparepart;
    private String TAG= "Transaksi detil pengadaan sparepart";
    private String namaKelas= "transaksi detil pengadaan sparepart";
    private List<SparepartDAO> listSparepart= new ArrayList<>();
    private List<TransaksiPengadaanDetilDAO> listTPD= new ArrayList<>();
    private List<TransaksiPengadaanDetilDAO> listTPD2= new ArrayList<>();
    private RecyclerView recyclerViewTransaksiSparepart;
    private RecyclerAdapterTransaksiPengadaanSparepart raTransaksiPengadaanSparepart;
    double harga_temp= 0;

    // search bar
    private RecyclerView recyclerViewSearch;
    private RecyclerAdapterSparepartTransaksiSparepartSearch raSparepartTransaksiSparepartSearch;
    private EditText searchSparepart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_pengadaan_detil);
        getSupportActionBar().setTitle("Selesai pengadaan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Helper.networkPermission();

        initAttribute();
        buildRecyclerViewTampil();
        buildRecyclerViewSearch();
        loadDataSparepart();
        loadSelectedItemTransaksi();
        tampil();
        tampilSearch();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnKosongkanKolom:
                kosongkanKolom();
                break;
            case R.id.btnHapusSparepart_pengadaan:
                hapus();
                kosongkanKolom();
                break;
            case R.id.btnEditSparepart_pengadaan:
                ubah();
                break;
            case R.id.btnSimpanSparepart_pengadaan:
                simpan();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                startActivity(new Intent(Transaksi_pengadaan_detil.this, Transaksi_pengadaan.class));
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_pengadaan_detil.this);
                builder.setMessage("Selesai pengadaan,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        startActivity(new Intent(Transaksi_pengadaan_detil.this, Transaksi_pengadaan.class));
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_pengadaan_detil.this);
        builder.setMessage("Selesai pengadaan,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
    }

    private void initAttribute()
    {
        kosongkanKolom= findViewById(R.id.btnKosongkanKolom);
        kosongkanKolom.setOnClickListener(this);
        hapus= findViewById(R.id.btnHapusSparepart_pengadaan);
        hapus.setOnClickListener(this);
        ubah= findViewById(R.id.btnEditSparepart_pengadaan);
        ubah.setOnClickListener(this);
        simpan= findViewById(R.id.btnSimpanSparepart_pengadaan);
        simpan.setOnClickListener(this);
        deleteSearchSparepart= findViewById(R.id.textView_deleteSearchSparepart_pengadaan);

        //search bar
        searchSparepart= findViewById(R.id.editText_searchSparepart_pengadaan);

        namaSparepart= findViewById(R.id.editText_NamaSparepart_pengadaan);
        jumlahBeliSparepart= findViewById(R.id.editText_JumlahBeliSparepart_pengadaan);
    }

    private void buildRecyclerViewTampil()
    {
        recyclerViewTransaksiSparepart= findViewById(R.id.recyclerView_Sparepart_pengadaan);
        raTransaksiPengadaanSparepart= new RecyclerAdapterTransaksiPengadaanSparepart(this, listTPD);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewTransaksiSparepart.setLayoutManager(mLayoutManager);
        recyclerViewTransaksiSparepart.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTransaksiSparepart.setAdapter(raTransaksiPengadaanSparepart);
    }

    private void buildRecyclerViewSearch()
    {
        recyclerViewSearch= findViewById(R.id.recyclerView_SparepartSearch_pengadaan);
        raSparepartTransaksiSparepartSearch= new RecyclerAdapterSparepartTransaksiSparepartSearch(this, listSparepart);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
    }

    private void kosongkanKolom()
    {
        kosongkanSearchSparepartPreferences();
        kosongkanBeliSparepartPreferences();
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_BELI_TRANSAKSI_SPAREPART, null);
        editor.commit();
        namaSparepart.setText("");
        jumlahBeliSparepart.setText("");
        FancyToast.makeText(Transaksi_pengadaan_detil.this, "Kolom dikosongkan", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void tampil()
    {
        FancyToast.makeText(Transaksi_pengadaan_detil.this, "Sedang menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_PENGADAAN_SPAREPART, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_TRANSAKSI_PENGADAAN_SPAREPART, "");
        final TransaksiPengadaanDAO t= gson2.fromJson(json2, TransaksiPengadaanDAO.class);

        if(t != null)
        {
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<List<TransaksiPengadaanDetilDAO>> tpDAO= apiClient.getTransaksiPengadaanDetil(t.getId_pengadaan_sparepart());
            tpDAO.enqueue(new Callback<List<TransaksiPengadaanDetilDAO>>() {
                @Override
                public void onResponse(Call<List<TransaksiPengadaanDetilDAO>> call, Response<List<TransaksiPengadaanDetilDAO>> response) {
                    if(response.body() != null)
                    {
                        // mengambil data dari response, agar listSparepart terisi
                        raTransaksiPengadaanSparepart.notifyDataSetChanged();
                        raTransaksiPengadaanSparepart= new RecyclerAdapterTransaksiPengadaanSparepart(Transaksi_pengadaan_detil.this, response.body());
                        recyclerViewTransaksiSparepart.setAdapter(raTransaksiPengadaanSparepart);
                        if(response.body() != null)
                        {
                            for(int i=0; i<response.body().size(); i++)
                            {
                                listTPD2.add(response.body().get(i));
                            }
                        }
                        FancyToast.makeText(Transaksi_pengadaan_detil.this, "Berhasil menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    }
                }

                @Override
                public void onFailure(Call<List<TransaksiPengadaanDetilDAO>> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(Transaksi_pengadaan_detil.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        if (t instanceof IOException) {
                            FancyToast.makeText(Transaksi_pengadaan_detil.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            // logging probably not necessary
                        }
                        else {
                            FancyToast.makeText(Transaksi_pengadaan_detil.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                    }
                }
            });
        }
        else
        {
            Log.d(TAG, "tampil gagal ");
        }
    }

    private void simpan()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_TRANSAKSI_SPAREPART, "");
        final SparepartDAO s= gson.fromJson(json, SparepartDAO.class);

        if(s != null)
        {
            if(jumlahBeliSparepart.getText().toString().isEmpty())
            {
                FancyToast.makeText(getApplicationContext(), "Jumlah sparepart belum diisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(Integer.parseInt(jumlahBeliSparepart.getText().toString()) <= 0)
            {
                FancyToast.makeText(getApplicationContext(), "Jumlah beli minimal 1 buah", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else
            {
                int status_id_sparepart= 0;
                for(int i=0; i<listTPD2.size(); i++)
                {
                    if(s.getId_sparepart() == listTPD2.get(i).getId_sparepart())
                    {
                        status_id_sparepart= 1;
                    }
                }

                if(status_id_sparepart != 1)
                {
                    SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_PENGADAAN_SPAREPART, Context.MODE_PRIVATE);
                    Gson gson2= new Gson();
                    String json2= pref2.getString(Helper.PREF_TRANSAKSI_PENGADAAN_SPAREPART, "");
                    final TransaksiPengadaanDAO tp= gson2.fromJson(json2, TransaksiPengadaanDAO.class);

                    if(tp != null)
                    {
                        int jumlah= Integer.parseInt(jumlahBeliSparepart.getText().toString());
                        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit= builder.build();
                        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                        Call<TransaksiPengadaanDetilDAO> tpsDAO= apiClient.requestSaveTransaksiPengadaanDetil(tp.getId_pengadaan_sparepart(), s.getId_sparepart(), jumlah, s.getHarga_beli_sparepart()*jumlah);
                        tpsDAO.enqueue(new Callback<TransaksiPengadaanDetilDAO>() {
                            @Override
                            public void onResponse(Call<TransaksiPengadaanDetilDAO> call, Response<TransaksiPengadaanDetilDAO> response) {
                                TransaksiPengadaanDetilDAO ts= response.body();
                                if(response.body() != null)
                                {
                                    SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_LIST_BELI_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor= pref.edit();
                                    Gson gson= new Gson();
                                    String json= gson.toJson(ts);
                                    editor.putString(Helper.PREF_LIST_BELI_TRANSAKSI_SPAREPART, json);
                                    editor.commit();

                                    FancyToast.makeText(Transaksi_pengadaan_detil.this, "Berhasil menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                    recreate();
                                }
                                else
                                {
                                    try
                                    {
                                        Log.d(TAG, "errBody: " +response.errorBody().string());
                                        Log.d(TAG, "toString: " +response.toString());
                                        Log.d(TAG, "raw: " +response.raw().toString());
                                    }catch(Exception e){}
                                }
                            }

                            @Override
                            public void onFailure(Call<TransaksiPengadaanDetilDAO> call, Throwable t) {
                                if(!isNetworkAvailable())
                                {
                                    FancyToast.makeText(Transaksi_pengadaan_detil.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                                else
                                {
                                    if (t instanceof IOException) {
                                        FancyToast.makeText(Transaksi_pengadaan_detil.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                        // logging probably not necessary
                                    }
                                    else {
                                        FancyToast.makeText(Transaksi_pengadaan_detil.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                    }
                                }
                            }
                        });
                    }
                }
                else
                {
                    FancyToast.makeText(getApplicationContext(), "Sparepart tersebut sudah dibeli", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                }
            }
        }
        else
        {
            FancyToast.makeText(getApplicationContext(), "Pilih sparepart untuk dibeli", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
    }

    private void tampilSearch()
    {
        deleteSearchSparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSparepart.setText("");
            }
        });

        searchSparepart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(recyclerViewSearch.getAdapter() != null)
                {
                    loadSearchSparepartPreferences();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void loadSearchSparepartPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_TRANSAKSI_SPAREPART, "");
        final SparepartDAO s= gson.fromJson(json, SparepartDAO.class);

        if(s != null)
        {
            namaSparepart.setText(s.getNama_sparepart() +"(" +s.getKode_sparepart() +")");
            jumlahBeliSparepart.setText("");
        }
    }

    private void kosongkanSearchSparepartPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_TRANSAKSI_SPAREPART, null);
        editor.commit();
    }

    private void kosongkanBeliSparepartPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, null);
        editor.commit();
    }

    private void filter(String text) {
        List<SparepartDAO> filteredList = new ArrayList<>();

        for (SparepartDAO item : listSparepart) {
            if (item.getNama_sparepart().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        raSparepartTransaksiSparepartSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(raSparepartTransaksiSparepartSearch); //show something
        }
    }

    private void loadDataSparepart()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);

        Call<List<SparepartDAO>> sparepartDAOcall= apiClient.getAllSparepart();

        sparepartDAOcall.enqueue(new Callback<List<SparepartDAO>>() {
            @Override
            public void onResponse(Call<List<SparepartDAO>> call, Response<List<SparepartDAO>> response) {
                raSparepartTransaksiSparepartSearch.notifyDataSetChanged();
                raSparepartTransaksiSparepartSearch= new RecyclerAdapterSparepartTransaksiSparepartSearch(Transaksi_pengadaan_detil.this, response.body());
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listSparepart.add(response.body().get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<SparepartDAO>> call, Throwable t) {
            }
        });
    }

    private void loadSelectedItemTransaksi()
    {
        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, null);
        final TransaksiPengadaanDetilDAO tpd= gson2.fromJson(json2, TransaksiPengadaanDetilDAO.class);
        if(tpd != null)
        {
            try
            {
                for(int i= 0; i< 500000000; i++){}
                namaSparepart.setText(tpd.getNama_sparepart() +"(" +tpd.getKode_sparepart() +")");
                jumlahBeliSparepart.setText(String.valueOf(tpd.getJumlah_beli_detil_pengadaan_sparepart()));
            }catch (Exception e){
                Log.d(TAG, "loadSelectedItemTransaksi: " +e.getMessage());
                Log.d(TAG, "loadSelectedItemTransaksi: " +e.toString());
            }
        }
        else
        {
            kosongkanBeliSparepartPreferences();
        }
    }

    private void ubah()
    {
        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, null);
        final TransaksiPengadaanDetilDAO tpd= gson2.fromJson(json2, TransaksiPengadaanDetilDAO.class);

        if(tpd != null)
        {
            if(jumlahBeliSparepart.getText().toString().isEmpty())
            {
                FancyToast.makeText(getApplicationContext(), "Jumlah sparepart belum diisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(Integer.parseInt(jumlahBeliSparepart.getText().toString()) <= 0)
            {
                FancyToast.makeText(getApplicationContext(), "Jumlah beli minimal 1 buah", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else
            {
                harga_temp= tpd.getHarga_jual_sparepart();
                if(harga_temp > 0)
                {
                    final int jumlahBeli_temp= Integer.parseInt(jumlahBeliSparepart.getText().toString());
                    TransaksiPengadaanDetilDAO tpdDAO= new TransaksiPengadaanDetilDAO(jumlahBeli_temp, harga_temp*jumlahBeli_temp);

                    Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit= builder.build();
                    PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                    Call<TransaksiPengadaanDetilDAO> tsDAOCall= apiClient.requestUpdateTransaksiPengadaanDetil(tpdDAO, tpd.getId_detil_pengadaan_sparepart());
                    tsDAOCall.enqueue(new Callback<TransaksiPengadaanDetilDAO>() {
                        @Override
                        public void onResponse(Call<TransaksiPengadaanDetilDAO> call, Response<TransaksiPengadaanDetilDAO> response) {
//                            Log.d(TAG, "COBA COBA COBA COBA COBA COBA COBA COBA COBA COBA onResponse: " +ts.getId_transaksi_penjualan_sparepart() +", " +jumlahBeli_temp + ", " +harga_temp);
                            Log.d(TAG, "onResponse: " +response.toString());
//                            try {
////                                Log.d(TAG, "onResponse: " +response.errorBody().string());
////                            }catch (Exception e){}
                            FancyToast.makeText(Transaksi_pengadaan_detil.this, "Berhasil mengedit " +namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            recreate();
                            overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                        }

                        @Override
                        public void onFailure(Call<TransaksiPengadaanDetilDAO> call, Throwable t) {
                            Log.d(TAG, "getStackTrace: " +t.getStackTrace());
                            Log.d(TAG, "getCause: " +t.getCause());
                            Log.d(TAG, "call: " +call.toString());
                            FancyToast.makeText(Transaksi_pengadaan_detil.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                    });
                }
            }
        }
        else
        {
            FancyToast.makeText(getApplicationContext(), "Pilih sparepart untuk diedit", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
    }

    private void hapus()
    {
        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, null);
        final TransaksiPengadaanDetilDAO tpd= gson2.fromJson(json2, TransaksiPengadaanDetilDAO.class);
        if(tpd != null)
        {
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<String> tpdDAOCall= apiClient.deleteTransaksiPengadaanDetil(tpd.getId_detil_pengadaan_sparepart());
            tpdDAOCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    FancyToast.makeText(Transaksi_pengadaan_detil.this, "Berhasil menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    recreate();
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    FancyToast.makeText(Transaksi_pengadaan_detil.this, "Berhasil menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    recreate();
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                }
            });
        }
        else
        {
            FancyToast.makeText(getApplicationContext(), "Pilih sparepart untuk dihapus", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
    }
}
