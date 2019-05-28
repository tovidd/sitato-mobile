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
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.R;

import java.util.List;


public class RecyclerAdapterCabangSearch extends RecyclerView.Adapter<RecyclerAdapterCabangSearch.MyViewHolder> {

    private List<CabangDAO> listCabang;
    private Context context;
    public String TAG= "Recycler Adapter Cabang Search";
    public static final String PREF_CABANG= "PREF_CABANG";


    public RecyclerAdapterCabangSearch(Context context, List<CabangDAO> listCabang)
    {
        this.context= context;
        this.listCabang= listCabang;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter_cabang_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position)  {
        final CabangDAO cabangDAO= listCabang.get(position);
        myViewHolder.namaCabang.setText(cabangDAO.getNama_cabang());
        myViewHolder.noTelpCabang.setText(cabangDAO.getNo_telepon_cabang());
        myViewHolder.alamatCabang.setText(cabangDAO.getAlamat_cabang());

        myViewHolder.namaCabang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // shared preferences
            CabangDAO p= new CabangDAO(cabangDAO.getId_cabang(), cabangDAO.getNama_cabang(), cabangDAO.getNo_telepon_cabang(), cabangDAO.getAlamat_cabang(), cabangDAO.getCreated_at());
            SharedPreferences pref= context.getSharedPreferences(PREF_CABANG, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= pref.edit();
            Gson gson= new Gson();
            String json= gson.toJson(p);
            editor.putString(PREF_CABANG, json);
            editor.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listCabang.size();
    }

    public void filterList(List<CabangDAO> filteredList) {
        listCabang = filteredList;
        notifyDataSetChanged();
    }
}
