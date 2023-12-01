package com.example.myappcoach;

public class Trip {
    private String from, to, img;
    private int price;

    public Trip(String from, String to, String img, int price) {
        this.from = from;
        this.to = to;
        this.img = img;
        this.price = price;
    }

    public Trip() {
    }

    @Override
    public String toString() {
        return "Trip{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", img='" + img + '\'' +
                ", price=" + price +
                '}';
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
