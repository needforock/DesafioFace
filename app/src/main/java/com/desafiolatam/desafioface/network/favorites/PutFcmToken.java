package com.desafiolatam.desafioface.network.favorites;

import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Soporte on 02-Nov-17.
 */

public class PutFcmToken extends AsyncTask <String, Integer, Integer> {
    @Override
    protected Integer doInBackground(String... params) {
        int code = 666;
        String token = params[0];
        Favorites favorites = new FavoriteInterceptor().get();
        Call<String> call = favorites.putFcmToken(token);
        try {
            Response<String> response = call.execute();
            code = response.code();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
}
