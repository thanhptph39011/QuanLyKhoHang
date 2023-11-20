package com.example.quanlykhohang.Model;

public class CtHoaDon {
    private int maCthd;
    private int maSp;
    private int maHoaDon;
    private int soLuong;
    private int donGia;

    public CtHoaDon() {
    }

    public CtHoaDon(int maCthd, int maSp, int maHoaDon, int soLuong, int donGia) {
        this.maCthd = maCthd;
        this.maSp = maSp;
        this.maHoaDon = maHoaDon;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public int getMaCthd() {
        return maCthd;
    }

    public void setMaCthd(int maCthd) {
        this.maCthd = maCthd;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }
}
