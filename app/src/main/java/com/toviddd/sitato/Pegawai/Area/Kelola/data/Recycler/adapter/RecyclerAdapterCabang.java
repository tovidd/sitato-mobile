package com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.R;

import java.util.List;

public class RecyclerAdapterCabang extends RecyclerView.Adapter<RecyclerAdapterCabang.MyViewHolder>{

    private List<CabangDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Cabang";

    public RecyclerAdapterCabang(Context context, List<CabangDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterCabang(List<CabangDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView namaCabang, noTelpCabang, alamatCabang;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            namaCabang= itemView.findViewById(R.id.textView_NamaCabang);
            noTelpCabang= itemView.findViewById(R.id.textView_NoTelponCabang);
            alamatCabang= itemView.findViewById(R.id.textView_AlamatCabang);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_cabang, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final CabangDAO cabangDAO= result.get(i);
        myViewHolder.namaCabang.setText(cabangDAO.getNama_cabang());
        myViewHolder.noTelpCabang.setText(cabangDAO.getNo_telepon_cabang());
        myViewHolder.alamatCabang.setText(cabangDAO.getAlamat_cabang());
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

    public void filterListSparepart(List<CabangDAO> filteredList)
    {
        result= filteredList;
        notifyDataSetChanged();
    }

}
