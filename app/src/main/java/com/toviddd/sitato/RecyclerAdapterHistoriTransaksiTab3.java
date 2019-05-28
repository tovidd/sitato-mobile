package com.toviddd.sitato;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;

import java.util.List;

public class RecyclerAdapterHistoriTransaksiTab3 extends RecyclerView.Adapter<RecyclerAdapterHistoriTransaksiTab3.MyViewHolder> {

    private List<TransaksiDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart Tab3";

    public RecyclerAdapterHistoriTransaksiTab3(Context context, List<TransaksiDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterHistoriTransaksiTab3(List<TransaksiDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView kodeT, totalT, tanggalT, csT, kasirT;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            kodeT= itemView.findViewById(R.id.textView_KodeTransaksi_histori);
            totalT= itemView.findViewById(R.id.textView_TotalTransaksi_histori);
            tanggalT= itemView.findViewById(R.id.textView_TanggalTransaksi_histori);
            csT= itemView.findViewById(R.id.textView_CSTransaksi_histori);
            kasirT= itemView.findViewById(R.id.textView_KasirTransaksi_histori);
        }

        @Override
        public void onClick(View view)
        {
            //Toast.makeText(context, "hey you clicked on me", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_histori_transaksi_tab_3, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final TransaksiDAO tDAO= result.get(i);
        //Log.d(TAG, "-----------------------> onCreate lisSparepart sebelum createObject: " +result.get(i).getNama_sparepart());
        myViewHolder.kodeT.setText(tDAO.getKode_transaksi());
        myViewHolder.totalT.setText("Rp. " +String.valueOf(tDAO.getTotal_transaksi()));
        String tanggal= tDAO.getCreated_at().substring(0, 10);
        String jam= tDAO.getCreated_at().substring(11, 19);
        myViewHolder.tanggalT.setText(tanggal +" (" +jam +")");
        myViewHolder.csT.setText("CS: " +tDAO.getNama_cs());
        myViewHolder.kasirT.setText("Kasir: " +tDAO.getNama_kasir());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(v.getContext(), HistoriTransaksiPelangganDetil.class);
                i.putExtra("id_transaksi", tDAO.getId_transaksi());
                v.getContext().startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(result == null)
        {
            return 0;
        }
        return result.size();
    }
}
