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
import spengergasse.at.ultiport.entities.TransportRequest;

public interface RequestWebService {

    String BASIS_URL = "http://ultiport.htl5.org/";
    String REQUEST_FEED = "GetRequestData.php";
    String REQUEST_ADD = "addRequest.php";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    Gson gson2 = new GsonBuilder()
            .setDateFormat("EEE MMM dd kk:mm:ss z yyyy")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASIS_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl(BASIS_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @GET(REQUEST_FEED)
    Call<TransportRequest[]> requests();

    @POST(REQUEST_ADD)
    @Headers("Content-Type: application/json")
    Call<TransportRequest> addRequest(@Body TransportRequest request);

    @FormUrlEncoded
    @POST(REQUEST_ADD)
    Call<TransportRequest> addRequest2(@Field("a_e_anf") String anf,
                                       @Field("a_r_start") String startR,
                                       @Field("a_r_ziel") String zielR,
                                       @Field("a_oe_start") String startOE,
                                       @Field("a_oe_ende") String endOE,
                                       @Field("a_o_id") String art,
                                       @Field("a_s_status") String status,
                                       @Field("a_e_trans") String trans,
                                       @Field("a_beschr") String beschr);
}
