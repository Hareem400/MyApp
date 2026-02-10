package com.example.myapp;

import java.io.Serializable;

public class City implements Serializable {
    private String name;
    private String province;
    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }
    public String getCityName() {
        return name;
    }
    public void setCityName(String name) {
        this.name = name;
    }
    public String getProvinceName() {
        return province;
    }
    public void setProvinceName(String province) {
        this.province = province;
    }
}
