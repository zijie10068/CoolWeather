package com.example.zijie.weather.db;

import org.litepal.crud.DataSupport;

public class County extends DataSupport {
    private int id;

    private String countyName;

    private int cityId;

    private String weatherId;

    public int getId() {
        return id;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCountyName() {
        return countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

}
