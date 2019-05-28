package com.toviddd.sitato.tab;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.toviddd.sitato.DAO.ApiClient;
import com.toviddd.sitato.Helper;
import com.toviddd.sitato.Pegawai.Area.DAO.PegawaiApiClient;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Recycler.adapter.RecyclerAdapterSparepart;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Sparepart;
import com.toviddd.sitato.R;
import com.toviddd.sitato.RecyclerAdapterSparepartTab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Tab2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private String TAG= "Tab 2";
    private View rootView;
    Context context;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    private RecyclerAdapterSparepartTab2 adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<SparepartDAO> listSparepartStokBesar= new ArrayList<>();
    private List<SparepartDAO> listSparepartStokKecil= new ArrayList<>();
    private List<SparepartDAO> listSparepartHargaBesar= new ArrayList<>();
    private List<SparepartDAO> listSparepartHargaKecil= new ArrayList<>();
    private List<String> sortingSpinner= new ArrayList<>();
    private Spinner spinner;
    private TextView deleteSearchSparepart;
    private EditText etSearchSparepart;
    private String[] str= {"Sorting stok terbesar-terkecil", "Sorting stok terkecil-terbesar", "Sorting harga terbesar-terkecil", "Sorting harga terkecil-terbesar"};
    private String status= str[0];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.tab_2, container, false);
        initAttribute();
        loadSparepart();
        buildRecyclerView();
        setSortingSpinner();
        cari();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context= context;
    }

    @Override
    public void onRefresh() {
        nestedScrollView.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                loadSparepart();
                nestedScrollView.setVisibility(View.VISIBLE);
            }
        }, 3000);
    }

    private void initAttribute()
    {
        swipeRefreshLayout= rootView.findViewById(R.id.swipeRefreshLayout_tab2);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.orange),
                getResources().getColor(R.color.red)
        );
        nestedScrollView= rootView.findViewById(R.id.nestedScrollView_tab2);
        spinner= rootView.findViewById(R.id.spinnerSorting);
        deleteSearchSparepart= rootView.findViewById(R.id.textView_deleteSearchSparepart_tab2);
        etSearchSparepart= rootView.findViewById(R.id.editText_searchSparepart_tab2);
    }

    private void buildRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerView_Sparepart_tab2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecyclerAdapterSparepartTab2(getContext(), listSparepartStokBesar);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void loadSparepart()
    {
        Retrofit.Builder builder= new Retrofit
                .Builder()
                .baseUrl(Helper.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit= builder.build();
        ApiClient apiClient= retrofit.create(ApiClient.class);
        Call<List<SparepartDAO>> sparepartDAOCall= apiClient.getSparepartStokBesar();
        sparepartDAOCall.enqueue(new Callback<List<SparepartDAO>>() {
            @Override
            public void onResponse(Call<List<SparepartDAO>> call, Response<List<SparepartDAO>> response) {
                if(response.body() != null)
                {
                    listSparepartStokBesar= response.body();
                    adapter.notifyDataSetChanged();
                    adapter= new RecyclerAdapterSparepartTab2(context, listSparepartStokBesar);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<SparepartDAO>> call, Throwable t) {

            }
        });

        sparepartDAOCall= apiClient.getSparepartStokKecil();
        sparepartDAOCall.enqueue(new Callback<List<SparepartDAO>>() {
            @Override
            public void onResponse(Call<List<SparepartDAO>> call, Response<List<SparepartDAO>> response) {
                listSparepartStokKecil= response.body();
            }

            @Override
            public void onFailure(Call<List<SparepartDAO>> call, Throwable t) {

            }
        });

        sparepartDAOCall= apiClient.getSparepartHargaBesar();
        sparepartDAOCall.enqueue(new Callback<List<SparepartDAO>>() {
            @Override
            public void onResponse(Call<List<SparepartDAO>> call, Response<List<SparepartDAO>> response) {
                listSparepartHargaBesar= response.body();
            }

            @Override
            public void onFailure(Call<List<SparepartDAO>> call, Throwable t) {

            }
        });

        sparepartDAOCall= apiClient.getSparepartHargaKecil();
        sparepartDAOCall.enqueue(new Callback<List<SparepartDAO>>() {
            @Override
            public void onResponse(Call<List<SparepartDAO>> call, Response<List<SparepartDAO>> response) {
                listSparepartHargaKecil= response.body();
            }

            @Override
            public void onFailure(Call<List<SparepartDAO>> call, Throwable t) {

            }
        });
    }

    private void setSortingSpinner()
    {
        sortingSpinner.add(str[0]);
        sortingSpinner.add(str[1]);
        sortingSpinner.add(str[2]);
        sortingSpinner.add(str[3]);
        ArrayAdapter<String> dataAdapter= new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sortingSpinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text= parent.getSelectedItem().toString();
                switch(text)
                {
                    case "Sorting stok terbesar-terkecil":
                        adapter.notifyDataSetChanged();
                        adapter= new RecyclerAdapterSparepartTab2(context, listSparepartStokBesar);
                        recyclerView.setAdapter(adapter);
                        status= str[0];
                        Log.d(TAG, "onItemSelected: " +str[0]);
                        break;
                    case "Sorting stok terkecil-terbesar":
                        adapter.notifyDataSetChanged();
                        adapter= new RecyclerAdapterSparepartTab2(context, listSparepartStokKecil);
                        recyclerView.setAdapter(adapter);
                        status= str[1];
                        Log.d(TAG, "onItemSelected: " +str[1]);
                        break;
                    case "Sorting harga terbesar-terkecil":
                        adapter.notifyDataSetChanged();
                        adapter= new RecyclerAdapterSparepartTab2(context, listSparepartHargaBesar);
                        recyclerView.setAdapter(adapter);
                        status= str[2];
                        Log.d(TAG, "onItemSelected: " +str[2]);
                        break;
                    case "Sorting harga terkecil-terbesar":
                        adapter.notifyDataSetChanged();
                        adapter= new RecyclerAdapterSparepartTab2(context, listSparepartHargaKecil);
                        recyclerView.setAdapter(adapter);
                        status= str[3];
                        Log.d(TAG, "onItemSelected: " +str[3]);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void cari()
    {
        deleteSearchSparepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearchSparepart.setText("");
            }
        });

        etSearchSparepart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(status.equals(str[0]))
                {
                    filter(s.toString(), listSparepartStokBesar);
                }
                else if(status.equals(str[1]))
                {
                    filter(s.toString(), listSparepartStokKecil);
                }
                else if(status.equals(str[2]))
                {
                    filter(s.toString(), listSparepartHargaBesar);
                }
                else if(status.equals(str[3]))
                {
                    filter(s.toString(), listSparepartHargaKecil);
                }
            }
        });
    }

    private void filter(String text, List<SparepartDAO> myList)
    {
        List<SparepartDAO> filteredList= new ArrayList<>();
        for(SparepartDAO item: myList)
        {
            if(item.getNama_sparepart().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(item);
            }
        }
        adapter.filterListSparepart(filteredList);
    }

    private void referensi()
    {
        // https://github.com/programingjd/justified
    }

}
