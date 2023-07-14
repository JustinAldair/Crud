package com.example.orderxpress;

public class APIUtils {
    private static final String BASE_URL = "http://192.168.0.227:3000/";

    public static String getFullUrl(String route) {
        return BASE_URL + route;
    }
}
