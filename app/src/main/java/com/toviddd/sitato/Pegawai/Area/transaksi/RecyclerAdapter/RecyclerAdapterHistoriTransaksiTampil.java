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
import android.widget.Toast;

import com.google.gson.Gson;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.HistoriTransaksiPelangganDetil;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_menu;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_tampil;
import com.toviddd.sitato.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapterHistoriTransaksiTampil extends RecyclerView.Adapter<RecyclerAdapterHistoriTransaksiTampil.MyViewHolder> {

    private List<TransaksiDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart Tab3";
    View v;

    public RecyclerAdapterHistoriTransaksiTampil(Context context, List<TransaksiDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterHistoriTransaksiTampil(List<TransaksiDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView kodeT, totalT, tanggalT, csT, kasirT;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            kodeT= itemView.findViewById(R.id.textView_KodeTransaksi_tampilTransaksi);
            totalT= itemView.findViewById(R.id.textView_TotalTransaksi_tampilTransaksi);
            tanggalT= itemView.findViewById(R.id.textView_TanggalTransaksi_tampilTransaksi);
            csT= itemView.findViewById(R.id.textView_CSTransaksi_tampilTransaksi);
            kasirT= itemView.findViewById(R.id.textView_KasirTransaksi_tampilTransaksi);
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
        v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_histori_transaksi_tampil, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final TransaksiDAO tDAO= result.get(i);
        //Log.d(TAG, "-----------------------> onCreate lisSparepart sebelum createObject: " +result.get(i).getNama_sparepart());
        myViewHolder.kodeT.setText(tDAO.getKode_transaksi());
        if(tDAO.getKode_transaksi() == null)
        {
            myViewHolder.kodeT.setText(" ");
        }
        myViewHolder.totalT.setText("Rp. " +String.valueOf(tDAO.getTotal_transaksi()));
        String tanggal= tDAO.getCreated_at().substring(0, 10);
        String jam= tDAO.getCreated_at().substring(11, 19);
        myViewHolder.tanggalT.setText(tanggal +" (" +jam +")");
        myViewHolder.csT.setText("CS: " +tDAO.getNama_cs());
        if(tDAO.getNama_cs() == null)
        {
            myViewHolder.csT.setText("CS: ");
        }
        myViewHolder.kasirT.setText("Kasir: " +tDAO.getNama_kasir());
        if(tDAO.getNama_kasir() == null)
        {
            myViewHolder.kasirT.setText("Kasir: ");
        }
        myViewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Pilih aksi");
                //groupId, itemId, order, title
                menu.add(0, v.getId(), 0, "Detail").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        detailTransaksi(tDAO.getId_transaksi());
                        return true;
                    }
                });

                menu.add(0, v.getId(), 0, "Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        editTransaksi(tDAO.getId_transaksi());
                        return true;
                    }
                });
                menu.add(0, v.getId(), 0, "Hapus").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        deleteTransaksi(tDAO.getId_transaksi());
                        return true;
                    }
                });
            }
        });

    }

    private void detailTransaksi(int id_transaksi)
    {
        Intent i= new Intent(v.getContext(), HistoriTransaksiPelangganDetil.class);
        i.putExtra("id_transaksi", id_transaksi);
        v.getContext().startActivity(i);
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

                    Toast.makeText(v.getContext(), "id_transaksi: " +response.body().getId_transaksi(), Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(v.getContext(), Transaksi_menu.class);
                    v.getContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<TransaksiDAO> call, Throwable t) {
            }
        });
    }

    private void deleteTransaksi(final int id_transaksi)
    {
        DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit= builder.build();
                        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                        Call<String> tDAO= apiClient.deleteTransaksi2(id_transaksi);
                        tDAO.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.body() != null)
                                {
                                    Intent intent= new Intent(v.getContext(), Transaksi_tampil.class);
                                    v.getContext().startActivity(intent);
                                    ((Activity)v.getContext()).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder= new AlertDialog.Builder(v.getContext());
        builder.setMessage("Hapus transaksi,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
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
