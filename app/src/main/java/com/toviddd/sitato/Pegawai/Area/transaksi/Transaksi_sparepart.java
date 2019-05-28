package com.toviddd.sitato.Pegawai.Area.transaksi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.MainActivity;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterSparepartTransaksiSparepartSearch;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterTransaksiSparepart;
import com.toviddd.sitato.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaksi_sparepart extends AppCompatActivity {

    private Button kosongkanKolom, hapus, ubah, simpan;
    private EditText namaSparepart, jumlahBeliSparepart;
    private TextView deleteSearchSparepart;
    private String TAG= "Transaksi sparepart";
    private String namaKelas= "transaksi sparepart";
    private List<SparepartDAO> listSparepart= new ArrayList<>();
    private List<TransaksiSparepartDAO> listTransaksiSparepart= new ArrayList<>();
    private List<TransaksiSparepartDAO> listTransaksiSparepart2= new ArrayList<>();
    private RecyclerView recyclerViewTransaksiSparepart;
    private RecyclerAdapterTransaksiSparepart recyclerAdapterTransaksiSparepart;
    private static final int PICK_IMAGE = 100;
    double harga_temp= 0;

    private int id_transaksi;
    // search bar
    private RecyclerView recyclerViewSearch;
    private RecyclerAdapterSparepartTransaksiSparepartSearch recyclerAdapterSparepartTransaksiSparepartSearch;
    private EditText searchSparepart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_sparepart);
        getSupportActionBar().setTitle("Kembali ke menu transaksi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Helper.networkPermission();

        initAttribute();
        buildRecyclerViewTampil();
        buildRecyclerViewSearch();
        loadDataSparepart();
        loadSelectedItemTransaksi();
        tampil();
        tampilSearch();

        kosongkanKolom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kosongkanKolom();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubah();
            }
        });
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus(); kosongkanKolom();
            }
        });


    }

    private void initAttribute()
    {
        kosongkanKolom= findViewById(R.id.btnKosongkanKolom);
        hapus= findViewById(R.id.btnHapusTransaksiSparepart);
        ubah= findViewById(R.id.btnEditTransaksiSparepart);
        simpan= findViewById(R.id.btnSimpanTransaksiSparepart);
        deleteSearchSparepart= findViewById(R.id.textView_deleteSearchSparepartTransaksiSparepart);

        //search bar
        searchSparepart= findViewById(R.id.editText_searchSparepartTransaksiSparepart);

        namaSparepart= findViewById(R.id.editText_NamaSparepartTransaksiSparepart);
        jumlahBeliSparepart= findViewById(R.id.editText_JumlahBeliSparepartTransaksiSparepart);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE)
        {
            DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            startActivity(new Intent(Transaksi_sparepart.this, Transaksi_menu.class));
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_sparepart.this);
            builder.setMessage("Kembali ke menu transaksi,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
        }
        return super.onKeyDown(keyCode, event);
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
                                startActivity(new Intent(Transaksi_sparepart.this, Transaksi_menu.class));
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_sparepart.this);
                builder.setMessage("Kembali ke menu transaksi,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildRecyclerViewTampil()
    {
        recyclerViewTransaksiSparepart= findViewById(R.id.recyclerView_TransaksiSparepart);
        recyclerAdapterTransaksiSparepart= new RecyclerAdapterTransaksiSparepart(this, listTransaksiSparepart);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewTransaksiSparepart.setLayoutManager(mLayoutManager);
        recyclerViewTransaksiSparepart.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTransaksiSparepart.setAdapter(recyclerAdapterTransaksiSparepart);
    }

    private void buildRecyclerViewSearch()
    {
        recyclerViewSearch= findViewById(R.id.recyclerView_SparepartTransaksiSparepart);
        recyclerAdapterSparepartTransaksiSparepartSearch= new RecyclerAdapterSparepartTransaksiSparepartSearch(this, listSparepart);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
    }

    private void kosongkanKolom()
    {
        kosongkanSearchSparepartPreferences();
        kosongkanBeliSparepartPreferences();
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_BELI_TRANSAKSI_SPAREPART, null);
        editor.commit();
        namaSparepart.setText("");
        jumlahBeliSparepart.setText("");
        FancyToast.makeText(Transaksi_sparepart.this, "Kolom dikosongkan", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void tampil()
    {
        FancyToast.makeText(Transaksi_sparepart.this, "Sedang menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_TRANSAKSI, "");
        final TransaksiDAO t= gson2.fromJson(json2, TransaksiDAO.class);

        if(t != null)
        {
            Retrofit.Builder builder= new Retrofit
                    .Builder()
                    .baseUrl(Helper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Log.d(TAG, "ERROR ERROR ERROR ERROR ERROR ERROR ERROR ======> " +t.getId_transaksi());
            Call<List<TransaksiSparepartDAO>> transaksiDAOcall= apiClient.getTransaksiSparepart(t.getId_transaksi());
            transaksiDAOcall.enqueue(new Callback<List<TransaksiSparepartDAO>>() {
                @Override
                public void onResponse(Call<List<TransaksiSparepartDAO>> call, Response<List<TransaksiSparepartDAO>> response) {
                    // mengambil data dari response, agar listSparepart terisi
                    recyclerAdapterTransaksiSparepart.notifyDataSetChanged();
                    recyclerAdapterTransaksiSparepart= new RecyclerAdapterTransaksiSparepart(Transaksi_sparepart.this, response.body());
                    recyclerViewTransaksiSparepart.setAdapter(recyclerAdapterTransaksiSparepart);
                    if(response.body() != null)
                    {
                        for(int i=0; i<response.body().size(); i++)
                        {
                            listTransaksiSparepart2.add(response.body().get(i));
                        }
                    }
//                FancyToast.makeText(Transaksi_sparepart.this, "Berhasil menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                }

                @Override
                public void onFailure(Call<List<TransaksiSparepartDAO>> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(Transaksi_sparepart.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        if (t instanceof IOException) {
                            FancyToast.makeText(Transaksi_sparepart.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            // logging probably not necessary
                        }
                        else {
                            FancyToast.makeText(Transaksi_sparepart.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
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
                for(int i=0; i<listTransaksiSparepart2.size(); i++)
                {
                    if(s.getId_sparepart() == listTransaksiSparepart2.get(i).getId_sparepart())
                    {
                        status_id_sparepart= 1;
                    }
                }

                if(status_id_sparepart != 1)
                {
                    SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI, Context.MODE_PRIVATE);
                    Gson gson2= new Gson();
                    String json2= pref2.getString(Helper.PREF_TRANSAKSI, "");
                    final TransaksiDAO t= gson2.fromJson(json2, TransaksiDAO.class);
                    if(t != null)
                    {
                        int jumlah= Integer.parseInt(jumlahBeliSparepart.getText().toString());
                        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit= builder.build();
                        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                        Call<TransaksiSparepartDAO> transaksiSparepartDAOCall= apiClient.requestSaveTransaksiSparepart(t.getId_transaksi(), s.getId_sparepart(), jumlah, s.getHarga_jual_sparepart()*jumlah);
                        transaksiSparepartDAOCall.enqueue(new Callback<TransaksiSparepartDAO>() {
                            @Override
                            public void onResponse(Call<TransaksiSparepartDAO> call, Response<TransaksiSparepartDAO> response) {
                                TransaksiSparepartDAO ts= response.body();
                                SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_LIST_BELI_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor= pref.edit();
                                Gson gson= new Gson();
                                String json= gson.toJson(ts);
                                editor.putString(Helper.PREF_LIST_BELI_TRANSAKSI_SPAREPART, json);
                                editor.commit();

                                FancyToast.makeText(Transaksi_sparepart.this, "Berhasil menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                recreate();
                            }

                            @Override
                            public void onFailure(Call<TransaksiSparepartDAO> call, Throwable t) {
                                if(!isNetworkAvailable())
                                {
                                    FancyToast.makeText(Transaksi_sparepart.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                                else
                                {
                                    if (t instanceof IOException) {
                                        FancyToast.makeText(Transaksi_sparepart.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                        // logging probably not necessary
                                    }
                                    else {
                                        FancyToast.makeText(Transaksi_sparepart.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
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
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_BELI_TRANSAKSI_SPAREPART, null);
        editor.commit();
    }

    private void filter(String text) {
        List<SparepartDAO> filteredList = new ArrayList<>();

        for (SparepartDAO item : listSparepart) {
            if (item.getNama_sparepart().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerAdapterSparepartTransaksiSparepartSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(recyclerAdapterSparepartTransaksiSparepartSearch); //show something
        }
    }

    private void loadDataSparepart()
    {
        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);

        Call<List<SparepartDAO>> sparepartDAOcall= apiClient.getAllSparepart();

        sparepartDAOcall.enqueue(new Callback<List<SparepartDAO>>() {
            @Override
            public void onResponse(Call<List<SparepartDAO>> call, Response<List<SparepartDAO>> response) {
                recyclerAdapterSparepartTransaksiSparepartSearch.notifyDataSetChanged();
                recyclerAdapterSparepartTransaksiSparepartSearch= new RecyclerAdapterSparepartTransaksiSparepartSearch(Transaksi_sparepart.this, response.body());
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listSparepart.add(response.body().get(i));
                        Log.d(TAG, "onResponse: =b=b=b=b=b=b=b=b=b=b=b====> " +response.body().get(i).getNama_sparepart());
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
        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_BELI_TRANSAKSI_SPAREPART, null);
        final TransaksiSparepartDAO t= gson2.fromJson(json2, TransaksiSparepartDAO.class);
        if(t != null)
        {
            try
            {
                for(int i= 0; i< 500000000; i++){}
                Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " +t.getNama_sparepart() +", " +t.getKode_sparepart() +", " +t.getJumlah_transaksi_penjualan_sparepart());
                namaSparepart.setText(t.getNama_sparepart() +"(" +t.getKode_sparepart() +")");
                jumlahBeliSparepart.setText(String.valueOf(t.getJumlah_transaksi_penjualan_sparepart()));
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
        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_BELI_TRANSAKSI_SPAREPART, null);
        final TransaksiSparepartDAO ts= gson2.fromJson(json2, TransaksiSparepartDAO.class);

        if(ts != null)
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
                harga_temp= ts.getHarga_jual_sparepart();
                if(harga_temp > 0)
                {
                    final int jumlahBeli_temp= Integer.parseInt(jumlahBeliSparepart.getText().toString());
                    TransaksiSparepartDAO tsDAO= new TransaksiSparepartDAO(jumlahBeli_temp, harga_temp*jumlahBeli_temp);

                    Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit= builder.build();
                    PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                    Call<String> tsDAOCall= apiClient.requestUpdateTransaksiSparepart(tsDAO, ts.getId_transaksi_penjualan_sparepart());
                    tsDAOCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d(TAG, "COBA COBA COBA COBA COBA COBA COBA COBA COBA COBA onResponse: " +ts.getId_transaksi_penjualan_sparepart() +", " +jumlahBeli_temp + ", " +harga_temp);
                            Log.d(TAG, "onResponse: " +response.toString());
//                            try {
//                                Log.d(TAG, "onResponse: " +response.errorBody().string());
//                            }catch (Exception e){}
                            FancyToast.makeText(Transaksi_sparepart.this, "Berhasil mengedit " +namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            recreate();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            FancyToast.makeText(Transaksi_sparepart.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
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
        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_BELI_TRANSAKSI_SPAREPART, null);
        final TransaksiSparepartDAO ts= gson2.fromJson(json2, TransaksiSparepartDAO.class);
        if(ts != null)
        {
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<TransaksiSparepartDAO> tsDAOCall= apiClient.deleteTransaksiSparepart(ts.getId_transaksi_penjualan_sparepart());
            tsDAOCall.enqueue(new Callback<TransaksiSparepartDAO>() {
                @Override
                public void onResponse(Call<TransaksiSparepartDAO> call, Response<TransaksiSparepartDAO> response) {
                    FancyToast.makeText(Transaksi_sparepart.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    recreate();
                }

                @Override
                public void onFailure(Call<TransaksiSparepartDAO> call, Throwable t) {
                    FancyToast.makeText(Transaksi_sparepart.this, "Berhasil menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    recreate();
                }
            });
        }
        else
        {
            FancyToast.makeText(getApplicationContext(), "Pilih sparepart untuk diedit", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
    }
}
