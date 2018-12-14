package com.wzw.demo.repo;

import com.wzw.demo.vo.Province;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 旅游团相关事务
 */
@Repository
public class GroupRepository {
    /**
     * 获得目的地
     * @return
     */
    public List<Province> getDistinations(){
        return null;
    }

    /**
     * 获得出发地
     * @return
     */
    public List<Province> getArrivals(){
        return null;
    }

    /**
     * 获得旅游团旅行时间<br>
     * @return
     */
    public List<Integer> getMonths(){

        return null;
    }

    /**
     * 获取服务等级
     * @return
     */
    public List<String> getServices(){

        return null;
    }


}
