package com.toviddd.sitato.DAO;

import java.util.List;

public class PegawaiDAO {
    int id_pegawai;
    int id_role;
    int id_cabang;
    String nama_pegawai;
    String no_telepon_pegawai;
    String alamat_pegawai;
    double gaji_pegawai;
    String username_pegawai;
    String sandi_pegawai;
    String created_at;
    String updated_at;

    public PegawaiDAO(int id_pegawai, int id_role, int id_cabang, String nama_pegawai, String no_telepon_pegawai, String alamat_pegawai, double gaji_pegawai, String username_pegawai, String sandi_pegawai, String created_at, String updated_at) {
        this.id_pegawai = id_pegawai;
        this.id_role = id_role;
        this.id_cabang = id_cabang;
        this.nama_pegawai = nama_pegawai;
        this.no_telepon_pegawai = no_telepon_pegawai;
        this.alamat_pegawai = alamat_pegawai;
        this.gaji_pegawai = gaji_pegawai;
        this.username_pegawai = username_pegawai;
        this.sandi_pegawai = sandi_pegawai;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public PegawaiDAO(int id_pegawai, int id_role) {
        this.id_pegawai = id_pegawai;
        this.id_role = id_role;
    }

    //getter and setter is below

    public int getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(int id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public int getId_cabang() {
        return id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getNo_telepon_pegawai() {
        return no_telepon_pegawai;
    }

    public void setNo_telepon_pegawai(String no_telepon_pegawai) {
        this.no_telepon_pegawai = no_telepon_pegawai;
    }

    public String getAlamat_pegawai() {
        return alamat_pegawai;
    }

    public void setAlamat_pegawai(String alamat_pegawai) {
        this.alamat_pegawai = alamat_pegawai;
    }

    public double getGaji_pegawai() {
        return gaji_pegawai;
    }

    public void setGaji_pegawai(double gaji_pegawai) {
        this.gaji_pegawai = gaji_pegawai;
    }

    public String getUsername_pegawai() {
        return username_pegawai;
    }

    public void setUsername_pegawai(String username_pegawai) {
        this.username_pegawai = username_pegawai;
    }

    public String getSandi_pegawai() {
        return sandi_pegawai;
    }

    public void setSandi_pegawai(String sandi_pegawai) {
        this.sandi_pegawai = sandi_pegawai;
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
}
