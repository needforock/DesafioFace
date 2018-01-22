package com.desafiolatam.desafioface.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.desafiolatam.desafioface.models.CurrentUser;
import com.desafiolatam.desafioface.views.login.LoginActivity;
import com.orm.SugarApp;

/**
 * Created by Soporte on 03-Nov-17.
 */

public class DesafioApp extends SugarApp {

    public static final String SESSION_EXPIRED = "com.desafiolatam.desafioface.app.SESSION_EXPIRED";
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter = new IntentFilter();
        intentFilter.addAction(SESSION_EXPIRED);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(SESSION_EXPIRED.equals(intent.getAction())){
                    CurrentUser.deleteAll(CurrentUser.class);
                    Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    goToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(goToLogin);
                    Toast.makeText(context, "Sesión Expirada", Toast.LENGTH_SHORT).show();

                }
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
