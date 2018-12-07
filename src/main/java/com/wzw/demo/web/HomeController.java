package com.wzw.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping({"/","/index","/index.jsp"})
    public String index(){
        return "index.html";
    }
    @RequestMapping("/notdone")
    public String notdone(){
        return "notdone.html";
    }
}
