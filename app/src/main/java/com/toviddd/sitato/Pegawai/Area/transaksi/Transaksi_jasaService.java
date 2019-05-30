package com.toviddd.sitato.Pegawai.Area.transaksi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.toviddd.sitato.DAO.PegawaiDAO;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.JasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.KendaraanDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiJasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterJasaServiceTransaksiJasaServiceSearch;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterTransaksiJasaService;
import com.toviddd.sitato.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaksi_jasaService extends AppCompatActivity {

    private Button kosongkanKolom, hapus, ubah, simpan, btnPlatSpinner, btnMontirSpinner;
    private EditText namaJasaService, jumlahJasaService;
    private TextView deleteSearchJasaService, tvPlatKendaraan, tvNamaMontir;
    private String TAG= "Transaksi jasa service";
    private String namaKelas= "transaksi jasa service";
    private List<JasaServiceDAO> listJasaService= new ArrayList<>();
    private List<TransaksiJasaServiceDAO> listTransaksiJasaService= new ArrayList<>();
    private List<TransaksiJasaServiceDAO> listTransaksiJasaService2= new ArrayList<>();
    private ArrayList<String> listMontir= new ArrayList<>();
    private List<PegawaiDAO> listMontir2= new ArrayList<>();
    private ArrayList<String> listKendaraan= new ArrayList<>();
    private List<KendaraanDAO> listKendaraan2= new ArrayList<>();
    private SpinnerDialog spinnerDialogKendaraan;
    private SpinnerDialog spinnerDialogMontir;
    private RecyclerView recyclerViewTransaksiJasaService;
    private RecyclerAdapterTransaksiJasaService recyclerAdapterTransaksiJasaService;
    private static final int PICK_IMAGE = 100;
    double harga_temp= 0;

    private int id_transaksi, id_montir, id_kendaraan;
    // search bar
    private RecyclerView recyclerViewSearch;
    private RecyclerAdapterJasaServiceTransaksiJasaServiceSearch recyclerAdapterJasaServiceTransaksiJasaServiceSearch;
    private EditText searchJasaService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_jasa_service);
        getSupportActionBar().setTitle("Kembali ke menu transaksi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Helper.networkPermission();

        initAttribute();
        buildRecyclerViewTampil();
        buildRecyclerViewSearch();
        loadDataJasaService();
        loadSelectedItemTransaksi();
        tampil();
        tampilSearch();
        setMontirAndSpinner();
        setKendaraanAndSpinner();

        kosongkanKolom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEnable(true);
                kosongkanKolom();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEnable(true);
                simpan();
            }
        });
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEnable(true);
                ubah();
            }
        });
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEnable(true);
                hapus();
                kosongkanKolom();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE)
        {
            DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            startActivity(new Intent(Transaksi_jasaService.this, Transaksi_menu.class));
                            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_jasaService.this);
            builder.setMessage("Kembali ke menu transaksi,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                startActivity(new Intent(Transaksi_jasaService.this, Transaksi_menu.class));
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_jasaService.this);
                builder.setMessage("Kembali ke menu transaksi,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAttribute()
    {
        kosongkanKolom= findViewById(R.id.btnKosongkanKolom);
        hapus= findViewById(R.id.btnHapusTransaksiJasaService);
        ubah= findViewById(R.id.btnEditTransaksiJasaService);
        simpan= findViewById(R.id.btnSimpanTransaksiJasaService);
        deleteSearchJasaService= findViewById(R.id.textView_deleteSearchJasaServiceTransaksiJasaService);

        //search bar
        searchJasaService= findViewById(R.id.editText_searchJasaServiceTransaksiJasaService);

        tvPlatKendaraan= findViewById(R.id.textView_platKendaraanTransaksiJasaService);
        btnPlatSpinner= findViewById(R.id.button_platKendaraanTransaksiJasaService);
        tvNamaMontir= findViewById(R.id.textView_namaMontirTransaksiJasaService);
        btnMontirSpinner= findViewById(R.id.button_namaMontirTransaksiJasaService);
        namaJasaService= findViewById(R.id.editText_NamaJasaServiceTransaksiJasaService);
        jumlahJasaService= findViewById(R.id.editText_JumlahJasaServiceTransaksiJasaService);
    }

    private void buttonEnable(Boolean b)
    {
        btnPlatSpinner.setEnabled(b);
        btnMontirSpinner.setEnabled(b);
    }

    private void buildRecyclerViewTampil()
    {
        recyclerViewTransaksiJasaService= findViewById(R.id.recyclerView_TransaksiJasaService);
        recyclerAdapterTransaksiJasaService= new RecyclerAdapterTransaksiJasaService(this, listTransaksiJasaService);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewTransaksiJasaService.setLayoutManager(mLayoutManager);
        recyclerViewTransaksiJasaService.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTransaksiJasaService.setAdapter(recyclerAdapterTransaksiJasaService);
    }

    private void buildRecyclerViewSearch()
    {
        recyclerViewSearch= findViewById(R.id.recyclerView_JasaServiceTransaksiJasaService);
        recyclerAdapterJasaServiceTransaksiJasaServiceSearch= new RecyclerAdapterJasaServiceTransaksiJasaServiceSearch(this, listJasaService);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
    }

    private void kosongkanKolom()
    {
        kosongkanSearchJasaServicePreferences();
        kosongkanBeliJasaServicePreferences();
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, null);
        editor.commit();
        namaJasaService.setText("");
        jumlahJasaService.setText("");
        tvPlatKendaraan.setText("Pilih kendaraan");
        tvNamaMontir.setText("Pilih montir");
        FancyToast.makeText(Transaksi_jasaService.this, "Kolom dikosongkan", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    private void setKendaraanAndSpinner()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<KendaraanDAO>> kendaraanDAOCall= apiClient.getAllKendaraan();
        kendaraanDAOCall.enqueue(new Callback<List<KendaraanDAO>>() {
            @Override
            public void onResponse(Call<List<KendaraanDAO>> call, Response<List<KendaraanDAO>> response) {
                listKendaraan2= response.body();
                for(int i= 0; i<listKendaraan2.size(); i++)
                {
                    String item= (listKendaraan2.get(i).getNo_plat_kendaraan() + " (" +listKendaraan2.get(i).getNama_pelanggan() +")");
                    listKendaraan.add(item);
                }
            }

            @Override
            public void onFailure(Call<List<KendaraanDAO>> call, Throwable t) {
            }
        });

        btnPlatSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listKendaraan.size() > 0)
                {
                    spinnerDialogKendaraan= new SpinnerDialog(Transaksi_jasaService.this, listKendaraan, "Pilih kendaraan", "Tutup");
                    spinnerDialogKendaraan.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
                            tvPlatKendaraan.setText(item);
                            id_kendaraan= position+1;
//                            Log.d(TAG, "onClick: :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: " +position);
                        }
                    });
                }
                try
                {
                    spinnerDialogKendaraan.showSpinerDialog();
                }catch(Exception e){}
            }
        });
    }

    private void setMontirAndSpinner()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<PegawaiDAO>> montir= apiClient.getAllPegawaiMontir();
        montir.enqueue(new Callback<List<PegawaiDAO>>() {
            @Override
            public void onResponse(Call<List<PegawaiDAO>> call, Response<List<PegawaiDAO>> response) {
                listMontir2= response.body();
                if(listMontir2 != null)
                {
                    for(int i= 0; i<listMontir2.size(); i++)
                    {
                        listMontir.add(listMontir2.get(i).getNama_pegawai());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PegawaiDAO>> call, Throwable t) {

            }
        });

        btnMontirSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listMontir.size() > 0)
                {
                    spinnerDialogMontir= new SpinnerDialog(Transaksi_jasaService.this, listMontir, "Pilih montir", "Tutup");
                    spinnerDialogMontir.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
                            tvNamaMontir.setText(item);
                            id_montir= position+1;
                        }
                    });
                }
                try
                {
                    spinnerDialogMontir.showSpinerDialog();
                }catch(Exception e){}
            }
        });
    }

    private void tampil()
    {
        FancyToast.makeText(Transaksi_jasaService.this, "Sedang menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_TRANSAKSI, "");
        final TransaksiDAO t= gson2.fromJson(json2, TransaksiDAO.class);

        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<TransaksiJasaServiceDAO>> transaksiDAOcall= apiClient.getTransaksiJasaService(t.getId_transaksi());

        transaksiDAOcall.enqueue(new Callback<List<TransaksiJasaServiceDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiJasaServiceDAO>> call, Response<List<TransaksiJasaServiceDAO>> response) {
                // mengambil data dari response, agar listSparepart terisi
                recyclerAdapterTransaksiJasaService.notifyDataSetChanged();
                recyclerAdapterTransaksiJasaService= new RecyclerAdapterTransaksiJasaService(Transaksi_jasaService.this, response.body());
                recyclerViewTransaksiJasaService.setAdapter(recyclerAdapterTransaksiJasaService);
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listTransaksiJasaService2.add(response.body().get(i));
                    }
                }
                FancyToast.makeText(Transaksi_jasaService.this, "Berhasil menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
            }

            @Override
            public void onFailure(Call<List<TransaksiJasaServiceDAO>> call, Throwable t) {
                if(!isNetworkAvailable())
                {
                    FancyToast.makeText(Transaksi_jasaService.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
                else
                {
                    if (t instanceof IOException) {
                        FancyToast.makeText(Transaksi_jasaService.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        // logging probably not necessary
                    }
                    else {
                        FancyToast.makeText(Transaksi_jasaService.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }
            }
        });
    }

    private void simpan()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_TRANSAKSI_JASA_SERVICE, "");
        final JasaServiceDAO js= gson.fromJson(json, JasaServiceDAO.class);

        if(namaJasaService.getText().toString().isEmpty())
        {
            FancyToast.makeText(getApplicationContext(), "Pilih jasa service untuk dibeli", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(tvPlatKendaraan.getText().toString() == "Pilih kendaraan")
        {
            FancyToast.makeText(getApplicationContext(), "Pilih kendaraan", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(tvNamaMontir.getText().toString() == "Pilih montir")
        {
            FancyToast.makeText(getApplicationContext(), "Pilih montir", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(js != null)
        {
            if(jumlahJasaService.getText().toString().isEmpty())
            {
                FancyToast.makeText(getApplicationContext(), "Jumlah jasa service belum diisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(Integer.parseInt(jumlahJasaService.getText().toString()) <= 0)
            {
                FancyToast.makeText(getApplicationContext(), "Jumlah jasa service minimal 1 buah", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else
            {
                int status_id_jasa_service= 0;
                for(int i=0; i<listTransaksiJasaService2.size(); i++)
                {
                    if(js.getId_jasa_service() == listTransaksiJasaService2.get(i).getId_jasa_service())
                    {
                        status_id_jasa_service= 1;
                    }
                }

                if(status_id_jasa_service != 1)
                {
                    SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI, Context.MODE_PRIVATE);
                    Gson gson2= new Gson();
                    String json2= pref2.getString(Helper.PREF_TRANSAKSI, "");
                    final TransaksiDAO t= gson2.fromJson(json2, TransaksiDAO.class);
                    if(t != null)
                    {
                        int jumlah= Integer.parseInt(jumlahJasaService.getText().toString());
                        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit= builder.build();
                        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);

                        Call<TransaksiJasaServiceDAO> transaksiSparepartDAOCall= apiClient.requestSaveTransaksiJasaService(t.getId_transaksi(), id_montir, js.getId_jasa_service(), id_kendaraan, jumlah, js.getHargaJasaService()*jumlah);
                        Log.d(TAG, "simpan: " +String.valueOf(t.getId_transaksi()) +", " +String.valueOf(id_montir) +", " +String.valueOf(js.getId_jasa_service()) +", " +String.valueOf(id_kendaraan) +", " +String.valueOf(jumlah) +", " +String.valueOf(js.getHargaJasaService()*jumlah));
                        transaksiSparepartDAOCall.enqueue(new Callback<TransaksiJasaServiceDAO>() {
                            @Override
                            public void onResponse(Call<TransaksiJasaServiceDAO> call, Response<TransaksiJasaServiceDAO> response) {
                                TransaksiJasaServiceDAO ts= response.body();
                                if(ts != null)
                                {
                                    SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor= pref.edit();
                                    Gson gson= new Gson();
                                    String json= gson.toJson(ts);
                                    editor.putString(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, json);
                                    editor.commit();

                                    FancyToast.makeText(Transaksi_jasaService.this, "Berhasil menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                    recreate();
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            }

                            @Override
                            public void onFailure(Call<TransaksiJasaServiceDAO> call, Throwable t) {
                                if(!isNetworkAvailable())
                                {
                                    FancyToast.makeText(Transaksi_jasaService.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                                else
                                {
                                    if (t instanceof IOException) {
                                        FancyToast.makeText(Transaksi_jasaService.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                        // logging probably not necessary
                                    }
                                    else {
                                        FancyToast.makeText(Transaksi_jasaService.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                    }
                                }
                            }
                        });
                    }
                }
                else
                {
                    FancyToast.makeText(getApplicationContext(), "jasa service tersebut sudah dibeli", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                }
            }
        }
    }

    private void tampilSearch()
    {
        deleteSearchJasaService.setOnClickListener(new View.OnClickListener() {
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
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_TRANSAKSI_JASA_SERVICE, "");
        final JasaServiceDAO js= gson.fromJson(json, JasaServiceDAO.class);
        if(js != null)
        {
            namaJasaService.setText(js.getNamaJasaService() +"(" +js.getKodeJasaService() +")");
            jumlahJasaService.setText("");
        }
    }

    private void kosongkanSearchJasaServicePreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_TRANSAKSI_JASA_SERVICE, null);
        editor.commit();
    }

    private void kosongkanBeliJasaServicePreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, null);
        editor.commit();
    }

    private void filter(String text) {
        List<JasaServiceDAO> filteredList = new ArrayList<>();

        for (JasaServiceDAO item : listJasaService) {
            if (item.getNamaJasaService().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerAdapterJasaServiceTransaksiJasaServiceSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(recyclerAdapterJasaServiceTransaksiJasaServiceSearch); //show something
        }
    }

    private void loadDataJasaService()
    {
        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<JasaServiceDAO>> jsDAOcall= apiClient.getAllJasaService();
        jsDAOcall.enqueue(new Callback<List<JasaServiceDAO>>() {
            @Override
            public void onResponse(Call<List<JasaServiceDAO>> call, Response<List<JasaServiceDAO>> response) {
                recyclerAdapterJasaServiceTransaksiJasaServiceSearch.notifyDataSetChanged();
                recyclerAdapterJasaServiceTransaksiJasaServiceSearch= new RecyclerAdapterJasaServiceTransaksiJasaServiceSearch(Transaksi_jasaService.this, response.body());
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listJasaService.add(response.body().get(i));
                        Log.d(TAG, "onResponse: =b=b=b=b=b=b=b=b=b=b=b====> " +response.body().get(i).getNamaJasaService());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JasaServiceDAO>> call, Throwable t) {
            }
        });
    }

    private void loadSelectedItemTransaksi()
    {
        SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
        Gson gson2= new Gson();
        String json2= pref2.getString(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, "");
        final TransaksiJasaServiceDAO tjs= gson2.fromJson(json2, TransaksiJasaServiceDAO.class);
        if(tjs != null)
        {

            if(tjs != null)
            {
                try
                {
                    for(int i= 0; i< 500000000; i++){}
                    Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " +tjs.getNama_jasa_service() +", " +tjs.getKode_jasa_service() +", " +tjs.getJumlah_transaksi_penjualan_jasa_service());
                    buttonEnable(false);
                    tvPlatKendaraan.setText(tjs.getNo_plat_kendaraan());
                    tvNamaMontir.setText(tjs.getNama_montir());
                    namaJasaService.setText(tjs.getNama_jasa_service() +"(" +tjs.getKode_jasa_service() +")");
                    jumlahJasaService.setText(String.valueOf(tjs.getJumlah_transaksi_penjualan_jasa_service()));
                }catch (Exception e){
                    Log.d(TAG, "loadSelectedItemTransaksi: " +e.getMessage());
                    Log.d(TAG, "loadSelectedItemTransaksi: " +e.toString());
                }
            }
        }
        else
        {
            kosongkanBeliJasaServicePreferences();
        }
    }

    private void ubah()
    {
        SharedPreferences pref3= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
        Gson gson3= new Gson();
        String json3= pref3.getString(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, "");
        final TransaksiJasaServiceDAO tjs= gson3.fromJson(json3, TransaksiJasaServiceDAO.class);
        if(tjs != null)
        {
            if(jumlahJasaService.getText().toString().isEmpty())
            {
                FancyToast.makeText(getApplicationContext(), "Jumlah jasa service belum diisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(Integer.parseInt(jumlahJasaService.getText().toString()) <= 0)
            {
                FancyToast.makeText(getApplicationContext(), "Jumlah beli minimal 1 buah", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else
            {
                harga_temp= tjs.getHarga_jasa_service();
                if(harga_temp > 0)
                {
                    final int jumlahBeli_temp= Integer.parseInt(jumlahJasaService.getText().toString());
                    TransaksiJasaServiceDAO tjsDAO= new TransaksiJasaServiceDAO(tjs.getId_jasa_service(), tjs.getId_kendaraan(), jumlahBeli_temp, harga_temp*jumlahBeli_temp);

                    Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit= builder.build();
                    PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                    Call<String> tsDAOCall= apiClient.requestUpdateTransaksiJasaService(tjsDAO, tjs.getId_transaksi_penjualan_jasa_service());
                    tsDAOCall.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try
                            {
                                Log.d(TAG, "onResponse XXX: " +response.errorBody().string());
                            }catch(Exception e){}
                            Log.d(TAG, "onResponse: " +response.toString());
                            FancyToast.makeText(Transaksi_jasaService.this, "Berhasil mengedit " +namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            recreate();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            FancyToast.makeText(Transaksi_jasaService.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                    });
                }
            }
        }
        else
        {
            FancyToast.makeText(getApplicationContext(), "Pilih jasa service untuk diedit", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
    }

    private void hapus()
    {
        SharedPreferences pref3= getApplicationContext().getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
        Gson gson3= new Gson();
        String json3= pref3.getString(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, "");
        final TransaksiJasaServiceDAO tjs= gson3.fromJson(json3, TransaksiJasaServiceDAO.class);
        if(tjs != null)
        {
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<TransaksiJasaServiceDAO> tjsDAOCall= apiClient.deleteTransaksiJasaService(tjs.getId_transaksi_penjualan_jasa_service());
            tjsDAOCall.enqueue(new Callback<TransaksiJasaServiceDAO>() {
                @Override
                public void onResponse(Call<TransaksiJasaServiceDAO> call, Response<TransaksiJasaServiceDAO> response) {
                    FancyToast.makeText(Transaksi_jasaService.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    recreate();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                @Override
                public void onFailure(Call<TransaksiJasaServiceDAO> call, Throwable t) {
                    FancyToast.makeText(Transaksi_jasaService.this, "Berhasil menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    recreate();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        }
        else
        {
            FancyToast.makeText(getApplicationContext(), "Pilih jasa service untuk dihapus", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
    }
}
