package com.wzw.demo.repo;

import com.wzw.demo.vo.Group;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 旅游团相关事务
 */
@Repository
public class GroupRepository {
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

    /**
     * 获取旅游团的信息
     * @param groupId
     * @return
     */
    public List<Group> getGroupInfoByGroupId(Integer groupId){
        return null;
    }
}
