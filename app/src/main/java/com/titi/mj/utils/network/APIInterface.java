package com.titi.mj.utils.network;

import com.titi.mj.model.CategoryResponse;
import com.titi.mj.model.ConfirmationResponse;
import com.titi.mj.model.DonatedResponse;
import com.titi.mj.model.DonatingResponse;
import com.titi.mj.model.DonationResponse;
import com.titi.mj.model.LoginResponse;
import com.titi.mj.model.MydonationResponse;
import com.titi.mj.model.PaymentResponse;
import com.titi.mj.model.RegisterResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface APIInterface {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> doLogin(@Field("email") String str_email, @Field("password") String str_password);

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> doRegist(@Field("name") String str_name, @Field("email") String str_email,
                                    @Field("password") String str_password, @Field("password_confirmation") String str_password_confirmation,
                                    @Field("phone") String str_phone);

    @POST("donator/{donator_user_id}/{status_id}")
    Call<DonatedResponse> getDonated(@Path("donator_user_id") int user_id, @Path("status_id") int status_id);

    @POST("getDonationById/{user_id}")
    Call<DonatingResponse> getDonating(@Path("user_id") int user_id);

    @Multipart
    @POST("addDonationConfirmation/")
    Call<ConfirmationResponse> sendConfirmation(@Part MultipartBody.Part photo,
                                           @PartMap Map<String, RequestBody> text);

    @FormUrlEncoded
    @POST("sendDonation")
    Call<DonatedResponse> doDonation(@Field("donator_user_id") int user_id, @Field("donation_id") int donation_id,
                                      @Field("donation_amount") int donation_nominal, @Field("payment_method") int payment_id);

    @GET("donation")
    Call<DonationResponse> getDonation();

    @GET("donation")
    Call<MydonationResponse> getDonation2();

    @GET("payment")
    Call<PaymentResponse> getPayment();

    @GET("category")
    Call<CategoryResponse> getCategory();
}
