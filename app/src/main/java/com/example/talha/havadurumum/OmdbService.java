package com.example.talha.havadurumum;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by talha on 12.04.2017.
 */

public interface OmdbService {
    //@Header("a26d26aa2d7b42b27524a3fe389b136f") String apiKey,
    //data/2.5/
    @GET("data/2.5/weather")
    Call<HavaDurumu> getSyss(@Query("lat") double latitude,
                             @Query("lon") double longitude,
                             @Query("units") String units,
                             @Query("APPID") String appid);

}
