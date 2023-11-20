package com.example.quanlykhohang.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlykhohang.DataBase.DbHelper;
import com.example.quanlykhohang.Model.HoaDon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDao {
    private SQLiteDatabase db;

    public HoaDonDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();
    }

    public List<HoaDon> getAll() {
        String sql = "Select * from HoaDon";
        return getData(sql);
    }

    public boolean insertHoaDon(HoaDon hd) {
        ContentValues values = new ContentValues();
        values.put("soHoaDon", hd.getSoHoaDon());
        values.put("maUser", hd.getMaUser());
        values.put("loaiHoaDon", hd.getLoaiHoaDon());
        values.put("ngayThang", hd.getNgay().getTime());
        long row = db.insert("HoaDon", null, values);
        return (row > 0);
    }

    public boolean updateHoaDon(HoaDon hd) {
        ContentValues values = new ContentValues();
        values.put("soHoaDon", hd.getSoHoaDon());
        values.put("maUser", hd.getMaUser());
        values.put("loaiHoaDon", hd.getLoaiHoaDon());
        values.put("ngayThang", hd.getNgay().getTime());
        long row = db.update("HoaDon", values, "maHoaDon=?", new String[]{String.valueOf(hd.getMaHd())});
        return (row > 0);
    }

    public boolean deleteHoaDon(int maHd) {
        long row = db.delete("HoaDon", "maHoaDon=?", new String[]{String.valueOf(maHd)});
        return (row > 0);
    }

    private List<HoaDon> getData(String sql, String... selectionArgs) {
        List<HoaDon> list = new ArrayList<HoaDon>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            HoaDon hd = new HoaDon();
            hd.setMaHd(c.getInt(0));
            hd.setSoHoaDon(c.getString(1));
            hd.setMaUser(c.getString(2));
            hd.setLoaiHoaDon(c.getInt(3));
            hd.setNgay(new Date(c.getLong(4)));
            list.add(hd);
        }
        return list;
    }
}
