package com.toviddd.sitato;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class RecyclerAdapterSparepartTab2 extends RecyclerView.Adapter<RecyclerAdapterSparepartTab2.MyViewHolder>{

    private List<SparepartDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart Tab2";

    public RecyclerAdapterSparepartTab2(Context context, List<SparepartDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterSparepartTab2(List<SparepartDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView namaDanJenisSparepart, hargaJualSparepart, stokSparepart, merkDanJenisKendaraanSparepart;
        private ImageView gambarSparepart;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            namaDanJenisSparepart= itemView.findViewById(R.id.textView_NamaDanJenisSparepart_tab2);
            hargaJualSparepart= itemView.findViewById(R.id.textView_HargaJualSparepart_tab2);
            stokSparepart= itemView.findViewById(R.id.textView_StokSparepart_tab2);
            gambarSparepart= itemView.findViewById(R.id.imageView_GambarSparepart_tab2);
            merkDanJenisKendaraanSparepart= itemView.findViewById(R.id.textView_MerekDanJenisKendaraanSparepart_tab2);
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
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_sparepart_tab_2, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final SparepartDAO sparepartDAO= result.get(i);
        //Log.d(TAG, "-----------------------> onCreate lisSparepart sebelum createObject: " +result.get(i).getNama_sparepart());
        myViewHolder.namaDanJenisSparepart.setText(sparepartDAO.getNama_sparepart() +"(" +sparepartDAO.getJenis_sparepart() +")");
        myViewHolder.stokSparepart.setText("Sisa stok " + sparepartDAO.getStok_sparepart());
        myViewHolder.hargaJualSparepart.setText("Rp. " + sparepartDAO.getHarga_jual_sparepart());
        myViewHolder.merkDanJenisKendaraanSparepart.setText("Cocok untuk kendaraan merek: " +sparepartDAO.getMerek_kendaraan_sparepart() +" " +sparepartDAO.getJenis_kendaraan_sparepart());

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
    public int getItemCount() {
        if(result == null)
        {
            return 0;
        }
        return result.size();
    }

    public void filterListSparepart(List<SparepartDAO> filteredList)
    {
        result= filteredList;
        notifyDataSetChanged();
    }

}
