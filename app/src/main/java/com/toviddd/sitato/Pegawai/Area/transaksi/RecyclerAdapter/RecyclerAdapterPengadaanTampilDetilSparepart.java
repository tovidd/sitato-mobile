package com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiPengadaanDetilDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Cabang;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_pengadaan_tampil_detil;
import com.toviddd.sitato.R;

import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapterPengadaanTampilDetilSparepart extends RecyclerView.Adapter<RecyclerAdapterPengadaanTampilDetilSparepart.MyViewHolder> {

    private List<TransaksiPengadaanDetilDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart Tab3 List Sparepart";

    public RecyclerAdapterPengadaanTampilDetilSparepart(Context context, List<TransaksiPengadaanDetilDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterPengadaanTampilDetilSparepart(List<TransaksiPengadaanDetilDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView namaSparepart, kodeSparepart, jumlahPesanSparepart;
        private EditText jumlahTerima;
        private Button perbaharui;
        private LinearLayout ll;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaSparepart= itemView.findViewById(R.id.texView_namaSparepart_pengadaanTampilDetil);
            kodeSparepart= itemView.findViewById(R.id.texView_kodeSparepart_pengadaanTampilDetil);
            jumlahPesanSparepart= itemView.findViewById(R.id.texView_jumlahPesan_pengadaanTampilDetil);
            jumlahTerima= itemView.findViewById(R.id.editText_jumlahTerima_pengadaanTampilDetil);
            perbaharui= itemView.findViewById(R.id.button_perbaharui_pengadaanTampilDetil);
            ll= itemView.findViewById(R.id.linearLayout_pengadaanTampilDetil);
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
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_pengadaan_tampil_detil_sparepart, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final TransaksiPengadaanDetilDAO tpdDAO= result.get(i);
        if(tpdDAO.getJumlah_beli_detil_pengadaan_sparepart() != tpdDAO.getJumlah_barang_datang_pengadaan_sparepart())
        {
            myViewHolder.ll.setBackgroundColor(myViewHolder.itemView.getResources().getColor(R.color.red));
        }
        myViewHolder.namaSparepart.setText(tpdDAO.getNama_sparepart());
        myViewHolder.kodeSparepart.setText(tpdDAO.getKode_sparepart());
        myViewHolder.jumlahPesanSparepart.setText(String.valueOf(tpdDAO.getJumlah_beli_detil_pengadaan_sparepart()) +" unit");
        myViewHolder.jumlahTerima.setText(String.valueOf(tpdDAO.getJumlah_barang_datang_pengadaan_sparepart()));
        myViewHolder.perbaharui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myViewHolder.jumlahTerima.getText().toString().length() > 0)
                {
                    perbaharuiBarangMasuk(tpdDAO.getId_pengadaan_sparepart(), tpdDAO.getId_detil_pengadaan_sparepart(), Integer.parseInt(myViewHolder.jumlahTerima.getText().toString()));
                }
                else
                {
                    FancyToast.makeText(context, "Jumlah terima tidak boleh kosong", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
            }
        });
    }

    private void perbaharuiBarangMasuk(final int id_pengadaan, final int id_pengadaan_detil, int jumlah_terima)
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<TransaksiPengadaanDetilDAO> tpdDAOCall= apiClient.perbahruiBarangMasukTransaksiPengadaanDetil(id_pengadaan_detil, jumlah_terima);
        tpdDAOCall.enqueue(new Callback<TransaksiPengadaanDetilDAO>() {
            @Override
            public void onResponse(Call<TransaksiPengadaanDetilDAO> call, Response<TransaksiPengadaanDetilDAO> response) {
                Intent i= new Intent(context, Transaksi_pengadaan_tampil_detil.class);
                i.putExtra("id_pengadaan_sparepart", id_pengadaan);
                context.startActivity(i);
                CustomIntent.customType(context, "fadein-to-fadeout");
                FancyToast.makeText(context, "Berhasil memperbaharui jumlah terima", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
            }

            @Override
            public void onFailure(Call<TransaksiPengadaanDetilDAO> call, Throwable t) {
                FancyToast.makeText(context, "Gagal memperbaharui jumlah terima", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
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
