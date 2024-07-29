package controller;

import dto.CitiesResponseDto;
import exceptions.ErrorApiConnectionException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "FindLocationController", value = "/location")
public class FindLocationController extends BaseController {
    private static final Logger logger = LogManager.getLogger(FindLocationController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String location = request.getParameter("location");
        context.setVariable("userName", request.getAttribute("login"));
        if (location.isEmpty()) {
            context.setVariable("error", "Поле ввода пустое. Введите название города.");
            templateEngine.process("location", context, response.getWriter());
        } else {
            try {
                CitiesResponseDto[] citiesResponseDto = openWeatherApiService.getCities(location);
                logger.info(citiesResponseDto.toString());
                context.setVariable("citiesResponse", citiesResponseDto);
                templateEngine.process("location", context, response.getWriter());
            } catch (ErrorApiConnectionException e) {
                context.setVariable("error", "Не удается подключиться к серверу с погодой.");
                templateEngine.process("location", context, response.getWriter());
            }
        }


    }
}
