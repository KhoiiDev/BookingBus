package com.example.myappcoach;

import java.util.ArrayList;
import java.util.Objects;

public class Search{
    private String from, to, fromDate, fromTime;
    private int cost;
    private ArrayList<Bus> listBuss;



    public Search(String from, String to, String fromDate, String fromTime, int cost, ArrayList<Bus> listBuss) {
        this.from = from;
        this.to = to;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
        this.cost = cost;
        this.listBuss = listBuss;
    }


    public Search(String from, String to, String fromDate) {
        this.from = from;
        this.to = to;
        this.fromDate = fromDate;
    }

    public Search(String from, String to, String fromDate, String fromTime) {
        this.from = from;
        this.to = to;
        this.fromDate = fromDate;
        this.fromTime = fromTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return Objects.equals(from, search.from) && Objects.equals(to, search.to) && Objects.equals(fromDate, search.fromDate);
    }

    @Override
    public String toString() {
        return "Search{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", fromTime='" + fromTime + '\'' +
                ", cost=" + cost +
                ", listBuss=" + listBuss +
                '}';
    }

    public boolean equals4(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return Objects.equals(from, search.from) && Objects.equals(to, search.to) && Objects.equals(fromDate, search.fromDate) && Objects.equals(fromTime, search.fromTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, fromDate);
    }

    public Search() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<Bus> getListBuss() {
        return listBuss;
    }

    public void setListBuss(ArrayList<Bus> listBuss) {
        this.listBuss = listBuss;
    }
}
