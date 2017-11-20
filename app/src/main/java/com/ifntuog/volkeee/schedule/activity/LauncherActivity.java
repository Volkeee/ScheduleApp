package com.ifntuog.volkeee.schedule.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ifntuog.volkeee.schedule.R;
import com.ifntuog.volkeee.schedule.adapter.SearchableSpinnerAdapter;
import com.ifntuog.volkeee.schedule.model.Group;
import com.ifntuog.volkeee.schedule.model.User;
import com.ifntuog.volkeee.schedule.tools.manager.ConnectionManager;
import com.ifntuog.volkeee.schedule.tools.FBUtil;
import com.ifntuog.volkeee.schedule.tools.views.CustomMaterialSpinner;
import com.ifntuog.volkeee.schedule.tools.views.CustomSearchableSpinner;


import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class LauncherActivity extends AppCompatActivity {
    public static int RC_SIGN_IN = 1,
            RC_FB_SIGN_IN = 2;
    private GroupsBroadcastReceiver mGroupsBroadcastReceiver;
    private CallbackManager mCallbackManager;
    private CustomMaterialSpinner mSpinner;
    private SearchableSpinnerAdapter mAdapter;
    private FBUtil prefUtil;
    private User mUser;
    private Group mGroup;

    private static Context mContext;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mContext = this;
        mSpinner = findViewById(R.id.groups_spinner);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, v -> {
                    Log.e("GOOGLESIGN", "Can't connect to google!");
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        prefUtil = new FBUtil(this);
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {


                        String accessToken = loginResult.getAccessToken().getToken();

                        // save accessToken to SharedPreference
                        prefUtil.saveAccessToken(accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (jsonObject, response) -> {

                                    // Getting FB User Data
                                    Bundle facebookData = getFacebookData(jsonObject);

                                    mUser = new User();

                                    mUser.setName(facebookData.getString("first_name"));
                                    mUser.setEmail(facebookData.getString("email"));
                                    mUser.setOrigin("fb");

                                    startActivityWithData(mUser, (Group) mSpinner.getSelectedItem());
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }


                    @Override
                    public void onCancel() {
                        Log.d("LauncherActivity", "Login attempt cancelled.");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        e.printStackTrace();
                        Log.d("LauncherActivity", "Login attempt failed.");
                        deleteAccessToken();
                    }
                });
        findViewById(R.id.button3).setOnClickListener(v -> {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
        findViewById(R.id.button2).setOnClickListener(view -> {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
        });

        findViewById(R.id.mainCardView).setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        IntentFilter groupsFilter = new IntentFilter(ConnectionManager.ACTION_RETURN_GROUPS);
        groupsFilter.addCategory(Intent.CATEGORY_DEFAULT);

        mGroupsBroadcastReceiver = new GroupsBroadcastReceiver();
        registerReceiver(mGroupsBroadcastReceiver, groupsFilter);

        ConnectionManager connectionManager = new ConnectionManager(this);
        connectionManager.requestGroups();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("GOOGLESIGN", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(this, "Logged in as ".concat(acct.getDisplayName()), Toast.LENGTH_LONG).show();

            mUser = new User();

            mUser.setName(acct.getDisplayName());
            mUser.setEmail(acct.getEmail());
            mUser.setOrigin("ggl");

            startActivityWithData(mUser, (Group) mSpinner.getSelectedItem());
        } else {
            Log.e("GOOGLESIGN", "Error while trying to login with Google!" + result.toString());
        }
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));


            prefUtil.saveFacebookUserInfo(object.getString("first_name"),
                    object.getString("last_name"), object.getString("email"),
                    object.getString("gender"), profile_pic.toString());

        } catch (Exception e) {
            Log.d("LauncherActivity", "BUNDLE Exception : " + e.toString());
        }

        return bundle;
    }

    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    //User logged out
                    prefUtil.clearToken();
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }

    private void startActivityWithData(User user, Group group) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("group", group);

        Log.d("SELECTED", group.toString());

        startActivity(intent);
    }

    public static Context getContext() {
        return mContext;
    }

    private class GroupsBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            findViewById(R.id.mainCardView).setVisibility(View.VISIBLE);
            findViewById(R.id.progressbarCardView).setVisibility(View.INVISIBLE);
            ArrayList<Group> groups = (ArrayList<Group>) intent.getSerializableExtra("groups");
            findViewById(R.id.mainCardView).setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(LauncherActivity.getContext(), R.anim.swing_up_left);
            findViewById(R.id.mainCardView).startAnimation(animation);

            ArrayList<String> groupsStrings = new ArrayList<>();
            for (Group group :
                    groups) {
                groupsStrings.add(group.getName());
            }
            Collections.sort(groupsStrings);

            mAdapter = new SearchableSpinnerAdapter(LauncherActivity.mContext, R.layout.item_spinner_textview, groups);

            mSpinner.setAdapter(mAdapter);
//            mSpinner.setTitle("Select you group");
        }
    }

}
