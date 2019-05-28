package com.toviddd.sitato;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.toviddd.sitato.DAO.ApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.CabangDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Cabang;
import com.toviddd.sitato.Pegawai.Area.PegawaiMainActivity;
import com.toviddd.sitato.Pegawai.Area.tab.PegawaiArea_tab2;
import com.toviddd.sitato.Pegawai.Area.tab.PegawaiArea_tab3;
import com.toviddd.sitato.Pegawai.Area.transaksi.Transaksi_sparepart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnPxWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationSparepart extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    String TAG= "Notifikasi";
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout llNotifikasiSparepart;
    TableView tableView;
    public String[] TABLE_HEADERS= {"No", "Nama", "Min", "Stok"};
    public List<SparepartDAO> listSparepartKurang2= new ArrayList<>();
    public String[][] listSparepartKurang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_sparepart);
        getSupportActionBar().setTitle("Kembali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkLoggedIn();
        initAttribute();
        listSparepartKurang2= null;
        loadSparepartKurang();
    }

    private void checkLoggedIn()
    {
        SharedPreferences pref= getApplicationContext().getSharedPreferences(Helper.PREF_LOGIN_PEGAWAI, Context.MODE_PRIVATE);
        int id_pegawai_login= pref.getInt(Helper.ID_PEGAWAI_LOGIN, -1);
        int id_role_login= pref.getInt(Helper.ID_ROLE_LOGIN, -1);
        if(id_pegawai_login == -1)
        {
            Intent i= new Intent(NotificationSparepart.this, LoginActivity.class);
            i.putExtra(Helper.NOTIF_SPAREPART, 1);
            startActivity(i);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotificationSparepart.this, PegawaiMainActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                startActivity(new Intent(NotificationSparepart.this, PegawaiMainActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        llNotifikasiSparepart.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                loadSparepartKurang();
                llNotifikasiSparepart.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    private void initAttribute()
    {
        swipeRefreshLayout= findViewById(R.id.swipeRefreshLayout_notifikasiSparepart);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.red)
        );
        llNotifikasiSparepart= findViewById(R.id.linearLayout_notifikasiSparepart);
        tableView= findViewById(R.id.tableView_sparepart);
    }


    private void table()
    {
        TableColumnPxWidthModel columnModel = new TableColumnPxWidthModel(4, 350);
        columnModel.setColumnWidth(0, 80);
        columnModel.setColumnWidth(1, 300);
        columnModel.setColumnWidth(2, 100);
        columnModel.setColumnWidth(3, 100);

        tableView.setColumnModel(columnModel);
//        tableView.setDataAdapter(new SimpleTableDataAdapter(this, listSparepartKurang));
        SimpleTableDataAdapter stda= new SimpleTableDataAdapter(this, listSparepartKurang);
        stda.setTextSize(10);
        tableView.setDataAdapter(stda);
        SimpleTableHeaderAdapter stha= new SimpleTableHeaderAdapter(this, TABLE_HEADERS);
        stha.setTextSize(10);
        tableView.setHeaderAdapter(stha);
//        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
        tableView.setHeaderBackgroundColor(getResources().getColor(R.color.orange80));
//        tableView.setWeightSum(1);
    }

    private void loadSparepartKurang()
    {
        Log.d(TAG, "onResponse: MASUK RETROFIT");
        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        ApiClient apiClient= retrofit.create(ApiClient.class);

        Call<List<SparepartDAO>> sparepartDAOCall= apiClient.getSparepartStokKurang();
        sparepartDAOCall.enqueue(new Callback<List<SparepartDAO>>() {
            @Override
            public void onResponse(Call<List<SparepartDAO>> call, Response<List<SparepartDAO>> response) {
                listSparepartKurang2= response.body();
                if(listSparepartKurang2 != null)
                {
                    listSparepartKurang= new String[listSparepartKurang2.size()][4];
                }
                else
                {
                    listSparepartKurang= new String[1][4];
                }
                Log.d(TAG, "onResponse: BERHASIL LOAD SPAREPART KURANG DARI STOK MINIMAL");
                assignList();
                table();
            }

            @Override
            public void onFailure(Call<List<SparepartDAO>> call, Throwable t) {
                Log.d(TAG, "onFailure: GAGAL LOAD SPAREPART KURANG DARI STOK MINIMAL");
            }
        });
    }

    private void assignList()
    {
        if(listSparepartKurang2 != null)
        {
            for(int i= 0; i<listSparepartKurang2.size(); i++)
            {
                listSparepartKurang[i][0]= String.valueOf(i+1);
                listSparepartKurang[i][1]= listSparepartKurang2.get(i).getNama_sparepart();
                listSparepartKurang[i][2]= String.valueOf(listSparepartKurang2.get(i).getStok_minimum_sparepart());
                listSparepartKurang[i][3]= String.valueOf(listSparepartKurang2.get(i).getStok_sparepart());
                Log.d(TAG, "=-=-========-> assignList: " +listSparepartKurang[i][0] +", " +listSparepartKurang[i][1] +", " +listSparepartKurang[i][2] +", " +listSparepartKurang[i][3]);
            }
        }
        else
        {
            Log.d(TAG, "assignList: SPAREPART KURANG DARI MINIMAL, KOSOSONG");
            listSparepartKurang= new String[1][4];
            listSparepartKurang[0][0]= ""; 
            listSparepartKurang[0][1]= "Belum ada"; 
            listSparepartKurang[0][2]= ""; 
            listSparepartKurang[0][3]= ""; 
        }
    }

    private void referensi()
    {
        // https://github.com/ISchwarz23/SortableTableView
        // https://convertingcolors.com/android-color-4278190335.html
    }
}
