package com.example.reforaccion.models;

public class Stats {

    String month;
    Integer totalPlanted;
    Double averagePriceMonth;
    String mostPlantedZone;

    public Stats(){}

    public Stats(String month, Integer totalPlanted, Double averagePriceMonth, String mostPlantedZone) {
        this.month = month;
        this.totalPlanted = totalPlanted;
        this.averagePriceMonth = averagePriceMonth;
        this.mostPlantedZone = mostPlantedZone;
    }

    public String getMonth() { return month; }

    public void setMonth(String month) { this.month = month; }

    public Integer getTotalPlanted() { return totalPlanted; }

    public void setTotalPlanted(Integer totalPlanted) { this.totalPlanted = totalPlanted; }

    public Double getAveragePriceMonth() { return averagePriceMonth; }

    public void setAveragePriceMonth(Double averagePriceMonth) { this.averagePriceMonth = averagePriceMonth; }

    public String getMostPlantedZone() { return mostPlantedZone; }

    public void setMostPlantedZone(String mostPlantedZone) { this.mostPlantedZone = mostPlantedZone; }

    @Override
    public String toString() {
        return "Stats{" +
                "month='" + month + '\'' +
                ", totalPlanted=" + totalPlanted +
                ", averagePriceMonth=" + averagePriceMonth +
                ", mostPlantedZone='" + mostPlantedZone + '\'' +
                '}';
    }
}
