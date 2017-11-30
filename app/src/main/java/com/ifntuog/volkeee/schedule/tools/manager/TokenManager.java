package com.ifntuog.volkeee.schedule.tools.manager;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by volkeee on 10/29/17.
 */

public class TokenManager extends IntentService {
    public static final String PREFS_NAME = "SCHEDULE_APP";
    public static final String PREFS_NAME_TOKEN = "SCHEDULE_APP_TOKEN";
    public static final String PREFS_NAME_USER = "SCHEDULE_APP_USER";
    public static final String PREFS_NAME_GROUP = "SCHEDULE_APP_USER";
    public static final String ACTION_USER_DELETED = "USER_DELETED";
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

    public void createUserRequest(User user, Group group) {
        StringRequest request = new StringRequest(Request.Method.POST, rootUrl + "users", response -> {
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
//                Log.d("REQBODY", (("{" + group.toString() + "," + user.toString() + "}")));

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
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);
    }

    public void deleteUserRequest() {
        StringRequest request = new StringRequest(Request.Method.DELETE, rootUrl + "user/deactivate-device", response -> {
            Log.d("DeleteUser", response);
            Intent serviceIntent = new Intent(mContext, this.getClass());
            serviceIntent.setData(Uri.parse(response)).putExtra("type", "userDeleted");

            mContext.startService(serviceIntent);
        }, error -> {
            Log.e("DeleteUser", error.toString());
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", readToken().getToken());
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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

    public void removeToken() {

        mPreferencesEditor.remove(PREFS_NAME_USER).remove(PREFS_NAME_GROUP).remove(PREFS_NAME_TOKEN);
        mPreferencesEditor.apply();
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

    public void logoutUser() {
        deleteUserRequest();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String type = intent.getStringExtra("type");
        Intent response = new Intent();
        String dataString = intent.getDataString();
        TokenManager manager = new TokenManager(getApplicationContext());

        if (type.equals("userDeleted")) {
            response.setAction(ACTION_USER_DELETED);
            response.addCategory(Intent.CATEGORY_DEFAULT);

            manager.removeToken();

            sendBroadcast(response);
        }
    }

    public static JSONObject getDeviceName() throws JSONException {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String api_level = String.valueOf(Build.VERSION.SDK_INT);
        return new JSONObject().put("device_manufacturer", manufacturer).put("device_model", model).put("os_version", api_level);
    }
}
