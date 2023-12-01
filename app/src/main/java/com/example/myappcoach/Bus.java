package com.example.myappcoach;

public class Bus {
    private String seat, name;

    public Bus(String name, String seat) {
        this.seat = seat;
        this.name = name;
    }

    public Bus() {
    }


    @Override
    public String toString() {
        return "Bus{" +
                "seat='" + seat + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
