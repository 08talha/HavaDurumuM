package com.example.talha.havadurumum;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class ServiceBaglanti {
    private static final String URL = "http://api.openweathermap.org/";

    public static Retrofit connectHava() {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
