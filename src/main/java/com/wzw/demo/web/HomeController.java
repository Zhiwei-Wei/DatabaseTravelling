package com.wzw.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping({"/","/index","/index.jsp"})
    public String index(Model model){
        model.addAttribute("page","sy");
        return "index.html";
    }
    @RequestMapping("/jipiao")
    public String jipiao(Model model){
        model.addAttribute("page","jp");
        return "notdone.html";
    }
    @RequestMapping("/jiudian")
    public String jiudian(Model model){
        model.addAttribute("page","jd");
        return "notdone.html";
    }
    @RequestMapping("/tuangou")
    public String tuangou(Model model){
        model.addAttribute("page","tg");
        return "notdone.html";
    }
    @RequestMapping("/gonglue")
    public String gonglue(Model model){
        model.addAttribute("page","gl");
        return "notdone.html";
    }
    @RequestMapping("/baoxian")
    public String baoxian(Model model){
        model.addAttribute("page","bx");
        return "notdone.html";
    }
}
