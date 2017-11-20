package com.ifntuog.volkeee.schedule.tools.manager;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ifntuog.volkeee.schedule.model.User;

/**
 * Created by volkeee on 10/29/17.
 */

public class TokenManager extends IntentService {
    private Context mContext;
    private RequestQueue mRequestQueue;
    private String rootUrl;

    public TokenManager() {
        super("TokenManagerService");
    }

    public TokenManager(Context context) {
        super("TokenManagerService");

        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
        rootUrl = "http://192.168.43.222/";
    }

    public void authorizeUser(User user) {
        StringRequest authorizeRequest = new StringRequest(Request.Method.POST, rootUrl, response -> {

        }, error -> {

        });
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
