package utils;

import java.util.UUID;

public class UtilityClass {

    public static String getNewCorrelationId(){
        return UUID.randomUUID().toString();
    }
}
