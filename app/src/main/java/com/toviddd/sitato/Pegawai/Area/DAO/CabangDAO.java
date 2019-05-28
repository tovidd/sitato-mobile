package com.toviddd.sitato.Pegawai.Area.DAO;

public class CabangDAO {
    int id_cabang;
    String nama_cabang, no_telepon_cabang, alamat_cabang;
    String created_at, updated_at, deleted_at;

    public CabangDAO(String nama_cabang, String no_telepon_cabang, String alamat_cabang) {
        this.nama_cabang = nama_cabang;
        this.no_telepon_cabang = no_telepon_cabang;
        this.alamat_cabang = alamat_cabang;
    }

    public CabangDAO(int id_cabang, String nama_cabang, String no_telepon_cabang, String alamat_cabang, String created_at) {
        this.id_cabang = id_cabang;
        this.nama_cabang = nama_cabang;
        this.no_telepon_cabang = no_telepon_cabang;
        this.alamat_cabang = alamat_cabang;
        this.created_at= created_at;
    }

    public int getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getNama_cabang() {
        return nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }

    public String getNo_telepon_cabang() {
        return no_telepon_cabang;
    }

    public void setNo_telepon_cabang(String no_telepon_cabang) {
        this.no_telepon_cabang = no_telepon_cabang;
    }

    public String getAlamat_cabang() {
        return alamat_cabang;
    }

    public void setAlamat_cabang(String alamat_cabang) {
        this.alamat_cabang = alamat_cabang;
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
