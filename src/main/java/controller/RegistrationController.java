package controller;

import dto.RegistrationDto;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.RegistrationService;

import java.io.IOException;

@WebServlet(name = "RegistrationController", value = "/registration")
public class RegistrationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("registration.html");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        if (login.isEmpty() || password.isEmpty()) {
            System.out.println("Поля не должны быть пустыми!");
        } else {
            if (!password.equals(passwordRepeat)) {
                System.out.println("Пароли не совпадают!");
            } else {
                HttpSession session = request.getSession();
                String sessionId = session.getId();
                RegistrationDto registrationDto = new RegistrationDto(sessionId,login,password);
                new RegistrationService().registration(registrationDto);
                response.sendRedirect("weather");
            }
        }
    }
}
