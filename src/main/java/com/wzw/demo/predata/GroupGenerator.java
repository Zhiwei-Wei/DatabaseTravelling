package com.wzw.demo.predata;

import com.wzw.demo.vo.Group;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class GroupGenerator {
    public static List<Group> getGroups(int size) throws Exception{
        String[] service = new String[]{"D","C","B","A","S","SS","SSS"};
        String[] trans = new String[]{"轮船","飞机","火箭","任意门","次元黑洞","步行","自行车","担架","独轮车","三轮车"
        ,"麒麟","青龙","白虎","朱雀","玄武","汽车","璀璨酷炫七彩翅膀","公交车","长途大巴","绿皮火车","铁胆火车侠","四驱赛车"
        ,"F1赛车","滑板鞋","雪橇","一次性飞剑001","一次性飞剑003","一次性飞剑002","一次性飞剑004","一次性飞剑005","一次性飞剑006"
                ,"一次性飞剑007","一次性飞剑008","一次性飞剑009","一次性飞剑011","一次性飞剑010","一次性飞剑099"};
        String[] titles = new String[]{"快来玩","取个名字","标题","x天x夜游","您的明智之选"};
        String[] intros = new String[]{"豪华酒店，来就是赚到！","纯玩，0购物！","网红海景，网红美食"};
        List<String> urls = PicUrls.getUrls();
        Random random = new Random();
        List<Group> groups = new ArrayList<>();
        for(int i = 0; i < size; i++){
            int rr = random.nextInt(34097);
            Group group = new Group();
            group.setGroupId(i+1);
            group.setMaxCusNumber(30);
            group.setServiceLevel(service[rr%service.length]);
            group.setGuideId(rr%500+1);//500个导游
            group.setTransportation(trans[rr%trans.length]);
            group.setPictureUrl(urls.get(rr%urls.size()));
            group.setTitle(titles[rr%titles.length]);
            group.setIntroduction(titles[rr%intros.length]);
            Calendar calendar = GroupGenerator.randomDateBetweenMinAndMax();//获取时间calendar
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//转换成日期格式
            group.setStartTime(simpleDateFormat.format(calendar.getTime()));
            int days = rr%30+1;
            calendar.add(Calendar.DATE,days);
            group.setEndTime(simpleDateFormat.format(calendar.getTime()));
            group.setDays(days);
            group.setRouteId(rr%1000+1);//需要1000条旅游路线
            groups.add(group);
            group.setPrice((rr%50+100)*group.getDays()*(rr%service.length+1)*0.6);//价格为服务等级*天数*0.6*随机数
        }
        return groups;
    }
    public static Calendar randomDateBetweenMinAndMax(){
        Calendar calendar = Calendar.getInstance();
        //注意月份要减去1
        calendar.set(2019,1,1);
        calendar.getTime().getTime();
        //根据需求，这里要将时分秒设置为0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        long min = calendar.getTime().getTime();;
        calendar.set(2019,12,31);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.getTime().getTime();
        long max = calendar.getTime().getTime();
        //得到大于等于min小于max的double值
        double randomDate = Math.random()*(max-min)+min;
        //将double值舍入为整数，转化成long类型
        calendar.setTimeInMillis(Math.round(randomDate));
        return calendar;
    }
    public static String getIntro(){
        String[] intros = new String[]{"豪华酒店，来就是赚到！","纯玩，0购物！","网红海景，网红美食"};
        Random random = new Random();
        return intros[random.nextInt(intros.length)];
    }
    public static double getPrice(Group group){
        Random random = new Random();
        int rr = random.nextInt(34097);
        group.setPrice((rr%50+100)*group.getDays()*(rr%7+1)*0.6);//价格为服务等级*天数*0.6*随机数
        return group.getPrice();
    }

}
