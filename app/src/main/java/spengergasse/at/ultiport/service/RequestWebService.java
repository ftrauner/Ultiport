package spengergasse.at.ultiport.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import spengergasse.at.ultiport.entities.TransportRequest;

public interface RequestWebService {

    String BASIS_URL = "http://ultiport.htl5.org/";
    String REQUEST_FEED = "GetRequestData.php";
    String REQUEST_GET = "GetOneRequest.php";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASIS_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    @GET(REQUEST_FEED)
    Call<TransportRequest[]> requests();

    @GET(REQUEST_GET)
    Call<TransportRequest> request(@Query("nummer") int nummer);
}
