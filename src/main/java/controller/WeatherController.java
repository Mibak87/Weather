package controller;

import dao.LocationsDao;
import dao.SessionsDao;
import dto.WeatherResponseDto;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Location;
import model.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import service.WeatherService;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
                List<WeatherResponseDto> dtoList = new WeatherService().getWeather(userSession.getUser().getLogin());
                TemplateEngine templateEngine = (TemplateEngine) getServletContext()
                        .getAttribute(ThymeleafUtil.TEMPLATE_ENGINE_ATTR);
                IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                        .buildExchange(request, response);
                WebContext context = new WebContext(webExchange);
                context.setVariable("userName", userSession.getUser().getLogin());
                context.setVariable("weatherDto",dtoList);
                templateEngine.process("weather",context,response.getWriter());
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/templates/weather.html");
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
