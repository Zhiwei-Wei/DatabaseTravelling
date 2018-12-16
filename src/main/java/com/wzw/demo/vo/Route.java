package com.wzw.demo.vo;

import java.util.List;

public class Route {
    private List<Spot> spots;
    private Integer spotsNumber,companyId,startSpotId,endSpotId,routeId;

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public Integer getSpotsNumber() {
        return spotsNumber;
    }

    public void setSpotsNumber(Integer spotsNumber) {
        this.spotsNumber = spotsNumber;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getStartSpotId() {
        return startSpotId;
    }

    public void setStartSpotId(Integer startSpotId) {
        this.startSpotId = startSpotId;
    }

    public Integer getEndSpotId() {
        return endSpotId;
    }

    public void setEndSpotId(Integer endSpotId) {
        this.endSpotId = endSpotId;
    }
}
