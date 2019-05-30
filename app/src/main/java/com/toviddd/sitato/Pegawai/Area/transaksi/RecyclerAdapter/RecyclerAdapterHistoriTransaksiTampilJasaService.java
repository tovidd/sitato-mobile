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
import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.HistoriTransaksiPelangganDetil;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiJasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Cabang;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_jasaService;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_menu;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_tampil;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_tampil_jasa_service;
import com.toviddd.sitato.R;

import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapterHistoriTransaksiTampilJasaService extends RecyclerView.Adapter<RecyclerAdapterHistoriTransaksiTampilJasaService.MyViewHolder> {

    private List<TransaksiJasaServiceDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart Tab3";
    View v;

    public RecyclerAdapterHistoriTransaksiTampilJasaService(Context context, List<TransaksiJasaServiceDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterHistoriTransaksiTampilJasaService(List<TransaksiJasaServiceDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView kodeTransaksi, statusPengerjaan, namaPelanggan, noPlat, kodeJasa, jumlahBeli, subtotal, montir;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            kodeTransaksi= itemView.findViewById(R.id.textView_KodeTransaksi_tampilTransaksiJasaService);
            statusPengerjaan= itemView.findViewById(R.id.textView_statusTransaksi_tampilTransaksiJasaService);
            namaPelanggan= itemView.findViewById(R.id.textView_namaPelanggan_tampilTransaksiJasaService);
            noPlat= itemView.findViewById(R.id.textView_noPlat_tampilTransaksiJasaService);
            kodeJasa= itemView.findViewById(R.id.textView_kodeJasa_tampilTransaksiJasaService);
            jumlahBeli= itemView.findViewById(R.id.textView_jumlahBeli_tampilTransaksiJasaService);
            subtotal= itemView.findViewById(R.id.textView_subtotal_tampilTransaksiJasaService);
            montir= itemView.findViewById(R.id.textView_namaMontir_tampilTransaksiJasaService);
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
        v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_histori_transaksi_tampil_jasa_service, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final TransaksiJasaServiceDAO tDAO= result.get(i);

        myViewHolder.kodeTransaksi.setText(tDAO.getKode_transaksi());
        myViewHolder.statusPengerjaan.setText(tDAO.getNama_status_pengerjaan());
        myViewHolder.namaPelanggan.setText(tDAO.getNama_pelanggan());
        myViewHolder.noPlat.setText(tDAO.getNo_plat_kendaraan());
        myViewHolder.kodeJasa.setText(String.valueOf(tDAO.getKode_jasa_service()) +"(" +tDAO.getNama_jasa_service() +")");
        myViewHolder.jumlahBeli.setText(String.valueOf(tDAO.getJumlah_transaksi_penjualan_jasa_service()) +" unit");
        myViewHolder.subtotal.setText("Rp. " +String.valueOf(tDAO.getSubtotal_transaksi_penjualan_jasa_service()));
        myViewHolder.montir.setText("Montir: " +tDAO.getNama_montir());

        myViewHolder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Pilih aksi");
                //groupId, itemId, order, title
                menu.add(0, v.getId(), 0, "Ubah status").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ubahStatus(tDAO.getId_transaksi_penjualan_jasa_service());
                        return true;
                    }
                });
            }
        });

    }

    private void ubahStatus(final int id_transaksi_penjualan_jasa_service)
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
                        Call<TransaksiJasaServiceDAO> tDAO= apiClient.ubahStatusJasaService(id_transaksi_penjualan_jasa_service);
                        tDAO.enqueue(new Callback<TransaksiJasaServiceDAO>() {
                            @Override
                            public void onResponse(Call<TransaksiJasaServiceDAO> call, Response<TransaksiJasaServiceDAO> response) {
                                if(response.code() == 200)
                                {
                                    v.getContext().startActivity(new Intent(v.getContext(), Transaksi_tampil_jasa_service.class));
                                    CustomIntent.customType(v.getContext(), "fadein-to-fadeout");
                                    FancyToast.makeText(v.getContext(), "Berhasil mengubah status", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                }
                                else
                                {
                                    FancyToast.makeText(v.getContext(), "Gagal mengubah status", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TransaksiJasaServiceDAO> call, Throwable t) {
                                FancyToast.makeText(v.getContext(), "Gagal mengubah status", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            }
                        });
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder= new AlertDialog.Builder(v.getContext());
        builder.setMessage("Yakin ingin mengubah status menjadi selesai dikerjakan,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
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
