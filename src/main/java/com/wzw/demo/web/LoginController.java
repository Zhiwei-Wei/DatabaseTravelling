package com.wzw.demo.web;

import com.wzw.demo.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String dologin(@ModelAttribute User user, HttpServletRequest request){



        return "login.html";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login.html";
    }
}
