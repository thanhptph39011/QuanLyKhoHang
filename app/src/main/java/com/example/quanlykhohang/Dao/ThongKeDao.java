package com.example.quanlykhohang.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlykhohang.DataBase.DbHelper;
import com.example.quanlykhohang.Model.HangTon;
import com.example.quanlykhohang.Model.ThongKe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ThongKeDao {
    private SQLiteDatabase db;
    private Context context;
    DbHelper dbHelper;

    public ThongKeDao(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public List<HangTon> thongKeSanPham() {
        List<HangTon> productStatisticsList = new ArrayList<>();
        String query = "SELECT SanPham.maLoai, TheLoai.tenLoai, SUM(SanPham.soLuong) AS soLuong, SUM(SanPham.soLuong * SanPham.gia) AS tongTien " +
                "FROM SanPham " +
                "JOIN TheLoai ON SanPham.maLoai = TheLoai.maLoai " +
                "GROUP BY SanPham.maLoai, TheLoai.tenLoai";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String maLoai = cursor.getString(cursor.getColumnIndex("maLoai"));
                String tenLoai = cursor.getString(cursor.getColumnIndex("tenLoai"));
                int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
                int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));

                HangTon productStatistics = new HangTon(maLoai, tenLoai, soLuong, tongTien);
                productStatisticsList.add(productStatistics);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productStatisticsList;
    }

    @SuppressLint("Range")
    public List<ThongKe> thongKeXuatTheoNgay() {
        List<ThongKe> danhSachThongKe = new ArrayList<>();

        // Lấy ngày hiện tại
        String ngayHienTai = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        // Truy vấn để thống kê số lượng hóa đơn xuất, số lượng sản phẩm và tổng tiền trong ngày hiện tại
        String query = "SELECT COUNT(*) AS soLuongHoaDon, SUM(CtHoaDon.soLuong) AS soLuong, SUM(CtHoaDon.donGia * CtHoaDon.soLuong) AS tongTien " +
                "FROM HoaDon INNER JOIN CtHoaDon ON HoaDon.maHoaDon = CtHoaDon.maHoaDon " +
                "WHERE HoaDon.loaiHoaDon = 1 AND HoaDon.ngayThang = ?";

        Cursor cursor = db.rawQuery(query, new String[]{ngayHienTai});

        if (cursor.moveToFirst()) {
            int soLuongHoaDon = cursor.getInt(cursor.getColumnIndex("soLuongHoaDon"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));

            ThongKe thongKe = new ThongKe();
            thongKe.setSoLuongHoaDon(soLuongHoaDon);
            thongKe.setSoLuong(soLuong);
            thongKe.setTongTien(tongTien);
            danhSachThongKe.add(thongKe);
        }

        cursor.close();
        db.close();

        return danhSachThongKe;
    }

    @SuppressLint("Range")
    public List<ThongKe> thongKeXuatTheoTuan() {
        List<ThongKe> danhSachThongKe = new ArrayList<>();

        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        String startOfWeek = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        calendar.add(Calendar.DATE, 6);
        String endOfWeek = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        // Truy vấn để thống kê số lượng sản phẩm và tổng tiền trong tuần
        String query = "SELECT COUNT(*) AS soLuongHoaDon, SUM(CtHoaDon.soLuong) AS soLuong, SUM(CtHoaDon.donGia*CtHoaDon.soLuong) AS tongTien " +
                "FROM HoaDon INNER JOIN CtHoaDon ON HoaDon.maHoaDon = CtHoaDon.maHoaDon " +
                " WHERE HoaDon.loaiHoaDon = 1 AND HoaDon.ngayThang >= ? AND HoaDon.ngayThang <= ?";
        Cursor cursor = db.rawQuery(query, new String[]{startOfWeek, endOfWeek});

        if (cursor.moveToFirst()) {
            int soLuongHoaDon = cursor.getInt(cursor.getColumnIndex("soLuongHoaDon"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));

            ThongKe thongKe = new ThongKe();
            thongKe.setSoLuongHoaDon(soLuongHoaDon);
            thongKe.setSoLuong(soLuong);
            thongKe.setTongTien(tongTien);
            danhSachThongKe.add(thongKe);
        }

        cursor.close();
        db.close();

        return danhSachThongKe;
    }

    @SuppressLint("Range")
    public List<ThongKe> thongKeXuatTheoThang() {
        List<ThongKe> danhSachThongKe = new ArrayList<>();

        // Lấy tháng và năm hiện tại
        Calendar cal = Calendar.getInstance();
        int thangHienTai = cal.get(Calendar.MONTH) + 1; // Tháng trong Java tính từ 0 đến 11
        int namHienTai = cal.get(Calendar.YEAR);

        // Truy vấn để thống kê số lượng sản phẩm và tổng tiền theo tháng hiện tại
        String query = "SELECT COUNT(*) AS soLuongHoaDon,SUM(CtHoaDon.soLuong) AS soLuong, SUM(CtHoaDon.donGia*CtHoaDon.soLuong) AS tongTien  " +
                "FROM HoaDon INNER JOIN CtHoaDon ON HoaDon.maHoaDon = CtHoaDon.maHoaDon " +
                " WHERE HoaDon.loaiHoaDon = 1 AND strftime('%m', HoaDon.ngayThang) = ? AND strftime('%Y', HoaDon.ngayThang) = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(thangHienTai), String.valueOf(namHienTai)});

        if (cursor.moveToFirst()) {
            int soLuongHoaDon = cursor.getInt(cursor.getColumnIndex("soLuongHoaDon"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));

            ThongKe thongKe = new ThongKe();
            thongKe.setSoLuongHoaDon(soLuongHoaDon);
            thongKe.setSoLuong(soLuong);
            thongKe.setTongTien(tongTien);
            danhSachThongKe.add(thongKe);
        }

        cursor.close();
        db.close();

        return danhSachThongKe;
    }

    @SuppressLint("Range")
    public List<ThongKe> thongKeXuatTheoNgayTuChon(String startDate, String endDate) {
        List<ThongKe> danhSachThongKe = new ArrayList<>();

        // Truy vấn để thống kê số lượng sản phẩm và tổng tiền trong khoảng thời gian nhập từ ngày đến ngày
        String query = "SELECT COUNT(*) AS soLuongHoaDon,SUM(CtHoaDon.soLuong) AS soLuong, SUM(CtHoaDon.donGia*CtHoaDon.soLuong) AS tongTien " +
                "FROM HoaDon INNER JOIN CtHoaDon ON HoaDon.maHoaDon = CtHoaDon.maHoaDon " +
                "WHERE HoaDon.loaiHoaDon = 1 AND HoaDon.ngayThang >= ? AND HoaDon.ngayThang <= ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});

        if (cursor.moveToFirst()) {
            int soLuongHoaDon = cursor.getInt(cursor.getColumnIndex("soLuongHoaDon"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));

            ThongKe thongKe = new ThongKe();
            thongKe.setSoLuongHoaDon(soLuongHoaDon);
            thongKe.setSoLuong(soLuong);
            thongKe.setTongTien(tongTien);
            danhSachThongKe.add(thongKe);
        }

        cursor.close();
        db.close();

        return danhSachThongKe;
    }

    @SuppressLint("Range")
    public List<ThongKe> thongKeNhapTheoNgay() {
        List<ThongKe> danhSachThongKe = new ArrayList<>();

        // Lấy ngày hiện tại
        String ngayHienTai = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Truy vấn để thống kê số lượng sản phẩm và tổng tiền theo ngày hiện tại
        String query = "SELECT COUNT(*) AS soLuongHoaDon, SUM(CtHoaDon.soLuong) AS soLuong, SUM(CtHoaDon.donGia*CtHoaDon.soLuong) AS tongTien " +
                "FROM HoaDon INNER JOIN CtHoaDon ON HoaDon.maHoaDon = CtHoaDon.maHoaDon " +
                " WHERE HoaDon.loaiHoaDon = 0 AND HoaDon.ngayThang = ? " +
                "GROUP BY HoaDon.ngayThang";
        Cursor cursor = db.rawQuery(query, new String[]{ngayHienTai});

        if (cursor.moveToFirst()) {
            int soLuongHoaDon = cursor.getInt(cursor.getColumnIndex("soLuongHoaDon"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));

            ThongKe thongKe = new ThongKe();
            thongKe.setSoLuongHoaDon(soLuongHoaDon);
            thongKe.setSoLuong(soLuong);
            thongKe.setTongTien(tongTien);
            danhSachThongKe.add(thongKe);
        }

        cursor.close();
        db.close();

        return danhSachThongKe;
    }

    @SuppressLint("Range")
    public List<ThongKe> thongKeNhapTheoTuan() {
        List<ThongKe> danhSachThongKe = new ArrayList<>();

        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        String startOfWeek = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        calendar.add(Calendar.DATE, 6);
        String endOfWeek = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        // Truy vấn để thống kê số lượng sản phẩm và tổng tiền trong tuần
        String query = "SELECT COUNT(*) AS soLuongHoaDon,SUM(CtHoaDon.soLuong) AS soLuong, SUM(CtHoaDon.donGia*CtHoaDon.soLuong) AS tongTien " +
                "FROM HoaDon INNER JOIN CtHoaDon ON HoaDon.maHoaDon = CtHoaDon.maHoaDon " +
                " WHERE HoaDon.loaiHoaDon = 0 AND HoaDon.ngayThang >= ? AND HoaDon.ngayThang <= ?";
        Cursor cursor = db.rawQuery(query, new String[]{startOfWeek, endOfWeek});

        if (cursor.moveToFirst()) {
            int soLuongHoaDon = cursor.getInt(cursor.getColumnIndex("soLuongHoaDon"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));

            ThongKe thongKe = new ThongKe();
            thongKe.setSoLuongHoaDon(soLuongHoaDon);
            thongKe.setSoLuong(soLuong);
            thongKe.setTongTien(tongTien);
            danhSachThongKe.add(thongKe);
        }

        cursor.close();
        db.close();

        return danhSachThongKe;
    }

    @SuppressLint("Range")
    public List<ThongKe> thongKeNhapTheoThang() {
        List<ThongKe> danhSachThongKe = new ArrayList<>();

        // Lấy tháng và năm hiện tại
        Calendar cal = Calendar.getInstance();
        int thangHienTai = cal.get(Calendar.MONTH) + 1; // Tháng trong Java tính từ 0 đến 11
        int namHienTai = cal.get(Calendar.YEAR);

        // Truy vấn để thống kê số lượng sản phẩm và tổng tiền theo tháng hiện tại
        String query = "SELECT COUNT(*) AS soLuongHoaDon,SUM(CtHoaDon.soLuong) AS soLuong, SUM(CtHoaDon.donGia*CtHoaDon.soLuong) AS tongTien  " +
                "FROM HoaDon INNER JOIN CtHoaDon ON HoaDon.maHoaDon = CtHoaDon.maHoaDon " +
                " WHERE HoaDon.loaiHoaDon = 0 AND strftime('%m', HoaDon.ngayThang) = ? AND strftime('%Y', HoaDon.ngayThang) = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(thangHienTai), String.valueOf(namHienTai)});

        if (cursor.moveToFirst()) {
            int soLuongHoaDon = cursor.getInt(cursor.getColumnIndex("soLuongHoaDon"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));

            ThongKe thongKe = new ThongKe();
            thongKe.setSoLuongHoaDon(soLuongHoaDon);
            thongKe.setSoLuong(soLuong);
            thongKe.setTongTien(tongTien);
            danhSachThongKe.add(thongKe);
        }

        cursor.close();
        db.close();

        return danhSachThongKe;
    }

    @SuppressLint("Range")
    public List<ThongKe> thongKeNhapTheoNgayTuChon(String startDate, String endDate) {
        List<ThongKe> danhSachThongKe = new ArrayList<>();

        // Truy vấn để thống kê số lượng sản phẩm và tổng tiền trong khoảng thời gian nhập từ ngày đến ngày
        String query = "SELECT COUNT(*) AS soLuongHoaDon,SUM(CtHoaDon.soLuong) AS soLuong, SUM(CtHoaDon.donGia*CtHoaDon.soLuong) AS tongTien " +
                "FROM HoaDon INNER JOIN CtHoaDon ON HoaDon.maHoaDon = CtHoaDon.maHoaDon " +
                "WHERE HoaDon.loaiHoaDon = 0 AND HoaDon.ngayThang >= ? AND HoaDon.ngayThang <= ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});

        if (cursor.moveToFirst()) {
            int soLuongHoaDon = cursor.getInt(cursor.getColumnIndex("soLuongHoaDon"));
            int soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
            int tongTien = cursor.getInt(cursor.getColumnIndex("tongTien"));

            ThongKe thongKe = new ThongKe();
            thongKe.setSoLuongHoaDon(soLuongHoaDon);
            thongKe.setSoLuong(soLuong);
            thongKe.setTongTien(tongTien);
            danhSachThongKe.add(thongKe);
        }

        cursor.close();
        db.close();

        return danhSachThongKe;
    }
}
