package com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
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
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiPengadaanDetilDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_pengadaan_detil;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_sparepart;
import com.toviddd.sitato.R;

import java.util.List;

import maes.tech.intentanim.CustomIntent;

public class RecyclerAdapterTransaksiPengadaanSparepart extends RecyclerView.Adapter<RecyclerAdapterTransaksiPengadaanSparepart.MyViewHolder>{

    private List<TransaksiPengadaanDetilDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Transaksi Sparepart";

    public RecyclerAdapterTransaksiPengadaanSparepart(Context context, List<TransaksiPengadaanDetilDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView kodeSparepart, namaSparepart, merekSparepart;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            namaSparepart= itemView.findViewById(R.id.textView_NamaSparepart_TransaksiSparepart);
            kodeSparepart= itemView.findViewById(R.id.textView_KodeSparepart_TransaksiSparepart);
            merekSparepart= itemView.findViewById(R.id.textView_MerekSparepart_TransaksiSparepart);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.transaksi_sparepart_recycler_adapter, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        final TransaksiPengadaanDetilDAO t= result.get(i);
        myViewHolder.namaSparepart.setText(t.getJumlah_beli_detil_pengadaan_sparepart() +" buah " +t.getNama_sparepart());
        myViewHolder.kodeSparepart.setText(t.getKode_sparepart());
        myViewHolder.merekSparepart.setText(t.getJenis_sparepart() +", harga Rp. " +t.getSubtotal_detil_pengadaan_sparepart());

        myViewHolder.namaSparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref= context.getSharedPreferences(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                Gson gson= new Gson();
                String json= gson.toJson(t);
                editor.putString(Helper.PREF_TRANSAKSI_DETIL_PENGADAAN_SPAREPART, json);
                editor.commit();
                context.startActivity(new Intent(context, Transaksi_pengadaan_detil.class));
                CustomIntent.customType(context, "fadein-to-fadeout");
            }
        });
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
}
