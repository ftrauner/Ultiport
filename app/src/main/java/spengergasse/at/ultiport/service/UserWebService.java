package spengergasse.at.ultiport.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import spengergasse.at.ultiport.entities.User;

public interface UserWebService {

    String BASIS_URL = "http://ultiport.htl5.org/";
    String USER_GET = "GetOneUser.php";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASIS_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    @GET(USER_GET)
    Call<User> user(@Query("id") int id);
}
