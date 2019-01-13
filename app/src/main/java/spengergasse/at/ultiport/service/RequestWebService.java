package spengergasse.at.ultiport.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import spengergasse.at.ultiport.entities.TransportRequest;

public interface RequestWebService {

    String BASIS_URL = "http://ultiport.htl5.org/";
    String REQUEST_FEED = "GetRequestData.php";
    String REQUEST_ADD = "addRequest.php";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASIS_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @GET(REQUEST_FEED)
    Call<TransportRequest[]> requests();

    @POST(REQUEST_ADD)
    Call<TransportRequest> addRequest(@Body TransportRequest request);
}