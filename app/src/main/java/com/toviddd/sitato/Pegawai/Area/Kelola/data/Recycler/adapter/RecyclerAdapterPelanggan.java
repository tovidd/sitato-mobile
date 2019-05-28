package com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SupplierDAO;
import com.toviddd.sitato.R;

import java.util.List;

public class RecyclerAdapterPelanggan extends RecyclerView.Adapter<RecyclerAdapterPelanggan.MyViewHolder>{

    private List<PelangganDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Pelanggan";

    public RecyclerAdapterPelanggan(Context context, List<PelangganDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterPelanggan(List<PelangganDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_pelanggan, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final PelangganDAO pelangganDAO= result.get(i);
        myViewHolder.namaPelanggan.setText(pelangganDAO.getNama_pelanggan());
        myViewHolder.noTelpPelanggan.setText(pelangganDAO.getNo_telepon_pelanggan());
        myViewHolder.alamatPelanggan.setText(pelangganDAO.getAlamat_pelanggan());
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

    public void filterListSparepart(List<PelangganDAO> filteredList)
    {
        result= filteredList;
        notifyDataSetChanged();
    }

}
