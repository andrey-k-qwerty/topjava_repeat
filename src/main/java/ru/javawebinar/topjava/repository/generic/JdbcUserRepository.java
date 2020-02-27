package ru.javawebinar.topjava.repository.generic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.generic.User;

@Repository()
public class JdbcUserRepository extends JdbcRepository<Integer, User> implements UserRepository<Integer, User> {

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);

        sqlInsert = "INSERT INTO users (name, email, password, registered, enabled, calories_per_day) " +
            "VALUES ( :name, :email, :password, :registered, :enabled, :caloriesPerDay) RETURNING id";

         sqlUpdate = "UPDATE users SET name=:name, email=:email, password=:password, " +
                "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id";
         sqlDelete = "DELETE FROM users WHERE id=?";

         sqlGetbyID = "SELECT * FROM users WHERE id=?";

         sqlGetAll = "SELECT * FROM users ORDER BY name, email";

          sqlTable = "users";
    }
     @Override
    public User getByEmail(String email) {
        return  DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email));
    }
}
