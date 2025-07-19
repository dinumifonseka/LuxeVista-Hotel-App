package com.example.LuxeVista.Models;

public class Room {
    private int id;
    private String title;
    private String description;
    private String dateRange;
    private double price;
    private int imageResId;

    public Room(int id, String title, String description, double price, int imageResId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateRange = dateRange;
        this.price = price;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateRange() {
        return dateRange;
    }

    public double getPrice() {
        return price;
    }



    public String getName() {
        return title;
    }

    public int getImageResource() {
        return imageResId;
    }
}
