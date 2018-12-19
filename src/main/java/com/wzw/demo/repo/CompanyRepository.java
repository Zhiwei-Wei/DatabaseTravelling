package com.wzw.demo.repo;

import com.wzw.demo.vo.Company;
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
 * 公司登录，注册，查询分类账，总账
 * <br>查询公司旗下的路线等等
 */
@Repository
public class CompanyRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ProvinceCityRepository provinceCityRepository;
    public Integer getCompanyIdByGuideId(Integer guideId){
        List<Integer> integers = jdbcTemplate.query("select company_id from employee inner " +
                "join guide on employee.employee_id=guide.employee_id where guide_id=?", new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, guideId);
            }
        }, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(1);
            }
        });
        return  integers.get(0);
    }
    public List<Company> getAllCompanies(){
        return jdbcTemplate.query("select * from company;", new RowMapper<Company>() {
            @Override
            public Company mapRow(ResultSet resultSet, int i) throws SQLException {
                Company company = new Company();
                company.setCompId(resultSet.getInt(1));
                company.setName(resultSet.getString(2));
                company.setAddress(resultSet.getString(3));
                company.setProvince(provinceCityRepository.getProvinceById(resultSet.getInt(4)));
                company.setCity(provinceCityRepository.getCityById(resultSet.getInt(4),resultSet.getInt(5)));
                return company;
            }
        });
    }
}
