package com.ifntuog.volkeee.schedule.tools.manager;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ifntuog.volkeee.schedule.model.Group;
import com.ifntuog.volkeee.schedule.model.Token;
import com.ifntuog.volkeee.schedule.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by volkeee on 10/29/17.
 */

public class TokenManager extends IntentService {
    public static final String PREFS_NAME = "SCHEDULE_APP";
    public static final String PREFS_NAME_TOKEN = "SCHEDULE_APP_TOKEN";
    public static final String PREFS_NAME_USER = "SCHEDULE_APP_USER";
    public static final String PREFS_NAME_GROUP = "SCHEDULE_APP_USER";
    private SharedPreferences mSharedPrefs;
    private SharedPreferences.Editor mPreferencesEditor;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private String rootUrl;
    private Gson mGson;

    public TokenManager() {
        super("TokenManagerService");
    }

    @SuppressLint("CommitPrefEdits")
    public TokenManager(Context context) {
        super("TokenManagerService");

        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
        mSharedPrefs = mContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        mPreferencesEditor = mSharedPrefs.edit();
        rootUrl = ConnectionManager.rootUrl;
        mGson = new Gson();
    }

    public void createUser(User user, Group group) {
        StringRequest request = new StringRequest(Request.Method.POST, rootUrl + "users", response -> {
            //TODO: Manage the response and save the received token
            Token token = new Token();
            Log.d("RESPONSE", response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonToken = jsonObject.getJSONObject("token");
                token.parseJSON(jsonToken);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            saveToken(user, group, token);
        }, error -> {
            Log.d("CreateUser", "Response is: " + error.toString());
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                Log.d("REQBODY", (("{" + group.toString() + "," + user.toString() + "}")));
                try {
                    String test = ("{\"group\":" + group.toJson().toString().trim() + ", " +
                            "\"user\":" + user.toJson().toString().trim() + ", " +
                            "\"device\":" + getDeviceName().toString().trim() + "}");
                    Log.d("SSS", test);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    return ("{\"group\":" + group.toJson().toString().trim() + ", " +
                            "\"user\":" + user.toJson().toString().trim() + ", " +
                            "\"device\":" + getDeviceName().toString().trim() + "}").getBytes();
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        mRequestQueue.add(request);
    }

    public boolean checkLocalToken() {
        if (readToken() != null) {
            return true;
        } else return false;
    }

    public void saveToken(Token token) {
        mPreferencesEditor.putString(PREFS_NAME_TOKEN, mGson.toJson(token));
        mPreferencesEditor.commit();
    }

    public void saveToken(User user, Group group, Token token) {
        mPreferencesEditor.putString(PREFS_NAME_USER, mGson.toJson(user))
                .putString(PREFS_NAME_GROUP, mGson.toJson(group))
                .putString(PREFS_NAME_TOKEN, mGson.toJson(token));
        mPreferencesEditor.commit();
    }

    @Nullable
    public Token readToken() {
        String json = mSharedPrefs.getString(PREFS_NAME_TOKEN, null);

        return mGson.fromJson(json, Token.class);
    }

    @Nullable
    public User readUser() {
        String json = mSharedPrefs.getString(PREFS_NAME_USER, null);

        return mGson.fromJson(json, User.class);
    }

    @Nullable
    public Group readGroup() {
        String json = mSharedPrefs.getString(PREFS_NAME_GROUP, null);

        return mGson.fromJson(json, Group.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    public static JSONObject getDeviceName() throws JSONException {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String api_level = String.valueOf(Build.VERSION.SDK_INT);
        return new JSONObject().put("device_manufacturer", manufacturer).put("device_model", model).put("os_version", api_level);
    }
}
