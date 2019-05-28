package com.toviddd.sitato.Pegawai.Area.DAO;

public class PelangganDAO {
    int id_pelanggan;
    String nama_pelanggan, no_telepon_pelanggan, alamat_pelanggan;
    String created_at, updated_at, deleted_at;

    public PelangganDAO(String nama_pelanggan, String no_telepon_pelanggan, String alamat_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
        this.no_telepon_pelanggan = no_telepon_pelanggan;
        this.alamat_pelanggan = alamat_pelanggan;
    }

    public PelangganDAO(int id_pelanggan, String nama_pelanggan, String no_telepon_pelanggan, String alamat_pelanggan, String created_at) {
        this.id_pelanggan = id_pelanggan;
        this.nama_pelanggan = nama_pelanggan;
        this.no_telepon_pelanggan = no_telepon_pelanggan;
        this.alamat_pelanggan = alamat_pelanggan;
        this.created_at = created_at;
    }

    public int getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(int id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
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
