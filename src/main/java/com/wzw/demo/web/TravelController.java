package com.wzw.demo.web;

import com.alibaba.fastjson.JSON;
import com.wzw.demo.repo.CustomerRepository;
import com.wzw.demo.repo.GroupRepository;
import com.wzw.demo.repo.ProvinceCityRepository;
import com.wzw.demo.repo.RouteRepository;
import com.wzw.demo.vo.Customer;
import com.wzw.demo.vo.Group;
import com.wzw.demo.vo.Province;
import com.wzw.demo.vo.TravelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class TravelController {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    ProvinceCityRepository provinceCityRepository;
    @Autowired
    CustomerRepository customerRepository;
    @RequestMapping(value = "/travel", method = RequestMethod.GET)
    public String travel(Model model, HttpServletRequest request){
//        List<Province> provinces = groupRepository.getDistinations();
//        List<Province> starts = groupRepository.getArrivals();
        model.addAttribute("page","lx");
        List<Province> provinces = provinceCityRepository.getAllProvinceCity();
        List<Province> starts = provinceCityRepository.getAllProvinceCity();
        List<Integer> months = groupRepository.getMonths();
        List<String> services = groupRepository.getServices();
        model.addAttribute("months",months);
        model.addAttribute("provinces",provinces);//目的地
        model.addAttribute("starts",starts);//出发地
        model.addAttribute("services",services);
        return "travel.html";
    }
    @ResponseBody
    @RequestMapping(value = "/getItems", method = RequestMethod.GET)
    public String getItems(HttpServletRequest request){
        Map<String,String> map = new HashMap<>();
        Integer page = 1;
        String str = request.getParameter("page");
        if(str!=null){
            page = Integer.parseInt(str);
        }
        String orderby = "pop";
        str = request.getParameter("orderby");
        if(str!=null)
            orderby = str;
        Integer dpr = 0, dct = 0, apr = 0, act = 0;
        str = request.getParameter("dist");
        if(str !=null){
            dpr = Integer.parseInt(str.split("_")[0]);
            dct = Integer.parseInt(str.split("_")[1]);
        }
        str = request.getParameter("arri");
        if(str !=null){
            apr = Integer.parseInt(str.split("_")[0]);
            act = Integer.parseInt(str.split("_")[1]);
        }
        int min_day = 0, max_day = 0;
        str = request.getParameter("day");
        if(str!=null){
            min_day = Integer.parseInt(str.split("_")[0]);
            max_day = Integer.parseInt(str.split("_")[1]);
        }
        int month = 0;
        str = request.getParameter("month");
        if(str!=null){
            month = Integer.parseInt(str);
        }
        int minPrice = 0;
        int maxPrice = 0;
        str = request.getParameter("min_pr");
        if(str!=null){
            minPrice = Integer.parseInt(str);
        }
        str = request.getParameter("max_pr");
        if(str!=null){
            maxPrice = Integer.parseInt(str);
        }
        String service = request.getParameter("service");
        if (service==null)
            service = "0";
        Object[] objects = routeRepository.getTravelItems(orderby,page,dpr,dct,apr,act,min_day,max_day,
                minPrice,maxPrice,month,service);
        List<TravelItem> travelItems = (List<TravelItem>)objects[1];
        String travellists = JSON.toJSONString(travelItems);
        Integer maxPage = (Integer)objects[0];
        map.put("curPage",page+"");
        map.put("maxPage",maxPage+"");
        map.put("travelItems",travellists);
        String s = JSON.toJSONString(map);
        return s;
    }

    @RequestMapping(value = "/buyIt", method = RequestMethod.GET)
    public String buyIt(HttpServletRequest request, Model model){
        Integer id = (Integer) request.getSession().getAttribute("uid");
        if(id==null){
            return "login.html";
        }
        Integer gid = Integer.parseInt(request.getParameter("gid"));
        if(gid==null){
            return "index.html";
        }
        Group group = groupRepository.getGroupInfoByGroupId(gid);
        model.addAttribute("group", group);
        return "ticket.html";
    }

    @RequestMapping(value = "/buyIt", method = RequestMethod.POST)
    public String order(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = (Integer) request.getSession().getAttribute("uid");
        if(id==null){
            response.sendRedirect("/login");
            return "/index";
        }
        int size = Integer.parseInt(request.getParameter("cus_num"));
        int gid = Integer.parseInt(request.getParameter("groupId"));
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setSex(request.getParameter("sex"));
        customer.setPhone(request.getParameter("phone"));
        customer.setVocation(request.getParameter("vocation"));
        customer.setIdentity(request.getParameter("identity"));
        customer.setWorkUnit(request.getParameter("workUnit"));
        customer.setRealName(request.getParameter("realName"));
        customers.add(customer);
        for(int i = 1; i <= size-1; i++){
            customer = new Customer();
            customer.setRealName(request.getParameter("realName_"+i));
            customer.setSex(request.getParameter("sex_"+i));
            customer.setPhone(request.getParameter("phone_"+i));
            customer.setVocation(request.getParameter("vocation_"+i));
            customer.setIdentity(request.getParameter("identity_"+i));
            customer.setWorkUnit(request.getParameter("workUnit_"+i));
            customers.add(customer);
        }
        customerRepository.joinCustomersIntoGroup(gid, customers, id);
        response.sendRedirect("/myorder");
        return "/index.html";
    }
}
