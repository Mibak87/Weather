package controller;

import dto.WeatherResponseDto;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import service.WeatherService;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "WeatherController", value = "/weather")
public class WeatherController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(WeatherController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = (String) request.getAttribute("login");
        logger.info("Login: " + login);
        List<WeatherResponseDto> dtoList = new WeatherService().getWeather(login);
        logger.info("WeatherResponseDto: " + dtoList.toString());
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafUtil.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);
        context.setVariable("userName", login);
        context.setVariable("weatherDto", dtoList);
        templateEngine.process("weather", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
