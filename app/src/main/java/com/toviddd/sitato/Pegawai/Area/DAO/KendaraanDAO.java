package com.toviddd.sitato.Pegawai.Area.DAO;

public class KendaraanDAO {
    int id_kendaraan, id_pelanggan;
    String no_plat_kendaraan, merek_kendaraan, jenis_kendaraan;
    String created_at, updated_at, deleted_at;

    // left join
    String nama_pelanggan;

    public KendaraanDAO(int id_pelanggan, String no_plat_kendaraan, String merek_kendaraan, String jenis_kendaraan) {
        this.id_pelanggan= id_pelanggan;
        this.no_plat_kendaraan = no_plat_kendaraan;
        this.merek_kendaraan = merek_kendaraan;
        this.jenis_kendaraan = jenis_kendaraan;
    }

    public KendaraanDAO(int id_kendaraan, int id_pelanggan, String no_plat_kendaraan, String merek_kendaraan, String jenis_kendaraan, String created_at) {
        this.id_kendaraan = id_kendaraan;
        this.id_pelanggan= id_pelanggan;
        this.no_plat_kendaraan = no_plat_kendaraan;
        this.merek_kendaraan = merek_kendaraan;
        this.jenis_kendaraan = jenis_kendaraan;
        this.created_at = created_at;
    }

    public int getId_kendaraan() {
        return id_kendaraan;
    }

    public void setId_kendaraan(int id_kendaraan) {
        this.id_kendaraan = id_kendaraan;
    }

    public int getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(int id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
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

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public void setNama_pelanggan(String nama_pelanggan) {
        this.nama_pelanggan = nama_pelanggan;
    }
}
