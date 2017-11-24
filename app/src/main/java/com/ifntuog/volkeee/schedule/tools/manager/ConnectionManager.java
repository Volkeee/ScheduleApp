package com.ifntuog.volkeee.schedule.tools.manager;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ifntuog.volkeee.schedule.model.Group;
import com.ifntuog.volkeee.schedule.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by volkeee on 10/10/17.
 */

public class ConnectionManager extends IntentService {
    public static final String ACTION_RETURN_GROUPS = "com.ifntuog.volkeee.schedule.RETURNGROUPS";
    private static final String SERVICE_NAME = "ConnectionManagerService";

    public static String rootUrl = "http://10.0.2.2/";
    public static String rootSiteUrl = "http://rozklad.nung.edu.ua/";

    private Context mContext;
    private RequestQueue mRequestQueue;

    public ConnectionManager() {
        super(SERVICE_NAME);
    }

    public ConnectionManager(Context context) {
        super(SERVICE_NAME);
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);

        try {
            if(getDeviceName().getString("device_model").equals("Android SDK built for x86")) {
                rootUrl = "http://10.0.2.2/";
                requestGroupsFromSite();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestGroups() {
        StringRequest request = new StringRequest(Request.Method.GET, rootUrl + "groups", response -> {
            Log.d("VolleyGroups", response);
            Intent serviceIntent = new Intent(mContext, this.getClass());
            serviceIntent.setData(Uri.parse(response)).putExtra("type", "groups");

            mContext.startService(serviceIntent);
        }, error -> {
            Log.e("VolleyGroups", error.toString());
        });
        mRequestQueue.add(request);
    }

    public void requestGroupsFromSite() {
        StringRequest request = new StringRequest(Request.Method.GET, rootSiteUrl.concat("application/api/groups.php?sort=asc"), response -> {
            Intent serviceIntent = new Intent(mContext, this.getClass());
            serviceIntent.setData(Uri.parse(response)).putExtra("type", "groups");

            mContext.startService(serviceIntent);
        }, error -> {
            Log.e("GROUPS", error.toString());
        }) {

        };
        mRequestQueue.add(request);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String type = intent.getStringExtra("type");
        Intent response = new Intent();
        String dataString = intent.getDataString();

        if (type.equals("groups")) {
            try {
                ArrayList<Group> groups = Group.parseJSON(dataString);
                response.setAction(ACTION_RETURN_GROUPS);
                response.putExtra("groups", groups);
                response.addCategory(Intent.CATEGORY_DEFAULT);

                sendBroadcast(response);
            } catch (JSONException e) {
                Log.e("JSONPARSING", e.toString());
            }
        } else {

        }
    }

    public static JSONObject getDeviceName() throws JSONException {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String api_level = String.valueOf(Build.VERSION.SDK_INT);
        return new JSONObject().put("device_manufacturer", manufacturer).put("device_model", model).put("os_version", api_level);
    }
}
