package controller;

import dto.RegistrationDto;
import exceptions.UserAlreadyExistsException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.RegistrationService;

import java.io.IOException;

@WebServlet(name = "RegistrationController", value = "/registration")
public class RegistrationController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(RegistrationController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("RegistrationController, Get");
        RequestDispatcher dispatcher = request.getRequestDispatcher("registration.html");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        logger.info("Login: " + login);
        logger.info("Password1: " + password);
        logger.info("Password2: " + passwordRepeat);
        if (login.isEmpty() || password.isEmpty()) {
            logger.info("The fields should not be empty!");
        } else if (!password.equals(passwordRepeat)) {
            logger.info("Passwords don't match!");
        } else if (password.length() < 3) {
            logger.info("The password is too short!");
        } else {
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            RegistrationDto registrationDto = new RegistrationDto(sessionId, login, password);
            try {
                new RegistrationService().registration(registrationDto);
                response.sendRedirect("weather");
            } catch (UserAlreadyExistsException e) {
                response.sendRedirect("registration");
            }

        }
    }
}
