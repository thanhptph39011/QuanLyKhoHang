package com.example.quanlykhohang.Model;

public class HangTon {
    String maSp;
    int soLuongTon,tienTonKho;

    public HangTon() {
    }

    public HangTon(String maSp, int soLuongTon, int tienTonKho) {
        this.maSp = maSp;
        this.soLuongTon = soLuongTon;
        this.tienTonKho = tienTonKho;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getTienTonKho() {
        return tienTonKho;
    }

    public void setTienTonKho(int tienTonKho) {
        this.tienTonKho = tienTonKho;
    }
}
