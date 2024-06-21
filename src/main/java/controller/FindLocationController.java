package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.SessionsDao;
import dto.CitiesResponseDto;
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
import utils.ThymeleafUtil;
import utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
                URL url = new URL(Util.getCitiesApiUrl(location));
                logger.info("ApiUrl: " + Util.getCitiesApiUrl(location));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                logger.info("Response Code: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String inputLine;
                        StringBuffer responseData = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            responseData.append(inputLine);
                        }
                        logger.info(responseData.toString());
                        ObjectMapper objectMapper = new ObjectMapper();
                        CitiesResponseDto[] citiesResponseDto = objectMapper
                                .readValue(responseData.toString(), CitiesResponseDto[].class);
                        logger.info(citiesResponseDto.toString());
                        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
                                .getAttribute(ThymeleafUtil.TEMPLATE_ENGINE_ATTR);
                        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                                .buildExchange(request, response);
                        WebContext context = new WebContext(webExchange);
                        context.setVariable("userName", userSession.getUser().getLogin());
                        context.setVariable("citiesResponse", citiesResponseDto);
                        templateEngine.process("location", context, response.getWriter());
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/templates/location.html");
                        dispatcher.forward(request, response);
                    }
                } else {
                    //Обработка ошибки
                }
            }
        }
    }
}
