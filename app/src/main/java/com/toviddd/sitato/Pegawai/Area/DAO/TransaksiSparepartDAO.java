package com.toviddd.sitato.Pegawai.Area.DAO;

public class TransaksiSparepartDAO {

    int id_transaksi_penjualan_sparepart;
    int id_transaksi;
    int id_sparepart;
    int jumlah_transaksi_penjualan_sparepart;
    double subtotal_transaksi_penjualan_sparepart;
    String created_at, updated_at;

    // left join
    String nama_sparepart, kode_sparepart, jenis_sparepart;
    float harga_jual_sparepart;


    public TransaksiSparepartDAO(int id_transaksi, int id_sparepart, int jumlah_transaksi_penjualan_sparepart, double subtotal_transaksi_penjualan_sparepart) {
        this.id_transaksi = id_transaksi;
        this.id_sparepart = id_sparepart;
        this.jumlah_transaksi_penjualan_sparepart = jumlah_transaksi_penjualan_sparepart;
        this.subtotal_transaksi_penjualan_sparepart = subtotal_transaksi_penjualan_sparepart;
    }

    public TransaksiSparepartDAO(int jumlah_transaksi_penjualan_sparepart, double subtotal_transaksi_penjualan_sparepart) {
        this.jumlah_transaksi_penjualan_sparepart = jumlah_transaksi_penjualan_sparepart;
        this.subtotal_transaksi_penjualan_sparepart = subtotal_transaksi_penjualan_sparepart;
    }

    public int getId_transaksi_penjualan_sparepart() {
        return id_transaksi_penjualan_sparepart;
    }

    public void setId_transaksi_penjualan_sparepart(int id_transaksi_penjualan_sparepart) {
        this.id_transaksi_penjualan_sparepart = id_transaksi_penjualan_sparepart;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getId_sparepart() {
        return id_sparepart;
    }

    public void setId_sparepart(int id_sparepart) {
        this.id_sparepart = id_sparepart;
    }

    public int getJumlah_transaksi_penjualan_sparepart() {
        return jumlah_transaksi_penjualan_sparepart;
    }

    public void setJumlah_transaksi_penjualan_sparepart(int jumlah_transaksi_penjualan_sparepart) {
        this.jumlah_transaksi_penjualan_sparepart = jumlah_transaksi_penjualan_sparepart;
    }

    public double getSubtotal_transaksi_penjualan_sparepart() {
        return subtotal_transaksi_penjualan_sparepart;
    }

    public void setSubtotal_transaksi_penjualan_sparepart(double subtotal_transaksi_penjualan_sparepart) {
        this.subtotal_transaksi_penjualan_sparepart = subtotal_transaksi_penjualan_sparepart;
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

    // left join

    public String getNama_sparepart() {
        return nama_sparepart;
    }

    public void setNama_sparepart(String nama_sparepart) {
        this.nama_sparepart = nama_sparepart;
    }

    public String getKode_sparepart() {
        return kode_sparepart;
    }

    public void setKode_sparepart(String kode_sparepart) {
        this.kode_sparepart = kode_sparepart;
    }

    public String getJenis_sparepart() {
        return jenis_sparepart;
    }

    public void setJenis_sparepart(String jenis_sparepart) {
        this.jenis_sparepart = jenis_sparepart;
    }

    public float getHarga_jual_sparepart() {
        return harga_jual_sparepart;
    }

    public void setHarga_jual_sparepart(float harga_jual_sparepart) {
        this.harga_jual_sparepart = harga_jual_sparepart;
    }
}
