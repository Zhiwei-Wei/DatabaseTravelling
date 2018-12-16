package com.wzw.demo.predata;

import com.wzw.demo.vo.Customer;
import com.wzw.demo.vo.Group;
import com.wzw.demo.vo.GroupCusTakes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupCusTakesGenerator {
    /**
     * 模拟消费者参加旅游团
     * @param groups
     * @param customers
     * @return
     */
    public static List<GroupCusTakes> getTakes(List<Group> groups, List<Customer> customers) {
        Random random = new Random();
        List<GroupCusTakes> groupCusTakes = new ArrayList<>();
        for(Customer customer:customers){
            int r = random.nextInt(1000007)%groups.size();
            Group group = groups.get(r);
            while(group.getCurCusNumber()>=group.getMaxCusNumber()){//团满了就不能再选择了
                r = random.nextInt(1000007)%groups.size();
                group = groups.get(r);
            }
            group.setCurCusNumber(group.getCurCusNumber()+1);
            GroupCusTakes groupCusTakes1 = new GroupCusTakes();
            groupCusTakes1.setCusId(customer.getCusId());
            groupCusTakes1.setGroupId(group.getGroupId());
            groupCusTakes.add(groupCusTakes1);
        }
        return groupCusTakes;
    }
}
