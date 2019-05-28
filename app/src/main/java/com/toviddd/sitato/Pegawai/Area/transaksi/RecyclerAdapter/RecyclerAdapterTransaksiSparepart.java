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
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.PelangganDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_sparepart;
import com.toviddd.sitato.R;

import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapterTransaksiSparepart extends RecyclerView.Adapter<RecyclerAdapterTransaksiSparepart.MyViewHolder>{

    private List<TransaksiSparepartDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Transaksi Sparepart";

    public RecyclerAdapterTransaksiSparepart(Context context, List<TransaksiSparepartDAO> result)
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
        final TransaksiSparepartDAO t= result.get(i);
        myViewHolder.namaSparepart.setText(t.getJumlah_transaksi_penjualan_sparepart() +" buah " +t.getNama_sparepart());
        myViewHolder.kodeSparepart.setText(t.getKode_sparepart());
        myViewHolder.merekSparepart.setText(t.getJenis_sparepart() +", harga Rp. " +t.getSubtotal_transaksi_penjualan_sparepart());

        myViewHolder.namaSparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=> onClick beli: " +t.getNama_sparepart());
                SharedPreferences pref= context.getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                Gson gson= new Gson();
                String json= gson.toJson(t);
                editor.putString(Helper.PREF_BELI_TRANSAKSI_SPAREPART, json);
                editor.commit();
                context.startActivity(new Intent(context, Transaksi_sparepart.class));
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
