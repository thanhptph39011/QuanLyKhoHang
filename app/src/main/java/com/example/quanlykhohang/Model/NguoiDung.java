package com.example.quanlykhohang.Model;

public class NguoiDung {
    private int maNguoiDung;
    private String userName;
    private String passWord;
    private String fullName;
    private String email;

    public NguoiDung() {
    }

    public NguoiDung(String email) {
        this.email = email;
    }

    public NguoiDung(String userName, String passWord, String fullName,String email) {
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
        this.email = email;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
