package com.util;

public class TypeUtil {
    public static Boolean isNum(String params){
        try {
            Integer.parseInt(params);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
