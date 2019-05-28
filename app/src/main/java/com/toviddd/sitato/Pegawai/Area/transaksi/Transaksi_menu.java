package com.toviddd.sitato.Pegawai.Area.transaksi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.R;

public class Transaksi_menu extends AppCompatActivity implements View.OnClickListener {

    private Button btnTransaksiSparepart, btnTransaksiJasaService, btnTampilSPK, btnUbahStatusDanMenyelesaikanTransaksi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_menu);
        getSupportActionBar().setTitle("Selesai transaksi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Helper.networkPermission();
        initAttribute();

        btnTransaksiSparepart.setOnClickListener(this);
        btnTransaksiJasaService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_transaksiSparepart:
                actionTransaksiSparepart();
                break;
            case R.id.button_transaksiJasaService:
                actionTransaksiJasaService();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                startActivity(new Intent(Transaksi_menu.this, PegawaiMainActivity.class));
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_menu.this);
                builder.setMessage("Selesai transaksi,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which)
                {
                    case DialogInterface.BUTTON_POSITIVE:
                        startActivity(new Intent(Transaksi_menu.this, PegawaiMainActivity.class));
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_menu.this);
        builder.setMessage("Selesai transaksi,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
    }

    private void initAttribute()
    {
        btnTransaksiSparepart= findViewById(R.id.button_transaksiSparepart);
        btnTransaksiJasaService= findViewById(R.id.button_transaksiJasaService);
//        btnTampilSPK= findViewById(R.id.button_transaksiSuratPerintahKerja);
//        btnUbahStatusDanMenyelesaikanTransaksi= findViewById(R.id.button_transaksiUbahStatusDanMenyelesaikan);
    }

    private void actionTransaksiSparepart()
    {
        startActivity(new Intent(Transaksi_menu.this, Transaksi_sparepart.class));
    }

    private void actionTransaksiJasaService()
    {
        startActivity(new Intent(Transaksi_menu.this, Transaksi_jasaService.class));
    }
}
