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
import service.AuthorizationService;
import utils.Util;

@WebServlet(name = "AuthorizationController", value = "/authorization")
public class AuthorizationController extends BaseController {
    private static final Logger logger = LogManager.getLogger(AuthorizationController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("AuthorizationController, Get");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/templates/authorization.html");
        dispatcher.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, UserNotFoundException, WrongPasswordException {
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
            new AuthorizationService().authorize(userSession);
            response.sendRedirect("weather");
        }
    }
}