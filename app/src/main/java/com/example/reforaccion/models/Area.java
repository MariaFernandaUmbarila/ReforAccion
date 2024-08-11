package com.example.reforaccion.models;

public class Area {

    public String zonePlanted;
    public Double areaPlanted;
    public String datePlanted;

    public Area(){}

    public Area(String zonePlanted, Double areaPlanted, String datePlanted) {
        this.zonePlanted = zonePlanted;
        this.areaPlanted = areaPlanted;
        this.datePlanted = datePlanted;
    }

    public String getZonePlanted() {
        return zonePlanted;
    }

    public void setZonePlanted(String zonePlanted) {
        this.zonePlanted = zonePlanted;
    }

    public Double getAreaPlanted() {
        return areaPlanted;
    }

    public void setAreaPlanted(Double areaPlanted) {
        this.areaPlanted = areaPlanted;
    }

    public String getDatePlanted() {  return datePlanted; }

    public void setDatePlanted(String datePlanted) {
        this.datePlanted = datePlanted;
    }


}
