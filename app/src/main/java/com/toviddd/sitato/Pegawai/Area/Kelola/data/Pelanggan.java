package com.toviddd.sitato.Pegawai.Area.Kelola.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SupplierDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterPelanggan;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterPelangganSearch;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterSupplier;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterSupplierSearch;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pelanggan extends AppCompatActivity implements View.OnClickListener {

    private Button kosongkanKolom, hapus, ubah, simpan;
    private EditText namaPelanggan, alamatPelanggan, noTelpPelanggan;
    private TextView deleteSearchPelanggan;
    private String TAG= "Pelanggan Activity";
    private String namaKelas= "pelanggan";
    private List<PelangganDAO> listPelanggan= new ArrayList<>();
    private List<PelangganDAO> listPelanggan2= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapterPelanggan recyclerAdapterPelanggan;
    private static final int PICK_IMAGE = 100;
    public int id_pelanggan;
    // search bar
    private RecyclerView recyclerViewSearch;
    private RecyclerAdapterPelangganSearch recyclerAdapterPelangganSearch;
    private EditText searchPelanggan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelanggan);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Helper.networkPermission();

        initAttribute();
        buildRecyclerViewTampil();
        tampil();
        buildRecyclerViewSearch();
        cari();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnSimpanPelanggan:
                simpan();
                kosongkanSearchPelangganPreferences();
                break;
            case R.id.btnEditPelanggan:
                ubah();
                break;
            case R.id.btnHapusPelanggan:
                hapus();
                kosongkanSearchPelangganPreferences();
                break;
            case R.id.btnKosongkanKolom:
                kosongkanKolom();
                kosongkanSearchPelangganPreferences();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Pelanggan.this, PegawaiMainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initAttribute()
    {
        kosongkanKolom= findViewById(R.id.btnKosongkanKolom);
        kosongkanKolom.setOnClickListener(this);
        hapus= findViewById(R.id.btnHapusPelanggan);
        hapus.setOnClickListener(this);
        ubah= findViewById(R.id.btnEditPelanggan);
        ubah.setOnClickListener(this);
        simpan= findViewById(R.id.btnSimpanPelanggan);
        simpan.setOnClickListener(this);
        deleteSearchPelanggan= findViewById(R.id.textView_deleteSearchPelanggan);

        //search bar
        searchPelanggan= findViewById(R.id.editText_searchPelanggan);

        namaPelanggan= findViewById(R.id.editText_NamaPelanggan);
        noTelpPelanggan= findViewById(R.id.editText_NoTelponPelanggan);
        alamatPelanggan= findViewById(R.id.editText_AlamatPelanggan);
    }

    public void buildRecyclerViewTampil()
    {
        recyclerView= findViewById(R.id.recyclerView_pelanggan);
        recyclerAdapterPelanggan= new RecyclerAdapterPelanggan(this, listPelanggan);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterPelanggan);
    }

    public void buildRecyclerViewSearch()
    {
        recyclerViewSearch= findViewById(R.id.recyclerView_PelangganSearch);
        recyclerAdapterPelangganSearch= new RecyclerAdapterPelangganSearch(this, listPelanggan2);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
    }

    private void kosongkanKolom()
    {
        namaPelanggan.setText("");
        noTelpPelanggan.setText("");
        alamatPelanggan.setText("");
        FancyToast.makeText(Pelanggan.this, "Kolom dikosongkan", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
    }

    private void isiKolom()
    {
        namaPelanggan.setText("AAA");
        noTelpPelanggan.setText("AAA");
        alamatPelanggan.setText("AAA");
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    public void hapus()
    {
        // get id sparepart
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_PELANGGAN, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_PELANGGAN, "");
        final PelangganDAO s= gson.fromJson(json, PelangganDAO.class);

        if(s == null)
        {
            FancyToast.makeText(Pelanggan.this, "Pilih " +namaKelas +" untuk dihapus", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            id_pelanggan= s.getId_pelanggan();
            Retrofit.Builder builder= new Retrofit
                    .Builder()
                    .baseUrl(Helper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<PelangganDAO> pelangganDAOCall= apiClient.deletePelanggan(id_pelanggan);
            pelangganDAOCall.enqueue(new Callback<PelangganDAO>() {
                @Override
                public void onResponse(Call<PelangganDAO> call, Response<PelangganDAO> response) {
                    if(response.code() == 404)
                    {
                        FancyToast.makeText(Pelanggan.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(Pelanggan.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }

                @Override
                public void onFailure(Call<PelangganDAO> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(Pelanggan.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(Pelanggan.this, "Berhasil menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        try {
                            Thread.sleep(1000);
                        }catch (Exception e){
                            Log.d(TAG, "onFailure: Thread.sleep ---> "+e.getMessage());
                        }
                        recreate();
                    }
                }
            });
        }
    }

    public void ubah()
    {
        String namaPelanggan_tampung, noTelpPelanggan_tampung, alamatPelanggan_tampung;
        namaPelanggan_tampung= namaPelanggan.getText().toString();
        noTelpPelanggan_tampung= noTelpPelanggan.getText().toString();
        alamatPelanggan_tampung= alamatPelanggan.getText().toString();

        // get id pelanggan
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_PELANGGAN, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_PELANGGAN, "");
        final PelangganDAO s= gson.fromJson(json, PelangganDAO.class);

        if(s == null)
        {
            FancyToast.makeText(Pelanggan.this, "Pilih " +namaKelas +" untuk diedit", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            if(namaPelanggan_tampung.isEmpty() || noTelpPelanggan_tampung.isEmpty() || alamatPelanggan_tampung.isEmpty())
            {
                FancyToast.makeText(Pelanggan.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(namaPelanggan_tampung.length()>0 && namaPelanggan_tampung.length()<4 || namaPelanggan_tampung.length()>50 ||
                    noTelpPelanggan_tampung.length()>0 && noTelpPelanggan_tampung.length()<12 || noTelpPelanggan_tampung.length()>13 ||
                    alamatPelanggan_tampung.length()>0 && alamatPelanggan_tampung.length()<10 || alamatPelanggan_tampung.length()>50)
            {
                if(namaPelanggan_tampung.length()<4 || namaPelanggan_tampung.length()>50)
                {
                    FancyToast.makeText(Pelanggan.this, "Nama " +namaKelas +" harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(noTelpPelanggan_tampung.length()<12 || noTelpPelanggan_tampung.length()>13)
                {
                    FancyToast.makeText(Pelanggan.this, "Nomor telepon harus 12-13 digit", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(alamatPelanggan_tampung.length()<10 || alamatPelanggan_tampung.length()>50)
                {
                    FancyToast.makeText(Pelanggan.this, "Alamat " +namaKelas +" harus 10-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
            }
            else
            {
                //POST data into API ,,Build retrofit
                Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit= builder.build();
                PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                PelangganDAO sp= new PelangganDAO(namaPelanggan_tampung, noTelpPelanggan_tampung, alamatPelanggan_tampung);

                id_pelanggan= s.getId_pelanggan();
                Call<String> pelangganDAOCall= apiClient.requestUpdatePelanggan(sp, id_pelanggan);
                pelangganDAOCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        FancyToast.makeText(Pelanggan.this, "Berhasil mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        recreate();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(!isNetworkAvailable())
                        {
                            FancyToast.makeText(Pelanggan.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        else
                        {
                            if (t instanceof IOException) {
                                FancyToast.makeText(Pelanggan.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                // logging probably not necessary
                            }
                            else {
                                FancyToast.makeText(Pelanggan.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            } // end of else
        }
    }

    public void simpan()
    {
        String namaPelanggan_tampung, noTelpPelanggan_tampung, alamatPelanggan_tampung;
        namaPelanggan_tampung= namaPelanggan.getText().toString();
        noTelpPelanggan_tampung= noTelpPelanggan.getText().toString();
        alamatPelanggan_tampung= alamatPelanggan.getText().toString();
        if(namaPelanggan_tampung.isEmpty() || noTelpPelanggan_tampung.isEmpty() || alamatPelanggan_tampung.isEmpty())
        {
            FancyToast.makeText(Pelanggan.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(namaPelanggan_tampung.length()>0 && namaPelanggan_tampung.length()<4 || namaPelanggan_tampung.length()>50 ||
                noTelpPelanggan_tampung.length()>0 && noTelpPelanggan_tampung.length()<12 || noTelpPelanggan_tampung.length()>13 ||
            alamatPelanggan_tampung.length()>0 && alamatPelanggan_tampung.length()<10 || alamatPelanggan_tampung.length()>50)
        {
            if(namaPelanggan_tampung.length()<4 || namaPelanggan_tampung.length()>50)
            {
                FancyToast.makeText(Pelanggan.this, "Nama " +namaKelas +" harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(noTelpPelanggan_tampung.length()<12 || noTelpPelanggan_tampung.length()>13)
            {
                FancyToast.makeText(Pelanggan.this, "Nomor telepon harus 12-13 digit", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(alamatPelanggan_tampung.length()<10 || alamatPelanggan_tampung.length()>50)
            {
                FancyToast.makeText(Pelanggan.this, "Alamat " +namaKelas +" harus 10-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
        }
        else
        {
            //POST data into API ,,Build retrofit
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<PelangganDAO> pelangganDAOCall= apiClient.requestSavePelanggan(namaPelanggan_tampung, noTelpPelanggan_tampung, alamatPelanggan_tampung);

            pelangganDAOCall.enqueue(new Callback<PelangganDAO>() {
                @Override
                public void onResponse(Call<PelangganDAO> call, Response<PelangganDAO> response) {
                    PelangganDAO p= response.body();
                    SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_PELANGGAN, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= pref.edit();
                    Gson gson= new Gson();
                    String json= gson.toJson(p);
                    editor.putString(Helper.PREF_PELANGGAN, json);
                    editor.commit();

                    FancyToast.makeText(Pelanggan.this, "Berhasil menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    recreate();
                }

                @Override
                public void onFailure(Call<PelangganDAO> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(Pelanggan.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        if (t instanceof IOException) {
                            FancyToast.makeText(Pelanggan.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            // logging probably not necessary
                        }
                        else {
                            FancyToast.makeText(Pelanggan.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                    }
                }
            });
        }
    }

    public void tampil()
    {
        FancyToast.makeText(Pelanggan.this, "Sedang menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);

        Call<List<PelangganDAO>> pelangganDAOCall= apiClient.getAllPelanggan();

        pelangganDAOCall.enqueue(new Callback<List<PelangganDAO>>() {
            @Override
            public void onResponse(Call<List<PelangganDAO>> call, Response<List<PelangganDAO>> response) {
                // mengambil data dari response, agar listSparepart terisi
                recyclerAdapterPelanggan.notifyDataSetChanged();
                recyclerAdapterPelanggan= new RecyclerAdapterPelanggan(Pelanggan.this, response.body());
                recyclerView.setAdapter(recyclerAdapterPelanggan);

                // mengambil data dari response, agar listSparepart2 terisi
                recyclerAdapterPelangganSearch.notifyDataSetChanged();
                recyclerAdapterPelangganSearch= new RecyclerAdapterPelangganSearch(Pelanggan.this, response.body());
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listPelanggan2.add(response.body().get(i));
                    }
                }
                FancyToast.makeText(Pelanggan.this, "Berhasil menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
            }

            @Override
            public void onFailure(Call<List<PelangganDAO>> call, Throwable t) {
                if(!isNetworkAvailable())
                {
                    FancyToast.makeText(Pelanggan.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
                else
                {
                    if (t instanceof IOException) {
                        FancyToast.makeText(Pelanggan.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        // logging probably not necessary
                    }
                    else {
                        FancyToast.makeText(Pelanggan.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }
            }
        });
    }

    public void cari()
    {
        deleteSearchPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPelanggan.setText("");
            }
        });

        searchPelanggan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(recyclerViewSearch.getAdapter() != null)
                {
                    loadSearchPelangganPreferences();
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

    private void loadSearchPelangganPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_PELANGGAN, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_PELANGGAN, "");
        final PelangganDAO s= gson.fromJson(json, PelangganDAO.class);

        if(s != null)
        {
            namaPelanggan.setText(s.getNama_pelanggan());
            noTelpPelanggan.setText(s.getNo_telepon_pelanggan());
            alamatPelanggan.setText(s.getAlamat_pelanggan());
        }
    }

    private void kosongkanSearchPelangganPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_PELANGGAN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_PELANGGAN, null);
        editor.commit();
    }

    private void filter(String text) {
        List<PelangganDAO> filteredList = new ArrayList<>();

        for (PelangganDAO item : listPelanggan2) {
            if (item.getNama_pelanggan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerAdapterPelangganSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(recyclerAdapterPelangganSearch); //show something
        }
    }

    private void referensi()
    {
        // https://codinginflow.com/tutorials/android/recyclerview-edittext-search
        // http://androidbitmaps.blogspot.com/2015/04/loading-images-in-android-part-iii-pick.html
    }

}

