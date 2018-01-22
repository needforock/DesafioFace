package com.desafiolatam.desafioface.views.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.desafiolatam.desafioface.R;
import com.desafiolatam.desafioface.background.RecentUserService;
import com.desafiolatam.desafioface.data.FcmToken;
import com.desafiolatam.desafioface.network.favorites.PutFcmToken;
import com.desafiolatam.desafioface.views.main.MainActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity implements SessionCallback {

    private TextInputLayout mailWrapper, passWrapper;
    private EditText mailEt, passEt;
    private Button button;
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        mailWrapper = (TextInputLayout) findViewById(R.id.emailTil);
        passWrapper = (TextInputLayout) findViewById(R.id.passwordTil);
        mailEt = (EditText) findViewById(R.id.emailEt);
        passEt = (EditText) findViewById(R.id.passwordEt);
        button = (Button) findViewById(R.id.signBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mailEt.getText().toString();
                String pass = passEt.getText().toString();
                mailWrapper.setVisibility(View.GONE);
                passWrapper.setVisibility(View.GONE);
                button.setVisibility(View.GONE);

                new SignInPresenter(LoginActivity.this).toServer(email, pass);
            }
        });
        intentFilter = new IntentFilter();
        intentFilter.addAction(RecentUserService.USERS_FINISHED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (RecentUserService.USERS_FINISHED.equals(action)) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver    );
    }

    private void restoreView() {
        mailEt.setError(null);
        passEt.setError(null);
        mailWrapper.setVisibility(View.VISIBLE);
        passWrapper.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
    }


    @Override
    public void requiredField() {
        restoreView();
        mailEt.setError("REQUERIDO");
        passEt.setError("REQUERIDO");

    }

    @Override
    public void mailFormat() {
        restoreView();
        mailEt.setError("Formato Incorrecto");
    }

    @Override
    public void success() {
        String fcmToken = new FcmToken(this).get();
        new PutFcmToken().execute(fcmToken);
        RecentUserService.startActionRecentUsers(this);

    }

    @Override
    public void failure() {
        restoreView();
        Toast.makeText(this, "mail o password incorrecto", Toast.LENGTH_SHORT).show();
    }
}
