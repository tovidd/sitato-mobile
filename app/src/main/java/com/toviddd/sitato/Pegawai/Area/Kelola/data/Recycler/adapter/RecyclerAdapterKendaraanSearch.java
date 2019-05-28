package com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.KendaraanDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecyclerAdapterKendaraanSearch extends RecyclerView.Adapter<RecyclerAdapterKendaraanSearch.MyViewHolder> {

    private List<KendaraanDAO> listKendaraan;
    private Context context;
    public String TAG= "Recycler Adapter Kendaraan Search";
    public static final String PREF_KENDARAAN= "PREF_KENDARAAN";


    public RecyclerAdapterKendaraanSearch(Context context, List<KendaraanDAO> listKendaraan)
    {
        this.context= context;
        this.listKendaraan= listKendaraan;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView noPlatKendaraan, merekKendaraan, jenisKendaraan;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            noPlatKendaraan= itemView.findViewById(R.id.textView_NoPlatKendaraan);
            merekKendaraan= itemView.findViewById(R.id.textView_MerekKendaraan);
            jenisKendaraan= itemView.findViewById(R.id.textView_JenisKendaraan);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter_kendaraan_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position)  {
        final KendaraanDAO kendaraanDAO= listKendaraan.get(position);

        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<PelangganDAO> sDAO= apiClient.getPelanggan(kendaraanDAO.getId_pelanggan());
        sDAO.enqueue(new Callback<PelangganDAO>() {
            @Override
            public void onResponse(Call<PelangganDAO> call, Response<PelangganDAO> response) {
                myViewHolder.noPlatKendaraan.setText(kendaraanDAO.getNo_plat_kendaraan() +"(" +response.body().getNama_pelanggan() +")");
                myViewHolder.merekKendaraan.setText(kendaraanDAO.getMerek_kendaraan());
                myViewHolder.jenisKendaraan.setText(kendaraanDAO.getJenis_kendaraan());
            }

            @Override
            public void onFailure(Call<PelangganDAO> call, Throwable t) {

            }
        });

        myViewHolder.noPlatKendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // shared preferences
            KendaraanDAO p= new KendaraanDAO(kendaraanDAO.getId_pelanggan(), kendaraanDAO.getId_kendaraan(), kendaraanDAO.getNo_plat_kendaraan(), kendaraanDAO.getMerek_kendaraan(), kendaraanDAO.getJenis_kendaraan(), kendaraanDAO.getCreated_at());
            SharedPreferences pref= context.getSharedPreferences(PREF_KENDARAAN, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= pref.edit();
            Gson gson= new Gson();
            String json= gson.toJson(p);
            editor.putString(PREF_KENDARAAN, json);
            editor.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listKendaraan.size();
    }

    public void filterList(List<KendaraanDAO> filteredList) {
        listKendaraan = filteredList;
        notifyDataSetChanged();
    }
}
