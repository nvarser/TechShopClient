package com.example.entities;

public class Seller {
    private int id;
    private int id_user;
    private String name;
    private String surname;
    private String EMail;
    private String login;
    private String password;
    private String role;
    private float hours;
    private float ratePerHour;
    private int amountSells;
    private float ratePerSell;
    private float salary;
    public Seller(){

    }


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEMail() {
        return EMail;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public float getHours() {
        return hours;
    }

    public void setHours(float hours) {
        this.hours = hours;
    }

    public float getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(float ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public int getAmountSells() {
        return amountSells;
    }

    public void setAmountSells(int amountSells) {
        this.amountSells = amountSells;
    }

    public float getRatePerSell() {
        return ratePerSell;
    }

    public void setRatePerSell(float ratePerSell) {
        this.ratePerSell = ratePerSell;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}
