package com.example.reforaccion.models;


public class Plant {

    public Integer quantity;
    public Double individualPrice;
    public String datePlanted;

    public Plant(){};

    public Plant(Integer quantity, Double individualPrice, String datePlanted) {
        this.quantity = quantity;
        this.individualPrice = individualPrice;
        this.datePlanted = datePlanted;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getIndividualPrice() {
        return individualPrice;
    }

    public void setIndividualPrice(Double individualPrice) {
        this.individualPrice = individualPrice;
    }

    public String getDatePlanted() {
        return datePlanted;
    }

    public void setDatePlanted(String datePlanted) {
        this.datePlanted = datePlanted;
    }

}
