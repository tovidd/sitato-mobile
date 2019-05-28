package com.toviddd.sitato.Pegawai.Area.DAO;

public class TransaksiDAO {

    int id_transaksi, id_pelanggan,id_cs, id_kasir, id_status_pengerjaan, id_cabang;
    double total_transaksi, diskon_transaksi, jumlah_uang_pembayaran_transaksi;
    String kode_transaksi, created_at, updated_at;

    // hasil leftjoin
    String nama_cs;
    String role_cs;
    String nama_kasir;
    String role_kasir;
    String nama_status_pengerjaan;
    String no_telepon_cabang;
    String nama_cabang;
    String alamat_cabang;

    public TransaksiDAO(int id_kasir, int id_status_pengerjaan, double total_transaksi, double diskon_transaksi, double jumlah_uang_pembayaran_transaksi) {
        this.id_kasir = id_kasir;
        this.id_status_pengerjaan = id_status_pengerjaan;
        this.total_transaksi = total_transaksi;
        this.diskon_transaksi = diskon_transaksi;
        this.jumlah_uang_pembayaran_transaksi = jumlah_uang_pembayaran_transaksi;
    }

    public TransaksiDAO(int id_transaksi, int id_pelanggan, int id_cs, int id_kasir, int id_status_pengerjaan, double total_transaksi, double diskon_transaksi, double jumlah_uang_pembayaran_transaksi) {
        this.id_transaksi = id_transaksi;
        this.id_pelanggan = id_pelanggan;
        this.id_cs = id_cs;
        this.id_kasir = id_kasir;
        this.id_status_pengerjaan = id_status_pengerjaan;
        this.total_transaksi = total_transaksi;
        this.diskon_transaksi = diskon_transaksi;
        this.jumlah_uang_pembayaran_transaksi = jumlah_uang_pembayaran_transaksi;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

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

    public double getTotal_transaksi() {
        return total_transaksi;
    }

    public void setTotal_transaksi(double total_transaksi) {
        this.total_transaksi = total_transaksi;
    }

    public double getDiskon_transaksi() {
        return diskon_transaksi;
    }

    public void setDiskon_transaksi(double diskon_transaksi) {
        this.diskon_transaksi = diskon_transaksi;
    }

    public double getJumlah_uang_pembayaran_transaksi() {
        return jumlah_uang_pembayaran_transaksi;
    }

    public void setJumlah_uang_pembayaran_transaksi(double jumlah_uang_pembayaran_transaksi) {
        this.jumlah_uang_pembayaran_transaksi = jumlah_uang_pembayaran_transaksi;
    }

    public String getKode_transaksi() {
        return kode_transaksi;
    }

    public void setKode_transaksi(String kode_transaksi) {
        this.kode_transaksi = kode_transaksi;
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

    // leftjoin

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

    public String getNama_status_pengerjaan() {
        return nama_status_pengerjaan;
    }

    public void setNama_status_pengerjaan(String nama_status_pengerjaan) {
        this.nama_status_pengerjaan = nama_status_pengerjaan;
    }

    public String getNo_telepon_cabang() {
        return no_telepon_cabang;
    }

    public void setNo_telepon_cabang(String no_telepon_cabang) {
        this.no_telepon_cabang = no_telepon_cabang;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }

    public String getAlamat_cabang() {
        return alamat_cabang;
    }

    public void setAlamat_cabang(String alamat_cabang) {
        this.alamat_cabang = alamat_cabang;
    }
}
