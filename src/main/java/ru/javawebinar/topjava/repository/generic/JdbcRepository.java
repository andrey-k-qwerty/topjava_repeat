package ru.javawebinar.topjava.repository.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.javawebinar.topjava.model.generic.AbstractBaseEntity;

import java.util.List;

public abstract class JdbcRepository<I extends Number, T extends AbstractBaseEntity<I>> implements Repository<I, T> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    //https://stackoverflow.com/questions/3437897/how-to-get-a-class-instance-of-generics-type-t
    private Class<T> entityBeanType;

    public Class<I> getIdType() {
        return idType;
    }

    private Class<I> idType;
    protected final BeanPropertyRowMapper<T> ROW_MAPPER;//= BeanPropertyRowMapper.newInstance(getEntityBeanType());

    protected final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertEntity;

    //    protected  String sqlUpdate = "UPDATE users SET name=:name, email=:email, password=:password, " +
    //            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id";

    // переобределить в потомках!!!!!!
    protected String sqlUpdate = "";
    //    protected  String sqlDelete = "DELETE FROM users WHERE id=?";
    protected String sqlDelete = "";
    //    String sqlGetbyID = "SELECT * FROM users WHERE id=?";
    protected String sqlGetbyID = "";
    //    String sqlGetAll = "SELECT * FROM users ORDER BY name, email";
    protected String sqlGetAll = "";
    //     String sqlTable = "users";
    protected String sqlTable = "";
    //    final String sqlInsert = "INSERT INTO users (name, email, password, registered, enabled, calories_per_day) " +
//            "VALUES ( :name, :email, :password, :registered, :enabled, :caloriesPerDay) RETURNING id";
    protected String sqlInsert = "";

    public JdbcRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.insertEntity = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(sqlTable)
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        // https://stackoverflow.com/questions/3437897/how-to-get-a-class-instance-of-generics-type-t

        this.idType = (Class<I>) GenericTypeResolver.resolveTypeArguments(getClass(), JdbcRepository.class)[0];
        this.entityBeanType = (Class<T>) GenericTypeResolver.resolveTypeArguments(getClass(), JdbcRepository.class)[1];
        this.ROW_MAPPER = BeanPropertyRowMapper.newInstance(getEntityBeanType());
    }

    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }

    @Override
    public T save(T user) {
        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {

            // Этот вариант НЕ работает! фак! чето с sql(((...
            //   Number newKey = insertEntity.executeAndReturnKey(map);

            // Этот вариант работает
//            final Integer newKey = jdbcTemplate.queryForObject("INSERT INTO users (name, email, password, registered, enabled, calories_per_day) " +
//                            "VALU ?, ?, ?, ?, ?) RETURNING id",
//                    new Object[]{((User) user).getName(), ((User) user).getEmail(), ((User) user).getPassword(), ((User) user).getRegistered(), ((User) user).isEnabled(), ((User) user).getCaloriesPerDay()}, Integer.class);

            // Точто нужно))
            I newKey = namedParameterJdbcTemplate.queryForObject(sqlInsert, map, getIdType());
            user.setId(newKey);
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
