package com.toviddd.sitato.Pegawai.Area.DAO;

public class SparepartDAO {
    int id_sparepart;
    //@SerializedName("kode_sparepart")
    String kode_sparepart;
    //@SerializedName("nama_sparepart")
    String nama_sparepart;
    //@SerializedName("jenis_sparepart")
    String jenis_sparepart;
    //@SerializedName("stok_sparepart")
    int stok_sparepart;
    //@SerializedName("stok_minimum_sparepart")
    int stok_minimum_sparepart;
    //@SerializedName("rak_sparepart")
    String rak_sparepart;
    //@SerializedName("harga_beli_sparepart")
    double harga_beli_sparepart;
    //@SerializedName("harga_jual_sparepart")
    double harga_jual_sparepart;
    //@SerializedName("merek_kendaraan_sparepart")
    String merek_kendaraan_sparepart;
    //@SerializedName("jenis_kendaraan_sparepart")
    String jenis_kendaraan_sparepart;
    //@SerializedName("gambar_sparepart")
    String gambar_sparepart;
    //@SerializedName("created_at")
    String created_at;
    //@SerializedName("updated_at")
    String updated_at;
    String deleted_at;

    public SparepartDAO(int id_sparepart, String kode_sparepart, String nama_sparepart, String jenis_sparepart, int stok_sparepart, int stok_minimum_sparepart, String rak_sparepart, double harga_beli_sparepart, double harga_jual_sparepart, String merek_kendaraan_sparepart, String jenis_kendaraan_sparepart, String gambar_sparepart) {
        this.id_sparepart= id_sparepart;
        this.kode_sparepart = kode_sparepart;
        this.nama_sparepart = nama_sparepart;
        this.jenis_sparepart = jenis_sparepart;
        this.stok_sparepart = stok_sparepart;
        this.stok_minimum_sparepart = stok_minimum_sparepart;
        this.rak_sparepart = rak_sparepart;
        this.harga_beli_sparepart = harga_beli_sparepart;
        this.harga_jual_sparepart = harga_jual_sparepart;
        this.merek_kendaraan_sparepart = merek_kendaraan_sparepart;
        this.jenis_kendaraan_sparepart = jenis_kendaraan_sparepart;
        this.gambar_sparepart= gambar_sparepart;
        this.created_at= created_at;
        this.updated_at= updated_at;
        this.deleted_at= deleted_at;
    }

    public SparepartDAO(String kode_sparepart, String nama_sparepart, String jenis_sparepart, int stok_sparepart, int stok_minimum_sparepart, String rak_sparepart, double harga_beli_sparepart, double harga_jual_sparepart, String merek_kendaraan_sparepart, String jenis_kendaraan_sparepart, String gambar_sparepart) {
        this.kode_sparepart = kode_sparepart;
        this.nama_sparepart = nama_sparepart;
        this.jenis_sparepart = jenis_sparepart;
        this.stok_sparepart = stok_sparepart;
        this.stok_minimum_sparepart = stok_minimum_sparepart;
        this.rak_sparepart = rak_sparepart;
        this.harga_beli_sparepart = harga_beli_sparepart;
        this.harga_jual_sparepart = harga_jual_sparepart;
        this.merek_kendaraan_sparepart = merek_kendaraan_sparepart;
        this.jenis_kendaraan_sparepart = jenis_kendaraan_sparepart;
        this.gambar_sparepart = gambar_sparepart;
    }

    public int getId_sparepart() {
        return id_sparepart;
    }

    public void setId_sparepart(int id_sparepart) {
        this.id_sparepart = id_sparepart;
    }

    public String getKode_sparepart() {
        return kode_sparepart;
    }

    public void setKode_sparepart(String kode_sparepart) {
        this.kode_sparepart = kode_sparepart;
    }

    public String getNama_sparepart() {
        return nama_sparepart;
    }

    public void setNama_sparepart(String nama_sparepart) {
        this.nama_sparepart = nama_sparepart;
    }

    public String getJenis_sparepart() {
        return jenis_sparepart;
    }

    public void setJenis_sparepart(String jenis_sparepart) {
        this.jenis_sparepart = jenis_sparepart;
    }

    public int getStok_sparepart() {
        return stok_sparepart;
    }

    public void setStok_sparepart(int stok_sparepart) {
        this.stok_sparepart = stok_sparepart;
    }

    public int getStok_minimum_sparepart() {
        return stok_minimum_sparepart;
    }

    public void setStok_minimum_sparepart(int stok_minimum_sparepart) {
        this.stok_minimum_sparepart = stok_minimum_sparepart;
    }

    public String getRak_sparepart() {
        return rak_sparepart;
    }

    public void setRak_sparepart(String rak_sparepart) {
        this.rak_sparepart = rak_sparepart;
    }

    public double getHarga_beli_sparepart() {
        return harga_beli_sparepart;
    }

    public void setHarga_beli_sparepart(double harga_beli_sparepart) {
        this.harga_beli_sparepart = harga_beli_sparepart;
    }

    public double getHarga_jual_sparepart() {
        return harga_jual_sparepart;
    }

    public void setHarga_jual_sparepart(double harga_jual_sparepart) {
        this.harga_jual_sparepart = harga_jual_sparepart;
    }

    public String getMerek_kendaraan_sparepart() {
        return merek_kendaraan_sparepart;
    }

    public void setMerek_kendaraan_sparepart(String merek_kendaraan_sparepart) {
        this.merek_kendaraan_sparepart = merek_kendaraan_sparepart;
    }

    public String getJenis_kendaraan_sparepart() {
        return jenis_kendaraan_sparepart;
    }

    public void setJenis_kendaraan_sparepart(String jenis_kendaraan_sparepart) {
        this.jenis_kendaraan_sparepart = jenis_kendaraan_sparepart;
    }

    public String getGambar_sparepart() {
        return gambar_sparepart;
    }

    public void setGambar_sparepart(String gambar_sparepart) {
        this.gambar_sparepart = gambar_sparepart;
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
