package com.example.quanlykhohang.Model;

public class ThongKe {
int soLuongHoaDon,soLuong, tongTien;

    public ThongKe() {
    }

    public ThongKe(int soLuong, int tongTien) {
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public ThongKe(int soLuongHoaDon, int soLuong, int tongTien) {
        this.soLuongHoaDon = soLuongHoaDon;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public int getSoLuongHoaDon() {
        return soLuongHoaDon;
    }

    public void setSoLuongHoaDon(int soLuongHoaDon) {
        this.soLuongHoaDon = soLuongHoaDon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
