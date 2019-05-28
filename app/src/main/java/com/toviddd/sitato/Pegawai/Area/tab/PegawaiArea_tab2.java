package com.toviddd.sitato.Pegawai.Area.tab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.DAO.PegawaiDAO;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Pelanggan;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterPelangganSearch;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter.RecyclerAdapterPelangganTransaksiSearch;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_menu;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_pengadaan;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_tampil;
import com.toviddd.sitato.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PegawaiArea_tab2 extends Fragment implements View.OnClickListener {
    public View rootView;
    private Button btnTransaksiMenu, btnKosongkanKolom, btnSimpanDanPilihPelanggan;
    private EditText editTextCariPelanggan, editTextNamaPelanggan, editTextNoTelpPelanggan, editTextAlamatPelanggan;
    private TextView textViewNamaPelanggan, textViewNoTelpPelanggan, textViewAlamatPelanggan, textViewNamaCS, textViewDeleteSearchPelanggan;
    private ImageView imageViewCentangDanXPelanggan;
    private String TAG= "Pegawai Area Tab 2";
    private String namaKelas= "transaksi";
    private FloatingActionButton fab_tampilTransaksi;
    private TextView tvFabTransaksi, tvFabPengadaan;
    private LinearLayout llFabTransaksi, llFabPengadaan;
    Boolean isFABOpen= false;

    // cari
    private List<PelangganDAO> listPelanggan= new ArrayList<>();
    private RecyclerAdapterPelangganTransaksiSearch recyclerAdapterPelangganTransaksiSearch;
    private RecyclerView recyclerViewSearch;

    // set nama cs
    SharedPreferences prefLogin;
    String namaCS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.pegawai_area_tab2, container, false);
        initAttribute();
        buildRecyclerViewSearch();
        kosongkanSearchPelangganPreferences();
        cariPelanggan();
        fab();

        return rootView;
    }

    private void showFABMenu(){
        isFABOpen= true;
        llFabTransaksi.animate().translationY(-getResources().getDimension(R.dimen.standard_75));
        llFabPengadaan.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
        tvFabTransaksi.setVisibility(View.VISIBLE);
        tvFabPengadaan.setVisibility(View.VISIBLE);
    }

    private void closeFABMenu(){
        isFABOpen= false;
        llFabTransaksi.animate().translationY(0);
        llFabPengadaan.animate().translationY(0);
        tvFabTransaksi.setVisibility(View.INVISIBLE);
        tvFabPengadaan.setVisibility(View.INVISIBLE);
    }

    private void fab()
    {
        fab_tampilTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });
    }

    private void fabTransaksi()
    {
        startActivity(new Intent(getContext(), Transaksi_tampil.class));
    }

    private void fabPengadaan()
    {
        startActivity(new Intent(getContext(), Transaksi_pengadaan.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_LanjutTransaksi:
                transaksiMenu();
                break;
            case R.id.textView_deleteSearchPelangganTransaksi:
                deleteSearchPelangganBox();
                break;
            case R.id.btnKosongkanKolom:
                kosongkanKolom();
                kosongkanSearchPelangganPreferences();
                break;
            case R.id.btnSimpanDanPilihPelanggan:
                if(editTextNamaPelanggan.isEnabled() == false)
                {
                    FancyToast.makeText(getContext(), "Kosongkan kolom terlebih dahulu", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                }
                else
                {
                    simpanDanPilihPelanggan();
                }
                break;
            case R.id.fab_tampilTransaksi:
                fab();
                break;
            case R.id.linearLayout_fabTransaksi:
                fabTransaksi();
                break;
            case R.id.linearLayout_fabPengadaan:
                fabPengadaan();
                break;
        }
    }

    public void buildRecyclerViewSearch()
    {
        recyclerViewSearch= rootView.findViewById(R.id.recyclerView_PelangganTransaksiSearch);
        recyclerAdapterPelangganTransaksiSearch= new RecyclerAdapterPelangganTransaksiSearch(getContext(), listPelanggan);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
    }

    public void initAttribute()
    {
        btnTransaksiMenu= rootView.findViewById(R.id.button_LanjutTransaksi);
        // search
        editTextCariPelanggan= rootView.findViewById(R.id.editText_searchPelangganTransaksi);
        textViewDeleteSearchPelanggan= rootView.findViewById(R.id.textView_deleteSearchPelangganTransaksi);
        // pelanggan
        btnKosongkanKolom= rootView.findViewById(R.id.btnKosongkanKolom);
        btnSimpanDanPilihPelanggan= rootView.findViewById(R.id.btnSimpanDanPilihPelanggan);
        imageViewCentangDanXPelanggan= rootView.findViewById(R.id.imageView_centangDanXPelanggan);
        editTextNamaPelanggan= rootView.findViewById(R.id.editText_NamaPelangganTransaksi);
        editTextNoTelpPelanggan= rootView.findViewById(R.id.editText_NoTelponPelangganTransaksi);
        editTextAlamatPelanggan= rootView.findViewById(R.id.editText_AlamatPelangganTransaksi);
        // info transaksi
        textViewNamaPelanggan= rootView.findViewById(R.id.textView_NamaPelangganTransaksi);
        textViewNoTelpPelanggan= rootView.findViewById(R.id.textView_NomorTelpPelangganTransaksi);
        textViewAlamatPelanggan= rootView.findViewById(R.id.textView_AlamatPelangganTransaksi);
        textViewNamaCS= rootView.findViewById(R.id.textView_NamaCSTransaksi);

        // set cs
        // set nama cs
        prefLogin = getContext().getSharedPreferences(Helper.PREF_LOGIN_PEGAWAI, getContext().MODE_PRIVATE);
        namaCS = prefLogin.getString(Helper.NAMA_PEGAWAI_LOGIN, "Tidak ada");
        textViewNamaCS.setText(namaCS);

        btnTransaksiMenu.setOnClickListener(this);
        // search
        editTextCariPelanggan.setOnClickListener(this);
        textViewDeleteSearchPelanggan.setOnClickListener(this);
        // pelanggan
        btnKosongkanKolom.setOnClickListener(this);
        btnSimpanDanPilihPelanggan.setOnClickListener(this);

        // fab
        fab_tampilTransaksi= rootView.findViewById(R.id.fab_tampilTransaksi);
        fab_tampilTransaksi.setOnClickListener(this);
        llFabTransaksi= rootView.findViewById(R.id.linearLayout_fabTransaksi);
        llFabTransaksi.setOnClickListener(this);
        llFabPengadaan= rootView.findViewById(R.id.linearLayout_fabPengadaan);
        llFabPengadaan.setOnClickListener(this);
        tvFabTransaksi= rootView.findViewById(R.id.textView_fabTransaksi);
        tvFabPengadaan= rootView.findViewById(R.id.textView_fabPengadaan);
    }

    public void transaksiMenu()
    {
        // get id_pelanggan
        SharedPreferences pref= getContext().getSharedPreferences(Helper.PREF_PELANGGAN_TRANSAKSI, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_PELANGGAN_TRANSAKSI, "");
        final PelangganDAO s= gson.fromJson(json, PelangganDAO.class);

        // get id_cs (pegawai)
        SharedPreferences prefLogin = getContext().getSharedPreferences(Helper.PREF_LOGIN_PEGAWAI, getContext().MODE_PRIVATE);
        int id_cs = prefLogin.getInt(Helper.ID_PEGAWAI_LOGIN, -1);
        int id_cabang = prefLogin.getInt(Helper.ID_CABANG_LOGIN, -1);

        if(s != null)
        {
            // insert transaksi
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<TransaksiDAO> transaksiDAOCall= apiClient.requestSaveTransaksi(s.getId_pelanggan(), id_cs, id_cabang);
            transaksiDAOCall.enqueue(new Callback<TransaksiDAO>() {
                @Override
                public void onResponse(Call<TransaksiDAO> call, Response<TransaksiDAO> response) {
                    // set id transaksi
                    TransaksiDAO t= response.body();
                    SharedPreferences pref= getContext().getSharedPreferences(Helper.PREF_TRANSAKSI, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= pref.edit();
                    Gson gson= new Gson();
                    String json= gson.toJson(t);
                    editor.putString(Helper.PREF_TRANSAKSI, json);
                    editor.commit();

                    if(response.body() != null)
                    {
                        Intent intent= new Intent(getActivity(), Transaksi_menu.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<TransaksiDAO> call, Throwable t) {
                }
            });
        }
        else
        {
            FancyToast.makeText(getContext(), "Pilih pelanggan untuk melanjutkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
        }
    }

    public void deleteSearchPelangganBox()
    {
        editTextCariPelanggan.setText("");
    }

    public void kosongkanKolom()
    {
        editTextNamaPelanggan.setText("");
        editTextNoTelpPelanggan.setText("");
        editTextAlamatPelanggan.setText("");
        editTextNamaPelanggan.setEnabled(true);
        editTextNoTelpPelanggan.setEnabled(true);
        editTextAlamatPelanggan.setEnabled(true);
        imageViewCentangDanXPelanggan.setImageResource(R.drawable.ic_x);
        ImageViewCompat.setImageTintList(imageViewCentangDanXPelanggan, ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.red)));
        textViewNamaPelanggan.setText("Nama: -");
        textViewNoTelpPelanggan.setText("No telp: -");
        textViewAlamatPelanggan.setText("Alamat: -");
    }

    public void simpanDanPilihPelanggan()
    {
        final String namaPelanggan_tampung, noTelpPelanggan_tampung, alamatPelanggan_tampung;
        namaPelanggan_tampung= editTextNamaPelanggan.getText().toString();
        noTelpPelanggan_tampung= editTextNoTelpPelanggan.getText().toString();
        alamatPelanggan_tampung= editTextAlamatPelanggan.getText().toString();
        if(namaPelanggan_tampung.isEmpty() || noTelpPelanggan_tampung.isEmpty() || alamatPelanggan_tampung.isEmpty())
        {
            FancyToast.makeText(getContext(), "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(namaPelanggan_tampung.length()>0 && namaPelanggan_tampung.length()<4 || namaPelanggan_tampung.length()>50 ||
                noTelpPelanggan_tampung.length()>0 && noTelpPelanggan_tampung.length()<12 || noTelpPelanggan_tampung.length()>13 ||
                alamatPelanggan_tampung.length()>0 && alamatPelanggan_tampung.length()<10 || alamatPelanggan_tampung.length()>50)
        {
            if(namaPelanggan_tampung.length()<4 || namaPelanggan_tampung.length()>50)
            {
                FancyToast.makeText(getContext(), "Nama pelanggan harus 4-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(noTelpPelanggan_tampung.length()<12 || noTelpPelanggan_tampung.length()>13)
            {
                FancyToast.makeText(getContext(), "Nomor telepon harus 12-13 digit", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(alamatPelanggan_tampung.length()<10 || alamatPelanggan_tampung.length()>50)
            {
                FancyToast.makeText(getContext(), "Alamat pelanggan harus 10-50 huruf", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
        }
        else
        {
            //POST data into API ,,Build retrofit
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<PelangganDAO> pelangganDAOCall= apiClient.requestSavePelangganTransaksi(namaPelanggan_tampung, noTelpPelanggan_tampung, alamatPelanggan_tampung);

            pelangganDAOCall.enqueue(new Callback<PelangganDAO>() {
                @Override
                public void onResponse(Call<PelangganDAO> call, Response<PelangganDAO> response) {
                    PelangganDAO p= response.body();
                    SharedPreferences pref= getContext().getSharedPreferences(Helper.PREF_PELANGGAN_TRANSAKSI, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= pref.edit();
                    Gson gson= new Gson();
                    String json= gson.toJson(p);
                    editor.putString(Helper.PREF_PELANGGAN_TRANSAKSI, json);
                    editor.commit();

                    imageViewCentangDanXPelanggan.setImageResource(R.drawable.ic_centang);
                    ImageViewCompat.setImageTintList(imageViewCentangDanXPelanggan, ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green)));
                    editTextNamaPelanggan.setEnabled(false);
                    editTextNoTelpPelanggan.setEnabled(false);
                    editTextAlamatPelanggan.setEnabled(false);
                    textViewNamaPelanggan.setText("Nama: " +namaPelanggan_tampung);
                    textViewNoTelpPelanggan.setText("No telp: " +noTelpPelanggan_tampung);
                    textViewAlamatPelanggan.setText("Alamat: " +alamatPelanggan_tampung);
                    cariPelanggan();
                    recyclerAdapterPelangganTransaksiSearch.notifyDataSetChanged();
                    FancyToast.makeText(getContext(), "Berhasil menyimpan pelanggan", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                }

                @Override
                public void onFailure(Call<PelangganDAO> call, Throwable t) {
                    FancyToast.makeText(getContext(), "Gagal menyimpan ", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
            });
        }
    }

    public void cariPelanggan()
    {
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
                if(response.body() != null)
                {
                    listPelanggan.clear();
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listPelanggan.add(response.body().get(i));
                        Log.d(TAG, "============> onResponse: " +response.body().get(i).getNama_pelanggan());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PelangganDAO>> call, Throwable t) {

            }
        });

        editTextCariPelanggan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(recyclerViewSearch.getAdapter() != null)
                {
                    loadSearchPelangganTransaksiPreferences();
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

    private void filter(String text) {
        List<PelangganDAO> filteredList = new ArrayList<>();

        for (PelangganDAO item : listPelanggan) {
            if (item.getNama_pelanggan().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerAdapterPelangganTransaksiSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(recyclerAdapterPelangganTransaksiSearch); //show something
        }
    }

    private void loadSearchPelangganTransaksiPreferences()
    {
        SharedPreferences pref= getContext().getSharedPreferences(Helper.PREF_PELANGGAN_TRANSAKSI, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(Helper.PREF_PELANGGAN_TRANSAKSI, "");
        final PelangganDAO s= gson.fromJson(json, PelangganDAO.class);

        if(s != null)
        {
            editTextNamaPelanggan.setText(s.getNama_pelanggan());
            editTextNoTelpPelanggan.setText(s.getNo_telepon_pelanggan());
            editTextAlamatPelanggan.setText(s.getAlamat_pelanggan());

            textViewNamaPelanggan.setText("Nama: " +s.getNama_pelanggan());
            textViewNoTelpPelanggan.setText("No telp: " +s.getNo_telepon_pelanggan());
            textViewAlamatPelanggan.setText("Alamat: " +s.getAlamat_pelanggan());
            textViewNamaCS.setText("Nama: " +namaCS);

            editTextNamaPelanggan.setEnabled(false);
            editTextNoTelpPelanggan.setEnabled(false);
            editTextAlamatPelanggan.setEnabled(false);

            imageViewCentangDanXPelanggan.setImageResource(R.drawable.ic_centang);
            ImageViewCompat.setImageTintList(imageViewCentangDanXPelanggan, ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green)));
        }
    }

    private void kosongkanSearchPelangganPreferences()
    {
        SharedPreferences pref= getContext().getSharedPreferences(Helper.PREF_PELANGGAN_TRANSAKSI, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(Helper.PREF_PELANGGAN_TRANSAKSI, null);
        editor.commit();
    }

    private void referensi()
    {
        // https://stackoverflow.com/questions/30699302/android-design-support-library-expandable-floating-action-buttonfab-menu
    }

}
