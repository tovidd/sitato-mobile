package com.toviddd.sitato.Pegawai.Area.Kelola.data;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.DAO.ApiClient;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.MainActivity;
import com.toviddd.sitato.NotificationSparepart;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterSparepart;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterSparepartSearch;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.R;
import com.toviddd.sitato.SplashScreen;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HEAD;

public class Sparepart extends AppCompatActivity implements View.OnClickListener {

    private Button kosongkanKolom, hapus, ubah, simpan, uploadGambar;
    private EditText kodeSparepart, namaSparepart, jenisSparepart, stokSparepart, stokMinimumSparepart, hargaBeliSparepart, hargaJualSparepart, merekKendaraanSparepart, jenisKendaraanSparepart;
    private Spinner rakSparepart1, rakSparepart2, rakSparepart3;
    private TextView deleteSearchSparepart;
    private ImageView gambarSparepart;
    private String TAG= "Sparepart Activity";
    private String namaKelas= "sparepart";
    private List<SparepartDAO> listSparepart= new ArrayList<>();
    private List<SparepartDAO> listSparepart2= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapterSparepart recyclerAdapterSparepart;
    public static final String PREF_SPAREPART= "PREF_SPAREPART";
    public int id_sparepart;

    // spinner rak
    private List<String> listRakSparepart1= new ArrayList<>();
    private List<String> listRakSparepart2= new ArrayList<>();
    private List<String> listRakSparepart3= new ArrayList<>();
    ArrayAdapter<String> sAdapter1;
    ArrayAdapter<String> sAdapter2;
    ArrayAdapter<String> sAdapter3;

    // search bar
    private RecyclerView recyclerViewSearch;
    private RecyclerAdapterSparepartSearch recyclerAdapterSparepartSearch;
    private EditText searchSparepart;

    // upload image
    private static final int STORAGE_PERMISSION_CODE= 123;
    private static final int PICK_IMAGE_REQUEST= 1;
    private Bitmap bitmap;
    private  Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparepart);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Helper.networkPermission();

        initAttribute();
        setSpinnerRakSparepart1();
        setSpinnerRakSparepart2();
        setSpinnerRakSparepart3();
        buildRecyclerViewTampil();
        tampil();
        buildRecyclerViewSearch();
        cari();
        isiKolom();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_GambarSparepart:
                requestStoragePermission();
                break;
            case R.id.btnSimpanSparepart:
                simpan();
                kosongkanSearchSparepartPreferences();
                break;
            case R.id.btnEditSparepart:
                ubah();
                break;
            case R.id.btnHapusSparepart:
                hapus();
                kosongkanSearchSparepartPreferences();
                break;
            case R.id.btnKosongkanKolom:
                kosongkanKolom();
                kosongkanSearchSparepartPreferences();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(Sparepart.this, PegawaiMainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            showFileChooser();
            return;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                showFileChooser();
                Toast.makeText(this, "Izin diberikan untuk mengakses ruang penyimpanan", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Izin ditolak untuk mengakses ruang penyimpanan", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:
                if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST)
                {
                    filePath = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        gambarSparepart.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor= null;
        try
        {
            cursor = getContentResolver().query(uri, null, null, null, null);
        }
        catch(Exception e)
        {
        }
        if(cursor != null)
        {
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
            cursor.close();

            cursor = getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();

            return path;
        }
        return null;
    }

    public String uploadGambar(String namaGambar) {
        String status= "gagal";
        //getting the actual path of the image
        String path= getPath(filePath);

        if(path != null)
        {
            //Uploading code
            try
            {
                String uploadId = UUID.randomUUID().toString();
                //Creating a multi part request
                new MultipartUploadRequest(this, uploadId, Helper.BASE_URL+"sparepart/uploadImage")
                        .addFileToUpload(path, "image") //Adding file
                        .addParameter("name", namaGambar) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .startUpload(); //Starting the upload
                status= "berhasil";

            } catch (Exception exc) {
                status= "gagal";
            }
        }
        else
        {
            FancyToast.makeText(Sparepart.this, "Gambar sparepart tidak ada", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        return status;
    }

    private String getSaltString(int panjang) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < panjang) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public void initAttribute()
    {
        kosongkanKolom= findViewById(R.id.btnKosongkanKolom);
        kosongkanKolom.setOnClickListener(this);
        hapus= findViewById(R.id.btnHapusSparepart);
        hapus.setOnClickListener(this);
        ubah= findViewById(R.id.btnEditSparepart);
        ubah.setOnClickListener(this);
        simpan= findViewById(R.id.btnSimpanSparepart);
        simpan.setOnClickListener(this);
        uploadGambar= findViewById(R.id.button_GambarSparepart);
        uploadGambar.setOnClickListener(this);
        gambarSparepart= findViewById(R.id.imageView_GambarSparepart);
        deleteSearchSparepart= findViewById(R.id.textView_deleteSearchSparepart);

        //search bar
        searchSparepart= findViewById(R.id.editText_searchSparepart);

        kodeSparepart= findViewById(R.id.editText_KodeSparepart);
        namaSparepart= findViewById(R.id.editText_NamaSparepart);
        jenisSparepart= findViewById(R.id.editText_JenisSparepart);
        stokSparepart= findViewById(R.id.editText_StokSparepart);
        stokMinimumSparepart= findViewById(R.id.editText_StokMinimumSparepart);
        rakSparepart1= findViewById(R.id.spinner_rakSparepart1);
        rakSparepart2= findViewById(R.id.spinner_rakSparepart2);
        rakSparepart3= findViewById(R.id.spinner_rakSparepart3);
        hargaBeliSparepart= findViewById(R.id.editText_HargaBeliSparepart);
        hargaJualSparepart= findViewById(R.id.editText_HargaJualSparepart);
        merekKendaraanSparepart= findViewById(R.id.editText_MerekKendaraanSparepart);
        jenisKendaraanSparepart= findViewById(R.id.editText_JenisKendaraanSparepart);
    }

    private void setSpinnerRakSparepart1()
    {
        listRakSparepart1.add("DPN");
        listRakSparepart1.add("TGH");
        listRakSparepart1.add("BLK");
        sAdapter1= new ArrayAdapter<>(Sparepart.this, android.R.layout.simple_spinner_item, listRakSparepart1);
        sAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rakSparepart1.setAdapter(sAdapter1);
        rakSparepart1.setSelection(0);
        rakSparepart1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSpinnerRakSparepart3();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setSpinnerRakSparepart2()
    {
        listRakSparepart2.add("KACA");
        listRakSparepart2.add("KAYU");
        sAdapter2= new ArrayAdapter<>(Sparepart.this, android.R.layout.simple_spinner_item, listRakSparepart2);
        sAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rakSparepart2.setAdapter(sAdapter2);
        rakSparepart2.setSelection(0);
        rakSparepart2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setSpinnerRakSparepart3();
                    }
                }, 100);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpinnerRakSparepart3()
    {
        Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<Integer> sparepartDAOCall= apiClient.spinnerRakSparepart(rakSparepart1.getSelectedItem().toString().toUpperCase(), rakSparepart2.getSelectedItem().toString().toUpperCase());
        sparepartDAOCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer i= response.body();
                listRakSparepart3= new ArrayList<>();
                if(i != null)
                {
                    if(i < 9)
                    {
                        String string= "0" +String.valueOf(i+1);
                        listRakSparepart3.add(string);
                    }
                    else
                    {
                        listRakSparepart3.add(String.valueOf(i+1));
                    }
                }
                else
                {
                    listRakSparepart3.add("01");
                }
                sAdapter3= new ArrayAdapter<>(Sparepart.this, android.R.layout.simple_spinner_item, listRakSparepart3);
                sAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sAdapter3.notifyDataSetChanged();
                rakSparepart3.setAdapter(sAdapter3);
                rakSparepart3.setSelection(0);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    public void buildRecyclerViewTampil()
    {
        recyclerView= findViewById(R.id.recyclerView_sparepart);
        recyclerAdapterSparepart= new RecyclerAdapterSparepart(this, listSparepart);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapterSparepart);
    }

    public void buildRecyclerViewSearch()
    {
//        listSparepart2.add(new SparepartDAO("1", "Busi", "3", 4, 5, "6", 7, 7, "9", "10", "11"));
//        listSparepart2.add(new SparepartDAO("1", "Velg", "3", 4, 5, "6", 7, 7, "9", "10", "11"));
        recyclerViewSearch= findViewById(R.id.recyclerView_SparepartSearch);
        recyclerAdapterSparepartSearch= new RecyclerAdapterSparepartSearch(this, listSparepart2);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerViewSearch.setLayoutManager(mLayoutManager);
        recyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
        //recyclerViewSearch.setAdapter(recyclerAdapterSparepartSearch);
    }

    public void kosongkanKolom()
    {
        id_sparepart= -1;
        kodeSparepart.setText("");
        namaSparepart.setText("");
        jenisSparepart.setText("");
        stokSparepart.setText("");
        stokMinimumSparepart.setText("");
        hargaBeliSparepart.setText("");
        hargaJualSparepart.setText("");
        merekKendaraanSparepart.setText("");
        jenisKendaraanSparepart.setText("");
        FancyToast.makeText(Sparepart.this, "Kolom dikosongkan", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
    }

    public void isiKolom()
    {
        kodeSparepart.setText("SDE");
        namaSparepart.setText("Oli samping 2T Evalube");
        jenisSparepart.setText("Oli");
        stokSparepart.setText("13");
        stokMinimumSparepart.setText("20");
        hargaBeliSparepart.setText("23000");
        hargaJualSparepart.setText("32000");
        merekKendaraanSparepart.setText("Jupiter");
        jenisKendaraanSparepart.setText("MX 3000");
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    public void hapus()
    {
        // get id sparepart
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_SPAREPART, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_SPAREPART, "");
        final SparepartDAO s= gson.fromJson(json, SparepartDAO.class);

        if(s == null)
        {
            FancyToast.makeText(Sparepart.this, "Pilih " +namaKelas +" untuk dihapus", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            id_sparepart= s.getId_sparepart();
            Retrofit.Builder builder= new Retrofit
                    .Builder()
                    .baseUrl(Helper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<SparepartDAO> sparepartDAOCall= apiClient.deleteSparepart(id_sparepart);
            sparepartDAOCall.enqueue(new Callback<SparepartDAO>() {
                @Override
                public void onResponse(Call<SparepartDAO> call, Response<SparepartDAO> response) {
                    //Log.d(TAG, "--------------------------------------> onResponse: message: "+response.message() +", toString: "+response.toString()+", raw"+response.raw());
                    if(response.code() == 404)
                    {
                        FancyToast.makeText(Sparepart.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(Sparepart.this, "Gagal menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }

                @Override
                public void onFailure(Call<SparepartDAO> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(Sparepart.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        FancyToast.makeText(Sparepart.this, "Berhasil menghapus "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        try {
                            Thread.sleep(1000);
                        }catch (Exception e){
                            Log.d(TAG, "onFailure: Thread.sleep ---> "+e.getMessage());
                        }
                        kosongkanSearchSparepartPreferences();
                        recreate();
                    }
                }
            });
        }
    }

    public void ubah()
    {
        String kodeSparepart_tampung, namaSparepart_tampung, jenisSparepart_tampung, rakSparepart_tampung, merekKendaraanSparepart_tampung, jenisKendaraanSparepart_tampung, gambarSparepart_tampung;
        int stokSparepart_tampung, stokMinimumSparepart_tampung;
        double hargaBeliSparepart_tampung, hargaJualSparepart_tampung;
        kodeSparepart_tampung= kodeSparepart.getText().toString();
        namaSparepart_tampung= namaSparepart.getText().toString();
        jenisSparepart_tampung= jenisSparepart.getText().toString();
        gambarSparepart_tampung= namaSparepart.getText().toString().trim() +"_" +getSaltString(64);
        if(stokSparepart.getText().toString().isEmpty())
        {
            stokSparepart_tampung= 0;
        }
        else
        {
            stokSparepart_tampung= Integer.parseInt(stokSparepart.getText().toString());
        }
        if(stokMinimumSparepart.getText().toString().isEmpty())
        {
            stokMinimumSparepart_tampung= 0;
        }
        else
        {
            stokMinimumSparepart_tampung= Integer.parseInt(stokMinimumSparepart.getText().toString());
        }

        if(Integer.parseInt(rakSparepart3.getSelectedItem().toString()) < 10)
        {
            rakSparepart_tampung= rakSparepart1.getSelectedItem().toString() +"-" +rakSparepart2.getSelectedItem().toString() +"-0" +rakSparepart3.getSelectedItem().toString();
        }
        else
        {
            rakSparepart_tampung= rakSparepart1.getSelectedItem().toString() +"-" +rakSparepart2.getSelectedItem().toString() +"-" +rakSparepart3.getSelectedItem().toString();
        }

        if(hargaBeliSparepart.getText().toString().isEmpty())
        {
            hargaBeliSparepart_tampung= 0;
        }
        else
        {
            hargaBeliSparepart_tampung= Double.parseDouble(hargaBeliSparepart.getText().toString());
        }
        if(hargaJualSparepart.getText().toString().isEmpty())
        {
            hargaJualSparepart_tampung= 0;
        }
        else
        {
            hargaJualSparepart_tampung= Double.parseDouble(hargaJualSparepart.getText().toString());
        }
        merekKendaraanSparepart_tampung= merekKendaraanSparepart.getText().toString();
        jenisKendaraanSparepart_tampung= jenisKendaraanSparepart.getText().toString();

        // get id sparepart
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_SPAREPART, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_SPAREPART, "");
        final SparepartDAO s= gson.fromJson(json, SparepartDAO.class);

        if(s == null)
        {
            FancyToast.makeText(Sparepart.this, "Pilih " +namaKelas +" untuk diedit", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        }
        else
        {
            if(kodeSparepart_tampung.isEmpty() || namaSparepart_tampung.isEmpty() || jenisSparepart_tampung.isEmpty() ||
                    stokSparepart_tampung == 0 || stokMinimumSparepart_tampung == 0 || rakSparepart_tampung.isEmpty() ||
                    hargaBeliSparepart_tampung == 0 || hargaJualSparepart_tampung == 0 || merekKendaraanSparepart_tampung.isEmpty() ||
                    jenisKendaraanSparepart_tampung.isEmpty())
            {
                FancyToast.makeText(Sparepart.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(kodeSparepart_tampung.length() > 0 && kodeSparepart_tampung.length() != 3 ||
                    namaSparepart_tampung.length() > 0 && namaSparepart_tampung.length() < 4 || namaSparepart_tampung.length()>50 ||
                    jenisSparepart_tampung.length() > 0 && jenisSparepart_tampung.length() < 3 || jenisSparepart_tampung.length()>50 ||
                    merekKendaraanSparepart_tampung.length() > 0 && merekKendaraanSparepart_tampung.length() < 4 || merekKendaraanSparepart_tampung.length()>50 ||
                    jenisKendaraanSparepart_tampung.length() > 0 && jenisKendaraanSparepart_tampung.length() < 4 || jenisKendaraanSparepart_tampung.length()>50)
            {
                if(kodeSparepart_tampung.length() != 3)
                {
                    FancyToast.makeText(Sparepart.this, "Kode " +namaKelas +" harus 3 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                }
                else if(namaSparepart_tampung.length() <4 || namaSparepart_tampung.length()>50)
                {
                    FancyToast.makeText(Sparepart.this, "Nama " +namaKelas +" harus 4 - 50 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                }
                else if(jenisSparepart_tampung.length()<3 || jenisSparepart_tampung.length()>50)
                {
                    FancyToast.makeText(Sparepart.this, "Jenis " +namaKelas +" harus 3 - 50 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                }
                else if(merekKendaraanSparepart_tampung.length()<4 || merekKendaraanSparepart_tampung.length()>50)
                {
                    FancyToast.makeText(Sparepart.this, "Merk kendaraan " +namaKelas +" harus 4 - 50 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                }
                else if(jenisKendaraanSparepart_tampung.length()<4 || jenisKendaraanSparepart_tampung.length()>50)
                {
                    FancyToast.makeText(Sparepart.this, "Jenis kendaraan " +namaKelas +" harus 4 - 50 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                }
            }
            else
            {
                String status= uploadGambar(gambarSparepart_tampung);
                if(status == "gagal")
                {
                    gambarSparepart_tampung= null;
                }
                //POST data into API ,,Build retrofit
                Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit= builder.build();
                PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
                SparepartDAO sp= new SparepartDAO(kodeSparepart_tampung.toUpperCase(), namaSparepart_tampung, jenisSparepart_tampung, stokSparepart_tampung, stokMinimumSparepart_tampung, rakSparepart_tampung, hargaBeliSparepart_tampung, hargaJualSparepart_tampung, merekKendaraanSparepart_tampung, jenisKendaraanSparepart_tampung, gambarSparepart_tampung+".jpg");

                id_sparepart= s.getId_sparepart();
                Call<String> sparepartDAOCall= apiClient.requestUpdateSparepart(sp, id_sparepart);
                sparepartDAOCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        FancyToast.makeText(Sparepart.this, "Berhasil mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        recreate();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(!isNetworkAvailable())
                        {
                            FancyToast.makeText(Sparepart.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        else
                        {
                            if (t instanceof IOException) {
                                FancyToast.makeText(Sparepart.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                // logging probably not necessary
                            }
                            else {
                                FancyToast.makeText(Sparepart.this, "Gagal mengedit "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                // todo log to some central bug tracking service
                            }
                        }
                    }
                });

            }
        } //end of else
    }

    public void simpan()
    {
        String kodeSparepart_tampung, namaSparepart_tampung, jenisSparepart_tampung, rakSparepart_tampung, merekKendaraanSparepart_tampung, jenisKendaraanSparepart_tampung;
        final String gambarSparepart_tampung;
        int stokSparepart_tampung, stokMinimumSparepart_tampung;
        double hargaBeliSparepart_tampung, hargaJualSparepart_tampung;
        kodeSparepart_tampung= kodeSparepart.getText().toString();
        namaSparepart_tampung= namaSparepart.getText().toString();
        jenisSparepart_tampung= jenisSparepart.getText().toString();
        gambarSparepart_tampung= namaSparepart.getText().toString().trim() +"_" +getSaltString(64);
        if(stokSparepart.getText().toString().isEmpty())
        {
            stokSparepart_tampung= 0;
        }
        else
        {
            stokSparepart_tampung= Integer.parseInt(stokSparepart.getText().toString());
        }
        if(stokMinimumSparepart.getText().toString().isEmpty())
        {
            stokMinimumSparepart_tampung= 0;
        }
        else
        {
            stokMinimumSparepart_tampung= Integer.parseInt(stokMinimumSparepart.getText().toString());
        }

        if(Integer.parseInt(rakSparepart3.getSelectedItem().toString()) < 10)
        {
            rakSparepart_tampung= rakSparepart1.getSelectedItem().toString() +"-" +rakSparepart2.getSelectedItem().toString() +"-0" +rakSparepart3.getSelectedItem().toString();
        }
        else
        {
            rakSparepart_tampung= rakSparepart1.getSelectedItem().toString() +"-" +rakSparepart2.getSelectedItem().toString() +"-" +rakSparepart3.getSelectedItem().toString();
        }

        if(hargaBeliSparepart.getText().toString().isEmpty())
        {
            hargaBeliSparepart_tampung= 0;
        }
        else
        {
            hargaBeliSparepart_tampung= Double.parseDouble(hargaBeliSparepart.getText().toString());
        }
        if(hargaJualSparepart.getText().toString().isEmpty())
        {
            hargaJualSparepart_tampung= 0;
        }
        else
        {
            hargaJualSparepart_tampung= Double.parseDouble(hargaJualSparepart.getText().toString());
        }
        merekKendaraanSparepart_tampung= merekKendaraanSparepart.getText().toString();
        jenisKendaraanSparepart_tampung= jenisKendaraanSparepart.getText().toString();

        if(kodeSparepart_tampung.isEmpty() || namaSparepart_tampung.isEmpty() || jenisSparepart_tampung.isEmpty() ||
                stokSparepart_tampung == 0 || stokMinimumSparepart_tampung == 0 || rakSparepart_tampung.isEmpty() ||
                hargaBeliSparepart_tampung == 0 || hargaJualSparepart_tampung == 0 || merekKendaraanSparepart_tampung.isEmpty() ||
                jenisKendaraanSparepart_tampung.isEmpty())
        {
            FancyToast.makeText(Sparepart.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(kodeSparepart_tampung.length() > 0 && kodeSparepart_tampung.length() != 3 ||
                namaSparepart_tampung.length() > 0 && namaSparepart_tampung.length() < 4 || namaSparepart_tampung.length()>50 ||
                jenisSparepart_tampung.length() > 0 && jenisSparepart_tampung.length() < 3 || jenisSparepart_tampung.length()>50 ||
                merekKendaraanSparepart_tampung.length() > 0 && merekKendaraanSparepart_tampung.length() < 4 || merekKendaraanSparepart_tampung.length()>50 ||
                jenisKendaraanSparepart_tampung.length() > 0 && jenisKendaraanSparepart_tampung.length() < 4 || jenisKendaraanSparepart_tampung.length()>50)
        {
            if(kodeSparepart_tampung.length() != 3)
            {
                FancyToast.makeText(Sparepart.this, "Kode " +namaKelas +" harus 3 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(namaSparepart_tampung.length() <4 || namaSparepart_tampung.length()>50)
            {
                FancyToast.makeText(Sparepart.this, "Nama " +namaKelas +" harus 4 - 50 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(jenisSparepart_tampung.length()<3 || jenisSparepart_tampung.length()>50)
            {
                FancyToast.makeText(Sparepart.this, "Jenis " +namaKelas +" harus 3 - 50 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(merekKendaraanSparepart_tampung.length()<4 || merekKendaraanSparepart_tampung.length()>50)
            {
                FancyToast.makeText(Sparepart.this, "Merk kendaraan " +namaKelas +" harus 4 - 50 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(jenisKendaraanSparepart_tampung.length()<4 || jenisKendaraanSparepart_tampung.length()>50)
            {
                FancyToast.makeText(Sparepart.this, "Jenis kendaraan " +namaKelas +" harus 4 - 50 huruf", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
        }
        else
        {
            //getting (random) name for the image
            uploadGambar(gambarSparepart_tampung);
            //POST data into API ,,Build retrofit
            Retrofit.Builder builder= new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit= builder.build();
            PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
            Call<String> sparepartDAOCall= apiClient.requestSaveSparepart(kodeSparepart_tampung.toUpperCase(), namaSparepart_tampung, jenisSparepart_tampung, stokSparepart_tampung, stokMinimumSparepart_tampung, rakSparepart_tampung, hargaBeliSparepart_tampung, hargaJualSparepart_tampung, merekKendaraanSparepart_tampung, jenisKendaraanSparepart_tampung, gambarSparepart_tampung+".jpg");

            Log.d(TAG, "simpan: " +kodeSparepart_tampung.toUpperCase()+namaSparepart_tampung+jenisSparepart_tampung);

            sparepartDAOCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    FancyToast.makeText(Sparepart.this, "Berhasil menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                    recreate();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if(!isNetworkAvailable())
                    {
                        FancyToast.makeText(Sparepart.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                    else
                    {
                        if (t instanceof IOException) {
                            FancyToast.makeText(Sparepart.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            // logging probably not necessary
                        }
                        else {
                            FancyToast.makeText(Sparepart.this, "Gagal menyimpan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                            // todo log to some central bug tracking service
                        }
                    }
                }
            });
        }
    }

    public void tampil()
    {
        FancyToast.makeText(Sparepart.this, "Sedang menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        PegawaiApiClient apiClient= retrofit.create(PegawaiApiClient.class);
        Call<List<SparepartDAO>> sparepartDAOCall= apiClient.getAllSparepart();

        sparepartDAOCall.enqueue(new Callback<List<SparepartDAO>>() {
            @Override
            public void onResponse(Call<List<SparepartDAO>> call, Response<List<SparepartDAO>> response) {
                // mengambil data dari response, agar listSparepart terisi

                int position= 0;
                response.body().get(position).getNama_sparepart();

                recyclerAdapterSparepart.notifyDataSetChanged();
                recyclerAdapterSparepart= new RecyclerAdapterSparepart(Sparepart.this, response.body());
                recyclerView.setAdapter(recyclerAdapterSparepart);

                // mengambil data dari response, agar listSparepart2 terisi
                recyclerAdapterSparepartSearch.notifyDataSetChanged();
                recyclerAdapterSparepartSearch= new RecyclerAdapterSparepartSearch(Sparepart.this, response.body());
                if(response.body() != null)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        listSparepart2.add(response.body().get(i));
                    }
                }
                FancyToast.makeText(Sparepart.this, "Berhasil menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
            }

            @Override
            public void onFailure(Call<List<SparepartDAO>> call, Throwable t) {
                if(!isNetworkAvailable())
                {
                    FancyToast.makeText(Sparepart.this, "Tidak ada koneksi internet", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
                else
                {
                    if (t instanceof IOException) {
                        FancyToast.makeText(Sparepart.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        // logging probably not necessary
                    }
                    else {
                        FancyToast.makeText(Sparepart.this, "Gagal menampilkan "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        // todo log to some central bug tracking service
                    }
                }
            }
        });
    }

    public void cari()
    {
        deleteSearchSparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSparepart.setText("");
            }
        });

        searchSparepart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(recyclerViewSearch.getAdapter() != null)
                {
                    loadSearchSparepartPreferences();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void loadSearchSparepartPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_SPAREPART, Context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= pref.getString(PREF_SPAREPART, "");
        final SparepartDAO s= gson.fromJson(json, SparepartDAO.class);

        if(s != null)
        {
            id_sparepart= s.getId_sparepart();
            kodeSparepart.setText(s.getKode_sparepart());
            namaSparepart.setText(s.getNama_sparepart());
            jenisSparepart.setText(s.getJenis_sparepart());
            stokSparepart.setText(Integer.toString(s.getStok_sparepart()));
            stokMinimumSparepart.setText(Integer.toString(s.getStok_minimum_sparepart()));
//            rakSparepart.setText(s.getRak_sparepart());
            String lokasi= s.getRak_sparepart().substring(0, 3);
            rakSparepart1.setSelection(sAdapter1.getPosition(lokasi));
            String tempat= s.getRak_sparepart().substring(4, 8);
            rakSparepart2.setSelection(sAdapter2.getPosition(tempat));
            String nomor= s.getRak_sparepart().substring(9, 11);
            listRakSparepart3= new ArrayList<>();
            listRakSparepart3.add(nomor);
            sAdapter3= new ArrayAdapter<>(Sparepart.this, android.R.layout.simple_spinner_item, listRakSparepart3);
            sAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sAdapter3.notifyDataSetChanged();
            rakSparepart3.setAdapter(sAdapter3);
            rakSparepart3.setSelection(sAdapter2.getPosition(nomor));
            Log.d(TAG, "lokasi: " +lokasi +", tempat: " +tempat +", nomor: " +nomor);
            hargaBeliSparepart.setText(Double.toString(s.getHarga_beli_sparepart()));
            hargaJualSparepart.setText(Double.toString(s.getHarga_jual_sparepart()));
            merekKendaraanSparepart.setText(s.getMerek_kendaraan_sparepart());
            jenisKendaraanSparepart.setText(s.getJenis_kendaraan_sparepart());

            class loadImage extends AsyncTask<Integer, Void, Integer>
            {
                Drawable drawable;
                @Override
                protected Integer doInBackground(Integer... integers) {
                    String link= Helper.BASE_URL_IMAGE + "gambar_sparepart/"+s.getGambar_sparepart();
                    drawable= LoadImageFromWeb(link);
                    return 0;
                }

                @Override
                protected void onPostExecute(Integer integer) {
                    super.onPostExecute(integer);
                    if(drawable != null)
                    {
                        gambarSparepart.setImageDrawable(drawable);
                    }
                    else
                    {
                        gambarSparepart.setImageResource(R.mipmap.no_image);
                    }

                }
            }
            new loadImage().execute();
        }
    }

    private void kosongkanSearchSparepartPreferences()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(PREF_SPAREPART, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(PREF_SPAREPART, null);
        editor.commit();
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

    private void filter(String text) {
        List<SparepartDAO> filteredList = new ArrayList<>();

        for (SparepartDAO item : listSparepart2) {
            if (item.getNama_sparepart().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        recyclerAdapterSparepartSearch.filterList(filteredList);
        if(text.isEmpty() || text == "" || text == null || text.equals(null))
        {
            recyclerViewSearch.setAdapter(null); // show nothing
        }
        else
        {
            recyclerViewSearch.setAdapter(recyclerAdapterSparepartSearch); //show something
        }
    }

    private void referensi()
    {
        // https://codinginflow.com/tutorials/android/recyclerview-edittext-search
        // http://androidbitmaps.blogspot.com/2015/04/loading-images-in-android-part-iii-pick.html

        // https://www.simplifiedcoding.net/android-upload-image-to-server/
        // https://www.youtube.com/watch?v=odmC3aa210Q

        // http://webideasole.com/blogs/2018/12/13/android-upload-image-using-retrofit-2/
    }



}

