package com.wzw.demo.predata;

import com.wzw.demo.repo.ProvinceCityRepository;
import com.wzw.demo.vo.Province;
import com.wzw.demo.vo.Spot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SpotGenerator {
    public List<Spot> getSpots(int size,
                               ProvinceCityRepository provinceCityRepository){
        List<Province> provinces = provinceCityRepository.getAllProvinceCity();
        List<Spot> spots = new ArrayList<>();
        int pcnt = 0,ccnt = 0;
        String[] spotLevel = new String[]{"A","AA","AAA","AAAA","AAAAA"};
        Random random = new Random();
        for(int i = 1; i <= size; i++){
            Spot spot = new Spot();
            spot.setSpotId(i);
            spot.setProvinceId(pcnt+1);
            spot.setCityId(ccnt+1);
            ccnt++;
            if(ccnt>=provinces.get(pcnt).getCities().size()){
                ccnt = 0;
                pcnt++;
            }
            if(pcnt>=provinces.size()){
                pcnt = 0;//循环添加,保证每一个地方都有景点
            }
            spot.setName(getSpotName());
            spot.setSpotLevel(spotLevel[random.nextInt(spotLevel.length)]);
            spots.add(spot);
        }
        return spots;
    }
    static String[] sp1 = new String[]{"穆","一","异","百","万","千","三","九","壹","青","赤","白","乐","好","古","共","天"
    ,"地","东","南","北","西","亖"};
    static String[] sp2 = new String[]{"风","云雾","王","将军","雷霆","仞","马","平","水","仙","棋","木","鱼","势","石"
    ,"花","华","楠","乾"};
    static String[] sp3 = new String[]{"山脉","山","阁","崖","公园","陵园","寺","瀑布","湖","沟","庙","角","路","园",
    "馆","谷","桥","岛","墓","碑","库"};
    static HashSet<String> set = new HashSet<>();
    public static String getSpotName(){
        Random random = new Random();
        String name = sp1[random.nextInt(sp1.length)]+sp2[random.nextInt(sp2.length)]+sp3[random.nextInt(sp3.length)];
        boolean f = true;
        while(set.contains(name)){
            if(f){
                f = !f;
                name = sp2[random.nextInt(sp2.length)]+sp3[random.nextInt(sp3.length)];
            }else{
                f = !f;
                name = sp1[random.nextInt(sp1.length)]+sp2[random.nextInt(sp2.length)]+sp3[random.nextInt(sp3.length)];
            }
        }
        set.add(name);
        return name;
    }
}
