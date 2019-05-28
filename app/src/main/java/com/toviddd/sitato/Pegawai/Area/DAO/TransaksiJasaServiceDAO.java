package com.toviddd.sitato.Pegawai.Area.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransaksiJasaServiceDAO {

    int id_transaksi_penjualan_jasa_service, id_transaksi, id_montir, id_jasa_service, id_kendaraan;
    @SerializedName("jumlah_transaksi_penjualan_jasa_service")
    @Expose
    int jumlah_transaksi_penjualan_jasa_service;
    double subtotal_transaksi_penjualan_jasa_service;
    String created_at, updated_at, deleted_at;

    // left join
    int id_pelanggan, id_cs, id_kasir, id_status_pengerjaan, id_cabang, kode_jasa_service;
    String kode_transaksi, nama_status_pengerjaan, nama_pelanggan, no_telepon_pelanggan,alamat_pelanggan;
    String nama_cs, role_cs, nama_kasir, role_kasir, nama_montir, role_montir, nama_jasa_service;
    String no_plat_kendaraan, merek_kendaraan, jenis_kendaraan;
    double harga_jasa_service;

    public TransaksiJasaServiceDAO(int id_transaksi, int id_montir, int id_jasa_service, int id_kendaraan, int jumlah_transaksi_penjualan_jasa_service, double subtotal_transaksi_penjualan_jasa_service) {
        this.id_transaksi = id_transaksi;
        this.id_montir = id_montir;
        this.id_jasa_service = id_jasa_service;
        this.id_kendaraan = id_kendaraan;
        this.jumlah_transaksi_penjualan_jasa_service = jumlah_transaksi_penjualan_jasa_service;
        this.subtotal_transaksi_penjualan_jasa_service = subtotal_transaksi_penjualan_jasa_service;
    }

    public TransaksiJasaServiceDAO(int id_jasa_service, int id_kendaraan, int jumlah_transaksi_penjualan_jasa_service, double subtotal_transaksi_penjualan_jasa_service) {
        this.id_jasa_service= id_jasa_service;
        this.id_kendaraan= id_kendaraan;
        this.jumlah_transaksi_penjualan_jasa_service = jumlah_transaksi_penjualan_jasa_service;
        this.subtotal_transaksi_penjualan_jasa_service = subtotal_transaksi_penjualan_jasa_service;
    }

    public int getId_transaksi_penjualan_jasa_service() {
        return id_transaksi_penjualan_jasa_service;
    }

    public void setId_transaksi_penjualan_jasa_service(int id_transaksi_penjualan_jasa_service) {
        this.id_transaksi_penjualan_jasa_service = id_transaksi_penjualan_jasa_service;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getId_montir() {
        return id_montir;
    }

    public void setId_montir(int id_montir) {
        this.id_montir = id_montir;
    }

    public int getId_jasa_service() {
        return id_jasa_service;
    }

    public void setId_jasa_service(int id_jasa_service) {
        this.id_jasa_service = id_jasa_service;
    }

    public int getId_kendaraan() {
        return id_kendaraan;
    }

    public void setId_kendaraan(int id_kendaraan) {
        this.id_kendaraan = id_kendaraan;
    }

    public int getJumlah_transaksi_penjualan_jasa_service() {
        return jumlah_transaksi_penjualan_jasa_service;
    }

    public void setJumlah_transaksi_penjualan_jasa_service(int jumlah_transaksi_penjualan_jasa_service) {
        this.jumlah_transaksi_penjualan_jasa_service = jumlah_transaksi_penjualan_jasa_service;
    }

    public double getSubtotal_transaksi_penjualan_jasa_service() {
        return subtotal_transaksi_penjualan_jasa_service;
    }

    public void setSubtotal_transaksi_penjualan_jasa_service(double subtotal_transaksi_penjualan_jasa_service) {
        this.subtotal_transaksi_penjualan_jasa_service = subtotal_transaksi_penjualan_jasa_service;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    // left join


    public int getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(int id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public int getId_cs() {
        return id_cs;
    }

    public void setId_cs(int id_cs) {
        this.id_cs = id_cs;
    }

    public int getId_kasir() {
        return id_kasir;
    }

    public void setId_kasir(int id_kasir) {
        this.id_kasir = id_kasir;
    }

    public int getId_status_pengerjaan() {
        return id_status_pengerjaan;
    }

    public void setId_status_pengerjaan(int id_status_pengerjaan) {
        this.id_status_pengerjaan = id_status_pengerjaan;
    }

    public int getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public int getKode_jasa_service() {
        return kode_jasa_service;
    }

    public void setKode_jasa_service(int kode_jasa_service) {
        this.kode_jasa_service = kode_jasa_service;
    }

    public String getKode_transaksi() {
        return kode_transaksi;
    }

    public void setKode_transaksi(String kode_transaksi) {
        this.kode_transaksi = kode_transaksi;
    }

    public String getNama_status_pengerjaan() {
        return nama_status_pengerjaan;
    }

    public void setNama_status_pengerjaan(String nama_status_pengerjaan) {
        this.nama_status_pengerjaan = nama_status_pengerjaan;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }

    public String getNo_telepon_pelanggan() {
        return no_telepon_pelanggan;
    }

    public void setNo_telepon_pelanggan(String no_telepon_pelanggan) {
        this.no_telepon_pelanggan = no_telepon_pelanggan;
    }

    public String getAlamat_pelanggan() {
        return alamat_pelanggan;
    }

    public void setAlamat_pelanggan(String alamat_pelanggan) {
        this.alamat_pelanggan = alamat_pelanggan;
    }

    public String getNama_cs() {
        return nama_cs;
    }

    public void setNama_cs(String nama_cs) {
        this.nama_cs = nama_cs;
    }

    public String getRole_cs() {
        return role_cs;
    }

    public void setRole_cs(String role_cs) {
        this.role_cs = role_cs;
    }

    public String getNama_kasir() {
        return nama_kasir;
    }

    public void setNama_kasir(String nama_kasir) {
        this.nama_kasir = nama_kasir;
    }

    public String getRole_kasir() {
        return role_kasir;
    }

    public void setRole_kasir(String role_kasir) {
        this.role_kasir = role_kasir;
    }

    public String getNama_montir() {
        return nama_montir;
    }

    public void setNama_montir(String nama_montir) {
        this.nama_montir = nama_montir;
    }

    public String getRole_montir() {
        return role_montir;
    }

    public void setRole_montir(String role_montir) {
        this.role_montir = role_montir;
    }

    public String getNama_jasa_service() {
        return nama_jasa_service;
    }

    public void setNama_jasa_service(String nama_jasa_service) {
        this.nama_jasa_service = nama_jasa_service;
    }

    public String getNo_plat_kendaraan() {
        return no_plat_kendaraan;
    }

    public void setNo_plat_kendaraan(String no_plat_kendaraan) {
        this.no_plat_kendaraan = no_plat_kendaraan;
    }

    public String getMerek_kendaraan() {
        return merek_kendaraan;
    }

    public void setMerek_kendaraan(String merek_kendaraan) {
        this.merek_kendaraan = merek_kendaraan;
    }

    public String getJenis_kendaraan() {
        return jenis_kendaraan;
    }

    public void setJenis_kendaraan(String jenis_kendaraan) {
        this.jenis_kendaraan = jenis_kendaraan;
    }

    public double getHarga_jasa_service() {
        return harga_jasa_service;
    }

    public void setHarga_jasa_service(double harga_jasa_service) {
        this.harga_jasa_service = harga_jasa_service;
    }
}
