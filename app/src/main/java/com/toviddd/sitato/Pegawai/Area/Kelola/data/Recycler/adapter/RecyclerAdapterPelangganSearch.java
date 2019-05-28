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
import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SupplierDAO;
import com.toviddd.sitato.R;

import java.util.List;


public class RecyclerAdapterPelangganSearch extends RecyclerView.Adapter<RecyclerAdapterPelangganSearch.MyViewHolder> {

    private List<PelangganDAO> listPelanggan;
    private Context context;
    public String TAG= "Recycler Adapter Pelanggan Search";


    public RecyclerAdapterPelangganSearch(Context context, List<PelangganDAO> listPelanggan)
    {
        this.context= context;
        this.listPelanggan= listPelanggan;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView namaPelanggan, noTelpPelanggan, alamatPelanggan;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            namaPelanggan= itemView.findViewById(R.id.textView_NamaPelanggan);
            noTelpPelanggan= itemView.findViewById(R.id.textView_NoTelponPelanggan);
            alamatPelanggan= itemView.findViewById(R.id.textView_AlamatPelanggan);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter_pelanggan_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position)  {
        final PelangganDAO pelangganDAO= listPelanggan.get(position);
        myViewHolder.namaPelanggan.setText(pelangganDAO.getNama_pelanggan());
        myViewHolder.noTelpPelanggan.setText(pelangganDAO.getNo_telepon_pelanggan());
        myViewHolder.alamatPelanggan.setText(pelangganDAO.getAlamat_pelanggan());

        myViewHolder.namaPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // shared preferences
            PelangganDAO p= new PelangganDAO(pelangganDAO.getId_pelanggan(), pelangganDAO.getNama_pelanggan(), pelangganDAO.getNo_telepon_pelanggan(), pelangganDAO.getAlamat_pelanggan(), pelangganDAO.getCreated_at());
            SharedPreferences pref= context.getSharedPreferences(Helper.PREF_PELANGGAN, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= pref.edit();
            Gson gson= new Gson();
            String json= gson.toJson(p);
            editor.putString(Helper.PREF_PELANGGAN, json);
            editor.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listPelanggan.size();
    }

    public void filterList(List<PelangganDAO> filteredList) {
        listPelanggan = filteredList;
        notifyDataSetChanged();
    }
}
