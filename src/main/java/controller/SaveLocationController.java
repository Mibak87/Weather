package controller;

import dto.SaveLocationDto;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.LocationService;

import java.io.IOException;

@WebServlet(name = "SaveLocationController", value = "/savelocation")
public class SaveLocationController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(SaveLocationController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cityName = request.getParameter("cityName");
        String lon = request.getParameter("lon");
        String lat = request.getParameter("lat");
        String userLogin = (String) request.getAttribute("login");
        SaveLocationDto saveLocationDto = SaveLocationDto.builder()
                .cityName(cityName)
                .lon(lon)
                .lat(lat)
                .login(userLogin)
                .build();
        logger.info("saveLocationDto: " + saveLocationDto.toString());
        new LocationService().saveLocation(saveLocationDto);
        response.sendRedirect("weather");
    }
}
