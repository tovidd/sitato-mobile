package com.toviddd.sitato.DAO;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.toviddd.sitato.Pegawai.Area.DAO.SparepartDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiJasaServiceDAO;
import com.toviddd.sitato.Pegawai.Area.DAO.TransaksiSparepartDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiClient {
    @FormUrlEncoded
    @POST("pegawai/login")
    Call<PegawaiDAO> cekLogin(
        @Field("username") String username,
        @Field("password") String password
    );



    //    ======= sparepart =========
    @GET("sparepart/indexStokBesar")
    Call<List<SparepartDAO>> getSparepartStokBesar();

    @GET("sparepart/indexStokKecil")
    Call<List<SparepartDAO>> getSparepartStokKecil();

    @GET("sparepart/indexHargaBesar")
    Call<List<SparepartDAO>> getSparepartHargaBesar();

    @GET("sparepart/indexHargaKecil")
    Call<List<SparepartDAO>> getSparepartHargaKecil();

    @GET("sparepart/indexStokKurang")
    Call<List<SparepartDAO>> getSparepartStokKurang();

    @GET("sparepart/indexStokLebih")
    Call<List<SparepartDAO>> getSparepartStokLebih();





    //    ======= histori transaksi =========
    @POST("transaksi/indexPelanggan")
    @FormUrlEncoded
    Call<List<TransaksiDAO>> getAllTransaksiPelanggan(
            @Field("no_telepon_pelanggan") String no_telepon_pelanggan
    );

    @POST("transaksi/indexPelangganProgress")
    @FormUrlEncoded
    Call<List<TransaksiDAO>> getAllTransaksiProgressPelanggan(
            @Field("no_telepon_pelanggan") String no_telepon_pelanggan
    );

    @FormUrlEncoded
    @POST("transaksi/login")
    Call<String> cekLoginPelanggan(
            @Field("no_plat_kendaraan") String no_plat_kendaraan,
            @Field("no_telepon_pelanggan") String no_telepon_pelanggan
    );





    //    ======= detil transaksi =========
    @GET("transaksi/indexTransaksi/{id_transaksi}")
    Call<TransaksiDAO> getTransaksi(
            @Path("id_transaksi") int id_transaksi
    );

    @GET("transaksiPenjualanSparepart/{id_transaksi}")
    Call<List<TransaksiSparepartDAO>> getTransaksiSparepart(
            @Path("id_transaksi") int id_transaksi
    );

    @GET("transaksiPenjualanJasaService/{id_transaksi}")
    Call<List<TransaksiJasaServiceDAO>> getTransaksiJasaService(
            @Path("id_transaksi") int id_transaksi
    );
}
