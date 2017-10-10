package com.example.talha.havadurumum;

import com.google.gson.annotations.SerializedName;

/**
 * Created by talha on 13.04.2017.
 */

public class Status {
    @SerializedName("Response")
    private String response;
    @SerializedName("Error")
    private String error;
    public String getResponse(){return response;}

    public String getError(){return error;}
}
