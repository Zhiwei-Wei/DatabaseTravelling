package com.wzw.demo.repo;

import com.wzw.demo.vo.Order;
import com.wzw.demo.vo.OrderInfo;
import org.aspectj.weaver.ast.Or;
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
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GuideRepository guideRepository;
    @Autowired
    RouteRepository routeRepository;

    /**
     * 根据用户id获取进行中的订单信息
     * @param id
     * @return
     */
    public List<Order> getCurOrders(Integer id){//uid应该等于cus_id
        return jdbcTemplate.query("select gg.group_id,gg.cus_id from group_cus_takes gg" +
                        " inner join `group` g on g.group_id=gg.group_id where cus_id=? and g.acitivated='1';",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1,id);
                    }
                }, new RowMapper<Order>() {
                    @Override
                    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
                        return getOrder(resultSet);
                    }
                });
    }
    public List<OrderInfo> getOrderInfos(List<Order> orders){
        List<OrderInfo> orderInfos = new ArrayList<>();
        for(Order order:orders){
            orderInfos.add(jdbcTemplate.query("select group_id,guide_id,route_id,price," +
                    "start_time,end_time,title from `group` where group_id = ?;",
                    new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setInt(1,order.getGroupId());
                }
            }, new RowMapper<OrderInfo>() {
                @Override
                public OrderInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                    OrderInfo orderInfo = new OrderInfo();
                    orderInfo.setGroupId(resultSet.getInt(1)+"");
                    orderInfo.setGuideId(resultSet.getInt(2)+"");
                    orderInfo.setRouteId(resultSet.getInt(3)+"");
                    orderInfo.setPrice(resultSet.getDouble(4)+"");
                    orderInfo.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(resultSet.getDate(5)));
                    orderInfo.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(resultSet.getDate(6)));
                    orderInfo.setGroupTitle(resultSet.getString(7));
                    return orderInfo;
                }
            }).get(0));
        }
        HashMap<Integer,String> guide = new HashMap<>();
        HashMap<Integer,String> route = new HashMap<>();
        for(OrderInfo orderInfo:orderInfos){
            int guideId = Integer.parseInt(orderInfo.getGuideId());
            if(guide.containsKey(guideId))
                orderInfo.setGuideName(guide.get(guideId));
            else{
                String guideName = guideRepository.getGuideNameByGuideId(guideId);
                guide.put(guideId,guideName);
                orderInfo.setGuideName(guideName);
            }
            int routeId = Integer.parseInt(orderInfo.getRouteId());
            if(route.containsKey(routeId))
                orderInfo.setGuideName(route.get(routeId));
            else{
                String routeName = routeRepository.getRouteByRouteId(routeId);
                route.put(routeId,routeName);
                orderInfo.setRoute(routeName);
            }
        }
        return orderInfos;
    }
    /**
     * 根据用户Id获取历史订单信息
     * @param id
     * @return
     */
    public List<Order> getHistoryOrders(Integer id){
        return jdbcTemplate.query("select gg.group_id,gg.cus_id from group_cus_takes gg" +
                        " inner join `group` g on g.group_id=gg.group_id where cus_id=? and g.acitivated='0';",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1,id);
                    }
                }, new RowMapper<Order>() {
                    @Override
                    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
                        return getOrder(resultSet);
                    }
                });
    }

    private Order getOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setGroupId(resultSet.getInt(1));
        order.setUserId(resultSet.getInt(2));
        return order;
    }
}
