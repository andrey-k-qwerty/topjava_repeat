package dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DataSource;

import java.util.List;

public class MealDAO implements AbstractDAO<Integer, Meal> {
    DataSource dataSource = DataSource.getInstance();

    @Override
    public List<Meal> findAll() {
        return dataSource.getMeals();
    }

    @Override
    public Meal findById(Integer id) {
        return dataSource.getMeals().stream().filter(meal -> meal.getID() == id).findFirst().orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        return dataSource.getMeals().removeIf(meal -> meal.getID() == id);
    }

    @Override
    public boolean create(Meal meal) {
        if (meal.getID() == 0) {
            meal.setID(dataSource.getNextID());
        }
        return dataSource.getMeals().add(meal);

    }

    @Override
    public Meal update(Meal entity) {
     return    dataSource.getMeals().stream()
                .filter(meal -> meal.getID() == entity.getID())
                .peek(meal -> {
                    meal.setCalories(entity.getCalories());
                    meal.setDescription(entity.getDescription());
                    meal.setDateTime(entity.getDateTime());
                }).findFirst().orElse(null);
    }
}
