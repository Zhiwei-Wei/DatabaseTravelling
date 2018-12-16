package com.wzw.demo.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonString;
import com.wzw.demo.repo.UserRepository;
import com.wzw.demo.vo.MessageStatus;
import com.wzw.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    /**
     * 使用ajax异步提交，返回状态码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String dologin(HttpServletRequest request, @RequestBody String requestJson){
        JSONObject jsonObject = JSON.parseObject(requestJson);
        String phone = jsonObject.getString("phone");
        String password = jsonObject.getString("password");
        String remember = jsonObject.getString("remember");//是否记住密码
        User user = userRepository.findUserByPhone(phone);
        Map<String,String> map = new HashMap<>();
        if(user==null){
            map.put("status",MessageStatus.WRONG_PHONE_NUMBER+"");
        }else if(user.getPassword().equals(password)){
            map.put("status",MessageStatus.SUCCESS_LOG_IN+"");
            request.getSession().setAttribute("uid",user.getUid());
            request.getSession().setAttribute("nickname",user.getNickname());
        }else{
            map.put("status",MessageStatus.WRONG_PASSWORD+"");
        }
        String s = JSON.toJSONString(map);
        return s;
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(){
        return "register.html";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String doRegister(HttpServletRequest request,@RequestBody String requestJson){
        JSONObject jsonObject = JSON.parseObject(requestJson);
        String phone = jsonObject.getString("phone");
        String password = jsonObject.getString("password");
        String check = jsonObject.getString("check");
        Map<String, String> map = new HashMap<>();
        map.put("status","3");
        if(check.trim().equals(request.getSession().getAttribute("check").toString().trim())){
            //验证码通过
            User user = new User();
            user.setPhone(phone);
            user.setPassword(password);
            user.setRole(User.CUSTOMER);
            user.setNickname(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            userRepository.register(user);
            map.put("status","1");
        }else{
            map.put("status","2");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/register/checkPhone", method = RequestMethod.GET)
    @ResponseBody
    public String checkPhone(HttpServletRequest request){
        String phone = request.getParameter("phone");
        Map<String,Integer> map = new HashMap<>();
        if(phone.trim().length()!=11){
            map.put("msg",MessageStatus.WRONG_PHONE_NUMBER);
        }else {
            User user = userRepository.findUserByPhone(phone);
            if (user == null) {
                map.put("msg",MessageStatus.SUCCESS_PHONE_CHECKED);
            }else{
                map.put("msg",MessageStatus.FAILURE_PHONE_CHECKED);
            }
        }
        String json = JSON.toJSONString(map);
        return json;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("uid");
        request.getSession().removeAttribute("nickname");
        return "index.html";
    }
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(){
        return "notdone.html";
    }
}
