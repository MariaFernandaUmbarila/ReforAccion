package com.example.reforaccion.models;

import android.app.Application;
import java.util.Date;

public class Area extends Application{

    public String zonePlanted;
    public Double areaPlanted;
    public Date datePlanted;
    public User userCellphone;

    public Area(String zonePlanted, Double areaPlanted, Date datePlanted, User userCellphone) {
        this.zonePlanted = zonePlanted;
        this.areaPlanted = areaPlanted;
        this.datePlanted = datePlanted;
        this.userCellphone = userCellphone;
    }

}
