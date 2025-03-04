package com.boushra.Utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.boushra.R;

public class GlobalVariables {
    public static final String SUCCESS ="SUCCESS" ;
    public static final String FAILURE = "FAILURE";
    public static String firebase_token="deviceToken";
    public static String profilePic="profilePic";
    public static String status="status";
    public static String notificationStatus="notificationStatus";
    public static String totalPoints="totalPoints";
    public static String _id="_id";
    public static String name="name";
    public static String birthPlace="birthPlace";
    public static String email="email";
    public static String mobileNumber="mobileNumber";
    public static String dob="dob";
    public static String gender="gender";
    public static String maritalStatus="maritalStatus";
    public static String createdAt="createdAt";
    public static String updatedAt="updatedAt";
    public static String __v="__v";
    public static String google="google";
    public static String facebook="facebook";
    public static String device_type="Android";

    public static String countryCode="countryCode";


    public static String password="password";
    public static String deviceToken="deviceToken";
    public static String jwtToken="jwtToken";
    public static String language="language";
    public static String islogin="LOGIN";
    public static String forecasterId="forecasterId";
    public static String points="points";
    public static String categoryName="categoryName";
    public static String bookingStatus="bookingStatus";
    public static String paymentStatusVerifyByAdmin="paymentStatusVerifyByAdmin";
    public static String userId="userId";
    public static String question="question";
    public static String transactionStatus="transactionStatus";
    public static String roomId="roomId";
    public static String dream="dream";
    public static String voice="voice";
    public static String type;
    public static String videouri="videouri";
    public static String invalidoken="Invalid Token";
    public static String old_passcode="old_passcode";
    public static String touchid="touchid";
    public static String senderId="senderId";
    public static String receiverId="receiverId";
    public static String message="message";
    public static String messageType="messageType";
    public static String username="username";
    public static String totalPrice="totalPrice";
    public static String storeId="storeId";
    public static String profile="profile";
    public static String langCode="langCode";


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            Drawable background = activity.getResources().getDrawable(R.drawable.action_bar_bg);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
//            window.setBackgroundDrawable(background);

        }
    }


    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        GlobalVariables.type = type;
    }
}
