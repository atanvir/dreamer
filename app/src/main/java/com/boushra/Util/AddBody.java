package com.boushra.Util;

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


    }

    public Map<String,RequestBody> getBody()
    {
        return requestBodyMap;
    }
}
