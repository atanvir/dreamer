package com.boushra.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data implements Parcelable {
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
    private Float totalPoints;

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
    private Float totalRating;
    @SerializedName("avgRating")
    @Expose
    private Float avgRating;
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
    private String spendedPoints;
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


    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("ratingMessage")
    @Expose
    private String ratingMessage;
    @SerializedName("bookingId")
    @Expose
    private String bookingId;


    @SerializedName("profileSetup")
    @Expose
    private Boolean profileSetup;
    @SerializedName("profileComplete")
    @Expose
    private Boolean profileComplete;
    @SerializedName("bookingCount")
    @Expose
    private Integer bookingCount;

    @SerializedName("accountHolderName")
    @Expose
    private String accountHolderName;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("documentType")
    @Expose
    private String documentType;

    @SerializedName("notiTo")
    @Expose
    private String notiTo;
    @SerializedName("notiTitle")
    @Expose
    private String notiTitle;
    @SerializedName("notiMessage")
    @Expose
    private String notiMessage;
    @SerializedName("userType")
    @Expose
    private String userType;

    @SerializedName("requestAcceptStatus")
    @Expose
    private boolean requestAcceptStatus;

    @SerializedName("senderId")
    @Expose
    private String senderId;
    @SerializedName("receiverId")
    @Expose
    private String receiverId;

    @SerializedName("requestAcceptTime")
    @Expose
    private Object requestAcceptTime;
    @SerializedName("requestSendTime")
    @Expose
    private Object requestSendTime;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("messageType")
    @Expose
    private String messageType;

    @SerializedName("media")
    @Expose
    private String media;


    @SerializedName("promocode")
    @Expose
    private String promocode;
    @SerializedName("discount")
    @Expose
    private long discount;
    @SerializedName("expiryDate")
    @Expose
    private String expiryDate;

    @SerializedName("amount")
    @Expose
    private long amount;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public static Creator<Data> getCREATOR() {
        return CREATOR;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }


    public Data(String roomId, String senderId, String receiverId,String name,String profilePic) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.name=name;
        this.profilePic=profilePic;
    }


    public Data(String roomId, String senderId, String receiverId, String message, String messageType,String createdAt) {
       // this.profilePic = profilePic;
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.messageType = messageType;
        this.createdAt=createdAt;
    }

    public Data(String roomId, String senderId, String receiverId, String message, String messageType,String createdAt,String media) {
        // this.profilePic = profilePic;
        this.roomId = roomId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.messageType = messageType;
        this.createdAt=createdAt;
        this.media=media;
    }

    protected Data(Parcel in) {
        profilePic = in.readString();
        status = in.readString();
        byte tmpNotificationStatus = in.readByte();
        notificationStatus = tmpNotificationStatus == 0 ? null : tmpNotificationStatus == 1;
        language = in.readString();
        if (in.readByte() == 0) {
            totalPoints = null;
        } else {
            totalPoints = in.readFloat();
        }
        id = in.readString();
        socialId = in.readString();
        socialType = in.readString();
        jwtToken = in.readString();
        name = in.readString();
        email = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
        countryCode = in.readString();
        password = in.readString();
        deviceToken = in.readString();
        deviceType = in.readString();
        aboutUs = in.readString();
        title = in.readString();
        description = in.readString();
        type = in.readString();
        birthPlace = in.readString();
        mobileNumber = in.readString();
        dob = in.readString();
        gender = in.readString();
        maritalStatus = in.readString();
        if (in.readByte() == 0) {
            totalRating = null;
        } else {
            totalRating = in.readFloat();
        }
        if (in.readByte() == 0) {
            avgRating = null;
        } else {
            avgRating = in.readFloat();
        }
        byte tmpOnlineStatus = in.readByte();
        onlineStatus = tmpOnlineStatus == 0 ? null : tmpOnlineStatus == 1;
        responseTime = in.readString();
        if (in.readByte() == 0) {
            pendingQueue = null;
        } else {
            pendingQueue = in.readInt();
        }
        pricePerQues = in.readString();
        bookingStatus = in.readString();
        paymentStatusVerifyByAdmin = in.readString();
        userId = in.readString();
        forecasterId = in.readString();
        question = in.readString();
        points = in.readString();
        transactionStatus = in.readString();
        categoryName = in.readString();
        roomId = in.readString();
        image = in.readString();
        uploadedVideo = in.readString();
        username = in.readString();
        attachedDocument = in.readString();
        voiceRecording = in.readString();
        spendedPoints = in.readString();
        if (in.readByte() == 0) {
            progressBooking = null;
        } else {
            progressBooking = in.readInt();
        }
        if (in.readByte() == 0) {
            completeBooking = null;
        } else {
            completeBooking = in.readInt();
        }
        if (in.readByte() == 0) {
            cancelBooking = null;
        } else {
            cancelBooking = in.readInt();
        }
        byte tmpRequestStatus = in.readByte();
        requestStatus = tmpRequestStatus == 0 ? null : tmpRequestStatus == 1;
        byte tmpChatCloseByDreamerStatus = in.readByte();
        chatCloseByDreamerStatus = tmpChatCloseByDreamerStatus == 0 ? null : tmpChatCloseByDreamerStatus == 1;
        byte tmpChatCloseStatus = in.readByte();
        chatCloseStatus = tmpChatCloseStatus == 0 ? null : tmpChatCloseStatus == 1;
        byte tmpIsSeen = in.readByte();
        isSeen = tmpIsSeen == 0 ? null : tmpIsSeen == 1;
        recieverId = in.readString();
        lastMessage = in.readString();
        forecasterData=in.readParcelable(Data.class.getClassLoader());
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readInt();
        }
        ratingMessage = in.readString();
        bookingId = in.readString();
        byte tmpProfileSetup = in.readByte();
        profileSetup = tmpProfileSetup == 0 ? null : tmpProfileSetup == 1;
        byte tmpProfileComplete = in.readByte();
        profileComplete = tmpProfileComplete == 0 ? null : tmpProfileComplete == 1;
        if (in.readByte() == 0) {
            bookingCount = null;
        } else {
            bookingCount = in.readInt();
        }
        accountHolderName = in.readString();
        accountNumber = in.readString();
        bankName = in.readString();
        documentType = in.readString();
        notiTo = in.readString();
        notiTitle = in.readString();
        notiMessage = in.readString();
        userType = in.readString();
        requestAcceptStatus = in.readByte() != 0;
        senderId = in.readString();
        receiverId = in.readString();
        message=in.readString();
        messageType=in.readString();
    }


    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public boolean isRequestAcceptStatus() {
        return requestAcceptStatus;
    }

    public void setRequestAcceptStatus(boolean requestAcceptStatus) {
        this.requestAcceptStatus = requestAcceptStatus;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Object getRequestAcceptTime() {
        return requestAcceptTime;
    }

    public void setRequestAcceptTime(Object requestAcceptTime) {
        this.requestAcceptTime = requestAcceptTime;
    }

    public Object getRequestSendTime() {
        return requestSendTime;
    }

    public void setRequestSendTime(Object requestSendTime) {
        this.requestSendTime = requestSendTime;
    }

    public String getNotiTo() {
        return notiTo;
    }

    public void setNotiTo(String notiTo) {
        this.notiTo = notiTo;
    }

    public String getNotiTitle() {
        return notiTitle;
    }

    public void setNotiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
    }

    public String getNotiMessage() {
        return notiMessage;
    }

    public void setNotiMessage(String notiMessage) {
        this.notiMessage = notiMessage;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Float totalRating) {
        this.totalRating = totalRating;
    }

    public Boolean getProfileSetup() {
        return profileSetup;
    }

    public void setProfileSetup(Boolean profileSetup) {
        this.profileSetup = profileSetup;
    }

    public Boolean getProfileComplete() {
        return profileComplete;
    }

    public void setProfileComplete(Boolean profileComplete) {
        this.profileComplete = profileComplete;
    }

    public Integer getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(Integer bookingCount) {
        this.bookingCount = bookingCount;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRatingMessage() {
        return ratingMessage;
    }

    public void setRatingMessage(String ratingMessage) {
        this.ratingMessage = ratingMessage;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }


    public RatingData getRatingData() {
        return ratingData;
    }

    public void setRatingData(RatingData ratingData) {
        this.ratingData = ratingData;
    }

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }

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

    public String getSpendedPoints() {
        return spendedPoints;
    }

    public void setSpendedPoints(String spendedPoints) {
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

    public Float getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Float totalPoints) {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profilePic);
        dest.writeString(status);
        dest.writeByte((byte) (notificationStatus == null ? 0 : notificationStatus ? 1 : 2));
        dest.writeString(language);
        if (totalPoints == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(totalPoints);
        }
        dest.writeString(id);
        dest.writeString(socialId);
        dest.writeString(socialType);
        dest.writeString(jwtToken);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
        dest.writeString(countryCode);
        dest.writeString(password);
        dest.writeString(deviceToken);
        dest.writeString(deviceType);
        dest.writeString(aboutUs);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeString(birthPlace);
        dest.writeString(mobileNumber);
        dest.writeString(dob);
        dest.writeString(gender);
        dest.writeString(maritalStatus);
        if (totalRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(totalRating);
        }
        if (avgRating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(avgRating);
        }
        dest.writeByte((byte) (onlineStatus == null ? 0 : onlineStatus ? 1 : 2));
        dest.writeString(responseTime);
        if (pendingQueue == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pendingQueue);
        }
        dest.writeString(pricePerQues);
        dest.writeString(bookingStatus);
        dest.writeString(paymentStatusVerifyByAdmin);
        dest.writeString(userId);
        dest.writeString(forecasterId);
        dest.writeString(question);
        dest.writeString(points);
        dest.writeString(transactionStatus);
        dest.writeString(categoryName);
        dest.writeString(roomId);
        dest.writeString(image);
        dest.writeString(uploadedVideo);
        dest.writeString(username);
        dest.writeString(attachedDocument);
        dest.writeString(voiceRecording);
        dest.writeString(spendedPoints);
        if (progressBooking == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(progressBooking);
        }
        if (completeBooking == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(completeBooking);
        }
        if (cancelBooking == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(cancelBooking);
        }
        dest.writeByte((byte) (requestStatus == null ? 0 : requestStatus ? 1 : 2));
        dest.writeByte((byte) (chatCloseByDreamerStatus == null ? 0 : chatCloseByDreamerStatus ? 1 : 2));
        dest.writeByte((byte) (chatCloseStatus == null ? 0 : chatCloseStatus ? 1 : 2));
        dest.writeByte((byte) (isSeen == null ? 0 : isSeen ? 1 : 2));
        dest.writeString(recieverId);
        dest.writeString(lastMessage);
        dest.writeParcelable(forecasterData,flags);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rating);
        }
        dest.writeString(ratingMessage);
        dest.writeString(bookingId);
        dest.writeByte((byte) (profileSetup == null ? 0 : profileSetup ? 1 : 2));
        dest.writeByte((byte) (profileComplete == null ? 0 : profileComplete ? 1 : 2));
        if (bookingCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bookingCount);
        }
        dest.writeString(accountHolderName);
        dest.writeString(accountNumber);
        dest.writeString(bankName);
        dest.writeString(documentType);
        dest.writeString(notiTo);
        dest.writeString(notiTitle);
        dest.writeString(notiMessage);
        dest.writeString(userType);
        dest.writeByte((byte) (requestAcceptStatus ? 1 : 0));
        dest.writeString(senderId);
        dest.writeString(receiverId);
        dest.writeString(message);
        dest.writeString(messageType);
    }
}
