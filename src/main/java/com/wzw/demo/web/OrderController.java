package com.wzw.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
    @Autowired
    OrderController orderController;
    @RequestMapping(value = "/myorder/{uid}", method = RequestMethod.GET)
    public String myOrder(@PathVariable Integer uid, HttpServletRequest request, Model model){
        Integer id = (Integer) request.getSession().getAttribute("uid");
        if(id==null){
            model.addAttribute("msg","请登录！");//非自身用户
            return "error.html";
        }else if(id!=uid){
            model.addAttribute("msg","不具有访问权限！");
            return "error.html";
        }else{

        }
        return "order.html";
    }
}
