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
import com.toviddd.sitato.Pegawai.Area.DAO.JasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterCabang;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterCabangSearch;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterJasaService;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterJasaServiceSearch;
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
    private EditText namaJS, hargaJS;
    private TextView deleteSearchJS;
    private String TAG= "Jasa Service Activity";
    private String namaKelas= "jasa service";
    private List<JasaServiceDAO> listJS= new ArrayList<>();
    private List<JasaServiceDAO> listJS2= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapterJasaService raJS;
    public int id_jasa_service;
    // search bar
    private RecyclerView recyclerViewSearch;
    private RecyclerAdapterJasaServiceSearch raJSSearch;
    private EditText searchJasaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jasa_service);
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
            case R.id.btnSimpanJasaService:
                simpan();
                kosongkanSearchJasaServicePreferences();
                break;
            case R.id.btnEditJasaService:
                ubah();
                break;
            case R.id.btnHapusJasaService:
                hapus();
                kosongkanSearchJasaServicePreferences();
                break;
            case R.id.btnKosongkanKolom:
                kosongkanKolom();
                kosongkanSearchJasaServicePreferences();
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
        hapus= findViewById(R.id.btnHapusJasaService);
        hapus.setOnClickListener(this);
        ubah= findViewById(R.id.btnEditJasaService);
        ubah.setOnClickListener(this);
        simpan= findViewById(R.id.btnSimpanJasaService);
        simpan.setOnClickListener(this);
        deleteSearchJS= findViewById(R.id.textView_deleteSearchJasaService);

        //search bar
        searchJasaService= findViewById(R.id.editText_searchJasaService);

        namaJS= findViewById(R.id.editText_NamaJasaService);
        hargaJS= findViewById(R.id.editText_HargaJasaService);
    }

    public void buildRecyclerViewTampil()
    {
        recyclerView= findViewById(R.id.recyclerView_JasaService);
        raJS= new RecyclerAdapterJasaService(this, listJS);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(raJS);
    }

    public void buildRecyclerViewSearch()
    {
        recyclerViewSearch= findViewById(R.id.recyclerView_JasaServiceSearch);
        raJSSearch= new RecyclerAdapterJasaServiceSearch(this, listJS2);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
    }

    public void kosongkanKolom()
    {
        namaJS.setText("");
        hargaJS.setText("");
        FancyToast.makeText(JasaService.this, "Kolom dikosongkan", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
    }

    public void isiKolom()
    {
        namaJS.setText("Service busi dan starter");
        hargaJS.setText("15000");
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    public void hapus()
    {
        // get id jasa service
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_JASA_SERVICE, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_JASA_SERVICE, "");
        final JasaServiceDAO js= gson.fromJson(json, JasaServiceDAO.class);

        if(js == null)
        {
            FancyToast.makeText(JasaService.this, "Pilih " +namaKelas +" untuk dihapus", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            id_jasa_service= js.getId_jasa_service();
            Retrofit.Builder builder= new Retrofit
                    .Builder()
                    .baseUrl(Helper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<JasaServiceDAO> jsDAOCall= apiClient.deleteJasaService(id_jasa_service);
            jsDAOCall.enqueue(new Callback<JasaServiceDAO>() {
                @Override
                public void onResponse(Call<JasaServiceDAO> call, Response<JasaServiceDAO> response) {
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
                public void onFailure(Call<JasaServiceDAO> call, Throwable t) {
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
        String namaJS_tampung;
        double hargaJS_tampung;
        namaJS_tampung= namaJS.getText().toString();
        hargaJS_tampung= Double.parseDouble(hargaJS.getText().toString());

        // get id jasa service
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_JASA_SERVICE, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_JASA_SERVICE, "");
        final JasaServiceDAO js= gson.fromJson(json, JasaServiceDAO.class);

        if(js == null)
        {
            FancyToast.makeText(JasaService.this, "Pilih " +namaKelas +" untuk diedit", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            if(namaJS_tampung.isEmpty() || hargaJS.getText().toString().isEmpty())
            {
                FancyToast.makeText(JasaService.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(namaJS_tampung.length()>0 && namaJS_tampung.length()<4 || namaJS_tampung.length()>50 ||
                    hargaJS_tampung <= 0 )
            {
                if(namaJS_tampung.length()<4 || namaJS_tampung.length()>50)
                {
                    FancyToast.makeText(JasaService.this, "Nama " +namaKelas +" harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(hargaJS_tampung <= 0)
                {
                    FancyToast.makeText(JasaService.this, "Harga " +namaKelas +" harus diatas 0", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
            }
            else
            {
                //POST data into API ,,Build retrofit
                Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit= builder.build();
                PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                JasaServiceDAO js2= new JasaServiceDAO(namaJS_tampung, hargaJS_tampung);

                id_jasa_service= js.getId_jasa_service();
                Call<String> jsDAOCall= apiClient.requestUpdateJasaService(js2, id_jasa_service);
                jsDAOCall.enqueue(new Callback<String>() {
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
        String namaJS_tampung;
        double hargaJS_tampung;
        namaJS_tampung= namaJS.getText().toString();
        hargaJS_tampung= Double.parseDouble(hargaJS.getText().toString());

        if(namaJS_tampung.isEmpty() || hargaJS.getText().toString().isEmpty())
        {
            FancyToast.makeText(JasaService.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(namaJS_tampung.length()>0 && namaJS_tampung.length()<4 || namaJS_tampung.length()>50 ||
                hargaJS_tampung <= 0 )
        {
            if(namaJS_tampung.length()<4 || namaJS_tampung.length()>50)
            {
                FancyToast.makeText(JasaService.this, "Nama " +namaKelas +" harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(hargaJS_tampung <= 0)
            {
                FancyToast.makeText(JasaService.this, "Harga " +namaKelas +" harus diatas 0", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
        }
        else
        {
            //POST data into API ,,Build retrofit
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<String> jsDAOCall= apiClient.requestSaveJasaService(namaJS_tampung, hargaJS_tampung);

            jsDAOCall.enqueue(new Callback<String>() {
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
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<JasaServiceDAO>> jsDAOCall= apiClient.getAllJasaService();
        jsDAOCall.enqueue(new Callback<List<JasaServiceDAO>>() {
            @Override
            public void onResponse(Call<List<JasaServiceDAO>> call, Response<List<JasaServiceDAO>> response) {
                // mengambil data dari response, agar listSparepart terisi
                raJS.notifyDataSetChanged();
                raJS= new RecyclerAdapterJasaService(JasaService.this, response.body());
                recyclerView.setAdapter(raJS);

                // mengambil data dari response, agar listSparepart2 terisi
                raJSSearch.notifyDataSetChanged();
                raJSSearch= new RecyclerAdapterJasaServiceSearch(JasaService.this, response.body());
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listJS2.add(response.body().get(i));
                    }
                }
                FancyToast.makeText(JasaService.this, "Berhasil menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
            }

            @Override
            public void onFailure(Call<List<JasaServiceDAO>> call, Throwable t) {
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
        deleteSearchJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchJasaService.setText("");
            }
        });

        searchJasaService.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(recyclerViewSearch.getAdapter() != null)
                {
                    loadSearchJasaServicePreferences();
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

    private void loadSearchJasaServicePreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_JASA_SERVICE, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_JASA_SERVICE, "");
        final JasaServiceDAO js= gson.fromJson(json, JasaServiceDAO.class);

        if(js != null)
        {
            namaJS.setText(js.getNamaJasaService());
            hargaJS.setText(String.valueOf(js.getHargaJasaService()));
        }
    }

    private void kosongkanSearchJasaServicePreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_JASA_SERVICE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_JASA_SERVICE, null);
        editor.commit();
    }

    private void filter(String text) {
        List<JasaServiceDAO> filteredList = new ArrayList<>();

        for (JasaServiceDAO item : listJS2) {
            if (item.getNamaJasaService().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        raJSSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(raJSSearch); //show something
        }
    }

    private void referensi()
    {
        // https://codinginflow.com/tutorials/android/recyclerview-edittext-search
        // http://androidbitmaps.blogspot.com/2015/04/loading-images-in-android-part-iii-pick.html
    }

}

