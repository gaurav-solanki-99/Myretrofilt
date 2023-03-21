package com.example.myretrofilt.Network;

import com.example.myretrofilt.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitInstance {
    public   static ApiInterface apiInterface;

    public static ApiInterface getInstance()
    {

        if(apiInterface==null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
        }








        return  apiInterface;
    }

  public interface ApiInterface{



      @FormUrlEncoded
      @POST("posts")
      Call<ResponseBody> createPost(@Field("title") String title,
                                    @Field("body") String body,
                                    @Field("userId") int userId);


      @GET("posts")
      Call<ArrayList<User>> getALLUser();

      @DELETE("posts/{id}")
      Call<ResponseBody> deleteItem(@Query("id") int id);

      @FormUrlEncoded
      @PUT("posts/{id}")
      Call<User> updatePost(@Path("id") int id,
                            @Field("title") String title,
                                    @Field("body") String body,
                                    @Field("userId") int userId);







  }

}


