package com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter;

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
import com.toviddd.sitato.Pegawai.Area.DAO.JasaServiceDAO;
import com.toviddd.sitato.R;

import java.util.List;


public class RecyclerAdapterJasaServiceTransaksiJasaServiceSearch extends RecyclerView.Adapter<RecyclerAdapterJasaServiceTransaksiJasaServiceSearch.MyViewHolder> {

    private List<JasaServiceDAO> listJasaService;
    private Context context;
    public String TAG= "Recycler Adapter Jasa Service Transaksi Jasa Service Search";

    public RecyclerAdapterJasaServiceTransaksiJasaServiceSearch(Context context, List<JasaServiceDAO> listJasaService)
    {
        this.context= context;
        this.listJasaService= listJasaService;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView namaJasaService, kodeJasaService, hargaJasaService;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            namaJasaService= itemView.findViewById(R.id.textView_NamaJasaServiceTransaksiJasaService);
            kodeJasaService= itemView.findViewById(R.id.textView_KodeJasaServiceTransaksiJasaService);
            hargaJasaService= itemView.findViewById(R.id.textView_HargaJasaServiceTransaksiJasaService);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaksi_jasa_service_recycler_adapter_jasa_service_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position)  {
        final JasaServiceDAO jsDAO= listJasaService.get(position);
        //Log.d(TAG, "-----------------------> onCreate lisSparepart sebelum createObject: " +result.get(i).getNama_sparepart());
        myViewHolder.namaJasaService.setText(jsDAO.getNamaJasaService());
        myViewHolder.kodeJasaService.setText("Tipe: " + jsDAO.getKodeJasaService());
        myViewHolder.hargaJasaService.setText("Rp. " + jsDAO.getHargaJasaService());

        myViewHolder.namaJasaService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // shared preferences
            SharedPreferences pref= context.getSharedPreferences(Helper.PREF_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= pref.edit();
            Gson gson= new Gson();
            String json= gson.toJson(jsDAO);
            editor.putString(Helper.PREF_TRANSAKSI_JASA_SERVICE, json);
            editor.commit();
            Log.d(TAG, "==========================> onClick js name:  "+jsDAO.getNamaJasaService());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listJasaService.size();
    }

    public void filterList(List<JasaServiceDAO> filteredList) {
        listJasaService = filteredList;
        notifyDataSetChanged();
    }

}
