package com.boushra.Utility;

import com.boushra.Model.BookForcaster;
import com.boushra.Model.UpdateUserProfile;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddBody {

    private MediaType mediaType = MediaType.parse("text/plain");

    private Map<String, RequestBody> requestBodyMap = new HashMap<>();


    public AddBody(BookForcaster bookForcaster)
    {

        requestBodyMap.put("forecasterId",RequestBody.create(mediaType,bookForcaster.getForecasterId()));
        requestBodyMap.put("userId",RequestBody.create(mediaType,bookForcaster.getUserId()));
        requestBodyMap.put("points",RequestBody.create(mediaType, String.valueOf(bookForcaster.getPoints())));
        if(bookForcaster.getCategoryName()!=null)
        {
            requestBodyMap.put("categoryName",RequestBody.create(mediaType,bookForcaster.getCategoryName()));
        }
        requestBodyMap.put("question",RequestBody.create(mediaType,bookForcaster.getQuestion()));
        requestBodyMap.put("dob",RequestBody.create(mediaType,bookForcaster.getDob()));
        requestBodyMap.put("gender",RequestBody.create(mediaType,bookForcaster.getGender()));
        requestBodyMap.put("maritalStatus",RequestBody.create(mediaType,bookForcaster.getMaritalStatus()));
        requestBodyMap.put("name",RequestBody.create(mediaType,bookForcaster.getName()));


    }

    public Map<String,RequestBody> getBody()
    {
        return requestBodyMap;
    }
}
