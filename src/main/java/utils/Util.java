package utils;

import java.util.Date;

public class Util {
    public static Date getExpiryDate(int n) {
        Date currentDate = new Date();
        long updatedTimeInMillis = currentDate.getTime() + (n * 60 * 60 * 1000);
        return new Date(updatedTimeInMillis);
    }
}
