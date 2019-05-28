package com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.JasaServiceDAO;
import com.toviddd.sitato.R;

import java.util.List;


public class RecyclerAdapterJasaServiceSearch extends RecyclerView.Adapter<RecyclerAdapterJasaServiceSearch.MyViewHolder> {

    private List<JasaServiceDAO> listJS;
    private Context context;
    public String TAG= "Recycler Adapter Jasa Service Search";

    public RecyclerAdapterJasaServiceSearch(Context context, List<JasaServiceDAO> listJS)
    {
        this.context= context;
        this.listJS= listJS;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView namaJS, kodeJS, hargaJS;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            namaJS= itemView.findViewById(R.id.textView_NamaJasaServie);
            kodeJS= itemView.findViewById(R.id.textView_KodeJasaServie);
            hargaJS= itemView.findViewById(R.id.textView_HargaJasaServie);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter_jasa_service_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position)  {
        final JasaServiceDAO jsDAO= listJS.get(position);
        myViewHolder.namaJS.setText(jsDAO.getNamaJasaService());
        myViewHolder.kodeJS.setText(jsDAO.getKodeJasaService());
        myViewHolder.hargaJS.setText("Rp. " +String.valueOf(jsDAO.getHargaJasaService()));

        myViewHolder.namaJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // shared preferences
                JasaServiceDAO p= new JasaServiceDAO(jsDAO.getId_jasa_service(), jsDAO.getNamaJasaService(), jsDAO.getKodeJasaService(), jsDAO.getHargaJasaService());
                SharedPreferences pref= context.getSharedPreferences(Helper.PREF_JASA_SERVICE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                Gson gson= new Gson();
                String json= gson.toJson(p);
                editor.putString(Helper.PREF_JASA_SERVICE, json);
                editor.commit();
                Log.d(TAG, "JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ>> onClick: " +p.getNamaJasaService());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listJS.size();
    }

    public void filterList(List<JasaServiceDAO> filteredList) {
        listJS = filteredList;
        notifyDataSetChanged();
    }
}
