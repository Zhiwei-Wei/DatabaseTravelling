package com.wzw.demo.repo;

import com.wzw.demo.vo.User;
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
 * 用户注册登录
 */
@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public User findUserByUserId(Integer id){
        List<User> list = jdbcTemplate.query("select nickname,password,role,phone from " +
                "user where uid=?;", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, id);
            }
        }, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUid(id);
                user.setNickname(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setRole(resultSet.getInt(3));
                user.setPhone(resultSet.getString(4));
                return user;
            }
        });
        if(list.size()>=1)
            return list.get(0);
        return null;
    }
    public User findUserByPhone(String phone){
        List<User> list = jdbcTemplate.query("select nickname,password,role,uid from " +
                "user where phone=?;", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, phone);
            }
        }, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setPhone(phone);
                user.setUid(resultSet.getInt(4));
                user.setNickname(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setRole(resultSet.getInt(3));
                return user;
            }
        });
        if(list.size()>=1)
            return list.get(0);
        return null;
    }
    public void register(User user){
        jdbcTemplate.update("insert into `user`(password, role, phone, nickname)" +
                " values (?,?,?,?)", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,user.getPassword());
                preparedStatement.setInt(2,user.getRole());
                preparedStatement.setString(3,user.getPhone());
                preparedStatement.setString(4,user.getNickname());
            }
        });
    }
}
