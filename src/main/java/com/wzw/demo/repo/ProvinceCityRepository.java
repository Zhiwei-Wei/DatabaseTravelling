package com.wzw.demo.repo;

import com.wzw.demo.vo.City;
import com.wzw.demo.vo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProvinceCityRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Province> getAllProvinceCity(){
        List<Province> provinces = jdbcTemplate.query("select province_id,`name` from province;",
                new RowMapper<Province>() {
                    @Override
                    public Province mapRow(ResultSet resultSet, int i) throws SQLException {
                        Province province = new Province();
                        province.setProvinceId(resultSet.getInt(1));
                        province.setName(resultSet.getString(2));
                        province.setCities(jdbcTemplate.query("select city_id,`name`,province_id from city " +
                                        "where province_id=?;", new PreparedStatementSetter() {
                                    @Override
                                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                                        preparedStatement.setInt(1,province.getProvinceId());
                                    }
                                }
                                , new RowMapper<City>() {

                                    @Override
                                    public City mapRow(ResultSet resultSet, int i) throws SQLException {
                                        City city = new City();
                                        city.setCityId(resultSet.getInt(1));
                                        city.setName(resultSet.getString(2));
                                        city.setProvinceId(resultSet.getInt(3));
                                        return city;
                                    }
                                }));
                        return province;
                    }
                });
        return provinces;
    }
}
