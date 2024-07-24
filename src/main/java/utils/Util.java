package utils;

import dto.elements.Coord;
import jakarta.servlet.http.Cookie;

import java.util.Date;

public class Util {
    private static final int HOURS_BEFORE_SESSION_TIME_EXPIRES = 5;
    private static final String COORD_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private static final String CITY_URL = "https://api.openweathermap.org/geo/1.0/direct?q=";

    public static Date getExpiryDate() {
        Date currentDate = new Date();
        long updatedTimeInMillis = currentDate.getTime() + (HOURS_BEFORE_SESSION_TIME_EXPIRES * 60 * 60 * 1000);
        return new Date(updatedTimeInMillis);
    }

    public static String getCoordsApiUrl(Coord coord) {
        String api_key = "&appid=" + System.getenv("API_KEY");
        String lat = "lat=" + coord.getLat();
        String lon = "&lon=" + coord.getLon();
        String units = "&units=metric";
        String apiUrl = COORD_URL + lat + lon + api_key + units;
        return apiUrl;
    }

    public static String getCitiesApiUrl(String location) {
        String api_key = "&appid=" + System.getenv("API_KEY");
        String limit = "&limit=5";
        String citiesApiUrl = CITY_URL + location + limit + api_key;
        return citiesApiUrl;
    }

    public static String getSessionIdFromCookies(Cookie[] cookies) {
        if(cookies !=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sessionId")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
}
