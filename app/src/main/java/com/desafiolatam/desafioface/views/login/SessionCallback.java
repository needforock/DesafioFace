package com.desafiolatam.desafioface.views.login;

/**
 * Created by Soporte on 04-Oct-17.
 */

public interface SessionCallback {
    void requiredField();
    void mailFormat();
    void success();
    void failure();
}
