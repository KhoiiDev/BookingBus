package com.example.myappcoach;

public class Seat {
    private String name;

    public Seat() {
    }

    public Seat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "name='" + name + '\'' +
                '}';
    }
}
