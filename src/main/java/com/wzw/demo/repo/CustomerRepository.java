package com.wzw.demo.repo;

import com.wzw.demo.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
    @Lazy
    @Autowired
    ContractRepository contractRepository;
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
    public Integer getCustomerIdByPhone(String phone){
        List<Integer> integers = jdbcTemplate.query("select cus_id from customer where phone=?",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setString(1, phone);
                    }
                }, new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getInt(1);
                    }
                });
        return integers.size()>0&&integers.get(0)!=null?integers.get(0):null;
    }
    public void insertCustomer(Customer customer){
        jdbcTemplate.update("insert into customer(`identity`,real_name,work_unit,vocation,phone,sex)" +
                "value (?,?,?,?,?,?)", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,customer.getIdentity());
                preparedStatement.setString(2,customer.getRealName());
                preparedStatement.setString(3,customer.getWorkUnit());
                preparedStatement.setString(4,customer.getVocation());
                preparedStatement.setString(5,customer.getPhone());
                preparedStatement.setString(6,customer.getSex());
            }
        });
    }
    public void joinCustomersIntoGroup(Integer groupId, List<Customer> customers, Integer uid){
        String sql = "insert into group_cus_takes(group_id,cus_id,uid) values(?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Customer customer = customers.get(i);
                Integer cid = getCustomerIdByPhone(customer.getPhone());
                if(cid==null){
                    insertCustomer(customer);
                }
                cid = getCustomerIdByPhone(customer.getPhone());
                preparedStatement.setInt(1,groupId);
                preparedStatement.setInt(2,cid);
                preparedStatement.setInt(3,uid);
                contractRepository.insertContractByCusIdAndGroupId(groupId,cid);
            }

            @Override
            public int getBatchSize() {
                return customers.size();
            }
        });
    }
}
