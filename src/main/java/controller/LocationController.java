package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.WeatherResponseDto;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "LocationController", value = "/location")
public class LocationController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LocationController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String location = request.getParameter("location");
        URL url = new URL(Util.getApiUrl(location));
        logger.info("ApiUrl: " + Util.getApiUrl(location));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        logger.info("Response Code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuffer responseData = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    responseData.append(inputLine);
                }
                logger.info(responseData.toString());
                ObjectMapper objectMapper = new ObjectMapper();
                WeatherResponseDto weatherResponseDto = objectMapper
                        .readValue(responseData.toString(), WeatherResponseDto.class);
                logger.info(weatherResponseDto.toString());
                response.sendRedirect("weather");
            }
        } else {
            //Обработка ошибки
        }
    }
}