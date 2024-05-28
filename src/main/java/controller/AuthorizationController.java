package controller;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "AuthorizationController", value = "/authorization")
public class AuthorizationController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login.isEmpty() || password.isEmpty()) {
            System.out.println("Поля не должны быть пустыми!");
        } else {
            HttpSession session = request.getSession();
            String sessionId = session.getId();
        }
    }
}