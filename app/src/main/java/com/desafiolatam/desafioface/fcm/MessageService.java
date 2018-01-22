package com.desafiolatam.desafioface.fcm;

import android.util.Log;

import com.desafiolatam.desafioface.notifications.FavoriteNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Soporte on 30-Oct-17.
 */

public class MessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("NOTIFICATION", "is happening");
        String email = remoteMessage.getData().get("email");
        Log.d("EMAIL", String.valueOf(email));

        FavoriteNotification.notify(this, email);
    }
}
