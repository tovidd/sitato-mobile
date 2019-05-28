package com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.JasaServiceDAO;
import com.toviddd.sitato.R;

import java.util.List;

public class RecyclerAdapterJasaService extends RecyclerView.Adapter<RecyclerAdapterJasaService.MyViewHolder>{

    private List<JasaServiceDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Jasa Service";

    public RecyclerAdapterJasaService(Context context, List<JasaServiceDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterJasaService(List<JasaServiceDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_jasa_service, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final JasaServiceDAO jsDAO= result.get(i);
        myViewHolder.namaJS.setText(jsDAO.getNamaJasaService());
        myViewHolder.kodeJS.setText(jsDAO.getKodeJasaService());
        myViewHolder.hargaJS.setText("Rp. " +String.valueOf(jsDAO.getHargaJasaService()));
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

    public void filterListSparepart(List<JasaServiceDAO> filteredList)
    {
        result= filteredList;
        notifyDataSetChanged();
    }

}
