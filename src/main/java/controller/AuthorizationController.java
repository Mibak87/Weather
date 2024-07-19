package controller;

import java.io.*;

import exceptions.UserNotFoundException;
import exceptions.WrongPasswordException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.User;
import model.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import service.AuthorizationService;
import utils.ThymeleafUtil;
import utils.Util;

@WebServlet(name = "AuthorizationController", value = "/authorization")
public class AuthorizationController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AuthorizationController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("AuthorizationController, Get");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/templates/authorization.html");
        dispatcher.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafUtil.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        logger.info("Login: " + login);
        logger.info("Password: " + password);
        if (login.isEmpty() || password.isEmpty()) {
            logger.info("The fields should not be empty!");
            context.setVariable("error","Поля не должны быть пустыми!");
            templateEngine.process("authorization", context, response.getWriter());
        } else {
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            Cookie cookie = new Cookie("sessionId",sessionId);
            response.addCookie(cookie);
            User authorizingUser = new User(login,password);
            UserSession userSession = new UserSession(sessionId,authorizingUser, Util.getExpiryDate());
            try {
                new AuthorizationService().authorize(userSession);
                logger.info("Authorization by user '" + login + "' was successful!");
                response.sendRedirect("weather");
            } catch (UserNotFoundException e) {
                logger.info("User '" + login + "' is not found!");
                context.setVariable("error","Неправильные логин или пароль!");
                templateEngine.process("authorization", context, response.getWriter());
            } catch (WrongPasswordException e) {
                logger.info("Password for '" + login + "' is wrong!");
                context.setVariable("error","Неправильные логин или пароль!");
                templateEngine.process("authorization", context, response.getWriter());
            }
        }
    }
}