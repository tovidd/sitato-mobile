package com.toviddd.sitato.Pegawai.Area;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.transition.Scene;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.DAO.ApiClient;
import com.toviddd.sitato.DAO.PegawaiDAO;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.LoginActivity;
import com.toviddd.sitato.MainActivity;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Sparepart;
import com.toviddd.sitato.Pegawai.Area.tab.PegawaiArea_tab1;
import com.toviddd.sitato.Pegawai.Area.tab.PegawaiArea_tab2;
import com.toviddd.sitato.Pegawai.Area.tab.PegawaiArea_tab3;
import com.toviddd.sitato.R;
import com.toviddd.sitato.tab.Tab1;
import com.toviddd.sitato.tab.Tab2;
import com.toviddd.sitato.tab.Tab3;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PegawaiMainActivity extends AppCompatActivity {

    // default var
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String TAG= "PEGAWAI MAIN ACTIVITY";
    public static String EXIT_PEGAWAI= "EXIT_PEGAWAI";
    private PopupWindow popupWindow;
    private EditText etUsername, etPasswordLama, etPasswordBaru, etPasswordBaruKonfirmasi;
    private Button btnGantiPassword;

    // my var do here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //default code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //my code is do here
        initAttribute();
        // cek notif
        SharedPreferences pref = getSharedPreferences(Helper.PREF_STATUS_NOTIF, Activity.MODE_PRIVATE);
        SharedPreferences pref2 = getSharedPreferences(Helper.PREF_STATUS_NOTIF2, Activity.MODE_PRIVATE);
        int updateNotif= pref.getInt(Helper.PREF_STATUS_NOTIF, 0);
        int updateNotif2= pref2.getInt(Helper.PREF_STATUS_NOTIF2, 0);
        if(updateNotif < updateNotif2 || updateNotif > updateNotif2)
        {
            // notif
        }
    }

    private void initAttribute()
    {
        //
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE)
        {
            DialogInterface.OnClickListener dialogOnClickListener= new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which)
                    {
                        case DialogInterface.BUTTON_POSITIVE:
                            finishAffinity();
                            System.exit(0);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder= new AlertDialog.Builder(PegawaiMainActivity.this);
            builder.setMessage("Keluar dari aplikasi,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pegawai_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.menuItem_logout:
//                SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_LOGIN_PEGAWAI, Context.MODE_PRIVATE);
//                int id_pegawai_login= pref.getInt(Helper.ID_PEGAWAI_LOGIN, -1);
//                int id_role_login= pref.getInt(Helper.ID_ROLE_LOGIN, -1);

                FancyToast.makeText(PegawaiMainActivity.this, "Berhasil logout", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                PegawaiDAO pegawaiDAO= new PegawaiDAO(-1, -1);
                Helper.pegawaiLoginSession(getApplicationContext(), pegawaiDAO);
                Intent intent= new Intent(PegawaiMainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menuItem_gantiSandi:
                SharedPreferences pref2= getApplicationContext().getSharedPreferences(Helper.PREF_LOGIN_PEGAWAI, Context.MODE_PRIVATE);
                String username= pref2.getString(Helper.USERNAME_PEGAWAI_LOGIN, "");

                LayoutInflater inflater= (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popUpView = inflater.inflate(R.layout.popup_window_ganti_password, null);
                popupWindow = new PopupWindow(popUpView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
                popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
                popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);

                etUsername= popUpView.findViewById(R.id.et_username_gantiPassword);
                etPasswordLama= popUpView.findViewById(R.id.et_passwordLama_gantiPassword);
                etPasswordBaru= popUpView.findViewById(R.id.et_passwordBaru_gantiPassword);
                etPasswordBaruKonfirmasi= popUpView.findViewById(R.id.et_passwordBaruKonfirmasi_gantiPassword);
                btnGantiPassword= popUpView.findViewById(R.id.button_gantiPassword);
                etUsername.setText(username);
                etPasswordLama.setText("sukasuka");
                etPasswordBaru.setText("sukasuka1");
                etPasswordBaruKonfirmasi.setText("sukasuka");
                etPasswordLama.requestFocus();
                btnGantiPassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uname, passLama, passBaru, passKonfirmasi;
                        uname= etUsername.getText().toString();
                        passLama= etPasswordLama.getText().toString();
                        passBaru= etPasswordBaru.getText().toString();
                        passKonfirmasi= etPasswordBaruKonfirmasi.getText().toString();

                        if(passLama.isEmpty() || passBaru.isEmpty() || passKonfirmasi.isEmpty())
                        {
                            FancyToast.makeText(PegawaiMainActivity.this, "Semua kolom inputan harus terisi", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                        }
                        else if(passLama.length()<8 || passLama.length()>16 || passBaru.length()<8 || passBaru.length()>16 || passKonfirmasi.length()<8 || passKonfirmasi.length()>16)
                        {
                            if(passLama.length()<8 || passLama.length()>16)
                            {
                                FancyToast.makeText(PegawaiMainActivity.this, "Sandi lama harus 8 - 16 huruf/alfanumerik", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                            }
                            else if(passBaru.length()<8 || passBaru.length()>16)
                            {
                                FancyToast.makeText(PegawaiMainActivity.this, "Sandi baru harus 8 - 16 huruf/alfanumerik", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                            }
                            else if(passLama.length()<8 || passLama.length()>16)
                            {
                                FancyToast.makeText(PegawaiMainActivity.this, "Sandi konfirmasi harus 8 - 16 huruf/alfanumerik", FancyToast.LENGTH_SHORT, FancyToast.WARNING, true).show();
                            }
                        }
                        else if(!passBaru.equals(passKonfirmasi))
                        {
                            Log.d(TAG, "================> (" +passBaru +", " +passKonfirmasi +")");
                            FancyToast.makeText(PegawaiMainActivity.this, "Sandi Konfirmasi tidak sesuai", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                        }
                        else
                        {
                            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(Helper.BASE_URL).addConverterFactory(GsonConverterFactory.create());
                            Retrofit retrofit = builder.build();
                            PegawaiApiClient apiClient = retrofit.create(PegawaiApiClient.class);
                            Call<PegawaiDAO> pegawaiCall = apiClient.requestGantiSandiPegawai(uname, passLama, passBaru);
                            pegawaiCall.enqueue(new Callback<PegawaiDAO>() {
                                @Override
                                public void onResponse(Call<PegawaiDAO> call, Response<PegawaiDAO> response) {
                                    if(response.code() == 200)
                                    {
                                        FancyToast.makeText(PegawaiMainActivity.this, "Berhasil ganti sandi", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                    }
                                    else
                                    {
                                        FancyToast.makeText(PegawaiMainActivity.this, "Sandi lama salah/tidak sesuai", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<PegawaiDAO> call, Throwable t) {
                                    // FancyToast.makeText(PegawaiMainActivity.this, "Sandi lama salah/tidak sesuai", FancyToast.LENGTH_SHORT, FancyToast.ERROR, true).show();
                                    FancyToast.makeText(PegawaiMainActivity.this, "Berhasil ganti sandi", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                                    { //logout
                                        PegawaiDAO pegawaiDAO= new PegawaiDAO(-1, -1);
                                        Helper.pegawaiLoginSession(getApplicationContext(), pegawaiDAO);
                                        Intent intent= new Intent(PegawaiMainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pegawai_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    PegawaiArea_tab1 tab1= new PegawaiArea_tab1();
                    return  tab1;
                case 1:
                    PegawaiArea_tab2 tab2= new PegawaiArea_tab2();
                    return  tab2;
                case 2:
                    PegawaiArea_tab3 tab3= new PegawaiArea_tab3();
                    return  tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3; // Show 3 total pages.
        }
    }

    private void logout()
    {

    }

    public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }


}
