package com.example.quanlykhohang.Model;

public class ThongKe {
int soLuong, tongTien;

    public ThongKe() {
    }

    public ThongKe(int soLuong, int tongTien) {
        this.soLuong = soLuong;
        this.tongTien = tongTien;
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
