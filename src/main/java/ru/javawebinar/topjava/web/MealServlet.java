package ru.javawebinar.topjava.web;

import dao.MealDAO;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.DataSource;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealDAO MEAL_DAO = new MealDAO();
    private static final String INSERT_OR_EDIT = "/editMeal.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward = "";
        String action = request.getParameter("action");
        action = action == null ? "" : action.toLowerCase();
        switch (action) {

            case "add": {
                forward = INSERT_OR_EDIT;
                // тут нужно создавать еду через базу с получением айдишника
                request.setAttribute("meal", new Meal(LocalDateTime.now(),"",0));
                break;
            }
            case "edit": {
                forward = INSERT_OR_EDIT;
                Integer id = Integer.parseInt(request.getParameter("Id"));
                request.setAttribute("meal", MEAL_DAO.findById(id));
                break;
            }
            case "delete": {
                Integer id = Integer.parseInt(request.getParameter("Id"));
                MEAL_DAO.delete(id);
            }
            default: {
                forward = LIST_MEAL;
                List<MealTo> allListMealTo = MealsUtil.filteredByStreams(MEAL_DAO.findAll(), LocalTime.MIN, LocalTime.MAX, 2000);
               // log.debug(allListMealTo.toString());
                request.setAttribute("meals", allListMealTo);

            }
        }

        log.debug("forward to {}",forward);
        request.getRequestDispatcher(forward).forward(request, response);
        // response.sendRedirect("meals.jsp");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String date = req.getParameter("date");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");

        Integer id = Integer.parseInt(req.getParameter("id"));
        if (id == null || id == 0)
            MEAL_DAO.create(new Meal( LocalDateTime.parse(date,formatter),description,Integer.parseInt(calories)));
        else
            MEAL_DAO.update(new Meal( id, LocalDateTime.parse(date,formatter),description,Integer.parseInt(calories)));

        List<MealTo> allListMealTo = MealsUtil.filteredByStreams(MEAL_DAO.findAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", allListMealTo);
        req.getRequestDispatcher(LIST_MEAL).forward(req, resp);
    }
}
