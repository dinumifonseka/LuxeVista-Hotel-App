package com.example.LuxeVista.Models;

public class BookingHistoryItem {
    private String hotelName;
    private String roomType;
    private String bookingDate;
    private String bookingStatus;
    private String totalPrice;
    private int hotelImage;

    public BookingHistoryItem(String hotelName, String roomType, String bookingDate,
                              String bookingStatus, String totalPrice, int hotelImage) {
        this.hotelName = hotelName;
        this.roomType = roomType;
        this.bookingDate = bookingDate;
        this.bookingStatus = bookingStatus;
        this.totalPrice = totalPrice;
        this.hotelImage = hotelImage;
    }

    // Getters
    public String getHotelName() {
        return hotelName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public int getHotelImage() {
        return hotelImage;
    }
}