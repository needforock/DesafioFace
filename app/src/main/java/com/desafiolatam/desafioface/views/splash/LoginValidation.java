package com.desafiolatam.desafioface.views.splash;

import com.desafiolatam.desafioface.data.CurrentUsersQuery;

/**
 * Created by Soporte on 02-Oct-17.
 */

public class LoginValidation {

    private LoginCallback callback;

    public LoginValidation(LoginCallback callback) {
        this.callback = callback;
    }

    public void init(){

        if (new CurrentUsersQuery().isLogged()){
            callback.signed();
        }else{
            callback.signUp();
        }
    }
}
