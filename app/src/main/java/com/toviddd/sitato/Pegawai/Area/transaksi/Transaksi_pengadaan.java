package com.toviddd.sitato.Pegawai.Area.transaksi;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.KendaraanDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.SupplierDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiPengadaanDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Transaksi_pengadaan extends AppCompatActivity implements View.OnClickListener {

    String TAG= "Transaksi pengadaan sparepart";
    private TextView tvSupplier, tvTanggal, tvWaktu;
    private Button btnSupplier, btnTanggal, btnWaktu, btnLanjutkan;
    SpinnerDialog sdSupplier;
    private ArrayList<String> listSupplier= new ArrayList<>();
    private List<SupplierDAO> listSupplier2= new ArrayList<>();
    private int id_pengadaan_sparepart, id_supplier;
    private String tanggal_pengadaan, waktu_pengadaan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksi_pengadaan);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAttribute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadSupplier();
            }
        }, 1);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_namaSupplier_pengadaan:
                actionNamaSupplier();
                break;
            case R.id.button_tanggal_pengadaan:
                actionTanggalPengadaan();
                break;
            case R.id.button_waktu_pengadaan:
                actionWaktuPengadaan();
                break;
            case R.id.button_LanjutPengadaan_pengadaan:
                actionLanjutkan();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                startActivity(new Intent(Transaksi_pengadaan.this, PegawaiMainActivity.class));
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_pengadaan.this);
                builder.setMessage("Kembali,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
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
                        startActivity(new Intent(Transaksi_pengadaan.this, PegawaiMainActivity.class));
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder= new AlertDialog.Builder(Transaksi_pengadaan.this);
        builder.setMessage("Kembali,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
    }

    private void initAttribute()
    {
        tvSupplier= findViewById(R.id.textView_namaSupplier_pengadaan);
        tvTanggal= findViewById(R.id.textView_tanggal_pengadaan);
        tvWaktu= findViewById(R.id.textView_waktu_pengadaan);
        btnSupplier= findViewById(R.id.button_namaSupplier_pengadaan);
        btnTanggal= findViewById(R.id.button_tanggal_pengadaan);
        btnWaktu= findViewById(R.id.button_waktu_pengadaan);
        btnLanjutkan= findViewById(R.id.button_LanjutPengadaan_pengadaan);

        btnSupplier.setOnClickListener(this);
        btnTanggal.setOnClickListener(this);
        btnWaktu.setOnClickListener(this);
        btnLanjutkan.setOnClickListener(this);
    }

    private void actionNamaSupplier()
    {
        btnSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listSupplier.size() > 0)
                {
                    sdSupplier= new SpinnerDialog(Transaksi_pengadaan.this, listSupplier, "Pilih supplier", "Tutup");
                    sdSupplier.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
                            tvSupplier.setText(item);
                            id_pengadaan_sparepart= position+1;
                            for(int i= 0; i<listSupplier.size(); i++)
                            {
                                String item2= (listSupplier2.get(i).getNama_supplier() + " (" +listSupplier2.get(i).getNama_sales() +")");
                                if(item.equalsIgnoreCase(item2))
                                {
                                    id_supplier= listSupplier2.get(i).getId_supplier();
                                    Log.d(TAG, "item == item2 ===> " +id_supplier);
                                }
                                else
                                {
                                    Log.d(TAG +"\n", "item != item2 ===> " +item +", " +item2);
                                }
                            }
                        }
                    });
                }
                try {
                    sdSupplier.showSpinerDialog();
                }catch(Exception e){}
            }
        });
    }

    private void actionTanggalPengadaan()
    {
        Calendar c= Calendar.getInstance();
        final DatePickerDialog datePickerDialog= new DatePickerDialog(Transaksi_pengadaan.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date= dayOfMonth +"-" +month +"-" +year;
                        tvTanggal.setText(date);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        final DatePicker datePicker = datePickerDialog.getDatePicker();
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datePicker.clearFocus();
                        String[] MONTHS = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
                        String date= datePicker.getDayOfMonth() +" " +MONTHS[datePicker.getMonth()] +" " +datePicker.getYear();
                        tanggal_pengadaan= datePicker.getMonth() +"/" +datePicker.getDayOfMonth() +"/" +datePicker.getYear();
                        tvTanggal.setText(date);

                        Log.d(TAG, "tanggal_pengadaan: " +tanggal_pengadaan);
                    }
                });

        datePickerDialog.setTitle("Pilih tanggal");
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "BATAL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datePickerDialog.dismiss();
                    }
                });
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void actionWaktuPengadaan()
    {
        final Calendar c= Calendar.getInstance();
        final TimePickerDialog timePickerDialog= new TimePickerDialog(Transaksi_pengadaan.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        waktu_pengadaan= String.valueOf(hourOfDay) +":" +String.valueOf(minute) +":00";
                        tvWaktu.setText(waktu_pengadaan);
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

//        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        tvWaktu.setText(waktu_pengadaan);
//                    }
//                });
//        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "BATAL",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        timePickerDialog.dismiss();
//                    }
//                });
        timePickerDialog.setTitle("Pilih waktu");
        timePickerDialog.show();
    }

    private void actionLanjutkan()
    {
        // get id_cs (pegawai)
        SharedPreferences prefLogin = getApplicationContext().getSharedPreferences(Helper.PREF_LOGIN_PEGAWAI, getApplicationContext().MODE_PRIVATE);
        int id_cabang = prefLogin.getInt(Helper.ID_CABANG_LOGIN, -1);
        Log.d(TAG, "///////////////////////////// actionLanjutkan: " +id_cabang);
        if(String.valueOf(id_cabang) != null && String.valueOf(id_supplier) != null && !tanggal_pengadaan.isEmpty() && !waktu_pengadaan.isEmpty())
        {
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<TransaksiPengadaanDAO> tpDAOCall= apiClient.requestSaveTransaksiPengadaan(id_cabang, id_supplier, tanggal_pengadaan, waktu_pengadaan);
            tpDAOCall.enqueue(new Callback<TransaksiPengadaanDAO>() {
                @Override
                public void onResponse(Call<TransaksiPengadaanDAO> call, Response<TransaksiPengadaanDAO> response) {
                    if(response.body() != null)
                    {
                        // set id transaksi
                        TransaksiPengadaanDAO t= response.body();
                        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_TRANSAKSI_PENGADAAN_SPAREPART, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= pref.edit();
                        Gson gson= new Gson();
                        String json= gson.toJson(t);
                        editor.putString(Helper.PREF_TRANSAKSI_PENGADAAN_SPAREPART, json);
                        editor.commit();

                        startActivity(new Intent(Transaksi_pengadaan.this, Transaksi_pengadaan_detil.class));
                    }
                    else
                    {
                        try
                        {
                            Log.d(TAG, "onResponse: " +response.errorBody().string());
                        }catch(Exception e){}
                    }
                }

                @Override
                public void onFailure(Call<TransaksiPengadaanDAO> call, Throwable t) {
                    Log.d(TAG, "onFailure: " +t.getCause().getStackTrace());
                }
            });
        }
        else
        {
            FancyToast.makeText(Transaksi_pengadaan.this, "Semua kolom inputan harus diisi", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }


    }

    private void loadSupplier()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<SupplierDAO>> suppDAOCall= apiClient.getAllSupplier();
        suppDAOCall.enqueue(new Callback<List<SupplierDAO>>() {
            @Override
            public void onResponse(Call<List<SupplierDAO>> call, Response<List<SupplierDAO>> response) {
                listSupplier2= response.body();
                for(int i= 0; i<listSupplier2.size(); i++)
                {
                    String item= (listSupplier2.get(i).getNama_supplier() + " (" +listSupplier2.get(i).getNama_sales() +")");
                    listSupplier.add(item);
                }
            }

            @Override
            public void onFailure(Call<List<SupplierDAO>> call, Throwable t) {

            }
        });
    }
}
