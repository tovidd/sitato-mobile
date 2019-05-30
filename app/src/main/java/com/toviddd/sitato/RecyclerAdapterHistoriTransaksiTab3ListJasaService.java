package com.toviddd.sitato;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiJasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;

import java.util.List;

public class RecyclerAdapterHistoriTransaksiTab3ListJasaService extends RecyclerView.Adapter<RecyclerAdapterHistoriTransaksiTab3ListJasaService.MyViewHolder> {

    private List<TransaksiJasaServiceDAO> result;
    private Context context;
    public String TAG= "Recycler Adapter Sparepart Tab3 List Sparepart";

    public RecyclerAdapterHistoriTransaksiTab3ListJasaService(Context context, List<TransaksiJasaServiceDAO> result)
    {
        this.context= context;
        this.result= result;
    }

    public RecyclerAdapterHistoriTransaksiTab3ListJasaService(List<TransaksiJasaServiceDAO> result)
    {
        this.result= result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView statusJS, namaJS, kendaraanJS, kodeJS, jumlahBeliJS, subtotalJS, montirJS;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            statusJS= itemView.findViewById(R.id.texView_statusJasaService_detilHistoriTransaksi);
            namaJS= itemView.findViewById(R.id.texView_namaJasaService_detilHistoriTransaksi);
            kendaraanJS= itemView.findViewById(R.id.texView_kendaraanJasaService_detilHistoriTransaksi);
            kodeJS= itemView.findViewById(R.id.texView_kodeJasaService_detilHistoriTransaksi);
            jumlahBeliJS= itemView.findViewById(R.id.texView_jumlahBeliJasaService_detilHistoriTransaksi);
            subtotalJS= itemView.findViewById(R.id.texView_subtotalJasaService_detilHistoriTransaksi);
            montirJS= itemView.findViewById(R.id.texView_montirJasaService_detilHistoriTransaksi);
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
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_adapter_histori_transaksi_tab_3_list_jasa_service, viewGroup, false);
        final MyViewHolder holder= new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        final TransaksiJasaServiceDAO tjsDAO= result.get(i);
        //Log.d(TAG, "-----------------------> onCreate lisSparepart sebelum createObject: " +result.get(i).getNama_sparepart());
        myViewHolder.statusJS.setText(tjsDAO.getNama_status_pengerjaan());
        myViewHolder.namaJS.setText(tjsDAO.getNama_jasa_service());
        myViewHolder.kendaraanJS.setText(tjsDAO.getMerek_kendaraan() +" " +tjsDAO.getJenis_kendaraan());
        if(tjsDAO.getKode_jasa_service() < 10)
        {
            myViewHolder.kodeJS.setText("00" +String.valueOf(tjsDAO.getKode_jasa_service()));
        }
        else if(tjsDAO.getKode_jasa_service() < 100)
        {
            myViewHolder.kodeJS.setText("0" +String.valueOf(tjsDAO.getKode_jasa_service()));
        }
        else if(tjsDAO.getKode_jasa_service() < 1000)
        {
            myViewHolder.kodeJS.setText(String.valueOf(tjsDAO.getKode_jasa_service()));
        }
        myViewHolder.jumlahBeliJS.setText(String.valueOf(tjsDAO.getJumlah_transaksi_penjualan_jasa_service()) +" unit" +", @" +String.valueOf(tjsDAO.getHarga_jasa_service()));
        myViewHolder.subtotalJS.setText("Rp. " +String.valueOf(tjsDAO.getSubtotal_transaksi_penjualan_jasa_service()));
        myViewHolder.montirJS.setText("Montir: " +tjsDAO.getNama_montir());
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
