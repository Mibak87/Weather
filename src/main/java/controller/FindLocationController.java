package controller;

import dao.SessionsDao;
import dto.CitiesResponseDto;
import exceptions.ErrorApiConnectionException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import service.OpenWeatherApiService;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@WebServlet(name = "FindLocationController", value = "/location")
public class FindLocationController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(FindLocationController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sessionId = request.getSession().getId();
        Optional<UserSession> optionalUserSession = new SessionsDao().findById(sessionId);
        if (optionalUserSession.isEmpty()) {
            response.sendRedirect("authorization");
        } else {
            UserSession userSession = optionalUserSession.get();
            if (new Date().after(userSession.getExpiresAt())) {
                response.sendRedirect("authorization");
            } else {
                String location = request.getParameter("location");
                TemplateEngine templateEngine = (TemplateEngine) getServletContext()
                        .getAttribute(ThymeleafUtil.TEMPLATE_ENGINE_ATTR);
                IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                        .buildExchange(request, response);
                WebContext context = new WebContext(webExchange);
                if (location.isEmpty()) {
                    context.setVariable("error", "Поле ввода пустое. Введите название города.");
                    templateEngine.process("location", context, response.getWriter());
                } else {
                    try {
                        CitiesResponseDto[] citiesResponseDto = new OpenWeatherApiService().getCities(location);
                        logger.info(citiesResponseDto.toString());
                        context.setVariable("userName", userSession.getUser().getLogin());
                        context.setVariable("citiesResponse", citiesResponseDto);
                        templateEngine.process("location", context, response.getWriter());
                    } catch (ErrorApiConnectionException e) {
                        context.setVariable("error", "Не удается подключиться к серверу с погодой.");
                        templateEngine.process("location", context, response.getWriter());
                    }
                }
            }
        }
    }
}
