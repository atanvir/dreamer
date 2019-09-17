package com.boushra.Utility;

import com.boushra.Model.UpdateUserProfile;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddServiceBody
{
    private MediaType mediaType = MediaType.parse("text/plain");

    private Map<String, RequestBody> requestBodyMap = new HashMap<>();


    public AddServiceBody(UpdateUserProfile userProfile)
    {

        requestBodyMap.put("userId",RequestBody.create(mediaType,userProfile.getUserId()));
        if(userProfile.getEmail()!=null) {
            requestBodyMap.put("email", RequestBody.create(mediaType, userProfile.getEmail()));
        }
        requestBodyMap.put("name",RequestBody.create(mediaType,userProfile.getName()));
        requestBodyMap.put("gender",RequestBody.create(mediaType,userProfile.getGender()));
        if(userProfile.getUsername()!=null) {
            requestBodyMap.put("username", RequestBody.create(mediaType, userProfile.getUsername()));
        }requestBodyMap.put("birthPlace",RequestBody.create(mediaType,userProfile.getBirthPlace()));
        requestBodyMap.put("maritalStatus",RequestBody.create(mediaType,userProfile.getMaritalStatus()));
        requestBodyMap.put("dob",RequestBody.create(mediaType,userProfile.getDob()));


    }

    public Map<String,RequestBody> getBody()
    {
        return requestBodyMap;
    }
}
