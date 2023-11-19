package com.release.core.repository;

import com.release.core.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.*;

public class JdbcTemplateUserRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired // 생성자 하나일 때 생략 가능
    public JdbcTemplateUserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("UserTable").usingGeneratedKeyColumns("userId");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userName", user.getName());
        parameters.put("userEmail", user.getUserEmail()); // Assuming you have an "email" field in your User class
        parameters.put("userPassword", user.getUserPassword());
        parameters.put("userRegisterDate", user.getUserRegisterDate());
        parameters.put("userIsAdmin", user.getUserIsAdmin());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        user.setId(key.longValue());
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> result = jdbcTemplate.query("select * from Usertable where userid = ?", userRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> result = jdbcTemplate.query("select * from Usertable where username = ?", userRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from Usertable", userRowMapper());
    }

    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("userId"));
            user.setName(rs.getString("userName"));
            return user;
        };
    }

    @Override
    public void deleteUser(User user) {
        return;
    }

    @Override
    public User updateUser(User user) {
        return user;
    }

    @Override
    public List<User> findAdminUsers() {
        return null;
    }

    @Override
    public List<User> findUsersWithPagination(int page, int pageSize) {
        return null;
    }

}
