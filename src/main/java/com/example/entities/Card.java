package com.example.entities;

public class Card {
    private int id;
    private int id_client;
    private String serialNumber;
    private String date;
    private String cvv;
    private float sum;
    private float addingBalance;

    public Card() {

    }

    public float getAddingBalance() {
        return addingBalance;
    }

    public void setAddingBalance(float addingBalance) {
        this.addingBalance = addingBalance;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
