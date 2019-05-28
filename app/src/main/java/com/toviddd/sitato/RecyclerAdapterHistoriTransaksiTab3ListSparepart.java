package com.toviddd.sitato;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;

import java.util.List;

public class RecyclerAdapterHistoriTransaksiTab3ListSparepart extends RecyclerView.Adapter<RecyclerAdapterHistoriTransaksiTab3ListSparepart.MyViewHolder> {

    private List<TransaksiSparepartDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart Tab3 List Sparepart";

    public RecyclerAdapterHistoriTransaksiTab3ListSparepart(Context context, List<TransaksiSparepartDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterHistoriTransaksiTab3ListSparepart(List<TransaksiSparepartDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView namaSparepart, kodeSparepart, jumlahBeliSparepart, subtotalSparepart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaSparepart= itemView.findViewById(R.id.texView_NamaSparepart_detilHistoriTransaksi);
            kodeSparepart= itemView.findViewById(R.id.texView_kodeSparepart_detilHistoriTransaksi);
            jumlahBeliSparepart= itemView.findViewById(R.id.texView_jumlahBeliSparepart_detilHistoriTransaksi);
            subtotalSparepart= itemView.findViewById(R.id.texView_subtotalSparepart_detilHistoriTransaksi);
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
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_histori_transaksi_tab_3_list_sparepart, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final TransaksiSparepartDAO tsDAO= result.get(i);
        //Log.d(TAG, "-----------------------> onCreate lisSparepart sebelum createObject: " +result.get(i).getNama_sparepart());
        myViewHolder.namaSparepart.setText(tsDAO.getNama_sparepart());
        myViewHolder.kodeSparepart.setText(tsDAO.getKode_sparepart());
        myViewHolder.jumlahBeliSparepart.setText(String.valueOf(tsDAO.getJumlah_transaksi_penjualan_sparepart()) +" unit" +", @" +String.valueOf(tsDAO.getHarga_jual_sparepart()));
        myViewHolder.subtotalSparepart.setText("Rp. " +String.valueOf(tsDAO.getSubtotal_transaksi_penjualan_sparepart()));
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
