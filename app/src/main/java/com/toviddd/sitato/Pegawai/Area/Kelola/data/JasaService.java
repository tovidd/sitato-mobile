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
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterCabang;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterCabangSearch;
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

public class JasaService extends AppCompatActivity implements View.OnClickListener {

    private Button kosongkanKolom, hapus, ubah, simpan;
    private EditText namaCabang, alamatCabang, noTelpCabang;
    private TextView deleteSearchCabang;
    private String TAG= "Cabang Activity";
    private String namaKelas= "cabang";
    private List<CabangDAO> listCabang= new ArrayList<>();
    private List<CabangDAO> listCabang2= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapterCabang recyclerAdapterCabang;
    private static final int PICK_IMAGE = 100;
    public static final String PREF_CABANG= "PREF_CABANG";
    public int id_cabang;
    // search bar
    private RecyclerView recyclerViewSearch;
    private RecyclerAdapterCabangSearch recyclerAdapterCabangSearch;
    private EditText searchCabang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabang);
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
            case R.id.btnSimpanCabang:
                simpan();
                kosongkanSearchCabangPreferences();
                break;
            case R.id.btnEditCabang:
                ubah();
                break;
            case R.id.btnHapusCabang:
                hapus();
                kosongkanSearchCabangPreferences();
                break;
            case R.id.btnKosongkanKolom:
                kosongkanKolom();
                kosongkanSearchCabangPreferences();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(JasaService.this, PegawaiMainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(JasaService.this, PegawaiMainActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public void initAttribute()
    {
        kosongkanKolom= findViewById(R.id.btnKosongkanKolom);
        kosongkanKolom.setOnClickListener(this);
        hapus= findViewById(R.id.btnHapusCabang);
        hapus.setOnClickListener(this);
        ubah= findViewById(R.id.btnEditCabang);
        ubah.setOnClickListener(this);
        simpan= findViewById(R.id.btnSimpanCabang);
        simpan.setOnClickListener(this);
        deleteSearchCabang= findViewById(R.id.textView_deleteSearchCabang);

        //search bar
        searchCabang= findViewById(R.id.editText_searchCabang);

        namaCabang= findViewById(R.id.editText_NamaCabang);
        noTelpCabang= findViewById(R.id.editText_NoTelponCabang);
        alamatCabang= findViewById(R.id.editText_AlamatCabang);
    }

    public void buildRecyclerViewTampil()
    {
        recyclerView= findViewById(R.id.recyclerView_Cabang);
        recyclerAdapterCabang= new RecyclerAdapterCabang(this, listCabang);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterCabang);
    }

    public void buildRecyclerViewSearch()
    {
        recyclerViewSearch= findViewById(R.id.recyclerView_CabangSearch);
        recyclerAdapterCabangSearch= new RecyclerAdapterCabangSearch(this, listCabang2);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
    }

    public void kosongkanKolom()
    {
        namaCabang.setText("");
        noTelpCabang.setText("");
        alamatCabang.setText("");
        FancyToast.makeText(JasaService.this, "Kolom dikosongkan", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
    }

    public void isiKolom()
    {
        namaCabang.setText("AAA");
        noTelpCabang.setText("123");
        alamatCabang.setText("AAA");
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    public void hapus()
    {
        // get id cabang
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_CABANG, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_CABANG, "");
        final CabangDAO s= gson.fromJson(json, CabangDAO.class);

        if(s == null)
        {
            FancyToast.makeText(JasaService.this, "Pilih " +namaKelas +" untuk dihapus", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            id_cabang= s.getId_cabang();
            Retrofit.Builder builder= new Retrofit
                    .Builder()
                    .baseUrl(Helper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<CabangDAO> cabangDAOCall= apiClient.deleteCabang(id_cabang);
            cabangDAOCall.enqueue(new Callback<CabangDAO>() {
                @Override
                public void onResponse(Call<CabangDAO> call, Response<CabangDAO> response) {
                    if(response.code() == 404)
                    {
                        FancyToast.makeText(JasaService.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(JasaService.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }

                @Override
                public void onFailure(Call<CabangDAO> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(JasaService.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(JasaService.this, "Berhasil menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
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
        String namaCabang_tampung, noTelpCabang_tampung, alamatCabang_tampung;
        namaCabang_tampung= namaCabang.getText().toString();
        noTelpCabang_tampung= noTelpCabang.getText().toString();
        alamatCabang_tampung= alamatCabang.getText().toString();

        // get id cabang
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_CABANG, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_CABANG, "");
        final CabangDAO s= gson.fromJson(json, CabangDAO.class);

        if(s == null)
        {
            FancyToast.makeText(JasaService.this, "Pilih " +namaKelas +" untuk diedit", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            if(namaCabang_tampung.isEmpty() || noTelpCabang_tampung.isEmpty() || alamatCabang_tampung.isEmpty())
            {
                FancyToast.makeText(JasaService.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(namaCabang_tampung.length()>0 && namaCabang_tampung.length()<4 || namaCabang_tampung.length()>50 ||
                    noTelpCabang_tampung.length()>0 && noTelpCabang_tampung.length()<12 || noTelpCabang_tampung.length()>13 ||
                    alamatCabang_tampung.length()>0 && alamatCabang_tampung.length()<10 || alamatCabang_tampung.length()>50)
            {
                if(namaCabang_tampung.length()<4 || namaCabang_tampung.length()>50)
                {
                    FancyToast.makeText(JasaService.this, "Nama " +namaKelas +" harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(noTelpCabang_tampung.length()<12 || noTelpCabang_tampung.length()>13)
                {
                    FancyToast.makeText(JasaService.this, "Nomor telepon harus 12-13 digit", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(alamatCabang_tampung.length()<10 || alamatCabang_tampung.length()>50)
                {
                    FancyToast.makeText(JasaService.this, "Alamat " +namaKelas +" harus 10-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
            }
            else
            {
                //POST data into API ,,Build retrofit
                Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit= builder.build();
                PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                CabangDAO sp= new CabangDAO(namaCabang_tampung, noTelpCabang_tampung, alamatCabang_tampung);

                id_cabang= s.getId_cabang();
                Call<String> cabangDAOCall= apiClient.requestUpdateCabang(sp, id_cabang);
                cabangDAOCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        FancyToast.makeText(JasaService.this, "Berhasil mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        recreate();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(!isNetworkAvailable())
                        {
                            FancyToast.makeText(JasaService.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        else
                        {
                            if (t instanceof IOException) {
                                FancyToast.makeText(JasaService.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                // logging probably not necessary
                            }
                            else {
                                FancyToast.makeText(JasaService.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            } // end of else
        }
    }

    public void simpan()
    {
        String namaCabang_tampung, noTelpCabang_tampung, alamatCabang_tampung;
        namaCabang_tampung= namaCabang.getText().toString();
        noTelpCabang_tampung= noTelpCabang.getText().toString();
        alamatCabang_tampung= alamatCabang.getText().toString();
        if(namaCabang_tampung.isEmpty() || noTelpCabang_tampung.isEmpty() || alamatCabang_tampung.isEmpty())
        {
            FancyToast.makeText(JasaService.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(namaCabang_tampung.length()>0 && namaCabang_tampung.length()<4 || namaCabang_tampung.length()>50 ||
                noTelpCabang_tampung.length()>0 && noTelpCabang_tampung.length()<12 || noTelpCabang_tampung.length()>13 ||
                alamatCabang_tampung.length()>0 && alamatCabang_tampung.length()<10 || alamatCabang_tampung.length()>50)
        {
            if(namaCabang_tampung.length()<4 || namaCabang_tampung.length()>50)
            {
                FancyToast.makeText(JasaService.this, "Nama " +namaKelas +" harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(noTelpCabang_tampung.length()<12 || noTelpCabang_tampung.length()>13)
            {
                FancyToast.makeText(JasaService.this, "Nomor telepon harus 12-13 digit", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(alamatCabang_tampung.length()<10 || alamatCabang_tampung.length()>50)
            {
                FancyToast.makeText(JasaService.this, "Alamat " +namaKelas +" harus 10-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
        }
    else
    {
        //POST data into API ,,Build retrofit
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<String> cabangDAOCall= apiClient.requestSaveCabang(namaCabang_tampung, noTelpCabang_tampung, alamatCabang_tampung);

        cabangDAOCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                FancyToast.makeText(JasaService.this, "Berhasil menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                recreate();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if(!isNetworkAvailable())
                {
                    FancyToast.makeText(JasaService.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
                else
                {
                    if (t instanceof IOException) {
                        FancyToast.makeText(JasaService.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        // logging probably not necessary
                    }
                    else {
                        FancyToast.makeText(JasaService.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }
            }
        });
    }
}

    public void tampil()
    {
        FancyToast.makeText(JasaService.this, "Sedang menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);

        Call<List<CabangDAO>> cabangDAOCall= apiClient.getAllCabang();

        cabangDAOCall.enqueue(new Callback<List<CabangDAO>>() {
            @Override
            public void onResponse(Call<List<CabangDAO>> call, Response<List<CabangDAO>> response) {
                // mengambil data dari response, agar listSparepart terisi
                recyclerAdapterCabang.notifyDataSetChanged();
                recyclerAdapterCabang= new RecyclerAdapterCabang(JasaService.this, response.body());
                recyclerView.setAdapter(recyclerAdapterCabang);

                // mengambil data dari response, agar listSparepart2 terisi
                recyclerAdapterCabangSearch.notifyDataSetChanged();
                recyclerAdapterCabangSearch= new RecyclerAdapterCabangSearch(JasaService.this, response.body());
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listCabang2.add(response.body().get(i));
                    }
                }
                FancyToast.makeText(JasaService.this, "Berhasil menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
            }

            @Override
            public void onFailure(Call<List<CabangDAO>> call, Throwable t) {
                if(!isNetworkAvailable())
                {
                    FancyToast.makeText(JasaService.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
                else
                {
                    if (t instanceof IOException) {
                        FancyToast.makeText(JasaService.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        // logging probably not necessary
                    }
                    else {
                        FancyToast.makeText(JasaService.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }
            }
        });
    }

    public void cari()
    {
        deleteSearchCabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCabang.setText("");
            }
        });

        searchCabang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(recyclerViewSearch.getAdapter() != null)
                {
                    loadSearchCabangPreferences();
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

    private void loadSearchCabangPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_CABANG, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_CABANG, "");
        final CabangDAO s= gson.fromJson(json, CabangDAO.class);

        if(s != null)
        {
            namaCabang.setText(s.getNama_cabang());
            noTelpCabang.setText(s.getNo_telepon_cabang());
            alamatCabang.setText(s.getAlamat_cabang());
        }
    }

    private void kosongkanSearchCabangPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_CABANG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(PREF_CABANG, null);
        editor.commit();
    }

    private void filter(String text) {
        List<CabangDAO> filteredList = new ArrayList<>();

        for (CabangDAO item : listCabang2) {
            if (item.getNama_cabang().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerAdapterCabangSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(recyclerAdapterCabangSearch); //show something
        }
    }

    private void referensi()
    {
        // https://codinginflow.com/tutorials/android/recyclerview-edittext-search
        // http://androidbitmaps.blogspot.com/2015/04/loading-images-in-android-part-iii-pick.html
    }

}

