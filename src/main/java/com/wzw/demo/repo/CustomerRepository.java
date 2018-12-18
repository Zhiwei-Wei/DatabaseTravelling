package com.wzw.demo.repo;

import com.wzw.demo.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 游客信息查询，游客完善信息
 * <br>游客加入旅游团，退订单等等
 */
@Repository
public class CustomerRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Customer> getAllCustomers(){
        return jdbcTemplate.query("select * from customer;", new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
                Customer customer = new Customer();
                customer.setCusId(resultSet.getInt(1));
                customer.setIdentity(resultSet.getString(2));
                customer.setRealName(resultSet.getString(3));
                customer.setWorkUnit(resultSet.getString(4));
                customer.setVocation(resultSet.getString(5));
                customer.setPhone(resultSet.getString(6));
                customer.setSex(resultSet.getString(7));
                return customer;
            }
        });
    }

    public String getCustomerNameById(Integer id){
        List<String> integers = jdbcTemplate.query("select real_name from customer where cus_id=?",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setInt(1, id);
                    }
                }, new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getString(1);
                    }
                });
        return integers.size()>0?integers.get(0):null;
    }

    public void joinCustomersIntoGroup(Integer groupId, List<Customer> customers){

    }
}
