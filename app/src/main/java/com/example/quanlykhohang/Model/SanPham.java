package com.example.quanlykhohang.Model;

public class SanPham {
    private int maSp;
    private int maLoai;
    private String tenSp;
    private int gia;
    private int soLuong;
    private String moTa;

    public SanPham() {
    }

    public SanPham(int maSp, int maLoai, String tenSp, int gia, int soLuong, String moTa) {
        this.maSp = maSp;
        this.maLoai = maLoai;
        this.tenSp = tenSp;
        this.gia = gia;
        this.soLuong = soLuong;
        this.moTa = moTa;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
