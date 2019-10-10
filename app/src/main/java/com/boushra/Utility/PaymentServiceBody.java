package com.boushra.Utility;

import android.util.Log;

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
        bodyMap.put("transactionId",RequestBody.create(mediaType, payment.getTransactionId()));
        bodyMap.put("transactionStatus",RequestBody.create(mediaType, payment.getTransactionStatus()));
        bodyMap.put("paymentType",RequestBody.create(mediaType, payment.getPaymentType()));
        Log.e("discountAmount", String.valueOf(payment.getDiscountAmount()));
        Log.e("promocode", payment.getPromoCode());
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

        bodyMap.put("transactionType", RequestBody.create(mediaType, payment.getTransactionType()));

    }

    public Map<String,RequestBody> getData(){
        return bodyMap;
    }

}
