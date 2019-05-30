package com.toviddd.sitato;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.DAO.ApiClient;
import com.toviddd.sitato.DAO.PegawaiDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Cabang;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Supplier;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity{
    private EditText username, password;
    private Button btnLoginPegawai, btnIgnoreLoginPegawai;
    private ProgressDialog mDialog;
    String testUsername, testPassword;
    private String namaKelas= "login";
    public String TAG= "LOGIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.networkPermission();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAttribute();
        username.setText("philip");
        password.setText("sukasuka");

        btnIgnoreLoginPegawai.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, PegawaiMainActivity.class);
                PegawaiDAO pegawaiDAO= new PegawaiDAO(1, 1);
                Helper.pegawaiLoginSession(getApplicationContext(), pegawaiDAO);
                startActivity(intent);
            }
        });
        btnLoginPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSignIn();
            }
        });
        mDialog = new ProgressDialog(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE)
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initAttribute()
    {
        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        btnLoginPegawai = findViewById(R.id.btnLoginPegawai);
        btnIgnoreLoginPegawai= findViewById(R.id.btnIgnoreLogin);
        btnIgnoreLoginPegawai.setVisibility(View.GONE);
    }

    private void UserSignIn() {

        testUsername = username.getText().toString().trim();
        testPassword = password.getText().toString().trim();

        mDialog.setMessage("Login");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        if (testUsername.length() == 0 || testPassword.length() == 0)
        {
            FancyToast.makeText(LoginActivity.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
        }
        else if(testUsername.length()<4 || testUsername.length()>50 || testPassword.length()<8 || testPassword.length()>16)
        {
            if(testUsername.length()<4 || testUsername.length()>50)
            {
                FancyToast.makeText(LoginActivity.this, "Username harus 4 - 50 alfanumerik", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
            else if(testPassword.length()<8 || testPassword.length()>16)
            {
                FancyToast.makeText(LoginActivity.this, "Password harus 8 - 16 huruf/alfanumerik", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
            }
        }
        else
        {
            //PROSES LOGIN
            Retrofit.Builder builder = new Retrofit
                    .Builder()
                    .baseUrl(Helper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();
            ApiClient apiClient = retrofit.create(ApiClient.class);
            Call<PegawaiDAO> PegawaiCall = apiClient.cekLogin(testUsername, testPassword);

            PegawaiCall.enqueue(new Callback<PegawaiDAO>() {
                @Override
                public void onResponse(Call<PegawaiDAO> call, Response<PegawaiDAO> response)
                {
                    if(response.body() != null)
                    {
                        PegawaiDAO pegawaiDAO= response.body();
                        Log.d(TAG, "====================> onResponse login activity: " +pegawaiDAO.getUsername_pegawai());
                        Helper.pegawaiLoginSession(getApplicationContext(), pegawaiDAO);
                        setStatusLogin(1);
                        FancyToast.makeText(LoginActivity.this, "Berhasil "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                        //mDialog.cancel();
                        Bundle b= getIntent().getExtras();
                        if(b != null)
                        {
                            if(b.getInt(Helper.NOTIF_SPAREPART, 0) == 1)
                            {
                                startActivity(new Intent(LoginActivity.this, NotificationSparepart.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                        }
                        else
                        {
                            startActivity(new Intent(LoginActivity.this, PegawaiMainActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                    else
                    {
                        FancyToast.makeText(LoginActivity.this, "Username atau sandi salah", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                    }
                }

                @Override
                public void onFailure(Call<PegawaiDAO> call, Throwable t) {
                    FancyToast.makeText(LoginActivity.this, "Gagal "+namaKelas, FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                }
            });
        }
        mDialog.cancel();
    }

    void setStatusLogin(int status){
        SharedPreferences pref;
        pref = getApplicationContext().getSharedPreferences(Helper.PREF_STATUS_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putInt(Helper.PREF_STATUS_LOGIN, status);
        editor.commit();
    }

}

