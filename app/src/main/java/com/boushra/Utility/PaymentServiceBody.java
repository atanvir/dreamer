package com.boushra.Utility;

import android.util.Log;

import androidx.annotation.NonNull;

import com.boushra.Model.Payment;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PaymentServiceBody {
    private MediaType mediaType = MediaType.parse("text/plain");

    private Map<String, RequestBody> bodyMap = new HashMap<>();

    public PaymentServiceBody(Payment payment)
    {
        bodyMap.put("userId",RequestBody.create(mediaType,payment.getUserId()));
        bodyMap.put("storeId",RequestBody.create(mediaType,payment.getStoreId()));
        bodyMap.put("amount",RequestBody.create(mediaType, String.valueOf(payment.getAmount())));
        bodyMap.put("points",RequestBody.create(mediaType, String.valueOf(payment.getPoints())));
        if(!payment.getPaymentType().equalsIgnoreCase("Bank"))
        {
            bodyMap.put("transactionId",RequestBody.create(mediaType, payment.getTransactionId()));
            bodyMap.put("transactionStatus",RequestBody.create(mediaType, payment.getTransactionStatus()));
            bodyMap.put("transactionType", RequestBody.create(mediaType, payment.getTransactionType()));


        }
        bodyMap.put("paymentType",RequestBody.create(mediaType, payment.getPaymentType()));


        if(payment.getDiscountAmount()!=0) {

            bodyMap.put("discountAmount", RequestBody.create(mediaType, String.valueOf(payment.getDiscountAmount())));
            Log.e("discountAmount", String.valueOf(payment.getDiscountAmount()));
            Log.e("promocode", payment.getPromoCode());
            bodyMap.put("promoCode", RequestBody.create(mediaType, payment.getPromoCode()));
        }
        if(!payment.getPaymentType().isEmpty())
        {
            bodyMap.put("bankName",RequestBody.create(mediaType,payment.getBankName()));
            bodyMap.put("accountNumber",RequestBody.create(mediaType,payment.getAccountNumber()));
            bodyMap.put("accountHolderName",RequestBody.create(mediaType,payment.getAccountHolderName()));

        }


    }

    public Map<String,RequestBody> getData(){
        return bodyMap;
    }

    @NonNull
    @Override
    public String toString() {
        return        "userId:"+bodyMap.get("userId")
                +"\n"+"storeId:"+bodyMap.get("storeId")
                +"\n"+ "amount:"+bodyMap.get("amount")
                +"\n"+"points:"+bodyMap.get("points")
               // +"\n"+"transactionId:"+bodyMap.get("transactionId")
              //  +"\n"+"transactionStatus:"+bodyMap.get("transactionStatus")
                +"\n"+"paymentType:"+bodyMap.get("paymentType")
                +"\n"+"discountAmount:"+bodyMap.get("discountAmount")
                +"\n"+"promocode:"+bodyMap.get("promoCode")
               // +"\n"+"transactionType:"+bodyMap.get("transactionType")
                +"\n"+"accountNumber:"+bodyMap.get("accountNumber")
                +"\n"+"accountHolderName:"+bodyMap.get("accountHolderName");


    }
}
