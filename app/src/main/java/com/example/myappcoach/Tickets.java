package com.example.myappcoach;

import java.util.ArrayList;

public class Tickets {

    public Tickets() {}
    private int Cost;
    private String Date, From, To, Name, Phone, Time , email;
    private ArrayList<String> seat;

    public Tickets(int cost, String date, String from, String to, String name, String phone, String time, String email, ArrayList<String> seat) {
        Cost = cost;
        Date = date;
        From = from;
        To = to;
        Name = name;
        Phone = phone;
        Time = time;
        this.email = email;
        this.seat = seat;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "Cost=" + Cost +
                ", Date='" + Date + '\'' +
                ", From='" + From + '\'' +
                ", To='" + To + '\'' +
                ", Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Time='" + Time + '\'' +
                ", email='" + email + '\'' +
                ", seat=" + seat +
                '}';
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public ArrayList<String> getSeat() {
        return seat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSeat(ArrayList<String> seat) {
        this.seat = seat;
    }
}
