package com.example.reforaccion.models;

import java.util.ArrayList;
import java.util.Date;

public class User {

    public String cellphone;
    public String userName;
    public String password;
    public Date createdAt;
    public boolean termsAccepted;
    public ArrayList<Area> areas;
    public ArrayList<Plant> plants;

    public User(){}

    public User(String cellphone, String userName, String password, Date createdAt, boolean termsAccepted, ArrayList<Plant> plants, ArrayList<Area> areas) {
        this.cellphone = cellphone;
        this.userName = userName;
        this.password = password;
        this.createdAt = createdAt;
        this.termsAccepted = termsAccepted;
        this.plants = plants;
        this.areas = areas;
    }

    public ArrayList<Area> getAreas() { return areas; }

    public void setAreas(ArrayList<Area> areas) { this.areas = areas; }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public ArrayList<Plant> getPlants() { return plants; }

    public void setPlants(ArrayList<Plant> plants) { this.plants = plants; }
}

