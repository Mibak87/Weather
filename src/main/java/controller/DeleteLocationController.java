package controller;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long locationId = Long.parseLong(request.getParameter("locationId"));
        new LocationService().deleteLocation(locationId);
        response.sendRedirect("weather");
    }
}
