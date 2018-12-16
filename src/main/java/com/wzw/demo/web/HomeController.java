package com.wzw.demo.web;

import com.wzw.demo.predata.PreData;
import com.wzw.demo.repo.CustomerRepository;
import com.wzw.demo.repo.GroupRepository;
import com.wzw.demo.repo.GuideRepository;
import com.wzw.demo.repo.ProvinceCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    GuideRepository guideRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
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
    @RequestMapping("/predata")
    public String predata(Model model){
//        try {
//            new PreData().GenerateGroupCustomerAndTakesInfos(jdbcTemplate,groupRepository,
//                    customerRepository,guideRepository);
//            model.addAttribute("msg","成功！");
//        }catch (Exception e){
//            model.addAttribute("msg", e.toString());
//            e.printStackTrace();
//        }
        return "error.html";
    }
}
