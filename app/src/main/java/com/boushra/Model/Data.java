package com.boushra.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("profilePic")
    @Expose
    private String profilePic;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("notificationStatus")
    @Expose
    private Boolean notificationStatus;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("totalPoints")
    @Expose
    private Integer totalPoints;



    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("socialId")
    @Expose
    private String socialId;
    @SerializedName("socialType")
    @Expose
    private String socialType;
    @SerializedName("jwtToken")
    @Expose
    private String jwtToken;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    @SerializedName("countryCode")
    @Expose
    private String countryCode;

    @SerializedName("password")
    @Expose
    private String password;


    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;


    @SerializedName("deviceType")
    @Expose
    private String deviceType;

    @SerializedName("aboutUs")
    @Expose
    private String aboutUs;



   @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("Type")
    @Expose
    private String type;

    @SerializedName("birthPlace")
    @Expose
    private String birthPlace;

    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;

    @SerializedName("totalRating")
    @Expose
    private Integer totalRating;
    @SerializedName("avgRating")
    @Expose
    private Integer avgRating;
    @SerializedName("onlineStatus")
    @Expose
    private Boolean onlineStatus;
    @SerializedName("responseTime")
    @Expose
    private String responseTime;
    @SerializedName("pendingQueue")
    @Expose
    private Integer pendingQueue;

    @SerializedName("pricePerQues")
    @Expose
    private String pricePerQues;


    @SerializedName("bookingStatus")
    @Expose
    private String bookingStatus;
    @SerializedName("paymentStatusVerifyByAdmin")
    @Expose
    private String paymentStatusVerifyByAdmin;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("forecasterId")
    @Expose
    private String forecasterId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("transactionStatus")
    @Expose
    private String transactionStatus;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("roomId")
    @Expose
    private String roomId;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("uploadedVideo")
    @Expose
    private String uploadedVideo;


    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("attachedDocument")
    @Expose
    private String attachedDocument;
    @SerializedName("voiceRecording")
    @Expose
    private String voiceRecording;

    @SerializedName("ratingData")
    @Expose
    private RatingData ratingData;

    @SerializedName("spendedPoints")
    @Expose
    private Integer spendedPoints;
    @SerializedName("progressBooking")
    @Expose
    private Integer progressBooking;
    @SerializedName("completeBooking")
    @Expose
    private Integer completeBooking;
    @SerializedName("cancelBooking")
    @Expose
    private Integer cancelBooking;

    @SerializedName("requestStatus")
    @Expose
    private Boolean requestStatus;
    @SerializedName("chatCloseByDreamerStatus")
    @Expose
    private Boolean chatCloseByDreamerStatus;
    @SerializedName("chatCloseStatus")
    @Expose
    private Boolean chatCloseStatus;

    @SerializedName("isSeen")
    @Expose
    private Boolean isSeen;

    @SerializedName("recieverId")
    @Expose
    private String recieverId;
    @SerializedName("lastMessage")
    @Expose
    private String lastMessage;


    @SerializedName("forecasterData")
    @Expose
    private ForecasterData forecasterData;

    public Boolean getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Boolean requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Boolean getChatCloseByDreamerStatus() {
        return chatCloseByDreamerStatus;
    }

    public void setChatCloseByDreamerStatus(Boolean chatCloseByDreamerStatus) {
        this.chatCloseByDreamerStatus = chatCloseByDreamerStatus;
    }

    public Boolean getChatCloseStatus() {
        return chatCloseStatus;
    }

    public void setChatCloseStatus(Boolean chatCloseStatus) {
        this.chatCloseStatus = chatCloseStatus;
    }

    public Boolean getSeen() {
        return isSeen;
    }

    public void setSeen(Boolean seen) {
        isSeen = seen;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public ForecasterData getForecasterData() {
        return forecasterData;
    }

    public void setForecasterData(ForecasterData forecasterData) {
        this.forecasterData = forecasterData;
    }

    public Integer getSpendedPoints() {
        return spendedPoints;
    }

    public void setSpendedPoints(Integer spendedPoints) {
        this.spendedPoints = spendedPoints;
    }

    public Integer getProgressBooking() {
        return progressBooking;
    }

    public void setProgressBooking(Integer progressBooking) {
        this.progressBooking = progressBooking;
    }

    public Integer getCompleteBooking() {
        return completeBooking;
    }

    public void setCompleteBooking(Integer completeBooking) {
        this.completeBooking = completeBooking;
    }

    public Integer getCancelBooking() {
        return cancelBooking;
    }

    public void setCancelBooking(Integer cancelBooking) {
        this.cancelBooking = cancelBooking;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAttachedDocument() {
        return attachedDocument;
    }

    public void setAttachedDocument(String attachedDocument) {
        this.attachedDocument = attachedDocument;
    }

    public String getVoiceRecording() {
        return voiceRecording;
    }

    public void setVoiceRecording(String voiceRecording) {
        this.voiceRecording = voiceRecording;
    }

    public RatingData getRatingData() {
        return ratingData;
    }

    public void setRatingData(RatingData ratingData) {
        this.ratingData = ratingData;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getUploadedVideo() {
        return uploadedVideo;
    }

    public void setUploadedVideo(String uploadedVideo) {
        this.uploadedVideo = uploadedVideo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPaymentStatusVerifyByAdmin() {
        return paymentStatusVerifyByAdmin;
    }

    public void setPaymentStatusVerifyByAdmin(String paymentStatusVerifyByAdmin) {
        this.paymentStatusVerifyByAdmin = paymentStatusVerifyByAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getForecasterId() {
        return forecasterId;
    }

    public void setForecasterId(String forecasterId) {
        this.forecasterId = forecasterId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPricePerQues() {
        return pricePerQues;
    }

    public void setPricePerQues(String pricePerQues) {
        this.pricePerQues = pricePerQues;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

    public Integer getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Integer avgRating) {
        this.avgRating = avgRating;
    }

    public Boolean getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getPendingQueue() {
        return pendingQueue;
    }

    public void setPendingQueue(Integer pendingQueue) {
        this.pendingQueue = pendingQueue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }



    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }




    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }


}
