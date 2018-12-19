package com.wzw.demo.web;

import com.wzw.demo.repo.CompanyRepository;
import com.wzw.demo.repo.ContractRepository;
import com.wzw.demo.repo.RouteRepository;
import com.wzw.demo.repo.UserRepository;
import com.wzw.demo.vo.CusItem;
import com.wzw.demo.vo.GuideItem;
import com.wzw.demo.vo.RouteItem;
import com.wzw.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    ContractRepository contractRepository;
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String checkRole(HttpServletRequest request, Model model){
        Integer id = (Integer) request.getSession().getAttribute("uid");
        if(id==null||userRepository.findUserByUserId(id).getRole()!=User.ADMIN){
            return "error.html";
        }
        model.addAttribute("companies",companyRepository.getAllCompanies());
        return "data.html";
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public String getData(HttpServletRequest request, Model model){
        Integer compId = Integer.parseInt(request.getParameter("company"));
        String selector = request.getParameter("selector");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        model.addAttribute("companies",companyRepository.getAllCompanies());
        if(year==null) year="0";
        if(month==null) month="0";
        int yy = Integer.parseInt(year);
        int mm = Integer.parseInt(month);
        if(selector.equals("route")){
            List<RouteItem> routes = contractRepository.getAccountByRoute(yy,mm,compId);
            routes.sort(new Comparator<RouteItem>() {
                @Override
                public int compare(RouteItem o2, RouteItem o1) {
                    return o1.getTotalPrice().compareTo(o2.getTotalPrice());
                }
            });
            double sum = 0.0;
            for(RouteItem routeItem:routes){
                sum += routeItem.getTotalPrice();
            }
            model.addAttribute("sum",sum);
            model.addAttribute("routes", routes);
            return "route.html";
        }else if(selector.equals("guide")){
            List<GuideItem> guideItems = contractRepository.getAccountByGuide(yy,mm,compId);
            guideItems.sort(new Comparator<GuideItem>() {
                @Override
                public int compare(GuideItem o2, GuideItem o1) {
                    return o1.getTotalPrice().compareTo(o2.getTotalPrice());
                }
            });
            double sum = 0.0;
            for(GuideItem routeItem:guideItems){
                sum += routeItem.getTotalPrice();
            }
            model.addAttribute("sum",sum);
            model.addAttribute("guides", guideItems);
            return "guide.html";
        }else if(selector.equals("customer")){
            List<CusItem> cusItems = contractRepository.getAccountByCustomer(yy,mm,compId);
            cusItems.sort(new Comparator<CusItem>() {
                @Override
                public int compare(CusItem o2, CusItem o1) {
                    return o1.getTotalPrice().compareTo(o2.getTotalPrice());
                }
            });
            double sum = 0.0;
            for(CusItem routeItem:cusItems){
                sum += routeItem.getTotalPrice();
            }
            model.addAttribute("sum",sum);
            model.addAttribute("customers", cusItems);
            return "customer.html";
        }
        return "error.html";
    }
}
