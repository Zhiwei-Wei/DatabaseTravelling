package com.wzw.demo.repo;

import com.wzw.demo.vo.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 旅游团相关事务
 */
@Repository
public class GroupRepository {
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
    /**
     * 获得旅游团旅行时间<br>
     * @return
     */
    public List<Integer> getMonths(){
        List<Integer> integers = new ArrayList<>();
        for(int i = 1; i <= 12; i++)
            integers.add(i);
        return integers;
    }

    /**
     * 获取服务等级
     * @return
     */
    public List<String> getServices(){
        List<String> strings = new ArrayList<>();
        strings.add("D");
        strings.add("C");
        strings.add("B");
        strings.add("A");
        strings.add("S");
        strings.add("SS");
        strings.add("SSS");
        return strings;
    }

    /**
     * 获取旅游团的信息
     * @param groupId
     * @return
     */
    public Group getGroupInfoByGroupId(Integer groupId){
        List<Group> groups = jdbcTemplate.query("select * from `group` where group_id = ? and acitivated='1';",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1, groupId);
                    }
                }, new RowMapper<Group>() {
                    @Override
                    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
                        Group group = new Group();
                        group.setGroupId(resultSet.getInt(1));
                        group.setGuideId(resultSet.getInt(2));
                        group.setCurCusNumber(resultSet.getInt(3));
                        group.setMaxCusNumber(resultSet.getInt(4));
                        group.setStartTime(resultSet.getString(5));
                        group.setEndTime(resultSet.getString(6));
                        group.setDays(resultSet.getInt(7));
                        group.setTransportation(resultSet.getString(8));
                        group.setServiceLevel(resultSet.getString(9));
                        group.setPrice(resultSet.getDouble(10));
                        group.setRouteId(resultSet.getInt(11));
                        group.setPictureUrl(resultSet.getString(12));
                        group.setTitle(resultSet.getString(13));
                        group.setIntroduction(resultSet.getString(14));
                        group.setRoute(routeRepository.getRouteByRouteId(group.getRouteId()));
                        return group;
                    }
                });
        return groups.get(0);
    }

    /**
     * 获取所有的旅游团
     * @return
     */
    public List<Group> getAllGroups(){
        List<Group> groups = jdbcTemplate.query("select * from `group`;", new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet resultSet, int i) throws SQLException {
                Group group = new Group();
                group.setGroupId(resultSet.getInt(1));
                group.setGuideId(resultSet.getInt(2));
//                group.setCurCusNumber(resultSet.getInt(3));
                group.setMaxCusNumber(resultSet.getInt(4));
                group.setStartTime(resultSet.getString(5));
                group.setEndTime(resultSet.getString(6));
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
    /**
     * 添加用户购买的数据
     */
    public boolean JoinGroup(Integer uid, Integer gid){
        boolean flag = false;

        return flag;
    }

}
