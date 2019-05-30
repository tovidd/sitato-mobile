package com.toviddd.sitato.Pegawai.Area.DAO;

public class TransaksiPengadaanDAO {

    int id_pengadaan_sparepart, id_cabang, id_supplier;
    String tanggal_pengadaan_sparepart, created_at, updated_at, deleted_at;

    // left join
    String no_telepon_cabang, nama_cabang, alamat_cabang, no_telepon_supplier, nama_supplier, alamat_supplier, nama_sales;

    public TransaksiPengadaanDAO(int id_supplier, String tanggal_pengadaan_sparepart) {
        this.id_supplier = id_supplier;
        this.tanggal_pengadaan_sparepart = tanggal_pengadaan_sparepart;
    }

    public int getId_pengadaan_sparepart() {
        return id_pengadaan_sparepart;
    }

    public void setId_pengadaan_sparepart(int id_pengadaan_sparepart) {
        this.id_pengadaan_sparepart = id_pengadaan_sparepart;
    }

    public int getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getTanggal_pengadaan_sparepart() {
        return tanggal_pengadaan_sparepart;
    }

    public void setTanggal_pengadaan_sparepart(String tanggal_pengadaan_sparepart) {
        this.tanggal_pengadaan_sparepart = tanggal_pengadaan_sparepart;
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

    public String getNo_telepon_supplier() {
        return no_telepon_supplier;
    }

    public void setNo_telepon_supplier(String no_telepon_supplier) {
        this.no_telepon_supplier = no_telepon_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getAlamat_supplier() {
        return alamat_supplier;
    }

    public void setAlamat_supplier(String alamat_supplier) {
        this.alamat_supplier = alamat_supplier;
    }

    public String getNama_sales() {
        return nama_sales;
    }

    public void setNama_sales(String nama_sales) {
        this.nama_sales = nama_sales;
    }
}
