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
import com.toviddd.sitato.Pegawai.Area.DAO.KendaraanDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterKendaraan;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterKendaraanSearch;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterPelanggan;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_jasaService;
import com.toviddd.sitato.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Kendaraan extends AppCompatActivity implements View.OnClickListener {

    private Button kosongkanKolom, hapus, ubah, simpan, btnPilihPelanggan;
    private EditText noPlatKendaraan, merekKendaraan, jenisKendaraan;
    private TextView deleteSearchKendaraan, tvPilihPelanggan;
    private String TAG= "Kendaraan Activity";
    private String namaKelas= "kendaraan";
    private List<KendaraanDAO> listKendaraan= new ArrayList<>();
    private List<KendaraanDAO> listKendaraan2= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapterKendaraan recyclerAdapterKendaraan;
    private static final int PICK_IMAGE = 100;
    public static final String PREF_KENDARAAN= "PREF_KENDARAAN";
    public int id_kendaraan;
    // search bar
    private RecyclerView recyclerViewSearch;
    private RecyclerAdapterKendaraanSearch recyclerAdapterKendaraanSearch;
    private EditText searchKendaraan;
    // spinner
    private RecyclerAdapterPelanggan recyclerAdapterPelanggan;
    private List<PelangganDAO> listPelanggann= new ArrayList<>();
    private ArrayList<String> listPelanggann2= new ArrayList<>();
    private SpinnerDialog spinnerDialogPelanggan;
    private int id_pelanggan= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendaraan);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Helper.networkPermission();

        initAttribute();
        buildRecyclerViewTampil();
        tampil();
        buildRecyclerViewSearch();
        cari();
        isiKolom();
        setPelangganAndSpinner();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnSimpanKendaraan:
                simpan();
                kosongkanSearchKendaraanPreferences();
                break;
            case R.id.btnEditKendaraan:
                ubah();
                break;
            case R.id.btnHapusKendaraan:
                hapus();
                kosongkanSearchKendaraanPreferences();
                break;
            case R.id.btnKosongkanKolom:
                kosongkanKolom();
                kosongkanSearchKendaraanPreferences();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Kendaraan.this, PegawaiMainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initAttribute()
    {
        kosongkanKolom= findViewById(R.id.btnKosongkanKolom);
        kosongkanKolom.setOnClickListener(this);
        hapus= findViewById(R.id.btnHapusKendaraan);
        hapus.setOnClickListener(this);
        ubah= findViewById(R.id.btnEditKendaraan);
        ubah.setOnClickListener(this);
        simpan= findViewById(R.id.btnSimpanKendaraan);
        simpan.setOnClickListener(this);
        deleteSearchKendaraan= findViewById(R.id.textView_deleteSearchKendaraan);

        //search bar
        searchKendaraan= findViewById(R.id.editText_searchKendaraan);

        noPlatKendaraan= findViewById(R.id.editText_NoPlatKendaraan);
        merekKendaraan= findViewById(R.id.editText_MerekKendaraan);
        jenisKendaraan= findViewById(R.id.editText_JenisKendaraan);

        // spinner
        btnPilihPelanggan= findViewById(R.id.button_namaPelangganKendaraan);
        tvPilihPelanggan= findViewById(R.id.textView_namaPelangganKendaraan);
    }

    public void buildRecyclerViewTampil()
    {
        recyclerView= findViewById(R.id.recyclerView_Kendaraan);
        recyclerAdapterKendaraan= new RecyclerAdapterKendaraan(this, listKendaraan);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterKendaraan);
    }

    public void buildRecyclerViewSearch()
    {
        recyclerViewSearch= findViewById(R.id.recyclerView_KendaraanSearch);
        recyclerAdapterKendaraanSearch= new RecyclerAdapterKendaraanSearch(this, listKendaraan2);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
    }

    public void kosongkanKolom()
    {
        tvPilihPelanggan.setText("");
        noPlatKendaraan.setText("");
        merekKendaraan.setText("");
        jenisKendaraan.setText("");
        FancyToast.makeText(Kendaraan.this, "Kolom dikosongkan", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
    }

    public void isiKolom()
    {
        noPlatKendaraan.setText("AA1234GB");
        merekKendaraan.setText("Yamaha");
        jenisKendaraan.setText("MX 3000");
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void setPelangganAndSpinner()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<PelangganDAO>> pDAOCall= apiClient.getAllPelanggan();
        pDAOCall.enqueue(new Callback<List<PelangganDAO>>() {
            @Override
            public void onResponse(Call<List<PelangganDAO>> call, Response<List<PelangganDAO>> response) {
                listPelanggann= response.body();
                for(int i= 0; i<listPelanggann.size(); i++)
                {
                    String item= (listPelanggann.get(i).getNama_pelanggan() + " (" +listPelanggann.get(i).getNo_telepon_pelanggan() +")");
                    listPelanggann2.add(item);
                }
            }

            @Override
            public void onFailure(Call<List<PelangganDAO>> call, Throwable t) {
            }
        });

        btnPilihPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listPelanggann.size() > 0)
                {
                    spinnerDialogPelanggan= new SpinnerDialog(Kendaraan.this, listPelanggann2, "Pilih pelanggan", "Tutup");
                    spinnerDialogPelanggan.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
                            tvPilihPelanggan.setText(item);
                            id_pelanggan= position+1;
                        }
                    });
                }
                try
                {
                    spinnerDialogPelanggan.showSpinerDialog();
                }catch(Exception e){}
            }
        });
    }

    public void hapus()
    {
        // get id kendaraan
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_KENDARAAN, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_KENDARAAN, "");
        final KendaraanDAO s= gson.fromJson(json, KendaraanDAO.class);

        if(s == null)
        {
            FancyToast.makeText(Kendaraan.this, "Pilih " +namaKelas +" untuk dihapus", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            id_kendaraan= s.getId_kendaraan();
            Retrofit.Builder builder= new Retrofit
                    .Builder()
                    .baseUrl(Helper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<KendaraanDAO> kendaraanDAOCall= apiClient.deleteKendaraan(id_kendaraan);
            kendaraanDAOCall.enqueue(new Callback<KendaraanDAO>() {
                @Override
                public void onResponse(Call<KendaraanDAO> call, Response<KendaraanDAO> response) {
                    if(response.code() == 404)
                    {
                        FancyToast.makeText(Kendaraan.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(Kendaraan.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }

                @Override
                public void onFailure(Call<KendaraanDAO> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(Kendaraan.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(Kendaraan.this, "Berhasil menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
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
        String noPlatKendaraan_tampung, merekKendaraan_tampung, jenisKendaraan_tampung;
        noPlatKendaraan_tampung= noPlatKendaraan.getText().toString();
        merekKendaraan_tampung= merekKendaraan.getText().toString();
        jenisKendaraan_tampung= jenisKendaraan.getText().toString();

        // get id kendaraan
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_KENDARAAN, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_KENDARAAN, "");
        final KendaraanDAO s= gson.fromJson(json, KendaraanDAO.class);

        if(s == null)
        {
            FancyToast.makeText(Kendaraan.this, "Pilih " +namaKelas +" untuk diedit", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            if(noPlatKendaraan_tampung.isEmpty() || merekKendaraan_tampung.isEmpty() || jenisKendaraan_tampung.isEmpty())
            {
                FancyToast.makeText(Kendaraan.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(noPlatKendaraan_tampung.length()>0 && noPlatKendaraan_tampung.length() != 8 ||
                    merekKendaraan_tampung.length()>0 && merekKendaraan_tampung.length()<4 || merekKendaraan_tampung.length()>50 ||
                    jenisKendaraan_tampung.length()>0 && jenisKendaraan_tampung.length()<4 || jenisKendaraan_tampung.length()>50)
            {
                if(noPlatKendaraan_tampung.length() != 8)
                {
                    FancyToast.makeText(Kendaraan.this, "Nomor plat " +namaKelas +" harus 8 alfanumerik", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(merekKendaraan_tampung.length()<4 || merekKendaraan_tampung.length()>50)
                {
                    FancyToast.makeText(Kendaraan.this, "Merek " +namaKelas +" harus 4-50 alfanumerik", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
                else if(jenisKendaraan_tampung.length()<4 || jenisKendaraan_tampung.length()>50)
                {
                    FancyToast.makeText(Kendaraan.this, "Jenis " +namaKelas +" harus 5-50 alfanumerik", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
                }
            }
            else
            {
                Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit= builder.build();
                PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                KendaraanDAO sp= new KendaraanDAO(id_pelanggan, noPlatKendaraan_tampung, merekKendaraan_tampung, jenisKendaraan_tampung);

                id_kendaraan= s.getId_kendaraan();
                Call<String> kendaraanDAOCall= apiClient.requestUpdateKendaraan(sp, id_kendaraan);
                kendaraanDAOCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        FancyToast.makeText(Kendaraan.this, "Berhasil mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        recreate();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(!isNetworkAvailable())
                        {
                            FancyToast.makeText(Kendaraan.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        else
                        {
                            if (t instanceof IOException) {
                                FancyToast.makeText(Kendaraan.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                // logging probably not necessary
                            }
                            else {
                                FancyToast.makeText(Kendaraan.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            } // end of else
        }
    }

    public void simpan()
    {
        String noPlatKendaraan_tampung, merekKendaraan_tampung, jenisKendaraan_tampung;
        noPlatKendaraan_tampung= noPlatKendaraan.getText().toString();
        merekKendaraan_tampung= merekKendaraan.getText().toString();
        jenisKendaraan_tampung= jenisKendaraan.getText().toString();

        if(noPlatKendaraan_tampung.isEmpty() || merekKendaraan_tampung.isEmpty() || jenisKendaraan_tampung.isEmpty())
        {
            FancyToast.makeText(Kendaraan.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(noPlatKendaraan_tampung.length()>0 && noPlatKendaraan_tampung.length() != 8 ||
                merekKendaraan_tampung.length()>0 && merekKendaraan_tampung.length()<4 || merekKendaraan_tampung.length()>50 ||
                jenisKendaraan_tampung.length()>0 && jenisKendaraan_tampung.length()<4 || jenisKendaraan_tampung.length()>50)
        {
            if(noPlatKendaraan_tampung.length() != 8)
            {
                FancyToast.makeText(Kendaraan.this, "Nomor plat " +namaKelas +" harus 8 alfanumerik", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(merekKendaraan_tampung.length()<4 || merekKendaraan_tampung.length()>50)
            {
                FancyToast.makeText(Kendaraan.this, "Merek " +namaKelas +" harus 4-50 alfanumerik", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(jenisKendaraan_tampung.length()<4 || jenisKendaraan_tampung.length()>50)
            {
                FancyToast.makeText(Kendaraan.this, "Jenis " +namaKelas +" harus 5-50 alfanumerik", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
        }
        else
        {
            if(id_pelanggan == 0)
            {
                FancyToast.makeText(Kendaraan.this, "Id Pelanggan tidak tersedia", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            }
            else
            {
                //POST data into API ,,Build retrofit
                Retrofit.Builder builder2= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit2= builder2.build();
                PegawaiApiClient apiClient2= retrofit2.create(PegawaiApiClient.class);
                Call<String> kendaraanDAOCall= apiClient2.requestSaveKendaraan(id_pelanggan, noPlatKendaraan_tampung, merekKendaraan_tampung, jenisKendaraan_tampung);

                kendaraanDAOCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        FancyToast.makeText(Kendaraan.this, "Berhasil menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        recreate();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(!isNetworkAvailable())
                        {
                            FancyToast.makeText(Kendaraan.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        else
                        {
                            if (t instanceof IOException) {
                                FancyToast.makeText(Kendaraan.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                // logging probably not necessary
                            }
                            else {
                                FancyToast.makeText(Kendaraan.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        }
                    }
                });
            }
        }
    }

    public void tampil()
    {
        FancyToast.makeText(Kendaraan.this, "Sedang menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);

        Call<List<KendaraanDAO>> kendaraanDAOCall= apiClient.getAllKendaraan();

        kendaraanDAOCall.enqueue(new Callback<List<KendaraanDAO>>() {
            @Override
            public void onResponse(Call<List<KendaraanDAO>> call, Response<List<KendaraanDAO>> response) {
                // mengambil data dari response, agar listSparepart terisi
                recyclerAdapterKendaraan.notifyDataSetChanged();
                recyclerAdapterKendaraan= new RecyclerAdapterKendaraan(Kendaraan.this, response.body());
                recyclerView.setAdapter(recyclerAdapterKendaraan);

                // mengambil data dari response, agar listSparepart2 terisi
                recyclerAdapterKendaraanSearch.notifyDataSetChanged();
                recyclerAdapterKendaraanSearch= new RecyclerAdapterKendaraanSearch(Kendaraan.this, response.body());
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listKendaraan2.add(response.body().get(i));
                    }
                }
                FancyToast.makeText(Kendaraan.this, "Berhasil menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
            }

            @Override
            public void onFailure(Call<List<KendaraanDAO>> call, Throwable t) {
                if(!isNetworkAvailable())
                {
                    FancyToast.makeText(Kendaraan.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
                else
                {
                    if (t instanceof IOException) {
                        FancyToast.makeText(Kendaraan.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        // logging probably not necessary
                    }
                    else {
                        FancyToast.makeText(Kendaraan.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }
            }
        });
    }

    public void cari()
    {
        deleteSearchKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKendaraan.setText("");
            }
        });

        searchKendaraan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(recyclerViewSearch.getAdapter() != null)
                {
                    loadSearchKendaraanPreferences();
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

    private void loadSearchKendaraanPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_KENDARAAN, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_KENDARAAN, "");
        final KendaraanDAO s= gson.fromJson(json, KendaraanDAO.class);

        if(s != null)
        {
            tvPilihPelanggan.setText(s.getNama_pelanggan());
            noPlatKendaraan.setText(s.getNo_plat_kendaraan());
            merekKendaraan.setText(s.getMerek_kendaraan());
            jenisKendaraan.setText(s.getJenis_kendaraan());
        }
    }

    private void kosongkanSearchKendaraanPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_KENDARAAN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(PREF_KENDARAAN, null);
        editor.commit();
    }

    private void filter(String text) {
        List<KendaraanDAO> filteredList = new ArrayList<>();

        for (KendaraanDAO item : listKendaraan2) {
            if (item.getNo_plat_kendaraan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerAdapterKendaraanSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(recyclerAdapterKendaraanSearch); //show something
        }
    }

    private void referensi()
    {
        // https://codinginflow.com/tutorials/android/recyclerview-edittext-search
        // http://androidbitmaps.blogspot.com/2015/04/loading-images-in-android-part-iii-pick.html
    }

}

