package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import service.OpenWeatherApiService;
import utils.ThymeleafUtil;

import java.io.IOException;
import java.net.http.HttpClient;


public class BaseController extends HttpServlet {
    protected OpenWeatherApiService openWeatherApiService;
    protected TemplateEngine templateEngine;
    protected WebContext context;
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpClient httpClient = HttpClient.newHttpClient();
        openWeatherApiService = new OpenWeatherApiService(httpClient);
        templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafUtil.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        context = new WebContext(webExchange);
        super.service(request, response);
    }
}
