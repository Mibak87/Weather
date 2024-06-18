package controller;

import dto.SaveLocationDto;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import service.SaveLocationService;

import java.io.IOException;

@WebServlet(name = "SaveLocationController", value = "/savelocation")
public class SaveLocationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityName = request.getParameter("cityName");
        String lon = request.getParameter("lon");
        String lat = request.getParameter("lat");
        String userLogin = request.getParameter("userLogin");
        SaveLocationDto saveLocationDto = SaveLocationDto.builder()
                .cityName(cityName)
                .lon(lon)
                .lat(lat)
                .login(userLogin)
                .build();
        new SaveLocationService().saveLocation(saveLocationDto);
        response.sendRedirect("weather");
    }
}
