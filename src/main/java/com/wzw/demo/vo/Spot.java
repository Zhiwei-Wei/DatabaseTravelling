package com.wzw.demo.vo;

public class Spot {
    private Integer spotId,provinceId,cityId;
    private String name,spotLevel;

    public Integer getSpotId() {
        return spotId;
    }

    public void setSpotId(Integer spotId) {
        this.spotId = spotId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpotLevel() {
        return spotLevel;
    }

    public void setSpotLevel(String spotLevel) {
        this.spotLevel = spotLevel;
    }
}
