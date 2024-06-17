package utils;

import java.util.Date;

public class Util {
    private static final int HOURS_BEFORE_SESSION_TIME_EXPIRES = 5;
    public static Date getExpiryDate() {
        Date currentDate = new Date();
        long updatedTimeInMillis = currentDate.getTime() + (HOURS_BEFORE_SESSION_TIME_EXPIRES * 60 * 60 * 1000);
        return new Date(updatedTimeInMillis);
    }

    public static String getApiUrl(String location) {
        String apiKey = "dc6eaa0000964813c5c502600f228fa2";
        String url = "https://api.openweathermap.org/data/2.5/weather?";
        String units = "&units=metric";
        String apiUrl = url + "q=" + location + "&appid=" + apiKey + units;
        return apiUrl;
    }
}
