package dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDAO implements AbstractDAO<Integer,Meal> {


    @Override
    public List<Meal> findAll() {
        return null;
    }

    @Override
    public Meal findById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean create(Meal entity) {
        return false;
    }

    @Override
    public Meal update(Meal entity) {
        return null;
    }
}
