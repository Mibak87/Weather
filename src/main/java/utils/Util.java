package utils;

import dto.elements.Coord;
import jakarta.servlet.http.Cookie;

import java.util.Date;

public class Util {
    private static final int HOURS_BEFORE_SESSION_TIME_EXPIRES = 5;
    private static final String API_KEY = "&appid=dc6eaa0000964813c5c502600f228fa2";
    public static Date getExpiryDate() {
        Date currentDate = new Date();
        long updatedTimeInMillis = currentDate.getTime() + (HOURS_BEFORE_SESSION_TIME_EXPIRES * 60 * 60 * 1000);
        return new Date(updatedTimeInMillis);
    }

    public static String getCoordsApiUrl(Coord coord) {
        //String api_key = System.getenv("API_KEY");
        String url = "https://api.openweathermap.org/data/2.5/weather?";
        String lat = "lat=" + coord.getLat();
        String lon = "&lon=" + coord.getLon();
        String units = "&units=metric";
        String apiUrl = url + lat + lon + API_KEY + units;
        return apiUrl;
    }

    public static String getCitiesApiUrl(String location) {
        //Херня какая-то с переменной окружения
        //String api_key = System.getenv("API_KEY");
        String url = "https://api.openweathermap.org/geo/1.0/direct?q=";
        String limit = "&limit=5";
        String citiesApiUrl = url + location + limit + API_KEY;
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
