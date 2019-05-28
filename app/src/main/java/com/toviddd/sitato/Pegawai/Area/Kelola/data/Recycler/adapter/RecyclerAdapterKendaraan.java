package com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.KendaraanDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapterKendaraan extends RecyclerView.Adapter<RecyclerAdapterKendaraan.MyViewHolder>{

    private List<KendaraanDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Kendaraan";

    public RecyclerAdapterKendaraan(Context context, List<KendaraanDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView noPlatKendaraan, merekKendaraan, jenisKendaraan;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            noPlatKendaraan= itemView.findViewById(R.id.textView_NoPlat_Kendaraan);
            merekKendaraan= itemView.findViewById(R.id.textView_Merek_Kendaraan);
            jenisKendaraan= itemView.findViewById(R.id.textView_Jenis_Kendaraan);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_kendaraan, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final KendaraanDAO kendaraanDAO= result.get(i);

        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<PelangganDAO> sDAO= apiClient.getPelanggan(kendaraanDAO.getId_pelanggan());
        sDAO.enqueue(new Callback<PelangganDAO>() {
            @Override
            public void onResponse(Call<PelangganDAO> call, Response<PelangganDAO> response) {
                if(response.body() != null)
                {
                    // PERBAIKI KALO PELANGGA DI HAPUS ADAPTER NYA YANG KOSONG< ITU DIHILANGIN
                    myViewHolder.noPlatKendaraan.setText(kendaraanDAO.getNo_plat_kendaraan() +"(" +response.body().getNama_pelanggan() +")");
                    myViewHolder.merekKendaraan.setText(kendaraanDAO.getMerek_kendaraan());
                    myViewHolder.jenisKendaraan.setText(kendaraanDAO.getJenis_kendaraan());
                    Log.d(TAG, "ooooooooooooooooooooooooo>>> onBindViewHolder: " +response.body().getNama_pelanggan());
                }
            }

            @Override
            public void onFailure(Call<PelangganDAO> call, Throwable t) {
                Log.d(TAG, "ooooooooooooooooooooooooo>>> onBindViewHolder: ERRRROR ");
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if(result == null)
        {
            return 0;
        }
        return result.size();
    }

    public void filterListSparepart(List<KendaraanDAO> filteredList)
    {
        result= filteredList;
        notifyDataSetChanged();
    }

}
