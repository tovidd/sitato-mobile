package com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Sparepart;
import com.toviddd.sitato.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterSparepart extends RecyclerView.Adapter<RecyclerAdapterSparepart.MyViewHolder>{

    private List<SparepartDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart";

    public RecyclerAdapterSparepart(Context context, List<SparepartDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterSparepart(List<SparepartDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView namaSparepart, jenisSparepart, hargaJualSparepart, stokSparepart, merekDanJenisKendaraanSparepart;
        private TextView kodeSparepart, rakSparepart, hargaBeliSparepart, stokMinSparepart;
        private ImageView gambarSparepart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaSparepart= itemView.findViewById(R.id.textView_NamaSparepart);
            jenisSparepart= itemView.findViewById(R.id.textView_JenisSparepart);
            hargaJualSparepart= itemView.findViewById(R.id.textView_HargaJualSparepart);
            stokSparepart= itemView.findViewById(R.id.textView_StokSparepart);
            merekDanJenisKendaraanSparepart= itemView.findViewById(R.id.textView_MerekDanJenisKendaraanSparepart);
            gambarSparepart= itemView.findViewById(R.id.imageView_GambarSparepart);

            kodeSparepart= itemView.findViewById(R.id.textView_KodeSparepart);
            rakSparepart= itemView.findViewById(R.id.textView_RakSparepart);
            hargaBeliSparepart= itemView.findViewById(R.id.textView_HargaBeliSparepart);
            stokMinSparepart= itemView.findViewById(R.id.textView_StokMinimumSparepart);
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
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_sparepart, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final SparepartDAO sparepartDAO= result.get(i);
        //Log.d(TAG, "-----------------------> onCreate lisSparepart sebelum createObject: " +result.get(i).getNama_sparepart());
        myViewHolder.namaSparepart.setText(sparepartDAO.getNama_sparepart());
        myViewHolder.jenisSparepart.setText("Tipe: " + sparepartDAO.getJenis_sparepart());
        myViewHolder.hargaJualSparepart.setText("Rp. " + sparepartDAO.getHarga_jual_sparepart());
        myViewHolder.kodeSparepart.setText("Kode sparepart: " +sparepartDAO.getKode_sparepart());
        myViewHolder.rakSparepart.setText("Rak/penyimpanan sparepart: " +sparepartDAO.getRak_sparepart());
        myViewHolder.hargaBeliSparepart.setText("Harga beli Rp. " +sparepartDAO.getHarga_beli_sparepart());
        myViewHolder.stokMinSparepart.setText("minimum stok " +sparepartDAO.getStok_minimum_sparepart());
        myViewHolder.stokSparepart.setText("Sisa stok " + sparepartDAO.getStok_sparepart());
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
    public int getItemCount() {return result.size();}

    public void filterListSparepart(List<SparepartDAO> filteredList)
    {
        result= filteredList;
        notifyDataSetChanged();
    }

}
