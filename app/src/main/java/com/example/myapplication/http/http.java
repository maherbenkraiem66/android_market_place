package com.example.myapplication.http;

import android.content.Context;

import com.example.myapplication.model.Product;
import com.example.myapplication.model.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class http {
    private static final String BASE_URL = "http://192.168.1.17:80/manel/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
        }
        return retrofit;
    }

    public interface LoadUser {
        @GET("authentifier.php")
        Call<User> performLogin(@Query("username") String user, @Query("password") String pass);
    }

    public interface LoadProducts {
        @GET("get_products.php")
        Call<ArrayList<Product>> getProds(@Query("type") String type);
    }
}
