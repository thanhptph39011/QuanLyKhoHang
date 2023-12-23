package com.example.quanlykhohang.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlykhohang.DataBase.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    public int thongKeSanPhamXuat(Date startDate, Date endDate) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int soLuongXuat = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String start = sdf.format(startDate);
            String end = sdf.format(endDate);

            String query = "SELECT SUM(soLuong) FROM CtHoaDon " +
                    "JOIN HoaDon ON CtHoaDon.maHoaDon = HoaDon.maHoaDon " +
                    "WHERE HoaDon.loaiHoaDon = 1 AND HoaDon.ngayThang BETWEEN '" + start + "' AND '" + end + "'";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                soLuongXuat = cursor.getInt(0);
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("ThongKeDao", "Error: " + e.getMessage());
        } finally {
            db.close();
        }

        return soLuongXuat;
    }

    public int thongKeSanPhamNhap(Date startDate, Date endDate) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int soLuongNhap = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String start = sdf.format(startDate);
            String end = sdf.format(endDate);

            String query = "SELECT SUM(soLuong) FROM CtHoaDon " +
                    "JOIN HoaDon ON CtHoaDon.maHoaDon = HoaDon.maHoaDon " +
                    "WHERE HoaDon.loaiHoaDon = 0 AND HoaDon.ngayThang BETWEEN '" + start + "' AND '" + end + "'";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                soLuongNhap = cursor.getInt(0);
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("ThongKeDao", "Error: " + e.getMessage());
        } finally {
            db.close();
        }
        return soLuongNhap;
    }

    public int thongKeSanPhamTon(Date endDate) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int soLuongTon = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String end = sdf.format(endDate);

            String query = "SELECT SUM(soLuong) FROM SanPham WHERE maSp IN (SELECT maSp FROM CtHoaDon " +
                    "JOIN HoaDon ON CtHoaDon.maHoaDon = HoaDon.maHoaDon " +
                    "WHERE HoaDon.ngayThang <= '" + end + "')";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                soLuongTon = cursor.getInt(0);
            }

            cursor.close();
        } catch (Exception e) {
            Log.e("ThongKeDao", "Error: " + e.getMessage());
        } finally {
            db.close();
        }

        return soLuongTon;
    }
}
