package com.toviddd.sitato.tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.DAO.ApiClient;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Cabang;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterCabang;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.R;
import com.toviddd.sitato.RecyclerAdapterHistoriTransaksiTab3;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab3 extends Fragment implements View.OnClickListener {

    private View rootView;
    private EditText noPlat, noHP;
    private Button cekHistori;
    private RecyclerView rvProgress, rvTransaksi;
    private RecyclerAdapterHistoriTransaksiTab3 raHistoriTransaksi;
    private RecyclerAdapterHistoriTransaksiTab3 raProgressTransaksi;
    private List<TransaksiDAO> listTransaksi= new ArrayList<>();
    private List<TransaksiDAO> listTransaksiProgress= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.tab_3, container, false);
        initAttribute();
        buildRVTransaksiProgress();
        buildRVTransaksi();
        noPlat.setText("AB4160UH");
        noHP.setText("080099992222");

        return rootView;
    }

    private void initAttribute()
    {
        noPlat= rootView.findViewById(R.id.et_noPlat_cekHistori);
        noHP= rootView.findViewById(R.id.et_noHP_cekHistori);
        rvProgress= rootView.findViewById(R.id.recyclerView_transaksiProgres_histori);
        rvTransaksi= rootView.findViewById(R.id.recyclerView_transaksi_histori);
        cekHistori= rootView.findViewById(R.id.btn_cekHistori);
        cekHistori.setOnClickListener(this);
    }

    private void buildRVTransaksiProgress()
    {
        raProgressTransaksi= new RecyclerAdapterHistoriTransaksiTab3(getContext(), listTransaksiProgress);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getContext());
        rvTransaksi.setLayoutManager(mLayoutManager);
        rvTransaksi.setItemAnimator(new DefaultItemAnimator());
        rvTransaksi.setAdapter(raProgressTransaksi);
    }

    private void buildRVTransaksi()
    {
        raHistoriTransaksi= new RecyclerAdapterHistoriTransaksiTab3(getContext(), listTransaksi);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getContext());
        rvProgress.setLayoutManager(mLayoutManager);
        rvProgress.setItemAnimator(new DefaultItemAnimator());
        rvProgress.setAdapter(raHistoriTransaksi);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_cekHistori:
                cekLogin();
                break;
        }
    }

    private void cekLogin()
    {
        if(noPlat.getText().toString().isEmpty() || noHP.getText().toString().isEmpty())
        {
            FancyToast.makeText(getContext(), "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(noPlat.getText().toString().length()>0 && noPlat.getText().toString().length()<4 || noPlat.getText().toString().length()>8 ||
                noHP.getText().toString().length()>0 && noHP.getText().toString().length()<12 || noHP.getText().toString().length()>13)
        {
            if(noPlat.getText().toString().length()<4 || noPlat.getText().toString().length()>8)
            {
                FancyToast.makeText(getContext(), "No. plat harus 4-8 alfanumerik", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
            else if(noHP.getText().toString().length()<12 || noHP.getText().toString().length()>13)
            {
                FancyToast.makeText(getContext(), "No. HP harus 12-13 digit", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            }
        }
        else
        {
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            ApiClient apiClient= retrofit.create(ApiClient.class);
            Call<String> cekLoginPelanggan= apiClient.cekLoginPelanggan(noPlat.getText().toString(), noHP.getText().toString());
            cekLoginPelanggan.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.code() == 200)
                    {
                        showHistoriTransaksi();
                        showProgressTransaksi();
                    }
                    else
                    {
                        FancyToast.makeText(getContext(), "No. plat atau No.hp tidak sesuai", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    FancyToast.makeText(getContext(), "No. plat atau No.hp tidak sesuai", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
            });
        }
    }

    private void showProgressTransaksi()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        ApiClient apiClient= retrofit.create(ApiClient.class);
        Call<List<TransaksiDAO>> tDAO= apiClient.getAllTransaksiProgressPelanggan(noHP.getText().toString());
        tDAO.enqueue(new Callback<List<TransaksiDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiDAO>> call, Response<List<TransaksiDAO>> response) {
                if(response.body() != null)
                {
                    raProgressTransaksi.notifyDataSetChanged();
                    raProgressTransaksi= new RecyclerAdapterHistoriTransaksiTab3(getContext(), response.body());
                    rvProgress.setAdapter(raProgressTransaksi);
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiDAO>> call, Throwable t) {

            }
        });
    }

    private void showHistoriTransaksi()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        ApiClient apiClient= retrofit.create(ApiClient.class);
        Call<List<TransaksiDAO>> tDAO= apiClient.getAllTransaksiPelanggan(noHP.getText().toString());
        tDAO.enqueue(new Callback<List<TransaksiDAO>>() {
            @Override
            public void onResponse(Call<List<TransaksiDAO>> call, Response<List<TransaksiDAO>> response) {
                if(response.body() != null)
                {
                    raHistoriTransaksi.notifyDataSetChanged();
                    raHistoriTransaksi= new RecyclerAdapterHistoriTransaksiTab3(getContext(), response.body());
                    rvTransaksi.setAdapter(raHistoriTransaksi);
                }
                else
                {
                    FancyToast.makeText(getContext(), "No. plat atau No.hp tidak sesuai", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiDAO>> call, Throwable t) {
                FancyToast.makeText(getContext(), "No. plat atau No.hp tidak sesuai", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
            }
        });
    }

}
