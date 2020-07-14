package com.oxfordplus.hypen.utils;

import com.oxfordplus.hypen.interfaces.ProfileInterface;
import com.oxfordplus.hypen.retrofit.RetrofitClient;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.8.32:8080/mycareNG/";

    public static ProfileInterface getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ProfileInterface.class);
    }
}
