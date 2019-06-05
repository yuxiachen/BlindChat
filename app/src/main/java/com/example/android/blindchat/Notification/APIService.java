package com.example.android.blindchat.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAANJM7hAU:APA91bHD5VUO5E_51RBarW6sB95FOVsTqduUZqfEKj0DEOrBbjPjqtvVWBcKNJXkhAcwxbmhTuOdfV-__MNQLSRuSJF9hXifEEUnlaYig8or9j3uTgiueZ5qPcQkRDjvieIACtwewnK0"
            }

    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
