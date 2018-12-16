package com.wzw.demo.repo;

import com.wzw.demo.vo.TravelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 游客查找路线<br>
 * 公司管理路线
 */
@Repository
public class RouteRepository {
    /**
     *
     * @param orderby
     * @param page
     * @param dist_province
     * @param dist_city
     * @param arri_province 出发省
     * @param arri_city 出发城市
     * @param min_day
     * @param max_day
     * @param month 几月出发
     * @param service
     * @return
     */
    public static final int PAGESIZE = 30;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    SpotRepository spotRepository;
    public Object[] getTravelItems(String orderby, int page, int dist_province, int dist_city,
                                           int arri_province, int arri_city, int min_day, int max_day,
                                           int min_price, int max_price, int month, String service){
        Object[] objects = new Object[2];
        Integer maxPage = 1000;
        String tmp=orderby;
        String[] s = orderby.split("_");
        if(s[0].equals("pop")){
            tmp = "cus_cur_num desc, start_time asc, service_level desc";
        }else{
            if(s[0].equals("sales")){
                s[0] = "cus_cur_num";
            }
            if(s.length>1){
                tmp = s[0]+" "+s[1];
            }
        }
        final String order = tmp;
        int a = (page-1)*PAGESIZE;
        List<TravelItem> travelItems = jdbcTemplate.query(
                "select g.route_id,g.pic_url,g.title,g.price,g.group_id," +
                        "g.guide_id,g.cus_cur_num,g.cus_max_num,g.days_number,g.start_time," +
                        "g.service_level from `group` g inner join (select route_id, " +
                        "spots_num from route where start_spot_id in (select spot_id " +
                        "from spot where (?=0 or province_id=?) and end_spot_id in (?=0 or city_id=?)) " +
                        "and (select spot_id from spot where (?=0 or province_id=?) and " +
                        "(?=0 or city_id=?))) r on g.route_id=r.route_id where" +
                        " start_time > ? and (? = 0 or days_number >= ?) and" +
                        " (? = 0 or days_number <= ?) and (?=0 or price >= ?) and " +
                        "(?=0 or price <= ?) and (?=0 or month(start_time)=?) and " +
                        "(? = '0' or service_level = ?) order by ? limit ?,?;", new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1, arri_province);
                        preparedStatement.setInt(2, arri_province);
                        preparedStatement.setInt(3, arri_city);
                        preparedStatement.setInt(4, arri_city);
                        preparedStatement.setInt(5, dist_province);
                        preparedStatement.setInt(6, dist_province);
                        preparedStatement.setInt(7, dist_city);
                        preparedStatement.setInt(8, dist_city);
                        preparedStatement.setString(9, new SimpleDateFormat("yyyy-MM-dd").
                                format(new Date(new Date().getTime() + 86400000L)));
                        preparedStatement.setInt(10, min_day);
                        preparedStatement.setInt(11, min_day);
                        preparedStatement.setInt(12, max_day);
                        preparedStatement.setInt(13, max_day);
                        preparedStatement.setInt(14, min_price);
                        preparedStatement.setInt(15, min_price);
                        preparedStatement.setInt(16, max_price);
                        preparedStatement.setInt(17, max_price);
                        preparedStatement.setInt(18, month);
                        preparedStatement.setInt(19, month);
                        preparedStatement.setString(20, service);
                        preparedStatement.setString(21, service);
                        preparedStatement.setString(22, order);
                        preparedStatement.setInt(23, a);
                        preparedStatement.setInt(24, PAGESIZE);
                    }
                }, new RowMapper<TravelItem>() {
                    @Override
                    public TravelItem mapRow(ResultSet resultSet, int i) throws SQLException {
                        TravelItem travelItem = new TravelItem();
                        travelItem.setRouteId(resultSet.getInt(1)+"");
                        travelItem.setPicture(resultSet.getString(2));
                        travelItem.setTt(resultSet.getString(3));
                        travelItem.setPrice(resultSet.getDouble(4)+"");
                        travelItem.setGroupId(resultSet.getInt(5)+"");
                        travelItem.setGuideId(resultSet.getInt(6)+"");
                        travelItem.setCurNum(resultSet.getInt(7)+"");
                        travelItem.setMaxNum(resultSet.getInt(8)+"");
                        travelItem.setDays(resultSet.getInt(9)+"");
                        travelItem.setStartTime(new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss")
                                .format(resultSet.getDate(10)));
                        travelItem.setService(resultSet.getString(11));
                        return travelItem;
                    }
                });
        List<Integer> pp = jdbcTemplate.query("select count(*) from (select g.route_id,g.pic_url,g.title,g.price,g.group_id," +
                        "g.guide_id,g.cus_cur_num,g.cus_max_num,g.days_number,g.start_time," +
                        "g.service_level from `group` g inner join (select route_id, " +
                        "spots_num from route where start_spot_id in (select spot_id " +
                        "from spot where (?=0 or province_id=?) and end_spot_id in (?=0 or city_id=?)) " +
                        "and (select spot_id from spot where (?=0 or province_id=?) and " +
                        "(?=0 or city_id=?))) r on g.route_id=r.route_id where" +
                        " start_time > ? and (? = 0 or days_number >= ?) and" +
                        " (? = 0 or days_number <= ?) and (?=0 or price >= ?) and " +
                        "(?=0 or price <= ?) and (?=0 or month(start_time)=?) and " +
                        "(? = '0' or service_level = ?) order by ?) a;", new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1, arri_province);
                        preparedStatement.setInt(2, arri_province);
                        preparedStatement.setInt(3, arri_city);
                        preparedStatement.setInt(4, arri_city);
                        preparedStatement.setInt(5, dist_province);
                        preparedStatement.setInt(6, dist_province);
                        preparedStatement.setInt(7, dist_city);
                        preparedStatement.setInt(8, dist_city);
                        preparedStatement.setString(9, new SimpleDateFormat("yyyy-MM-dd").
                                format(new Date(new Date().getTime() + 86400000L)));
                        preparedStatement.setInt(10, min_day);
                        preparedStatement.setInt(11, min_day);
                        preparedStatement.setInt(12, max_day);
                        preparedStatement.setInt(13, max_day);
                        preparedStatement.setInt(14, min_price);
                        preparedStatement.setInt(15, min_price);
                        preparedStatement.setInt(16, max_price);
                        preparedStatement.setInt(17, max_price);
                        preparedStatement.setInt(18, month);
                        preparedStatement.setInt(19, month);
                        preparedStatement.setString(20, service);
                        preparedStatement.setString(21, service);
                        preparedStatement.setString(22, order);
                    }
                }
                , new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getInt(1);
                    }
                });
        if(pp.size()>=1)
            maxPage = pp.get(0)/PAGESIZE+1;
        objects[0] = maxPage;
        objects[1] = travelItems;
        return objects;
    }

    /**
     * 返回空格相隔的路线表
     * @param id
     * @return
     */
    public String getRouteByRouteId(Integer id){
        List<Integer> integers = jdbcTemplate.query("select spot_id from route_spot_takes" +
                " where route_id=? order by `order`;", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, id);
            }
        }, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(1);
            }
        });
        HashMap<Integer, String> spot = new HashMap<>();
        StringBuilder route = new StringBuilder();
        for(Integer i:integers){
            if(spot.containsKey(i)){
                route.append(spot.get(i)+" ");
            }else{
                String spotName = spotRepository.getSpotNameBySpotId(i);
                spot.put(i,spotName);
                route.append(spotName+" ");
            }
        }
        return route.toString().trim();
    }
}
