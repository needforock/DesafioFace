package com.desafiolatam.desafioface.data;

import com.desafiolatam.desafioface.models.CurrentUser;

/**
 * Created by Soporte on 15-Oct-17.
 */

public class CurrentUsersQuery {

    public boolean isLogged(){
        return CurrentUser.listAll(CurrentUser.class).size()>0;
    }

    public CurrentUser get(){
        return CurrentUser.listAll(CurrentUser.class).get(0);
    }
}
