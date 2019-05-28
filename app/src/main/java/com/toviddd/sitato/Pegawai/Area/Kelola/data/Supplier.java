package com.toviddd.sitato.Pegawai.Area.Kelola.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SupplierDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterSparepart;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterSparepartSearch;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterSupplier;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterSupplierSearch;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Supplier extends AppCompatActivity implements View.OnClickListener {

    private Button kosongkanKolom, hapus, ubah, simpan;
    private EditText namaSupplier, alamatSupplier, noTelpSupplier, namaSales;
    private TextView deleteSearchSupplier;
    private String TAG= "Supplier Activity";
    private String namaKelas= "supplier";
    private List<SupplierDAO> listSupplier= new ArrayList<>();
    private List<SupplierDAO> listSupplier2= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapterSupplier recyclerAdapterSupplier;
    private static final int PICK_IMAGE = 100;
    public static final String PREF_SUPPLIER= "PREF_SUPPLIER";
    public int id_suplier;
    // search bar
    private RecyclerView recyclerViewSearch;
    private RecyclerAdapterSupplierSearch recyclerAdapterSupplierSearch;
    private EditText searchSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
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
            case R.id.btnSimpanSupplier:
                simpan();
                kosongkanSearchSupplierPreferences();
                break;
            case R.id.btnEditSupplier:
                ubah();
                break;
            case R.id.btnHapusSupplier:
                hapus();
                kosongkanSearchSupplierPreferences();
                break;
            case R.id.btnKosongkanKolom:
                kosongkanKolom();
                kosongkanSearchSupplierPreferences();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Supplier.this, PegawaiMainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initAttribute()
    {
        kosongkanKolom= findViewById(R.id.btnKosongkanKolom);
        kosongkanKolom.setOnClickListener(this);
        hapus= findViewById(R.id.btnHapusSupplier);
        hapus.setOnClickListener(this);
        ubah= findViewById(R.id.btnEditSupplier);
        ubah.setOnClickListener(this);
        simpan= findViewById(R.id.btnSimpanSupplier);
        simpan.setOnClickListener(this);
        deleteSearchSupplier= findViewById(R.id.textView_deleteSearchSupplier);

        //search bar
        searchSupplier= findViewById(R.id.editText_searchSupplier);

        namaSupplier= findViewById(R.id.editText_NamaSupplier);
        noTelpSupplier= findViewById(R.id.editText_NoTelponSupplier);
        alamatSupplier= findViewById(R.id.editText_AlamatSupplier);
        namaSales= findViewById(R.id.editText_NamaSales);
    }

    public void buildRecyclerViewTampil()
    {
        recyclerView= findViewById(R.id.recyclerView_supplier);
        recyclerAdapterSupplier= new RecyclerAdapterSupplier(this, listSupplier);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterSupplier);
    }

    public void buildRecyclerViewSearch()
    {
        recyclerViewSearch= findViewById(R.id.recyclerView_SupplierSearch);
        recyclerAdapterSupplierSearch= new RecyclerAdapterSupplierSearch(this, listSupplier2);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
    }

    public void kosongkanKolom()
    {
        namaSupplier.setText("");
        noTelpSupplier.setText("");
        alamatSupplier.setText("");
        namaSales.setText("");
        FancyToast.makeText(Supplier.this, "Kolom dikosongkan", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
    }

    public void isiKolom()
    {
        namaSupplier.setText("AAA");
        noTelpSupplier.setText("AAA");
        alamatSupplier.setText("AAA");
        namaSales.setText("AAA");
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
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_SUPPLIER, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_SUPPLIER, "");
        final SupplierDAO s= gson.fromJson(json, SupplierDAO.class);

        if(s == null)
        {
            FancyToast.makeText(Supplier.this, "Pilih " +namaKelas +" untuk dihapus", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            id_suplier= s.getId_supplier();
            Retrofit.Builder builder= new Retrofit
                    .Builder()
                    .baseUrl(Helper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<SupplierDAO> sparepartDAOCall= apiClient.deleteSupplier(id_suplier);
            sparepartDAOCall.enqueue(new Callback<SupplierDAO>() {
                @Override
                public void onResponse(Call<SupplierDAO> call, Response<SupplierDAO> response) {
                    if(response.code() == 404)
                    {
                        FancyToast.makeText(Supplier.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(Supplier.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }

                @Override
                public void onFailure(Call<SupplierDAO> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(Supplier.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(Supplier.this, "Berhasil menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
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
        String namaSupplier_tampung, noTelpSupplier_tampung, alamatSupplier_tampung, namaSales_tampung;
        namaSupplier_tampung= namaSupplier.getText().toString();
        noTelpSupplier_tampung= noTelpSupplier.getText().toString();
        alamatSupplier_tampung= alamatSupplier.getText().toString();
        namaSales_tampung= namaSales.getText().toString();

        // get id supp
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_SUPPLIER, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_SUPPLIER, "");
        final SupplierDAO s= gson.fromJson(json, SupplierDAO.class);

        if(s == null)
        {
            FancyToast.makeText(Supplier.this, "Pilih " +namaKelas +" untuk diedit", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            if(namaSupplier_tampung.isEmpty() || noTelpSupplier_tampung.isEmpty() || alamatSupplier_tampung.isEmpty() || namaSales_tampung.isEmpty())
            {
                FancyToast.makeText(Supplier.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(namaSupplier_tampung.length()>0 && namaSupplier_tampung.length()<4 || namaSupplier_tampung.length()>50 ||
                    noTelpSupplier_tampung.length()>0 && noTelpSupplier_tampung.length()<12 || noTelpSupplier_tampung.length()>13 ||
                    alamatSupplier_tampung.length()>0 && alamatSupplier_tampung.length()<10 || alamatSupplier_tampung.length()>50 ||
                    namaSales_tampung.length()>0 && namaSales_tampung.length()<4 || namaSales_tampung.length()>50)
            {
                if(namaSupplier_tampung.length()<4 || namaSupplier_tampung.length()>50)
                {
                    FancyToast.makeText(Supplier.this, "Nama " +namaKelas +" harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(noTelpSupplier_tampung.length()<12 || noTelpSupplier_tampung.length()>13)
                {
                    FancyToast.makeText(Supplier.this, "Nomor telepon harus 12-13 digit", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(alamatSupplier_tampung.length()<10 || alamatSupplier_tampung.length()>50)
                {
                    FancyToast.makeText(Supplier.this, "Alamat " +namaKelas +" harus 10-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(namaSales_tampung.length()<4 || namaSales_tampung.length()>50)
                {
                    FancyToast.makeText(Supplier.this, "Nama sales harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
            }
            else
            {
                //POST data into API ,,Build retrofit
                Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit= builder.build();
                PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                SupplierDAO sp= new SupplierDAO(namaSupplier_tampung, noTelpSupplier_tampung, alamatSupplier_tampung, namaSales_tampung);

                id_suplier= s.getId_supplier();
                Call<String> sparepartDAOCall= apiClient.requestUpdateSupplier(sp, id_suplier);
                sparepartDAOCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        FancyToast.makeText(Supplier.this, "Berhasil mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        recreate();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(!isNetworkAvailable())
                        {
                            FancyToast.makeText(Supplier.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        else
                        {
                            if (t instanceof IOException) {
                                FancyToast.makeText(Supplier.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                // logging probably not necessary
                            }
                            else {
                                FancyToast.makeText(Supplier.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                // todo log to some central bug tracking service
                            }
                        }
                    }
                });
            } // end of else
        }
    }

    public void simpan()
    {
        String namaSupplier_tampung, noTelpSupplier_tampung, alamatSupplier_tampung, namaSales_tampung;
        namaSupplier_tampung= namaSupplier.getText().toString();
        noTelpSupplier_tampung= noTelpSupplier.getText().toString();
        alamatSupplier_tampung= alamatSupplier.getText().toString();
        namaSales_tampung= namaSales.getText().toString();
        if(namaSupplier_tampung.isEmpty() || noTelpSupplier_tampung.isEmpty() || alamatSupplier_tampung.isEmpty() || namaSales_tampung.isEmpty())
        {
            FancyToast.makeText(Supplier.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(namaSupplier_tampung.length()>0 && namaSupplier_tampung.length()<4 || namaSupplier_tampung.length()>50 ||
                noTelpSupplier_tampung.length()>0 && noTelpSupplier_tampung.length()<4 || noTelpSupplier_tampung.length()>13 ||
                alamatSupplier_tampung.length()>0 && alamatSupplier_tampung.length()<12 || alamatSupplier_tampung.length()>50 ||
                namaSales_tampung.length()>0 && namaSales_tampung.length()<4 || namaSales_tampung.length()>50)
        {
            if(namaSupplier_tampung.length()<4 || namaSupplier_tampung.length()>50)
            {
                FancyToast.makeText(Supplier.this, "Nama " +namaKelas +" harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(noTelpSupplier_tampung.length()<12 || noTelpSupplier_tampung.length()>13)
            {
                FancyToast.makeText(Supplier.this, "Nomor telepon harus 12-13 digit", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(alamatSupplier_tampung.length()<10 || alamatSupplier_tampung.length()>50)
            {
                FancyToast.makeText(Supplier.this, "Alamat " +namaKelas +" harus 10-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(namaSales_tampung.length()<4 || namaSales_tampung.length()>50)
            {
                FancyToast.makeText(Supplier.this, "Nama sales harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
        }
        else
        {
            //POST data into API ,,Build retrofit
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<String> sparepartDAOCall= apiClient.requestSaveSupplier(namaSupplier_tampung, noTelpSupplier_tampung, alamatSupplier_tampung, namaSales_tampung);

            sparepartDAOCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    FancyToast.makeText(Supplier.this, "Berhasil menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    recreate();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(Supplier.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        if (t instanceof IOException) {
                            FancyToast.makeText(Supplier.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            // logging probably not necessary
                        }
                        else {
                            FancyToast.makeText(Supplier.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            // todo log to some central bug tracking service
                        }
                    }
                }
            });
        }
    }

    public void tampil()
    {
        FancyToast.makeText(Supplier.this, "Sedang menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);

        Call<List<SupplierDAO>> sparepartDAOCall= apiClient.getAllSupplier();

        sparepartDAOCall.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                // mengambil data dari response, agar listSparepart terisi
                recyclerAdapterSupplier.notifyDataSetChanged();
                recyclerAdapterSupplier= new RecyclerAdapterSupplier(Supplier.this, response.body());
                recyclerView.setAdapter(recyclerAdapterSupplier);

                // mengambil data dari response, agar listSparepart2 terisi
                recyclerAdapterSupplierSearch.notifyDataSetChanged();
                recyclerAdapterSupplierSearch= new RecyclerAdapterSupplierSearch(Supplier.this, response.body());
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listSupplier2.add(response.body().get(i));
                    }
                }
                FancyToast.makeText(Supplier.this, "Berhasil menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
            }

            @Override
            public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {
                if(!isNetworkAvailable())
                {
                    FancyToast.makeText(Supplier.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
                else
                {
                    if (t instanceof IOException) {
                        FancyToast.makeText(Supplier.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        // logging probably not necessary
                    }
                    else {
                        FancyToast.makeText(Supplier.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        // todo log to some central bug tracking service
                    }
                }
            }
        });
    }

    public void cari()
    {
        deleteSearchSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSupplier.setText("");
            }
        });

        searchSupplier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(recyclerViewSearch.getAdapter() != null)
                {
                    loadSearchSupplierPreferences();
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

    private void loadSearchSupplierPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_SUPPLIER, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_SUPPLIER, "");
        final SupplierDAO s= gson.fromJson(json, SupplierDAO.class);

        if(s != null)
        {
            namaSupplier.setText(s.getNama_supplier());
            noTelpSupplier.setText(s.getNo_telepon_supplier());
            alamatSupplier.setText(s.getAlamat_supplier());
            namaSales.setText(s.getNama_sales());
        }
    }

    private void kosongkanSearchSupplierPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_SUPPLIER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(PREF_SUPPLIER, null);
        editor.commit();
    }

    private void filter(String text) {
        List<SupplierDAO> filteredList = new ArrayList<>();

        for (SupplierDAO item : listSupplier2) {
            if (item.getNama_supplier().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerAdapterSupplierSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(recyclerAdapterSupplierSearch); //show something
        }
    }

    private void referensi()
    {
        // https://codinginflow.com/tutorials/android/recyclerview-edittext-search
        // http://androidbitmaps.blogspot.com/2015/04/loading-images-in-android-part-iii-pick.html
    }

}

