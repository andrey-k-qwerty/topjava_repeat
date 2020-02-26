package ru.javawebinar.topjava.repository.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.javawebinar.topjava.model.generic.AbstractBaseEntity;

import java.util.Collection;
import java.util.List;

public abstract class JdbcRepository<I extends Number, T extends AbstractBaseEntity<I>> implements Repository<I, T> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private Class<T> entityBeanType;

    protected final BeanPropertyRowMapper<T> ROW_MAPPER = BeanPropertyRowMapper.newInstance(getEntityBeanType());

    protected final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    //    protected  String sqlUpdate = "UPDATE users SET name=:name, email=:email, password=:password, " +
    //            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id";

    // переобределить в потомках
    protected String sqlUpdate = "";
    //    protected  String sqlDelete = "DELETE FROM users WHERE id=?";
    protected String sqlDelete = "";
    //    String sqlGetbyID = "SELECT * FROM users WHERE id=?";
    protected String sqlGetbyID = "";
    //    String sqlGetAll = "SELECT * FROM users ORDER BY name, email";
    String sqlGetAll = "";

    public JdbcRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }

    @Override
    public T save(T user) {
        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId((I) newKey);
        } else {
            if (namedParameterJdbcTemplate.update(
                    sqlUpdate, map) == 0) {
                return null;
            }
        }
        return user;
    }

    @Override
    public boolean delete(I id) {
        return jdbcTemplate.update(sqlDelete, id) != 0;
    }

    @Override
    public T get(I id) {
        List<T> users = jdbcTemplate.query(sqlGetbyID, ROW_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<T> getAll() {
        return jdbcTemplate.query(sqlGetAll, ROW_MAPPER);
    }
}
