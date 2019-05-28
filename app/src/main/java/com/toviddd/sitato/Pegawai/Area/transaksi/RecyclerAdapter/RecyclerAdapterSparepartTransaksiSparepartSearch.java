package com.toviddd.sitato.Pegawai.Area.transaksi.RecyclerAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_sparepart;
import com.toviddd.sitato.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;


public class RecyclerAdapterSparepartTransaksiSparepartSearch extends RecyclerView.Adapter<RecyclerAdapterSparepartTransaksiSparepartSearch.MyViewHolder> {

    private List<SparepartDAO> listSparepart;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart Transaksi Sparepart Search";

    public RecyclerAdapterSparepartTransaksiSparepartSearch(Context context, List<SparepartDAO> listSparepart)
    {
        this.context= context;
        this.listSparepart= listSparepart;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView namaSparepart, jenisSparepart, hargaJualSparepart, stokSparepart, merekDanJenisKendaraanSparepart;
        private ImageView gambarSparepart;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            namaSparepart= itemView.findViewById(R.id.textView_NamaSparepartTransaksiSparepart);
            jenisSparepart= itemView.findViewById(R.id.textView_JenisSparepartTransaksiSparepart);
            hargaJualSparepart= itemView.findViewById(R.id.textView_HargaJualSparepartTransaksiSparepart);
            stokSparepart= itemView.findViewById(R.id.textView_StokSparepartTransaksiSparepart);
            merekDanJenisKendaraanSparepart= itemView.findViewById(R.id.textView_MerekDanJenisKendaraanSparepartTransaksiSparepart);
            gambarSparepart= itemView.findViewById(R.id.imageView_GambarSparepartTransaksiSparepart);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaksi_sparepart_recycler_adapter_sparepart_search, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position)  {
        final SparepartDAO sparepartDAO= listSparepart.get(position);
        //Log.d(TAG, "-----------------------> onCreate lisSparepart sebelum createObject: " +result.get(i).getNama_sparepart());
        myViewHolder.namaSparepart.setText(sparepartDAO.getNama_sparepart());
        myViewHolder.jenisSparepart.setText("Tipe: " + sparepartDAO.getJenis_sparepart());
        myViewHolder.hargaJualSparepart.setText("Rp. " + sparepartDAO.getHarga_jual_sparepart());
        myViewHolder.stokSparepart.setText("Sisa stok " + (int) sparepartDAO.getStok_sparepart());
        myViewHolder.merekDanJenisKendaraanSparepart.setText("Untuk kendaraan " + sparepartDAO.getMerek_kendaraan_sparepart() + "(" + sparepartDAO.getJenis_kendaraan_sparepart() + ")");

        class loadImage extends AsyncTask<Integer, Void, Integer>
        {
            Drawable drawable;

            @Override
            protected Integer doInBackground(Integer... integers) {
                String link= Helper.BASE_URL_IMAGE + "gambar_sparepart/"+sparepartDAO.getGambar_sparepart();
                drawable= LoadImageFromWeb(link);
                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                if(drawable != null)
                {
                    myViewHolder.gambarSparepart.setImageDrawable(drawable);
                }
                else
                {
                    myViewHolder.gambarSparepart.setImageResource(R.mipmap.no_image);
                }

            }
        }
        new loadImage().execute();



        myViewHolder.gambarSparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // shared preferences
            SparepartDAO s= new SparepartDAO(sparepartDAO.getId_sparepart(), sparepartDAO.getKode_sparepart(), sparepartDAO.getNama_sparepart(), sparepartDAO.getJenis_sparepart(), sparepartDAO.getStok_sparepart(), sparepartDAO.getStok_minimum_sparepart(), sparepartDAO.getRak_sparepart(), sparepartDAO.getHarga_beli_sparepart(), sparepartDAO.getHarga_jual_sparepart(), sparepartDAO.getMerek_kendaraan_sparepart(), sparepartDAO.getJenis_kendaraan_sparepart(), sparepartDAO.getGambar_sparepart());
            SharedPreferences pref= context.getSharedPreferences(Helper.PREF_TRANSAKSI_SPAREPART, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= pref.edit();
            Gson gson= new Gson();
            String json= gson.toJson(s);
            editor.putString(Helper.PREF_TRANSAKSI_SPAREPART, json);
            editor.commit();
            Log.d(TAG, "==========================> onClick gambar:  "+s.getNama_sparepart());
            }
        });

    }

    private Drawable LoadImageFromWeb(String url){
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return listSparepart.size();
    }

    public void filterList(List<SparepartDAO> filteredList) {
        listSparepart = filteredList;
        notifyDataSetChanged();
    }

}
