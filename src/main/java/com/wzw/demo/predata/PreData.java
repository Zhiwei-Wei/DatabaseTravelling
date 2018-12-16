package com.wzw.demo.predata;

import com.wzw.demo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 预生成数据
 */
public class PreData {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void GenerateGroupCustomerAndTakesInfos() throws Exception{
        //生成1000条路线，500个景点
        List<Spot> spots = SpotGenerator.getSpots(500);
        List<Route> routes = RouteGenerator.getRoutes(spots,1000);
        String sql = "insert into spot(spot_id, `name`,province_id,city_id,spot_level) values(?,?,?,?,?)";
        //插入景点
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Spot spot = spots.get(i);
                preparedStatement.setInt(1,spot.getSpotId());
                preparedStatement.setString(2,spot.getName());
                preparedStatement.setInt(3,spot.getProvinceId());
                preparedStatement.setInt(4,spot.getCityId());
                preparedStatement.setString(5,spot.getSpotLevel());
            }

            @Override
            public int getBatchSize() {
                return spots.size();
            }
        });
        sql = "insert into route(route_id,spots_num,company_id,start_spot_id,end_spot_id) values(?,?,?,?,?)";
        //插入路线
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Route route = routes.get(i);
                preparedStatement.setInt(1,route.getRouteId());
                preparedStatement.setInt(2,route.getSpotsNumber());
                preparedStatement.setInt(3,route.getCompanyId());
                preparedStatement.setInt(4,route.getStartSpotId());
                preparedStatement.setInt(5,route.getEndSpotId());
            }

            @Override
            public int getBatchSize() {
                return routes.size();
            }
        });
        //插入takes，即为每一个路线插入其景点
        sql = "insert into route_spot_takes(route_id,spot_id,`order`) values (?,?,?)";
        for(Route route:routes){
            List<Spot> spots1 = route.getSpots();
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    Spot spot = spots1.get(i);
                    preparedStatement.setInt(1,route.getRouteId());
                    preparedStatement.setInt(2,spot.getSpotId());
                    preparedStatement.setInt(3,(i+1));
                }

                @Override
                public int getBatchSize() {
                    return spots1.size();
                }
            });
        }
        //生成5000旅游团,一共500个导游
        List<Customer> customers = CustomerGenerator.getCustomers(100000);
        List<Group> groups = GroupGenerator.getGroups(5000);
        List<GroupCusTakes> groupCusTakes = GroupCusTakesGenerator.getTakes(groups,customers);
        sql = "insert into `group`(guide_id,cus_cur_num,cus_max_num,start_time,end_time,days_number," +
                "transportation,service_level,price,route_id,pic_url,title,introduction,group_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Group group = groups.get(i);
                preparedStatement.setInt(1,group.getGuideId());
                preparedStatement.setInt(2,group.getCurCusNumber());
                preparedStatement.setInt(3,group.getMaxCusNumber());
                preparedStatement.setString(4,group.getStartTime());
                preparedStatement.setString(5,group.getEndTime());
                preparedStatement.setInt(6,group.getDays());
                preparedStatement.setString(7,group.getTransportation());
                preparedStatement.setString(8,group.getServiceLevel());
                preparedStatement.setDouble(9,group.getPrice());
                preparedStatement.setInt(10,group.getRouteId());
                preparedStatement.setString(11,group.getPictureUrl());
                preparedStatement.setString(12,group.getTitle());
                preparedStatement.setString(13,group.getIntroduction());
                preparedStatement.setInt(14,group.getGroupId());
            }

            @Override
            public int getBatchSize() {
                return groups.size();
            }
        });
        //生成十万客户信息
        sql = "insert into customer(`identity`, real_name, sex, work_unit, " +
                "vocation, phone,cus_id) values(?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Customer customer = customers.get(i);
                preparedStatement.setString(1,customer.getIdentity());
                preparedStatement.setString(2,customer.getRealName());
                preparedStatement.setString(3,customer.getSex());
                preparedStatement.setString(4,customer.getWorkUnit());
                preparedStatement.setString(5,customer.getVocation());
                preparedStatement.setString(6,customer.getPhone());
                preparedStatement.setInt(7,customer.getCusId());
            }

            @Override
            public int getBatchSize() {
                return customers.size();
            }
        });
        sql = "insert into group_cus_takes(group_id,cus_id) values(?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1,groupCusTakes.get(i).getGroupId());
                preparedStatement.setInt(2,groupCusTakes.get(i).getCusId());
            }

            @Override
            public int getBatchSize() {
                return groupCusTakes.size();
            }
        });
    }
}
