package com.toviddd.sitato.Pegawai.Area.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.toviddd.sitato.Pegawai.Area.Kelola.data.Cabang;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Kendaraan;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Pelanggan;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Sparepart;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Supplier;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.R;

import quatja.com.vorolay.VoronoiView;

public class PegawaiArea_tab1 extends Fragment implements View.OnClickListener {
    public View rootView;
    private Button sparepart, supplier, cabang, pelanggan, kendaraan;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.pegawai_area_tab1, container, false);
        initAttribute();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSparepart:
                actionSparepart();
                break;
            case R.id.btnSupplier:
                actionSupplier();
                break;
            case R.id.btnPelanggan:
                actionPelanggan();
                break;
            case R.id.btnCabang:
                actionCabang();
                break;
            case R.id.btnKendaraan:
                actionKendaraan();
                break;

        }
    }

    private void initAttribute()
    {
        sparepart= rootView.findViewById(R.id.btnSparepart);
        supplier= rootView.findViewById(R.id.btnSupplier);
        cabang= rootView.findViewById(R.id.btnCabang);
        pelanggan= rootView.findViewById(R.id.btnPelanggan);
        kendaraan= rootView.findViewById(R.id.btnKendaraan);

        sparepart.setOnClickListener(this);
        supplier.setOnClickListener(this);
        cabang.setOnClickListener(this);
        pelanggan.setOnClickListener(this);
        kendaraan.setOnClickListener(this);
    }

    private void actionSparepart()
    {
        Intent intent= new Intent(getActivity(), Sparepart.class);
        startActivity(intent);
    }

    private void actionSupplier()
    {
        Intent intent= new Intent(getActivity(), Supplier.class);
        startActivity(intent);
    }

    private void actionPelanggan()
    {
        Intent intent= new Intent(getActivity(), Pelanggan.class);
        startActivity(intent);
    }

    private void actionCabang()
    {
        Intent intent= new Intent(getActivity(), Cabang.class);
        startActivity(intent);
    }

    private void actionKendaraan()
    {
        Intent intent= new Intent(getActivity(), Kendaraan.class);
        startActivity(intent);
    }
}
