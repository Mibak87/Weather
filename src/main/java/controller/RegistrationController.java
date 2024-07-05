package controller;

import exceptions.UserAlreadyExistsException;
import jakarta.servlet.*;
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
import service.RegistrationService;
import utils.ThymeleafUtil;
import utils.Util;

import java.io.IOException;

@WebServlet(name = "RegistrationController", value = "/registration")
public class RegistrationController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegistrationController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("RegistrationController, Get");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/templates/registration.html");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine templateEngine = (TemplateEngine) getServletContext()
                .getAttribute(ThymeleafUtil.TEMPLATE_ENGINE_ATTR);
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(request, response);
        WebContext context = new WebContext(webExchange);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        logger.info("Login: " + login);
        logger.info("Password1: " + password);
        logger.info("Password2: " + passwordRepeat);
        if (login.isEmpty() || password.isEmpty()) {
            logger.info("The fields should not be empty!");
            context.setVariable("error","Поля не должны быть пустыми!");
            templateEngine.process("registration", context, response.getWriter());
        } else if (!password.equals(passwordRepeat)) {
            logger.info("Passwords don't match!");
            context.setVariable("passwordError","Пароли не совпадают!");
            templateEngine.process("registration", context, response.getWriter());
        } else if (password.length() < 3) {
            logger.info("The password is too short!");
            context.setVariable("passwordLengthError","Пароль слишком короткий!");
            templateEngine.process("registration", context, response.getWriter());
        } else {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            Cookie cookie = new Cookie("sessionId",sessionId);
            response.addCookie(cookie);
            User user = new User(login,hashedPassword);
            UserSession userSession = new UserSession(sessionId,user, Util.getExpiryDate());
            try {
                new RegistrationService().registration(userSession);
                logger.info("Registration of user '" + login + "' is successful!");
                response.sendRedirect("weather");
            } catch (UserAlreadyExistsException e) {
                logger.info("User with login '" + login + "' already exists!");
                context.setVariable("userError","Пользователь с таким логином уже существует!");
                templateEngine.process("registration", context, response.getWriter());
            }

        }
    }
}
