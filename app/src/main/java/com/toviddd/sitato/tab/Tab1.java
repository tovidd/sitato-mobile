package com.toviddd.sitato.tab;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.LoginActivity;
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Cabang;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterCabang;
import com.toviddd.sitato.R;
import com.toviddd.sitato.tab.adapter.ListViewAdapterCabangInformasiBengkel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab1 extends Fragment implements View.OnClickListener {

    private View rootView;
    private TextView namaBengkel, infoBengkel, jamBukaBengkel, noTelponBengkel, webBengkel, alamatBengkel;
    String nama, info, jam, no_hp, web, alamat;

    // cabang
    private List<CabangDAO> listCabang;
    private RecyclerView recyclerView;
    private ListViewAdapterCabangInformasiBengkel adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.tab_1, container, false);
        initAttribute();
        loadCabang();
        buildRecyclerView();
        tampil();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.textView_namaBengkel_InformasiBengkel:
                break;
            case R.id.textView_infoBengkel_InformasiBengkel:
                break;
            case R.id.textView_jamBukaBengkel__InformasiBengkel:
                break;
            case R.id.textView_NoTelponBengkel_InformasiBengkel:
                actionNoTelp();
                break;
            case R.id.textView_WebBengkel_InformasiBengkel:
                actionWeb();
                break;
            case R.id.textView_AlamatBengkel_InformasiBengkel:
                actionAlamat();
                break;
        }
    }

    public void initAttribute()
    {
        namaBengkel= rootView.findViewById(R.id.textView_namaBengkel_InformasiBengkel);
        infoBengkel= rootView.findViewById(R.id.textView_infoBengkel_InformasiBengkel);
        jamBukaBengkel= rootView.findViewById(R.id.textView_jamBukaBengkel__InformasiBengkel);
        noTelponBengkel= rootView.findViewById(R.id.textView_NoTelponBengkel_InformasiBengkel);
        webBengkel= rootView.findViewById(R.id.textView_WebBengkel_InformasiBengkel);
        alamatBengkel= rootView.findViewById(R.id.textView_AlamatBengkel_InformasiBengkel);

        namaBengkel.setOnClickListener(this);
        infoBengkel.setOnClickListener(this);
        jamBukaBengkel.setOnClickListener(this);
        noTelponBengkel.setOnClickListener(this);
        webBengkel.setOnClickListener(this);
        alamatBengkel.setOnClickListener(this);
    }

    private void buildRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerView_cabang_informasiBengkel);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new ListViewAdapterCabangInformasiBengkel(listCabang);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadCabang()
    {
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
                listCabang= response.body();
                adapter.notifyDataSetChanged();
                adapter= new ListViewAdapterCabangInformasiBengkel(response.body());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CabangDAO>> call, Throwable t) {

            }
        });
    }

    public void tampil()
    {
        nama= "Atma Auto Babarsari";
        info= "Bengkel Pusat Atma Auto Motorcycle Sparepart and Services";
        jam= "Buka pukul 08:00 A.M - 08:00 P.M";
        no_hp= "+62 822 5578 4350";
        web= "http://www.atmaauto.com";
        alamat= "Jalan babarsari gang purisari blok 11B, Kledokan, Caturtunggal, Kecamatan Depok, Kabupaten Sleman, Daerah Istimewa Yogykakarta";

        namaBengkel.setText(nama);
        infoBengkel.setText(info);
        jamBukaBengkel.setText(jam);
        noTelponBengkel.setText(no_hp +" (whatsapp)");
        webBengkel.setText(web);
        alamatBengkel.setText(alamat);
    }

    private void actionWeb()
    {
        Uri uri= Uri.parse(web);
        Intent intent= new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void actionNoTelp()
    {
        String url = "https://api.whatsapp.com/send?phone=" + no_hp;
        try {
            PackageManager pm = getContext().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getActivity(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void actionAlamat()
    {
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=-7.780382,110.414229");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
