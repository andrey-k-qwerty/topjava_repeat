package ru.javawebinar.topjava.web;

import dao.MealDAO;
import org.slf4j.Logger;
import ru.javawebinar.topjava.util.DataSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealDAO MEAL_DAO = new MealDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         log.debug("redirect to meals");
         request.setAttribute("meals",filteredByStreams(MEAL_DAO.findAll(), LocalTime.MIN, LocalTime.MAX, 2000));
         request.getRequestDispatcher("/meals.jsp").forward(request, response);
       // response.sendRedirect("meals.jsp");
    }
}
