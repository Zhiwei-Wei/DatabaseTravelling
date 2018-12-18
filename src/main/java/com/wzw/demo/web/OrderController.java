package com.wzw.demo.web;

import com.alibaba.fastjson.JSON;
import com.wzw.demo.repo.OrderRepository;
import com.wzw.demo.vo.Order;
import com.wzw.demo.vo.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class OrderController {
    public static final String CURRENT_ORDERS="进行中",COMPLETED_ORDERS="已完成";
    @Autowired
    OrderRepository orderRepository;
    @RequestMapping(value = {"/myorder","/myorder/dqdd"}, method = RequestMethod.GET)
    public String myOrderDq(HttpServletRequest request, Model model){
        Integer id = (Integer) request.getSession().getAttribute("uid");
        if(id==null){
            return "login.html";
        }else{
            model.addAttribute("page","dqdd");
            List<Order> orders = orderRepository.getCurOrders(id, 1);
            List<OrderInfo> orderInfos = orderRepository.getOrderInfos(orders);
            int maxPage = orderRepository.getCurMaxPage(id);
            model.addAttribute("status",CURRENT_ORDERS);
            model.addAttribute("maxPage",maxPage);
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
            List<Order> orders = orderRepository.getHistoryOrders(id, 1);
            List<OrderInfo> orderInfos = orderRepository.getOrderInfos(orders);
            int maxPage = orderRepository.getHistoryMaxPage(id);
            model.addAttribute("status",COMPLETED_ORDERS);
            model.addAttribute("maxPage",maxPage);
            model.addAttribute("orderInfos",orderInfos);
        }
        return "order.html";
    }
    @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    @ResponseBody
    public String getOrders(HttpServletRequest request){
        Integer id = (Integer)request.getSession().getAttribute("uid");
        if(id==null){
            throw new RuntimeException("非法访问！");
        }
        Integer page = Integer.parseInt(request.getParameter("page"));
        String status = request.getParameter("status").trim();
        if(page==null)
            page = 1;
        List<Order> orders = null;
        if(status.equals(COMPLETED_ORDERS)){
            orders = orderRepository.getHistoryOrders(id, page);
        }else if(status.equals(CURRENT_ORDERS)){
            orders = orderRepository.getCurOrders(id, page);
        }
        List<OrderInfo> orderInfos = orderRepository.getOrderInfos(orders);
        String itemLists = JSON.toJSONString(orderInfos);
        HashMap<String,String> map = new HashMap<>();
        map.put("data",itemLists);
        return JSON.toJSONString(map);
    }
}
