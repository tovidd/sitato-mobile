package com.toviddd.sitato;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.tab.Tab1;
import com.toviddd.sitato.tab.Tab2;
import com.toviddd.sitato.tab.Tab3;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // default var
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String TAG= "Main Activity";
    public static String EXIT_MAIN= "EXIT_MAIN";

    // my var do here


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // default code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        // my code goes here
        checkLoggedIn();
        // cek notif
        SharedPreferences pref = getSharedPreferences(Helper.PREF_STATUS_LOGIN, Activity.MODE_PRIVATE);
        int statusNotif = pref.getInt(Helper.PREF_STATUS_LOGIN, 0);
        if(statusNotif == 1)
        {
            notif();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.menuItem_login:
                Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.menuItem_notif:
                notif();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //fungsi default
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
    }

    // fungsi default
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    Tab1 tab1= new Tab1();
                    return  tab1;
                case 1:
                    Tab2 tab2= new Tab2();
                    return  tab2;
                case 2:
                    Tab3 tab3= new Tab3();
                    return  tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3; //3 tab
        }
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
                            // finishAndRemoveTask(); //minAPI 21 // work like press back button (bad)
                            finishAffinity();
                            System.exit(0);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Keluar dari aplikasi,?").setPositiveButton("Ya", dialogOnClickListener).setNegativeButton("Tidak", dialogOnClickListener).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void checkLoggedIn()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_LOGIN_PEGAWAI, Context.MODE_PRIVATE);
        int id_pegawai_login= pref.getInt(Helper.ID_PEGAWAI_LOGIN, -1);
        int id_role_login= pref.getInt(Helper.ID_ROLE_LOGIN, -1);
        Log.d(TAG, "checkLoggedIn: id_pegawai: "+id_pegawai_login +", id_role: "+id_role_login);
        if(id_pegawai_login != -1)
        {
            Log.d(TAG, "checkLoggedIn: START INTENT TO PEGAWAI");
            Intent intent= new Intent(MainActivity.this, PegawaiMainActivity.class);
            startActivity(intent);
        }
    }

    private void notif()
    {
        String pesan= "nama                         stok minimal        stok \n" +
                        "Ban                            10                             5\n" +
                        "Busi                           20                             11\n" +
                        "Kampas depan       20                             9\n" +
                        "...";
        // prepare notif
        Intent intent= new Intent(this, NotificationSparepart.class);
        PendingIntent pIntent= PendingIntent.getActivity(this, (int)System.currentTimeMillis(), intent, 0);

        //build notif
        Notification notification= new Notification.Builder(this)
                .setContentTitle("Sparepart kurang dari stok minimal")
                .setSmallIcon(R.drawable.ic_centang)
                .setWhen(Calendar.getInstance().getTimeInMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_user))
                .setContentText("Tekan untuk menampilkan ...")
                .setContentIntent(pIntent)
                .setStyle(new Notification.BigTextStyle().bigText(pesan))
                .addAction(R.drawable.ic_centang, "buka", pIntent).build();
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        long[] vibrate= {300, 400, 500};
        Vibrator v= (Vibrator)this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(vibrate, 1);
        Log.d(TAG, "notif mulai: " +System.currentTimeMillis());
        for(int i= 0; i<600000000; i++) {}
        Log.d(TAG, "notif selesai: " +System.currentTimeMillis());
        v.cancel();
        MediaPlayer ringtone= MediaPlayer.create(MainActivity.this, R.raw.notif1);
        ringtone.start();
        ringtone= MediaPlayer.create(MainActivity.this, R.raw.notif2);
        ringtone.start();
        notificationManager.notify(0, notification);
    }

    private void referensi()
    {
        //https://www.vogella.com/tutorials/AndroidNotifications/article.html
        // https://stackoverflow.com/questions/23836699/how-to-show-a-notification-even-app-is-not-running
        // https://www.androidauthority.com/android-push-notifications-with-firebase-cloud-messaging-925075/
    }
}
