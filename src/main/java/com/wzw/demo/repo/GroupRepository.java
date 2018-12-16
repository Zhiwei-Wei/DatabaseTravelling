package com.wzw.demo.repo;

import com.wzw.demo.vo.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 旅游团相关事务
 */
@Repository
public class GroupRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
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
    public Group getGroupInfoByGroupId(Integer groupId){
        return null;
    }

    /**
     * 获取所有的旅游团
     * @return
     */
    public List<Group> getAllGroups(){
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<Group> groups = jdbcTemplate.query("select * from `group`;", new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet resultSet, int i) throws SQLException {
                Group group = new Group();
                group.setGroupId(resultSet.getInt(1));
                group.setGuideId(resultSet.getInt(2));
//                group.setCurCusNumber(resultSet.getInt(3));
                group.setMaxCusNumber(resultSet.getInt(4));
                group.setStartTime(fm.format(resultSet.getDate(5)));
                group.setEndTime(fm.format(resultSet.getDate(6)));
                group.setDays(resultSet.getInt(7));
                group.setTransportation(resultSet.getString(8));
                group.setServiceLevel(resultSet.getString(9));
                group.setPrice(resultSet.getDouble(10));
                group.setRouteId(resultSet.getInt(11));
                group.setPictureUrl(resultSet.getString(12));
                group.setTitle(resultSet.getString(13));
                group.setIntroduction(resultSet.getString(14));
                return group;
            }
        });
        return groups;
    }
}
