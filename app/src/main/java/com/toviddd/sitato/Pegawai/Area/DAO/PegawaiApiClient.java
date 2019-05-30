package com.toviddd.sitato.Pegawai.Area.DAO;

import com.toviddd.sitato.DAO.PegawaiDAO;
import com.toviddd.sitato.Pegawai.Area.Kelola.data.Sparepart;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface PegawaiApiClient {

    //    ======= sparepart =========
    @GET("sparepart")
    Call<List<SparepartDAO>> getAllSparepart();

    @GET("sparepart/{id_sparepart}")
    Call<SparepartDAO> getSparepart(
            @Path("id_sparepart") int id_sparepart
    );

    @POST("sparepart")
    @FormUrlEncoded
    Call<String> requestSaveSparepart(
            @Field("kode_sparepart") String kode_sparepart,
            @Field("nama_sparepart") String nama_sparepart,
            @Field("jenis_sparepart") String jenis_sparepart,
            @Field("stok_sparepart") int stok_sparepart,
            @Field("stok_minimum_sparepart") int stok_minimum_sparepart,
            @Field("rak_sparepart") String rak_sparepart,
            @Field("harga_beli_sparepart") double harga_beli_sparepart,
            @Field("harga_jual_sparepart") double harga_jual_sparepart,
            @Field("merek_kendaraan_sparepart") String merek_kendaraan_sparepart,
            @Field("jenis_kendaraan_sparepart") String jenis_kendaraan_sparepart,
            @Field("gambar_sparepart") String gambar_sparepart
    );

    @DELETE("sparepart/{id_sparepart}")
    Call<SparepartDAO> deleteSparepart(
            @Path("id_sparepart") int id_sparepart
    );

    @PUT("sparepart/{id_sparepart}")
    Call<String> requestUpdateSparepart(
            @Body SparepartDAO sparepartDAO,
            @Path("id_sparepart") int id_sparepart
    );

    @POST("sparepart/spinnerRakSparepart")
    @FormUrlEncoded
    Call<Integer> spinnerRakSparepart(
        @Field("lokasi") String lokasi,
        @Field("tempat") String tempat
    );






//    ======= supplier =========
    @GET("supplier")
    Call<List<SupplierDAO>> getAllSupplier();

    @POST("supplier")
    @FormUrlEncoded
    Call<String> requestSaveSupplier(
            @Field("nama_supplier") String nama_supplier,
            @Field("no_telepon_supplier") String no_telepon_supplier,
            @Field("alamat_supplier") String alamat_supplier,
            @Field("nama_sales") String nama_sales
    );

    @DELETE("supplier/{id_supplier}")
    Call<SupplierDAO> deleteSupplier(
            @Path("id_supplier") int id_supplier
    );

    @PUT("supplier/{id_supplier}")
    Call<String> requestUpdateSupplier(
            @Body SupplierDAO supplierDAO,
            @Path("id_supplier") int id_supplier
    );






    //    ======= pelanggan =========
    @GET("pelanggan")
    Call<List<PelangganDAO>> getAllPelanggan();

    @GET("pelanggan/{id_pelanggan}")
    Call<PelangganDAO> getPelanggan(
            @Path("id_pelanggan") int id_pelanggan
    );

    @POST("pelanggan")
    @FormUrlEncoded
    Call<PelangganDAO> requestSavePelanggan(
            @Field("nama_pelanggan") String nama_pelanggan,
            @Field("no_telepon_pelanggan") String no_telepon_pelanggan,
            @Field("alamat_pelanggan") String alamat_pelanggan
    );

    @POST("pelanggan")
    @FormUrlEncoded
    Call<PelangganDAO> requestSavePelangganTransaksi(
            @Field("nama_pelanggan") String nama_pelanggan,
            @Field("no_telepon_pelanggan") String no_telepon_pelanggan,
            @Field("alamat_pelanggan") String alamat_pelanggan
    );

    @DELETE("pelanggan/{id_pelanggan}")
    Call<PelangganDAO> deletePelanggan(
            @Path("id_pelanggan") int id_pelanggan
    );

    @PUT("pelanggan/{id_pelanggan}")
    Call<String> requestUpdatePelanggan(
            @Body PelangganDAO pelangganDAO,
            @Path("id_pelanggan") int id_pelanggan
    );






    //    ======= cabang =========
    @GET("cabang")
    Call<List<CabangDAO>> getAllCabang();

    @POST("cabang")
    @FormUrlEncoded
    Call<String> requestSaveCabang(
            @Field("nama_cabang") String nama_cabang,
            @Field("no_telepon_cabang") String no_telepon_cabang,
            @Field("alamat_cabang") String alamat_cabang
    );

    @DELETE("cabang/{id_cabang}")
    Call<CabangDAO> deleteCabang(
            @Path("id_cabang") int id_cabang
    );

    @PUT("cabang/{id_cabang}")
    Call<String> requestUpdateCabang(
            @Body CabangDAO cabangDAO,
            @Path("id_cabang") int id_cabang
    );






    //    ======= kendaraan =========
    @GET("kendaraan")
    Call<List<KendaraanDAO>> getAllKendaraan();

    @GET("kendaraan/{id_kendaraan}")
    Call<KendaraanDAO> getKendaraan(
            @Path("id_kendaraan") int id_kendaraan
    );

    @POST("kendaraan")
    @FormUrlEncoded
    Call<String> requestSaveKendaraan(
            @Field("id_pelanggan") int id_pelanggan,
            @Field("no_plat_kendaraan") String no_plat_kendaran,
            @Field("merek_kendaraan") String merek_kendaraan,
            @Field("jenis_kendaraan") String jenis_kendaraan
    );

    @DELETE("kendaraan/{id_kendaraan}")
    Call<KendaraanDAO> deleteKendaraan(
            @Path("id_kendaraan") int id_kendaraan
    );

    @PUT("kendaraan/{id_kendaraan}")
    Call<String> requestUpdateKendaraan(
            @Body KendaraanDAO kendaraanDAO,
            @Path("id_kendaraan") int id_kendaraan
    );










    //    ======= jasaService =========
    @GET("jasaService")
    Call<List<JasaServiceDAO>> getAllJasaService();

    @GET("jasaService/{id_jasa_service}")
    Call<JasaServiceDAO> getJasaService(
            @Path("id_jasa_service") int id_jasa_service
    );

    @POST("jasaService")
    @FormUrlEncoded
    Call<String> requestSaveJasaService(
            @Field("nama_jasa_service") String nama_jasa_service,
            @Field("harga_jasa_service") double harga_jasa_service
    );

    @DELETE("jasaService/{id_jasa_service}")
    Call<JasaServiceDAO> deleteJasaService(
            @Path("id_jasa_service") int id_jasa_service
    );

    @PUT("jasaService/{id_jasa_service}")
    Call<String> requestUpdateJasaService(
            @Body JasaServiceDAO jasaServiceDAO,
            @Path("id_jasa_service") int id_jasa_service
    );










    //    ======= pegawai =========
    @GET("pegawai/montir")
    Call<List<PegawaiDAO>> getAllPegawaiMontir();

    @POST("pegawai/gantiSandi")
    @FormUrlEncoded
    Call<PegawaiDAO> requestGantiSandiPegawai(
            @Field("username") String username,
            @Field("password") String password,
            @Field("new_password") String new_password
    );







    //    ======= transaksi =========
    @GET("transaksi")
    Call<List<TransaksiDAO>> getAllTransaksi();

    @GET("transaksi/indexTransaksi/{id_transaksi}")
    Call<TransaksiDAO> getTransaksi(
            @Path("id_transaksi") int id_transaksi
    );

    @POST("transaksi")
    @FormUrlEncoded
    Call<TransaksiDAO> requestSaveTransaksi(
            @Field("id_pelanggan") int id_pelanggan,
            @Field("id_cs") int id_cs,
            @Field("id_cabang") int id_cabang
    );

    @DELETE("transaksi/{id_transaksi}")
    Call<String> deleteTransaksi(
            @Path("id_transaksi") int id_transaksi
    );

    @POST("transaksi/delete")
    @FormUrlEncoded
    Call<String> deleteTransaksi2(
            @Field("id_transaksi") int id_transaksi
    );

    @GET("transaksi/indexToday")
    Call<List<TransaksiDAO>> getTransaksiToday();

    @GET("transaksi/indexYesterday")
    Call<List<TransaksiDAO>> getTransaksiYesterday();






    //    ======= transaksi sparepart =========
    @GET("transaksiPenjualanSparepart")
    Call<List<TransaksiSparepartDAO>> getAllTransaksiSparepart();

    @GET("transaksiPenjualanSparepart/{id_transaksi}")
    Call<List<TransaksiSparepartDAO>> getTransaksiSparepart(
        @Path("id_transaksi") int id_transaksi
    );

    @GET("transaksiPenjualanSparepart/indexComplete/{id_transaksi}")
    Call<String> getTransaksiSparepartIndexComplete(
        @Path("id_transaksi") int id_transaksi
    );

    @POST("transaksiPenjualanSparepart")
    @FormUrlEncoded
    Call<TransaksiSparepartDAO> requestSaveTransaksiSparepart(
        @Field("id_transaksi") int id_transaksi,
        @Field("id_sparepart") int id_sparepart,
        @Field("jumlah_transaksi_penjualan_sparepart") int jumlah_transaksi_penjualan_sparepart,
        @Field("subtotal_transaksi_penjualan_sparepart") double subtotal_transaksi_penjualan_sparepart
    );

    @PUT("transaksiPenjualanSparepart/{id_transaksi_penjualan_sparepart}")
    Call<String> requestUpdateTransaksiSparepart(
        @Body TransaksiSparepartDAO transaksiSparepartDAO,
        @Path("id_transaksi_penjualan_sparepart") int id_transaksi_penjualan_sparepart
    );

    @DELETE("transaksiPenjualanSparepart/{id_transaksi_penjualan_sparepart}")
    Call<TransaksiSparepartDAO> deleteTransaksiSparepart(
        @Path("id_transaksi_penjualan_sparepart") int id_transaksi_penjualan_sparepart
    );






    //    ======= transaksi jasa service =========
    @GET("transaksiPenjualanJasaService")
    Call<List<TransaksiJasaServiceDAO>> getAllTransaksiJasaService();

    @GET("transaksiPenjualanJasaService/{id_transaksi}")
    Call<List<TransaksiJasaServiceDAO>> getTransaksiJasaService(
            @Path("id_transaksi") int id_transaksi
    );

    @POST("transaksiPenjualanJasaService")
    @FormUrlEncoded
    Call<TransaksiJasaServiceDAO> requestSaveTransaksiJasaService(
            @Field("id_transaksi") int id_transaksi,
            @Field("id_montir") int id_montir,
            @Field("id_jasa_service") int id_jasa_service,
            @Field("id_kendaraan") int id_kendaraan,
            @Field("jumlah_transaksi_penjualan_jasa_service") int jumlah_transaksi_penjualan_jasa_service,
            @Field("subtotal_transaksi_penjualan_jasa_service") double subtotal_transaksi_penjualan_jasa_service
    );

    @PUT("transaksiPenjualanJasaService/{id_transaksi_penjualan_jasa_service}")
    Call<String> requestUpdateTransaksiJasaService(
            @Body TransaksiJasaServiceDAO transaksiJasaServiceDAO,
            @Path("id_transaksi_penjualan_jasa_service") int id_transaksi_penjualan_jasa_service
    );

    @DELETE("transaksiPenjualanJasaService/{id_transaksi_penjualan_jasa_service}")
    Call<TransaksiJasaServiceDAO> deleteTransaksiJasaService(
            @Path("id_transaksi_penjualan_jasa_service") int id_transaksi_penjualan_jasa_service
    );












    //    ======= pengadaan =========
    @GET("pengadaanSparepart")
    Call<List<TransaksiPengadaanDAO>> getAllTransaksiPengadaan();

    @GET("pengadaanSparepart/{id_pengadaan_sparepart}")
    Call<TransaksiPengadaanDAO> getTransaksiPengadaan(
            @Path("id_pengadaan_sparepart") int id_pengadaan_sparepart
    );

    @POST("pengadaanSparepart")
    @FormUrlEncoded
    Call<TransaksiPengadaanDAO> requestSaveTransaksiPengadaan(
            @Field("id_cabang") int id_cabang,
            @Field("id_supplier") int id_supplier,
            @Field("tanggal_pengadaan") String tanggal_pengadaan,
            @Field("waktu_pengadaan") String waktu_pengadaan
    );

    @PUT("pengadaanSparepart/{id_pengadaan_sparepart}")
    Call<String> requestUpdateTransaksiPengadaan(
            @Body TransaksiPengadaanDAO transaksiPengadaanDAO,
            @Path("id_pengadaan_sparepart") int id_pengadaan_sparepart
    );

    @DELETE("pengadaanSparepart/{id_pengadaan_sparepart}")
    Call<String> deleteTransaksiPengadaan(
            @Path("id_pengadaan_sparepart") int id_pengadaan_sparepart
    );










    //    ======= detil pengadaan =========
    @GET("detilPengadaanSparepart")
    Call<List<TransaksiPengadaanDetilDAO>> getAllTransaksiPengadaanDetil();

    @GET("detilPengadaanSparepart/{id_pengadaan_sparepart}")
    Call<List<TransaksiPengadaanDetilDAO>> getTransaksiPengadaanDetil(
            @Path("id_pengadaan_sparepart") int id_pengadaan_sparepart
    );

    @POST("detilPengadaanSparepart")
    @FormUrlEncoded
    Call<TransaksiPengadaanDetilDAO> requestSaveTransaksiPengadaanDetil(
            @Field("id_pengadaan_sparepart") int id_pengadaan_sparepart,
            @Field("id_sparepart") int id_sparepart,
            @Field("jumlah_beli_detil_pengadaan_sparepart") int jumlah_beli_detil_pengadaan_sparepart,
            @Field("subtotal_detil_pengadaan_sparepart") double subtotal_detil_pengadaan_sparepart
    );

    @PUT("detilPengadaanSparepart/{id_detil_pengadaan_sparepart}")
    Call<TransaksiPengadaanDetilDAO> requestUpdateTransaksiPengadaanDetil(
            @Body TransaksiPengadaanDetilDAO transaksiPengadaanDetilDAO,
            @Path("id_detil_pengadaan_sparepart") int id_detil_pengadaan_sparepart
    );

    @DELETE("detilPengadaanSparepart/{id_detil_pengadaan_sparepart}")
    Call<String> deleteTransaksiPengadaanDetil(
            @Path("id_detil_pengadaan_sparepart") int id_detil_pengadaan_sparepart
    );


}
