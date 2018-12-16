package com.wzw.demo.predata;

import com.wzw.demo.repo.CustomerRepository;
import com.wzw.demo.repo.GroupRepository;
import com.wzw.demo.repo.GuideRepository;
import com.wzw.demo.repo.ProvinceCityRepository;
import com.wzw.demo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 预生成数据
 */
public class PreData {
    public void GenerateGroupCustomerAndTakesInfos(JdbcTemplate jdbcTemplate, GroupRepository groupRepository,
                                                   CustomerRepository customerRepository, GuideRepository guideRepository) throws Exception{
        //生成1000条路线，500个景点
//        List<Spot> spots = new SpotGenerator().getSpots(500, provinceCityRepository);
//        List<Route> routes = RouteGenerator.getRoutes(spots,1000);
        String sql = "insert into spot(spot_id, `name`,province_id,city_id,spot_level) values(?,?,?,?,?)";
//        //插入景点
//        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                Spot spot = spots.get(i);
//                preparedStatement.setInt(1,spot.getSpotId());
//                preparedStatement.setString(2,spot.getName());
//                preparedStatement.setInt(3,spot.getProvinceId());
//                preparedStatement.setInt(4,spot.getCityId());
//                preparedStatement.setString(5,spot.getSpotLevel());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return spots.size();
//            }
//        });
//        sql = "insert into route(route_id,spots_num,company_id,start_spot_id,end_spot_id) values(?,?,?,?,?)";
//        //插入路线
//        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                Route route = routes.get(i);
//                preparedStatement.setInt(1,route.getRouteId());
//                preparedStatement.setInt(2,route.getSpotsNumber());
//                preparedStatement.setInt(3,route.getCompanyId());
//                preparedStatement.setInt(4,route.getStartSpotId());
//                preparedStatement.setInt(5,route.getEndSpotId());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return routes.size();
//            }
//        });
//        //插入takes，即为每一个路线插入其景点
//        sql = "insert into route_spot_takes(route_id,spot_id,`order`) values (?,?,?)";
//        for(Route route:routes){
//            List<Spot> spots1 = route.getSpots();
//            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//                @Override
//                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                    Spot spot = spots1.get(i);
//                    preparedStatement.setInt(1,route.getRouteId());
//                    preparedStatement.setInt(2,spot.getSpotId());
//                    preparedStatement.setInt(3,(i+1));
//                }
//
//                @Override
//                public int getBatchSize() {
//                    return spots1.size();
//                }
//            });
//        }
        //生成5000旅游团,一共500个导游
//        List<Customer> customers = customerRepository.getAllCustomers();
        List<Group> groups = groupRepository.getAllGroups();
//        List<GroupCusTakes> groupCusTakes = GroupCusTakesGenerator.getTakes(groups,customers);
//        System.out.println("顾客消费记录生成完毕:"+groupCusTakes.size());
        sql = "update `group` set introduction=? where group_id = ?";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,GroupGenerator.getIntro());
                preparedStatement.setInt(2,groups.get(i).getGroupId());
            }

            @Override
            public int getBatchSize() {
                return groups.size();
            }
        });
        //生成十万客户信息
//        sql = "insert into customer(`identity`, real_name, sex, work_unit, " +
//                "vocation, phone,cus_id) values(?,?,?,?,?,?,?)";
//        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                Customer customer = customers.get(i);
//                preparedStatement.setString(1,customer.getIdentity());
//                preparedStatement.setString(2,customer.getRealName());
//                preparedStatement.setString(3,customer.getSex());
//                preparedStatement.setString(4,customer.getWorkUnit());
//                preparedStatement.setString(5,customer.getVocation());
//                preparedStatement.setString(6,customer.getPhone());
//                preparedStatement.setInt(7,customer.getCusId());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return customers.size();
//            }
//        });
//        sql = "insert into group_cus_takes(group_id,cus_id) values(?,?)";
//        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                preparedStatement.setInt(1,groupCusTakes.get(i).getGroupId());
//                preparedStatement.setInt(2,groupCusTakes.get(i).getCusId());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return groupCusTakes.size();
//            }
//        });
//        String[] ins = new String[]{"一级保险","没有保险","二级保险","三级保险","四级保险","五级保险","超级无敌霹雳保险"};
//        sql = "insert into `contract`(guide_id,company_id,route_id,type_id,`insurance`,`date`,cus_id,group_id) " +
//                "values(?,?,?,?,?,?,?,?)";
//        HashMap<Integer,Integer> guideComp = new HashMap<>();
//        Random random = new Random();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                int groupId = groupCusTakes.get(i).getGroupId();
//                int cusId = groupCusTakes.get(i).getCusId();
//                Group group = groups.get(groupId-1);
//                int guideId = group.getGuideId();
//                preparedStatement.setInt(1,guideId);
//                if(guideComp.containsKey(guideId)){
//                    preparedStatement.setInt(2,guideComp.get(guideId));
//                }else{
//                    int cid = guideRepository.getCompanyIdByGuideId(guideId);
//                    guideComp.put(guideId,cid);
//                    preparedStatement.setInt(2,cid);
//                }
//                preparedStatement.setInt(3,group.getRouteId());
//                preparedStatement.setInt(4,random.nextInt(100)%5+1);
//                preparedStatement.setString(5,ins[random.nextInt(100)%ins.length]);
//                try{
//                    preparedStatement.setDate(6,new java.sql.Date(
//                            simpleDateFormat.parse(group.getStartTime()).getTime()-23*67*45*random.nextInt(5)));
//                }catch (Exception e){}
//                preparedStatement.setInt(7,cusId);
//                preparedStatement.setInt(8,groupId);
//            }
//
//            @Override
//            public int getBatchSize() {
//                return groupCusTakes.size();
//            }
//        });
    }
}
