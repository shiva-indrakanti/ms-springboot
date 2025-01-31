package com.ms.payment.common;

public class StringUtil {
    private static final String EMPTY_STRING = "";

    /**
     * checks if string is not null and empty
     */
    public static boolean isEmptyOrNotNull(String str){
        if(str == null) {
            return false;
        }
        return !str.equals(EMPTY_STRING);
    }

}
