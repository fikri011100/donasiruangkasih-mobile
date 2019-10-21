package com.titi.mj.utils.network;

import com.titi.mj.model.CategoryResponse;
import com.titi.mj.model.DonationResponse;
import com.titi.mj.model.LoginResponse;
import com.titi.mj.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> doLogin(@Field("email") String str_email, @Field("password") String str_password);

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> doRegist(@Field("name") String str_name, @Field("email") String str_email,
                                    @Field("password") String str_password, @Field("password_confirmation") String str_password_confirmation);

    @GET("donation")
    Call<DonationResponse> getDonation();

    @GET("/list-category")
    Call<CategoryResponse> getCategory();
}
