package com.wzw.demo.repo;

import com.netflix.discovery.converters.Auto;
import com.wzw.demo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class ContractRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    GuideRepository guideRepository;
    @Lazy
    @Autowired
    CustomerRepository customerRepository;
    /**
     * 根据组队Id和用户Id获取合同Id
     * @param groupId
     * @param cusId
     * @return
     */
    public Integer getContractIdByGroupIdAndCusId(Integer groupId, Integer cusId){
        List<Integer> integers = jdbcTemplate.query("select contract_id from contract where cus_id=? and " +
                "group_id = ?;", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, cusId);
                preparedStatement.setInt(2, groupId);
            }
        }, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(1);
            }
        });
        return integers.size()>0?integers.get(0):null;
    }

    /**
     * 根据合同Id获取签订日期
     */
    public String getDateByContractId(Integer contractId){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<String> strings = jdbcTemplate.query("select c.date from contract c where c.contract_id = ?;"
                , new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1,contractId);
                    }
                }, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getString(1);
                    }
                });
        return strings.size()>0?strings.get(0):null;
    }

    public void insertContractByCusIdAndGroupId(Integer groupId, Integer cid){
        Group group = groupRepository.getGroupInfoByGroupId(groupId);
        jdbcTemplate.update("insert into contract(guide_id,company_id,route_id,type_id,insurance,cus_id,group_id)" +
                " value (?,?,?,?,?,?,?)", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,group.getGuideId());
                preparedStatement.setInt(2,companyRepository.getCompanyIdByGuideId(group.getGuideId()));
                preparedStatement.setInt(3,group.getRouteId());
                preparedStatement.setInt(4,1);
                preparedStatement.setString(5,"没有保险");
                preparedStatement.setInt(6,cid);
                preparedStatement.setInt(7,groupId);
            }
        });
    }

    public List<RouteItem> getAccountByRoute(Integer year, Integer month, Integer companyId){
        return jdbcTemplate.query(" select sum(price),route_id from `group` where route_id in " +
                        "(select route_id from contract c " +
                        " where year(c.date)=? and month(c.date)=? and c.company_id=? " +
                        "group by c.route_id ) group by route_id;",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1, year);
                        preparedStatement.setInt(2, month);
                        preparedStatement.setInt(3,companyId);
                    }
                }, new RowMapper<RouteItem>() {
                    @Override
                    public RouteItem mapRow(ResultSet resultSet, int i) throws SQLException {
                        RouteItem route = new RouteItem();
                        route.setTotalPrice(resultSet.getDouble(1));
                        route.setRoute(routeRepository.getRouteByRouteId(resultSet.getInt(2)));
                        return route;
                    }
                });
    }
    public List<GuideItem> getAccountByGuide(Integer year, Integer month, Integer companyId){
        return jdbcTemplate.query(" select sum(price),guide_id  from `group` where guide_id in " +
                        "(select guide_id from contract c " +
                        " where year(c.date)=? and month(c.date)=? and c.company_id=? " +
                        "group by c.guide_id ) group by guide_id;",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1, year);
                        preparedStatement.setInt(2, month);
                        preparedStatement.setInt(3,companyId);
                    }
                }, new RowMapper<GuideItem>() {
                    @Override
                    public GuideItem mapRow(ResultSet resultSet, int i) throws SQLException {
                        GuideItem guideItem = new GuideItem();
                        guideItem.setTotalPrice(resultSet.getDouble(1));
                        guideItem.setGuideId(resultSet.getInt(2));
                        guideItem.setGuideName(guideRepository.getGuideNameByGuideId(guideItem.getGuideId()));
                        return guideItem;
                    }
                });
    }
    public List<CusItem> getAccountByCustomer(Integer year, Integer month, Integer companyId){
        return jdbcTemplate.query(" select sum(price),c.cus_id  from `group` g inner join group_cus_takes gg " +
                        "on g.group_id=gg.group_id inner join contract c on c.cus_id=gg.cus_id " +
                        " where year(c.date)=? and month(c.date)=? and c.company_id=? " +
                        " group by c.cus_id;",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1, year);
                        preparedStatement.setInt(2, month);
                        preparedStatement.setInt(3,companyId);
                    }
                }, new RowMapper<CusItem>() {
                    @Override
                    public CusItem mapRow(ResultSet resultSet, int i) throws SQLException {
                        CusItem cusItem = new CusItem();
                        cusItem.setTotalPrice(resultSet.getDouble(1));
                        cusItem.setCusId(resultSet.getInt(2));
                        cusItem.setCusName(customerRepository.getCustomerNameById(cusItem.getCusId()));
                        return cusItem;
                    }
                });
    }
}
