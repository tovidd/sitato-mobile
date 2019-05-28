package com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SupplierDAO;
import com.toviddd.sitato.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class RecyclerAdapterSupplier extends RecyclerView.Adapter<RecyclerAdapterSupplier.MyViewHolder>{

    private List<SupplierDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Supplier";

    public RecyclerAdapterSupplier(Context context, List<SupplierDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterSupplier(List<SupplierDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView namaSupplier, noTelpSupplier, alamatSupplier, namaSales;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            namaSupplier= itemView.findViewById(R.id.textView_NamaSupplier);
            noTelpSupplier= itemView.findViewById(R.id.textView_NoTelponSupplier);
            alamatSupplier= itemView.findViewById(R.id.textView_AlamatSupplier);
            namaSales= itemView.findViewById(R.id.textView_NamaSales);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_supplier, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final SupplierDAO sparepartDAO= result.get(i);
        myViewHolder.namaSupplier.setText(sparepartDAO.getNama_supplier());
        myViewHolder.noTelpSupplier.setText(sparepartDAO.getNo_telepon_supplier());
        myViewHolder.alamatSupplier.setText(sparepartDAO.getAlamat_supplier());
        myViewHolder.namaSales.setText(sparepartDAO.getNama_sales());
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

    public void filterListSparepart(List<SupplierDAO> filteredList)
    {
        result= filteredList;
        notifyDataSetChanged();
    }

}
