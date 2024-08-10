package com.example.reforaccion.models;

import android.app.Application;
import java.util.Date;

public class Plant extends Application{

    public Integer quantity;
    public Double individualPrice;
    public Date datePlanted;
    public User userCellphone;

    public Plant(Integer quantity, Double individualPrice, Date datePlanted, User userCellphone) {
        this.quantity = quantity;
        this.individualPrice = individualPrice;
        this.datePlanted = datePlanted;
        this.userCellphone = userCellphone;
    }

}
