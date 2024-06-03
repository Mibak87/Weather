package controller;

import java.io.*;
import java.util.Date;

import exceptions.UserNotFoundException;
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
public class AuthorizationController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AuthorizationController.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("AuthorizationController, Get");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        dispatcher.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        logger.info("Login: " + login);
        logger.info("Password: " + password);
        if (login.isEmpty() || password.isEmpty()) {
            logger.info("The fields should not be empty!");
        } else {
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            User user = new User(login,password);
            UserSession userSession = new UserSession(sessionId,user, Util.getExpiryDate());
            try {
                new AuthorizationService().authorize(userSession);
                logger.info("Authorization by user '" + login + "' was successful!");
                response.sendRedirect("weather");
            } catch (UserNotFoundException e) {
                logger.info("User '" + login + "' is not found!");
                response.sendRedirect("authorization");
            }
        }
    }
}