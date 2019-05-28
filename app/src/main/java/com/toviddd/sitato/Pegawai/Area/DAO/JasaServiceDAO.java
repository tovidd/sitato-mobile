package com.toviddd.sitato.Pegawai.Area.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JasaServiceDAO {

    @SerializedName("id_jasa_service")
    @Expose
    int id_jasa_service;

    @SerializedName("kode_jasa_service")
    @Expose
    String kodeJasaService;

    @SerializedName("nama_jasa_service")
    @Expose
    String namaJasaService;

    @SerializedName("harga_jasa_service")
    @Expose
    double hargaJasaService;

    String created_at, updated_at, deleted_at;

    public JasaServiceDAO(String kodeJasaService, String namaJasaService, double hargaJasaService) {
        this.kodeJasaService = kodeJasaService;
        this.namaJasaService = namaJasaService;
        this.hargaJasaService = hargaJasaService;
    }

    public JasaServiceDAO() {
    }

    public int getId_jasa_service() {
        return id_jasa_service;
    }

    public void setId_jasa_service(int id_jasa_service) {
        this.id_jasa_service = id_jasa_service;
    }

    public String getKodeJasaService() {
        return kodeJasaService;
    }

    public void setKodeJasaService(String kodeJasaService) {
        this.kodeJasaService = kodeJasaService;
    }

    public String getNamaJasaService() {
        return namaJasaService;
    }

    public void setNamaJasaService(String namaJasaService) {
        this.namaJasaService = namaJasaService;
    }

    public double getHargaJasaService() {
        return hargaJasaService;
    }

    public void setHargaJasaService(double hargaJasaService) {
        this.hargaJasaService = hargaJasaService;
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
