package com.fifa.restaurant.controleur;

public class Booking {
    public String seat;
    public String date;
    public String hour;
    public String note;
    public String phone;

    public Booking() {
        super();
    }

    public Booking(String seat, String date, String hour, String note, String phone) {
        this.seat = seat;
        this.date = date;
        this.hour = hour;
        this.note = note;
        this.phone = phone;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
