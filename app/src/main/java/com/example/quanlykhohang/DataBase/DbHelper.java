package com.example.quanlykhohang.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String Db_name = "KhoHang";

    public DbHelper(@Nullable Context context) {
        super(context, Db_name, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//Bảng người dùng
        String createTableUser = "create table User(maUser integer primary key autoincrement, userName text not null, passWord text not null, fullName text not null, email text not null )";
        db.execSQL(createTableUser);
        db.execSQL("insert into User(userName,passWord,fullName,email) values('thukho1','12345','Pham Thanh','thanh123@gmail.com')");
//Bảng  thể loại
        String createTableTheLoai = "create table TheLoai(maLoai integer primary key autoincrement,tenLoai text not null )";
        db.execSQL(createTableTheLoai);
db.execSQL("insert into TheLoai(tenLoai) values('Hang Nhap Khau')");
        //Bảng sản phẩm
        String createTableSanPham = "create table SanPham(maSp integer primary key autoincrement, maLoai integer references TheLoai(maLoai),tenSp text not null, gia integer not null, soLuong integer not null, moTa text not null)";
        db.execSQL(createTableSanPham);
        db.execSQL("insert into SanPham(maLoai,tenSp,gia,soLuong,moTa) values(1,'Tủ Lạnh',20000,2,'Beatiful')");
        //Bảng hoá đơn
        String createTableHoaDon = "create table HoaDon(maHoaDon integer primary key autoincrement, soHoaDon text not null, maUser integer references User(maUser), loaiHoaDon integer not null, ngayThang date not null,xacNhanHoaDon int not null)";
        db.execSQL(createTableHoaDon);
        //Bảng CtHoaDon
        String createTableCtHoaDon = "create table CtHoaDon( maCtHd integer primary key autoincrement,maSp REFERENCES SanPham(maSp), maHoaDon REFERENCES HoaDon(maHoaDon),soLuong integer not null,donGia integet not null  ) ";
        db.execSQL(createTableCtHoaDon);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableUser = "drop table if exists User";
        db.execSQL(dropTableUser);
        String dropTableTheLoai = "drop table if exists TheLoai";
        db.execSQL(dropTableTheLoai);
        String dropTableSanPham = "drop table if exists SanPham";
        db.execSQL(dropTableSanPham);
        String dropTableHoaDon = "drop table if exists HoaDon";
        db.execSQL(dropTableHoaDon);
        String dropTableCtHoaDon = "drop table if exists CtHoaDon";
        db.execSQL(dropTableCtHoaDon);
        onCreate(db);
    }
}
