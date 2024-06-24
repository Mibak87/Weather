package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CitiesResponseDto;
import exceptions.ErrorApiConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FindLocationService {
    private static final Logger logger = LogManager.getLogger(FindLocationService.class);

    public CitiesResponseDto[] getCities(String location) throws IOException, ErrorApiConnectionException {
        URL url = new URL(Util.getCitiesApiUrl(location));
        logger.info("ApiUrl: " + Util.getCitiesApiUrl(location));
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
                return objectMapper.readValue(responseData.toString(), CitiesResponseDto[].class);
            }
        } else {
            throw new ErrorApiConnectionException("Error of API connection.");
        }
    }
}
