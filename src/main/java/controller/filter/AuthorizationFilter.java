package controller.filter;

import dao.SessionsDao;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Util;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@WebFilter(urlPatterns = "/weather")
public class AuthorizationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthorizationFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        String sessionId = Util.getSessionIdFromCookies(cookies);
        logger.info("SessionId: " + sessionId);
        if (sessionId.isEmpty()) {
            logger.info("SessionId is empty. It is a new User.");
            response.sendRedirect("authorization");
        } else {
            Optional<UserSession> optionalUserSession = new SessionsDao().findById(sessionId);
            if (optionalUserSession.isEmpty()) {
                logger.info("UserSession is empty.");
                response.sendRedirect("authorization");
            } else {
                UserSession userSession = optionalUserSession.get();
                if (new Date().after(userSession.getExpiresAt())) {
                    response.sendRedirect("authorization");
                } else {
                    logger.info("UserSession: " + userSession);
                    request.setAttribute("login", userSession.getUser().getLogin());
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }
    }
}
