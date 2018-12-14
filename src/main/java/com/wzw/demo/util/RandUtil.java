package com.wzw.demo.util;
import java.util.Random;

/**
 * 获取6位随机数
 */
public class RandUtil {

    public  static String  getRandomNum(){
        Random random=new Random();
        String randomNum = random.nextInt(1000000) + "";
        if(randomNum.length()!=6){
            System.out.println("6位伪随机数："+randomNum);
            return  getRandomNum();
        }
        System.out.println("6位随机数："+randomNum);
        return randomNum;
    }
}