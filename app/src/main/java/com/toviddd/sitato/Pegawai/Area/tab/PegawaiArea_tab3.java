package com.toviddd.sitato.Pegawai.Area.tab;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.toviddd.sitato.NotificationSparepart;
import com.toviddd.sitato.R;

public class PegawaiArea_tab3 extends Fragment implements View.OnClickListener {
    public View rootView;
    private LinearLayout llSparepartKurang, llSparepartTerlaris, llPendapatanBulanan, llPendapatanTahunan, llPengeluaranBulanan, llPenjualanJasa, llSisaStok;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.pegawai_area_tab3, container, false);
        initAttribute();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.linearLayout_SparepartKurangDariStokMinimal_Laporan:
                actionSparepartKurang();
                break;
            case R.id.linearLayout_SparepartTerlaris_Laporan:
                actionWeb("http://google.com");
                break;
            case R.id.linearLayout_PendapatanBulanan_Laporan:
                actionWeb("http://facebook.com");
                break;
            case R.id.linearLayout_PendapatanTahunan_Laporan:
                actionWeb("https://www.w3schools.com/");
                break;
            case R.id.linearLayout_PengeluaranBulanan_Laporan:
                actionWeb("http://github.com");
                break;
            case R.id.linearLayout_PenjualanJasa_Laporan:
                actionWeb("http://whatsapp.com");
                break;
            case R.id.linearLayout_SisaStok_Laporan:
                actionWeb("https://stackoverflow.com");
        }
    }

    private void initAttribute()
    {
        llSparepartKurang= rootView.findViewById(R.id.linearLayout_SparepartKurangDariStokMinimal_Laporan);
        llSparepartKurang.setOnClickListener(this);
        llSparepartTerlaris= rootView.findViewById(R.id.linearLayout_SparepartTerlaris_Laporan);
        llSparepartTerlaris.setOnClickListener(this);
        llPendapatanBulanan= rootView.findViewById(R.id.linearLayout_PendapatanBulanan_Laporan);
        llPendapatanBulanan.setOnClickListener(this);
        llPendapatanTahunan= rootView.findViewById(R.id.linearLayout_PendapatanTahunan_Laporan);
        llPendapatanTahunan.setOnClickListener(this);
        llPengeluaranBulanan= rootView.findViewById(R.id.linearLayout_PengeluaranBulanan_Laporan);
        llPengeluaranBulanan.setOnClickListener(this);
        llPenjualanJasa= rootView.findViewById(R.id.linearLayout_PenjualanJasa_Laporan);
        llPenjualanJasa.setOnClickListener(this);
        llSisaStok= rootView.findViewById(R.id.linearLayout_SisaStok_Laporan);
        llSisaStok.setOnClickListener(this);
    }

    private void actionSparepartKurang()
    {
        startActivity(new Intent(getActivity(), NotificationSparepart.class));
    }

    private void actionWeb(String url)
    {
        CustomTabsIntent.Builder builder= new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.orange));
        CustomTabsIntent customTabsIntent= builder.build();
        customTabsIntent.launchUrl(rootView.getContext(), Uri.parse(url));
    }

}
