package com.toviddd.sitato.Pegawai.Area.DAO;

public class TransaksiPengadaanDetilDAO {

    int id_detil_pengadaan_sparepart, id_pengadaan_sparepart, id_sparepart, jumlah_beli_detil_pengadaan_sparepart;
    String satuan_detil_pengadaan_sparepart, created_at, updated_at, deleted_at;
    double subtotal_detil_pengadaan_sparepart;

    // left join
    int id_cabang, id_supplier, stok_sparepart, stok_minimum_sparepart;
    String tanggal_pengadaan_sparepart, kode_sparepart, nama_sparepart, jenis_sparepart, rak_sparepart;
    String merek_kendaraan_sparepart, jenis_kendaraan_sparepart, gambar_sparepart;
    double harga_beli_sparepart, harga_jual_sparepart;

    public TransaksiPengadaanDetilDAO(int jumlah_beli_detil_pengadaan_sparepart, double subtotal_detil_pengadaan_sparepart) {
        this.jumlah_beli_detil_pengadaan_sparepart = jumlah_beli_detil_pengadaan_sparepart;
        this.subtotal_detil_pengadaan_sparepart = subtotal_detil_pengadaan_sparepart;
    }

    public int getId_detil_pengadaan_sparepart() {
        return id_detil_pengadaan_sparepart;
    }

    public void setId_detil_pengadaan_sparepart(int id_detil_pengadaan_sparepart) {
        this.id_detil_pengadaan_sparepart = id_detil_pengadaan_sparepart;
    }

    public int getId_pengadaan_sparepart() {
        return id_pengadaan_sparepart;
    }

    public void setId_pengadaan_sparepart(int id_pengadaan_sparepart) {
        this.id_pengadaan_sparepart = id_pengadaan_sparepart;
    }

    public int getId_sparepart() {
        return id_sparepart;
    }

    public void setId_sparepart(int id_sparepart) {
        this.id_sparepart = id_sparepart;
    }

    public int getJumlah_beli_detil_pengadaan_sparepart() {
        return jumlah_beli_detil_pengadaan_sparepart;
    }

    public void setJumlah_beli_detil_pengadaan_sparepart(int jumlah_beli_detil_pengadaan_sparepart) {
        this.jumlah_beli_detil_pengadaan_sparepart = jumlah_beli_detil_pengadaan_sparepart;
    }

    public String getSatuan_detil_pengadaan_sparepart() {
        return satuan_detil_pengadaan_sparepart;
    }

    public void setSatuan_detil_pengadaan_sparepart(String satuan_detil_pengadaan_sparepart) {
        this.satuan_detil_pengadaan_sparepart = satuan_detil_pengadaan_sparepart;
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

    public double getSubtotal_detil_pengadaan_sparepart() {
        return subtotal_detil_pengadaan_sparepart;
    }

    public void setSubtotal_detil_pengadaan_sparepart(double subtotal_detil_pengadaan_sparepart) {
        this.subtotal_detil_pengadaan_sparepart = subtotal_detil_pengadaan_sparepart;
    }



    // left join


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

    public String getTanggal_pengadaan_sparepart() {
        return tanggal_pengadaan_sparepart;
    }

    public void setTanggal_pengadaan_sparepart(String tanggal_pengadaan_sparepart) {
        this.tanggal_pengadaan_sparepart = tanggal_pengadaan_sparepart;
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

    public String getRak_sparepart() {
        return rak_sparepart;
    }

    public void setRak_sparepart(String rak_sparepart) {
        this.rak_sparepart = rak_sparepart;
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
}
