package com.wzw.demo.predata;

import com.wzw.demo.vo.Route;
import com.wzw.demo.vo.Spot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class RouteGenerator {
    public static List<Route> getRoutes(List<Spot> spots, int size){
        List<Route> routes = new ArrayList<>();
        Random random = new Random();
        for(int i = 1; i <= size; i++){
            Route route = new Route();
            route.setRouteId(i);
            int number = random.nextInt(10)+3;
            route.setSpotsNumber(number);
            route.setCompanyId(random.nextInt(107)%5+1);
            HashSet<Integer> tmp = new HashSet<>();
            List<Spot> spots1 = new ArrayList<>();
            while(number-->0){
                int r = random.nextInt(spots.size());
                while(tmp.contains(r)){
                    r = random.nextInt(spots.size());
                }
                tmp.add(r);
                spots1.add(spots.get(r));
            }
            route.setSpots(spots1);
            route.setStartSpotId(spots1.get(0).getSpotId());
            route.setEndSpotId(spots1.get(spots1.size()-1).getSpotId());
            routes.add(route);
        }
        return routes;
    }
}
