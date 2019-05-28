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
import com.toviddd.sitato.Pegawai.Area.DAO.JasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiJasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_jasaService;
import com.toviddd.sitato.R;

import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapterTransaksiJasaService extends RecyclerView.Adapter<RecyclerAdapterTransaksiJasaService.MyViewHolder>{

    private List<TransaksiJasaServiceDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Transaksi Sparepart";
//    JasaServiceDAO js;

    public RecyclerAdapterTransaksiJasaService(Context context, List<TransaksiJasaServiceDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView kodeJasaService, namaJasaService, hargaJasaService;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            namaJasaService= itemView.findViewById(R.id.textView_NamaJasaService_TransaksiJasaService);
            kodeJasaService= itemView.findViewById(R.id.textView_KodeJasaService_TransaksiJasaService);
            hargaJasaService= itemView.findViewById(R.id.textView_hargaJasaService_TransaksiJasaService);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.transaksi_jasa_service_recycler_adapter, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        final TransaksiJasaServiceDAO tjs= result.get(i);
        myViewHolder.namaJasaService.setText(String.valueOf(tjs.getJumlah_transaksi_penjualan_jasa_service()) +" buah " +tjs.getNama_jasa_service());
        myViewHolder.kodeJasaService.setText(String.valueOf(tjs.getKode_jasa_service()));
        myViewHolder.hargaJasaService.setText("subtotal Rp. " +String.valueOf(tjs.getSubtotal_transaksi_penjualan_jasa_service()));

        myViewHolder.namaJasaService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=> onClick beli: " +tjs.getNama_jasa_service());
                SharedPreferences pref= context.getSharedPreferences(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                Gson gson= new Gson();
                String json= gson.toJson(tjs);
                editor.putString(Helper.PREF_BELI_TRANSAKSI_JASA_SERVICE, json);
                editor.commit();
                context.startActivity(new Intent(context, Transaksi_jasaService.class));
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
