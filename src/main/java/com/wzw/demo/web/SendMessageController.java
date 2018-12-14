package com.wzw.demo.web;


import com.alibaba.fastjson.JSON;
import com.wzw.demo.util.GetSMS;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SendMessageController {

    @RequestMapping(value = "/sms",method = RequestMethod.GET)
    public String sendSMS(HttpServletRequest request){
        String to = request.getParameter("phone");
        String[] strings = GetSMS.getmMssage(to).split("_");
        if(strings[0].equals("00000")){
            request.getSession().setAttribute("check",strings[1]);
        }
        Map<String,String> map = new HashMap<>();
        map.put("status",strings[0]);
        return JSON.toJSONString(map);
    }
}
