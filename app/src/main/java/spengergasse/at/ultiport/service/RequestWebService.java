package spengergasse.at.ultiport.service;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import spengergasse.at.ultiport.entities.TransportRequest;

public interface RequestWebService {

    String BASIS_URL = "http://ultiport.htl5.org/";
    String REQUEST_FEED = "GetRequestData.php";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASIS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @POST(REQUEST_FEED)
    Call<TransportRequest[]> requests();
}
