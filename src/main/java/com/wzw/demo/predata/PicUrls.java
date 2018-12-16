package com.wzw.demo.predata;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicUrls {
    public static List<String> getUrls() throws Exception{
        String patt = "<Key>.*?</Key>";
        Pattern r = Pattern.compile(patt);
        InputStreamReader in = new InputStreamReader(new FileInputStream(new File("C:\\Users\\weizhiwei\\Desktop\\2018数据库大作业\\预设数据\\picurl.txt")));
        StringBuilder line = new StringBuilder();
        while(in.ready()){
            line.append((char)in.read());
        }
        Matcher m = r.matcher(line.toString());
        List<String> urls = new ArrayList<>();
        while (m.find()) {
            String url = m.group(0);
            if(url.contains("null"))
                continue;
            urls.add(url.substring(5,url.length()-6));
        }
        return urls;
    }
}
