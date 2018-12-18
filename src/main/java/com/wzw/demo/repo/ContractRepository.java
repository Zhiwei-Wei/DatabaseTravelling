package com.wzw.demo.repo;

import org.springframework.beans.factory.annotation.Autowired;
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
}
