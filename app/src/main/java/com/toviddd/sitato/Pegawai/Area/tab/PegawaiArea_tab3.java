package com.toviddd.sitato.Pegawai.Area.tab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.toviddd.sitato.NotificationSparepart;
import com.toviddd.sitato.R;

public class PegawaiArea_tab3 extends Fragment implements View.OnClickListener {
    public View rootView;
    private Button btnSparepartKurang;
    private LinearLayout llSparepartKurang;

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
            case R.id.btnSparepartKurangDariStokMinimal:
                break;
            case R.id.linearLayout_SparepartKurangDariStokMinimal_Laporan:
                startActivity(new Intent(getActivity(), NotificationSparepart.class));
                break;
        }
    }

    private void initAttribute()
    {
        btnSparepartKurang= rootView.findViewById(R.id.btnSparepartKurangDariStokMinimal);
        btnSparepartKurang.setOnClickListener(this);
        llSparepartKurang= rootView.findViewById(R.id.linearLayout_SparepartKurangDariStokMinimal_Laporan);
        llSparepartKurang.setOnClickListener(this);
    }
}
