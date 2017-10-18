package com.desafiolatam.desafioface.network.users;

import android.os.AsyncTask;
import android.util.Log;

import com.desafiolatam.desafioface.models.Developer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Soporte on 10-Oct-17.
 */

public class GetUsers extends AsyncTask<Map<String, String>, Integer, Integer> {

    private int additionalPages;
    private Map<String, String> queryMap;
    private int result;
    private final Users request = new UserInterceptor().get();


    public GetUsers(int additionalPages) {
        this.additionalPages = additionalPages;
    }

    @Override
    protected Integer doInBackground(Map<String, String>... maps) {
        queryMap = maps[0];
        if (additionalPages < 0) {
            while (200 == connect()) {
                increasePage();
            }
        } else {
            while (additionalPages >= 0) {
                additionalPages--;
                connect();
                increasePage();
            }
        }

        return null;
    }

    private void increasePage() {
        int page = Integer.parseInt(queryMap.get("page"));
        page++;
        queryMap.put("page", String.valueOf(page));
    }

    private int connect() {
        int code = 666;
        Call<Developer[]> call = request.get(queryMap);
        try {
            Response<Developer[]> response = call.execute();
            code = response.code();

            if (200 == code && response.isSuccessful()) {

                Developer[] developers = response.body();
                if (developers != null && developers.length > 0) {
                    Log.d("DEVELOPERS", String.valueOf(developers.length));
                    for(Developer servDeveloper: developers ){
                        List<Developer> localDev = Developer.find(Developer.class, "serverId = ?", String.valueOf(servDeveloper.getId()));
                        if(localDev!= null && localDev.size()>0){
                            Developer local = localDev.get(0);
                            local.setEmail(servDeveloper.getEmail());
                            local.setPhoto_url(servDeveloper.getPhoto_url());
                            local.save();
                        }else{
                            servDeveloper.create();
                        }
                    }
                }else{
                    code=123;
                }


            } else {
                code = 777;

            }


        } catch (IOException e) {
            e.printStackTrace();
            code = 888;

        }
        result = code;

        return result;
    }
}
