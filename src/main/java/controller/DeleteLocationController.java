package controller;

import dto.elements.Coord;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.LocationService;

import java.io.IOException;

@WebServlet(name = "DeleteLocationController", value = "/deletelocation")
public class DeleteLocationController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DeleteLocationController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        String userLogin = request.getParameter("userLogin");
        logger.info("Delete location by user: " + userLogin);
        new LocationService().deleteLocation(new Coord(longitude,latitude),userLogin);
        response.sendRedirect("weather");
    }
}
