package com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.HistoriTransaksiPelangganDetil;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiPengadaanDAO;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_menu;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_pengadaan_tampil_detil;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_tampil;
import com.toviddd.sitato.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapterTransaksiPengadaanTampil extends RecyclerView.Adapter<RecyclerAdapterTransaksiPengadaanTampil.MyViewHolder> {

    private List<TransaksiPengadaanDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Pengadaan";
    View v;

    public RecyclerAdapterTransaksiPengadaanTampil(Context context, List<TransaksiPengadaanDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterTransaksiPengadaanTampil(List<TransaksiPengadaanDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView supplier, cabang, tanggal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            supplier= itemView.findViewById(R.id.textView_supplier_pengadaanTampil);
            cabang= itemView.findViewById(R.id.textView_cabang_pengadaanTampil);
            tanggal= itemView.findViewById(R.id.textView_tanggal_pengadaanTampil);
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
        v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_transaksi_pengadaan_tampil, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final TransaksiPengadaanDAO tpDAO= result.get(i);
        myViewHolder.supplier.setText(tpDAO.getNama_supplier() +" (" +tpDAO.getNama_sales() +")");
        myViewHolder.cabang.setText(tpDAO.getNama_cabang());
        myViewHolder.tanggal.setText(tpDAO.getTanggal_pengadaan_sparepart());

        myViewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Pilih aksi");
                //groupId, itemId, order, title
                menu.add(0, v.getId(), 0, "Detail").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent i= new Intent(v.getContext(), Transaksi_pengadaan_tampil_detil.class);
                        i.putExtra("id_pengadaan_sparepart", tpDAO.getId_pengadaan_sparepart());
                        v.getContext().startActivity(i);
                        return true;
                    }
                });
            }
        });

    }

    private void editTransaksi(int id_transaksi)
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<TransaksiDAO> tDAO= apiClient.getTransaksi(id_transaksi);
        tDAO.enqueue(new Callback<TransaksiDAO>() {
            @Override
            public void onResponse(Call<TransaksiDAO> call, Response<TransaksiDAO> response) {
                if(response.body() != null)
                {
                    SharedPreferences pref= v.getContext().getSharedPreferences(Helper.PREF_TRANSAKSI, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= pref.edit();
                    Gson gson= new Gson();
                    String json= gson.toJson(response.body());
                    editor.putString(Helper.PREF_TRANSAKSI, json);
                    editor.commit();

                    Intent intent= new Intent(v.getContext(), Transaksi_menu.class);
                    v.getContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<TransaksiDAO> call, Throwable t) {
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
