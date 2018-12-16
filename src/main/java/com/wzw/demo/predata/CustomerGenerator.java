package com.wzw.demo.predata;

import com.wzw.demo.vo.Customer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @author weizhiwei <br/>
 * 2018-12-16 11:51
 */
public class CustomerGenerator {
    public static List<Customer> getCustomers(int size) {
        EmployeeGenerator g = new EmployeeGenerator();
        String[] vocation = new String[] {"上海理工大学-学生","交通大学-学生","北京大学-学生","清华大学-学生",
                "复旦大学-学生","同济大学-学生","北京工业大学-学生","浙江大学-学生","京师同文馆-翻译官","白皇学院-管家",
                "SOS部-部员","上海理工大学-老师","交通大学-老师","北京大学-老师","清华大学-老师",
                "复旦大学-老师","同济大学-老师","北京工业大学-老师","上海理工大学-厨师","交通大学-厨师","北京大学-厨师","清华大学-厨师",
                "复旦大学-厨师","同济大学-厨师","北京工业大学-厨师","上海理工大学-图书管理员","交通大学-图书管理员",
                "北京大学-图书管理员","清华大学-图书管理员","复旦大学-图书管理员","同济大学-图书管理员","北京工业大学-图书管理员",
                "华山派-大师兄","古墓-弟子","明教-教主","少林寺-扫地僧",
                "常盘台中学-风纪委员","上海市政府-程序员","全国人民代表大会-代表","北大图书馆-图书管理员"};
        List<Customer> customers = new ArrayList<>();
        Random random = new Random();
        HashSet<String> phone = new HashSet<>();
        HashSet<String> identity = new HashSet<>();
        int len = vocation.length;
        for (int i = 0; i < size; i++) {
            Customer customer = new Customer();
            customer.setCusId(i+1);
            String[] str = EmployeeGenerator.getName().split("-");
            String[] s = vocation[random.nextInt(10000)%len].split("-");
            String idt = g.generate();
            while(identity.contains(idt)) {
                idt = g.generate();
            }
            identity.add(idt);
            String tel = getTel();
            while(phone.contains(tel)) {
                tel = getTel();
            }
            phone.add(tel);
            customer.setIdentity(idt);
            customer.setPhone(tel);
            customer.setRealName(str[1]);
            customer.setSex(str[0]);
            customer.setWorkUnit(s[0]);
            customer.setVocation(s[1]);
        }
        return customers;
    }
    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    public static String getTel() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
}
