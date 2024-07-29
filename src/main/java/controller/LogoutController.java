package controller;

import dao.SessionsDao;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Util;

import java.io.IOException;

@WebServlet(name = "LogoutController", value = "/logout")
public class LogoutController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LogoutController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        String sessionId = Util.getSessionIdFromCookies(cookies);
        new SessionsDao().delete(sessionId);
        logger.info("Logout is successful.");
        response.sendRedirect("authorization");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
