package ru.javawebinar.topjava.repository.generic;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.generic.User;

@Repository
public class JdbcUserRepository extends JdbcRepository<Integer, User> implements UserRepository<Integer, User> {
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);

        String sqlUpdate = "UPDATE users SET name=:name, email=:email, password=:password, " +
                "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id";
        String sqlDelete = "DELETE FROM users WHERE id=?";

        String sqlGetbyID = "SELECT * FROM users WHERE id=?";

        String sqlGetAll = "SELECT * FROM users ORDER BY name, email";
    }
     @Override
    public User getByEmail(String email) {
        return (User) jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
    }
}
