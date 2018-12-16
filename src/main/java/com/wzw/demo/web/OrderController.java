package com.wzw.demo.web;

import com.wzw.demo.repo.OrderRepository;
import com.wzw.demo.vo.Order;
import com.wzw.demo.vo.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @RequestMapping(value = {"/myorder","/myorder/dqdd"}, method = RequestMethod.GET)
    public String myOrderDq(HttpServletRequest request, Model model){
        Integer id = (Integer) request.getSession().getAttribute("uid");
        if(id==null){
            return "login.html";
        }else{
            model.addAttribute("page","dqdd");
            List<Order> orders = orderRepository.getCurOrders(id);
            List<OrderInfo> orderInfos = orderRepository.getOrderInfos(orders);
            model.addAttribute("status","进行中");
            model.addAttribute("orderInfos",orderInfos);
        }
        return "order.html";
    }
    @RequestMapping(value = {"/myorder/lsdd"}, method = RequestMethod.GET)
    public String myOrderLs(HttpServletRequest request, Model model){
        Integer id = (Integer) request.getSession().getAttribute("uid");
        if(id==null){
            return "login.html";
        }else{
            model.addAttribute("page","lsdd");
            List<Order> orders = orderRepository.getHistoryOrders(id);
            List<OrderInfo> orderInfos = orderRepository.getOrderInfos(orders);
            model.addAttribute("status","已完成");
            model.addAttribute("orderInfos",orderInfos);
        }
        return "order.html";
    }

}
