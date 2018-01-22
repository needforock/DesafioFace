package com.desafiolatam.desafioface.network.favorites;

import com.desafiolatam.desafioface.models.Developer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Soporte on 31-Oct-17.
 */

public interface Favorites {

    @FormUrlEncoded
    @PUT ("users/fcm_token")
    Call<String> putFcmToken(@Field("users[fcm_token]") String token);

    @POST ("users/{id}/favorite")
    Call<Developer> postFavorite (@Path("id") long id);


}
