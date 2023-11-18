package com.example.quanlykhohang.Model;

public class nguoiDung {
    private int maNguoiDung;
    private String userName;
    private String passWord;
    private String fullName;
    private String email;

    public nguoiDung() {
    }

    public nguoiDung(String email) {
        this.email = email;
    }

    public nguoiDung(int maNguoiDung, String userName, String passWord, String fullName) {
        this.maNguoiDung = maNguoiDung;
        this.userName = userName;
        this.passWord = passWord;
        this.fullName = fullName;
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
