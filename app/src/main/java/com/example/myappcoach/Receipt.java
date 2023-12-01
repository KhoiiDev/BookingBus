package com.example.myappcoach;

public class Receipt {
    private String day, noiden, noidi, soghe;
    private int tien;

    public Receipt() {
    }

    public Receipt(String day, String noiden, String noidi, String soghe, int tien) {
        this.day = day;
        this.noiden = noiden;
        this.noidi = noidi;
        this.soghe = soghe;
        this.tien = tien;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNoiden() {
        return noiden;
    }

    public void setNoiden(String noiden) {
        this.noiden = noiden;
    }

    public String getNoidi() {
        return noidi;
    }

    public void setNoidi(String noidi) {
        this.noidi = noidi;
    }

    public String getSoghe() {
        return soghe;
    }

    public void setSoghe(String soghe) {
        this.soghe = soghe;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }
}
