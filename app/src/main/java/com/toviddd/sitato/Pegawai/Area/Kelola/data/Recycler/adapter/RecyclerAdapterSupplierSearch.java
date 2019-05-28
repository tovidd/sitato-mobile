package com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SupplierDAO;
import com.toviddd.sitato.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class RecyclerAdapterSupplierSearch extends RecyclerView.Adapter<RecyclerAdapterSupplierSearch.MyViewHolder> {

    private List<SupplierDAO> listSparepart;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart Search";
    public static final String PREF_SUPPLIER= "PREF_SUPPLIER";


    public RecyclerAdapterSupplierSearch(Context context, List<SupplierDAO> listSparepart)
    {
        this.context= context;
        this.listSparepart= listSparepart;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter_supplier_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position)  {
        final SupplierDAO sparepartDAO= listSparepart.get(position);
        myViewHolder.namaSupplier.setText(sparepartDAO.getNama_supplier());
        myViewHolder.noTelpSupplier.setText(sparepartDAO.getNo_telepon_supplier());
        myViewHolder.alamatSupplier.setText(sparepartDAO.getAlamat_supplier());
        myViewHolder.namaSales.setText(sparepartDAO.getNama_sales());

        myViewHolder.namaSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // shared preferences
            SupplierDAO s= new SupplierDAO(sparepartDAO.getId_supplier(), sparepartDAO.getNama_supplier(), sparepartDAO.getNo_telepon_supplier(), sparepartDAO.getAlamat_supplier(), sparepartDAO.getNama_supplier());
            SharedPreferences pref= context.getSharedPreferences(PREF_SUPPLIER, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= pref.edit();
            Gson gson= new Gson();
            String json= gson.toJson(s);
            editor.putString(PREF_SUPPLIER, json);
            editor.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSparepart.size();
    }

    public void filterList(List<SupplierDAO> filteredList) {
        listSparepart = filteredList;
        notifyDataSetChanged();
    }
}
