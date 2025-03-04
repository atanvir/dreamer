package com.boushra.Retrofit;


import com.boushra.Model.BankDetail;
import com.boushra.Model.Common;
import com.boushra.Model.ApplyPromocode;
import com.boushra.Model.BannerList;
import com.boushra.Model.BookForcaster;
import com.boushra.Model.ChangePassword;
import com.boushra.Model.Chat;
import com.boushra.Model.ChatHistory;
import com.boushra.Model.Chatlist;
import com.boushra.Model.ForcasterDetails;
import com.boushra.Model.ForcasterList;
import com.boushra.Model.ForecasterRating.ForecasterRating;
import com.boushra.Model.ForgotPassword;
import com.boushra.Model.Login;
import com.boushra.Model.Logout;
import com.boushra.Model.MyBooking.MyBooking;
import com.boushra.Model.Notification;
import com.boushra.Model.Payment;
import com.boushra.Model.Rating;
import com.boushra.Model.Signup;
import com.boushra.Model.SocialLogin;
import com.boushra.Model.StaticContent;
import com.boushra.Model.UpdateUserProfile;
import com.boushra.Model.User;
import com.boushra.Model.UserSetting;
import com.boushra.Model.WalletDetail;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RetroInterface {

    @POST("checkUserMobileNumber")
    Call<Signup> checkUserPhoneNumber(@Body Signup signup);

    @POST("userSignup")
    Call<Signup> sendSingupData(@Body Signup signup);

    @POST("userLogin")
    Call<Login> checkLogin(@Body Login login);

    @POST("socialLogin")
    Call<SocialLogin> socialLogin(@Body SocialLogin socialLogin);

    @POST("userLogout")
    Call<Logout> userLogout(@Body Logout logout);


    @POST("userChangePassword")
    Call<ChangePassword> userChangePassword(@Body ChangePassword changePassword,@Header("token") String token);


    @GET("getStaticContent")
    Call<StaticContent> getStaticContent();


    @POST("getUserSettings")
    Call<UserSetting> getUserSettings(@Body UserSetting setting,@Header("token") String token);


    @POST("userResetPassword")
    Call<ForgotPassword> userResetPassword(@Body ForgotPassword password);

    @POST("getUserDetails")
    Call<User> getUserDetails(@Body User user,@Header("token") String token);


    @Multipart
    @POST("userUpdateProfile")
    Call<UpdateUserProfile> updateUserProfile(@Part MultipartBody.Part image, @PartMap Map<String, RequestBody> params);


    @POST("updateUserSettings")
    Call<UserSetting> updateUserSetting(@Body UserSetting userSetting,@Header("token") String token);


    @POST("getForecasterList")
    Call<ForcasterList> getForecasterList(@Body ForcasterList list,@Header("token") String token);

    @Multipart
    @POST("bookForecaster")
    Call<BookForcaster> bookForcaster(@Part MultipartBody.Part voice, @PartMap Map<String,RequestBody> formdata);

    @POST("getBannerList")
    Call<BannerList> getBannerList();

    @POST("getForecasterDetailsForBooking")
    Call<ForcasterDetails> getForecasterDetails(@Body ForcasterDetails details,@Header("token") String token);

    @POST("walletDetail")
    Call<WalletDetail> getWalletDetail(@Body WalletDetail walletDetail, @Header("token") String token);

    @POST("chatListForDreamer")
    Call<Chat> chatListForDreamer(@Body Chat chat);

    @POST("getMyBooking")
    Call<MyBooking> getMyBooking(@Body MyBooking booking,@Header("token") String token);

    @POST("rating")
    Call<Rating> rating(@Body Rating rating,@Header("token") String token);

    @POST("getForecasterRating")
    Call<ForecasterRating> getForecasterRating(@Body ForcasterDetails details);

    @POST("getNotificationList")
    Call<Notification> getNotificationList(@Body Notification notification, @Header("token") String token);

    @POST("chatListForDreamer")
    Call<Chatlist> getchatList(@Body Chatlist chatlist);

    @POST("chatHistory")
    Call<ChatHistory> getchatHistory(@Body ChatHistory  history,@Header("token") String token);


    @POST("getAdminBankDetail")
    Call<BankDetail> getAdminBankDetail();

    @POST("applyPromocode")
    Call<ApplyPromocode> applyPromocode(@Body ApplyPromocode promocode,@Header("token") String token);

    @POST("getStoreList")
    Call<Common> getStoreList();

    @Multipart
    @POST("payment")
    Call<Payment> payment(@Part MultipartBody.Part photo,@PartMap Map<String, RequestBody> data);
}
