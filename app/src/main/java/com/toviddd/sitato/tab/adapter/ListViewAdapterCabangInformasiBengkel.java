package com.toviddd.sitato.tab.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.R;

import java.util.List;


public class ListViewAdapterCabangInformasiBengkel extends RecyclerView.Adapter<ListViewAdapterCabangInformasiBengkel.MyViewHolder> {

    private List<CabangDAO> listCabang;
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView nama, telp, alamat;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            nama= itemView.findViewById(R.id.texView_NamaCabang_informasiBengkel);
            telp= itemView.findViewById(R.id.texView_TelpCabang_informasiBengkel);
            alamat= itemView.findViewById(R.id.texView_AlamatCabang_informasiBengkel);
        }
    }

    public ListViewAdapterCabangInformasiBengkel(List<CabangDAO> listCabang)
    {
        this.listCabang= listCabang;
        if(this.listCabang != null)
        {
            for(int i=0; i<this.listCabang.size(); i++)
            {
                if(listCabang.get(i).getId_cabang() == 1)
                {
                    listCabang.remove(i);
                }
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_adapter_cabang_informasi_bengkel,
                parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        CabangDAO c= listCabang.get(position);
        myViewHolder.nama.setText(c.getNama_cabang());
        myViewHolder.telp.setText(c.getNo_telepon_cabang());
        myViewHolder.alamat.setText(c.getAlamat_cabang());
//        myViewHolder.itemView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        if(listCabang == null)
        {
            return 0;
        }
        return listCabang.size();
    }

}
