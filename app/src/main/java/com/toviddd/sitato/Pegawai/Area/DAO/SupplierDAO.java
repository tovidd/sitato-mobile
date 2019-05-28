package com.toviddd.sitato.Pegawai.Area.DAO;

public class SupplierDAO {
    int id_supplier;
    String nama_supplier, no_telepon_supplier, alamat_supplier, nama_sales;
    String created_at, updated_at, deleted_at;

    public SupplierDAO(String nama_supplier, String no_telepon_supplier, String alamat_supplier, String nama_sales) {
        this.nama_supplier= nama_supplier;
        this.no_telepon_supplier = no_telepon_supplier;
        this.alamat_supplier = alamat_supplier;
        this.nama_sales = nama_sales;
    }

    public SupplierDAO(int id_supplier, String nama_supplier, String no_telepon_supplier, String alamat_supplier, String nama_sales) {
        this.id_supplier = id_supplier;
        this.nama_supplier= nama_supplier;
        this.no_telepon_supplier = no_telepon_supplier;
        this.alamat_supplier = alamat_supplier;
        this.nama_sales = nama_sales;
    }

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getNo_telepon_supplier() {
        return no_telepon_supplier;
    }

    public void setNo_telepon_supplier(String no_telepon_supplier) {
        this.no_telepon_supplier = no_telepon_supplier;
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
}
