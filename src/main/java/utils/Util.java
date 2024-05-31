package utils;

import java.util.Date;

public class Util {
    private static final int HOURS_BEFORE_SESSION_TIME_EXPIRES = 5;
    public static Date getExpiryDate() {
        Date currentDate = new Date();
        long updatedTimeInMillis = currentDate.getTime() + (HOURS_BEFORE_SESSION_TIME_EXPIRES * 60 * 60 * 1000);
        return new Date(updatedTimeInMillis);
    }
}
