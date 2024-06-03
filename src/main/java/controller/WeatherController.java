package controller;

import dao.SessionsDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@WebServlet(name = "WeatherController", value = "/weather")
public class WeatherController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(WeatherController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        logger.info("sessionId: " + sessionId);
        Optional<UserSession> optionalUserSession = new SessionsDao().findById(sessionId);
        if (optionalUserSession.isEmpty()) {
            response.sendRedirect("authorization");
        } else {
            UserSession userSession = optionalUserSession.get();
            if (new Date().after(userSession.getExpiresAt())) {
                response.sendRedirect("authorization");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("weather.html");
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
