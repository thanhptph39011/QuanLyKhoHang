package com.example.quanlykhohang.Model;

import java.util.Date;

public class HoaDon {
private int maHd;
private String soHoaDon;
private String maUser;
private int loaiHoaDon;
private Date ngay;
private int xacNhanHd;

    public HoaDon() {
    }

    public HoaDon(int maHd, String soHoaDon, String maUser, int loaiHoaDon, Date ngay,int xacNhanHd) {
        this.maHd = maHd;
        this.soHoaDon = soHoaDon;
        this.maUser = maUser;
        this.loaiHoaDon = loaiHoaDon;
        this.ngay = ngay;
        this.xacNhanHd=xacNhanHd;
    }

    public int getXacNhanHd() {
        return xacNhanHd;
    }

    public void setXacNhanHd(int xacNhanHd) {
        this.xacNhanHd = xacNhanHd;
    }

    public int getMaHd() {
        return maHd;
    }

    public void setMaHd(int maHd) {
        this.maHd = maHd;
    }

    public String getSoHoaDon() {
        return soHoaDon;
    }

    public void setSoHoaDon(String soHoaDon) {
        this.soHoaDon = soHoaDon;
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
    }

    public int getLoaiHoaDon() {
        return loaiHoaDon;
    }

    public void setLoaiHoaDon(int loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }
}
