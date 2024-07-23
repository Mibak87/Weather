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
import service.OpenWeatherApiService;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Date;
import java.util.Optional;

@WebServlet(name = "FindLocationController", value = "/location")
public class FindLocationController extends BaseController {
    private static final Logger logger = LogManager.getLogger(FindLocationController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
                if (location.isEmpty()) {
                    context.setVariable("error", "Поле ввода пустое. Введите название города.");
                    templateEngine.process("location", context, response.getWriter());
                } else {
                    try {
                        CitiesResponseDto[] citiesResponseDto = openWeatherApiService.getCities(location);
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
