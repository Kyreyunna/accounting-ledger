package com.pluralsight;

public class Transaction {
    private String vendor;
    private int date;
    private int time;
    private double amount;
    public Transaction(int date, int time, String vendor, double amount) {
        this.vendor = vendor;
        this.amount = amount;
        this.date= date;
        this.time = time;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

