package com.toviddd.sitato;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;

import com.google.gson.Gson;
import com.toviddd.sitato.DAO.PegawaiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;

public class Helper {
    public static String BASE_URL= "http://192.168.43.104:8000/api/";
    public static String BASE_URL_IMAGE= "http://192.168.43.104:8000/files/";
//    public static String BASE_URL= "http://192.168.19.140/8839/api/";
//    public static String BASE_URL_IMAGE= "http://192.168.19.140/8839/files/";

    public static final String PREF_TRANSAKSI= "PREF_TRANSAKSI";
    public static final String PREF_TRANSAKSI_SPAREPART= "PREF_TRANSAKSI_SPAREPART";
    public static final String PREF_TRANSAKSI_JASA_SERVICE= "PREF_TRANSAKSI_JASA_SERVICE";
    public static final String PREF_BELI_TRANSAKSI_SPAREPART= "PREF_BELI_TRANSAKSI_SPAREPART";
    public static final String PREF_BELI_TRANSAKSI_JASA_SERVICE= "PREF_BELI_TRANSAKSI_JASA_SERVICE";
    public static final String PREF_LIST_BELI_TRANSAKSI_SPAREPART= "PREF_LIST_BELI_TRANSAKSI_SPAREPART";
    public static final String PREF_LIST_BELI_TRANSAKSI_JASA_SERVICE= "PREF_LIST_BELI_TRANSAKSI_JASA_SERVICE";
    public static final String PREF_PELANGGAN_TRANSAKSI= "PREF_PELANGGAN_TRANSAKSI";
    public static final String PREF_PELANGGAN= "PREF_PELANGGAN";
    public static final String PREF_STATUS_LOGIN= "PREF_STATUS_LOGIN";
    public static final String PREF_STATUS_NOTIF= "PREF_STATUS_NOTIF";
    public static final String PREF_STATUS_NOTIF2= "PREF_STATUS_NOTIF2";
    public static final String NOTIF_SPAREPART= "NOTIF_SPAREPART";



    public static String PREF_LOGIN_PEGAWAI = "PREF_LOGIN_PEGAWAI";
    public static String ID_PEGAWAI_LOGIN= "ID_PEGAWAI_LOGIN";
    public static String ID_ROLE_LOGIN= "ID_ROLE_LOGIN";
    public static String ID_CABANG_LOGIN= "ID_CABANG_LOGIN";
    public static String NAMA_PEGAWAI_LOGIN= "NAMA_PEGAWAI_LOGIN";
    public static String USERNAME_PEGAWAI_LOGIN= "USERNAME_PEGAWAI_LOGIN";
    public static SharedPreferences pref;


    public static void pegawaiLoginSession(Context context, PegawaiDAO pegawai){
        pref = context.getSharedPreferences(PREF_LOGIN_PEGAWAI, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putInt(ID_PEGAWAI_LOGIN, pegawai.getId_pegawai());
        editor.putInt(ID_ROLE_LOGIN, pegawai.getId_role());
        editor.putInt(ID_CABANG_LOGIN, pegawai.getId_cabang());
        editor.putString(NAMA_PEGAWAI_LOGIN, pegawai.getNama_pegawai());
        editor.putString(USERNAME_PEGAWAI_LOGIN, pegawai.getUsername_pegawai());
        editor.commit();
    }

    public static void networkPermission()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static void sessionMaker(Context context, String sessionName, Object object)
    {
        SharedPreferences pref= context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        Gson gson= new Gson();
        String json= gson.toJson(object);
        editor.putString(sessionName, json);
        editor.commit();
    }

}
