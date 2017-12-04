package com.ifntuog.volkeee.schedule.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ifntuog.volkeee.schedule.R;
import com.ifntuog.volkeee.schedule.fragment.ScheduleFragment;
import com.ifntuog.volkeee.schedule.model.Group;
import com.ifntuog.volkeee.schedule.model.Token;
import com.ifntuog.volkeee.schedule.model.User;
import com.ifntuog.volkeee.schedule.tools.manager.ConnectionManager;
import com.ifntuog.volkeee.schedule.tools.manager.TokenManager;

public class MainActivity extends AppCompatActivity {
    private User mUser;
    public static Group mGroup;
    private Token mToken;
    private TokenManager mTokenManager;
    private ConnectionManager mConnectionManager;
    private ServiceBroadcastReceiver mServiceBroadcastReceiver;
    private RecyclerView mRecyclerView;
    private boolean progressBarVisibility = false;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnectionManager = new ConnectionManager(this);
        mTokenManager = new TokenManager(this);
        mServiceBroadcastReceiver = new ServiceBroadcastReceiver();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (!mTokenManager.checkLocalToken()) {
            if (getIntent() != null) {
                mUser = (User) getIntent().getSerializableExtra("user");
                mGroup = (Group) getIntent().getSerializableExtra("group");

                Log.d("user", mUser.toString());
                Log.d("group", mGroup.toString());

                mTokenManager.createUserRequest(mUser, mGroup);
            }
        } else {
            mUser = mTokenManager.readUser();
            mGroup = mTokenManager.readGroup();
            mToken = mTokenManager.readToken();
        }
//        toggleProgressBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            toggleProgressBar();
            mTokenManager.logoutUser();

            IntentFilter userDeletedFilter = new IntentFilter(TokenManager.ACTION_USER_DELETED);
            userDeletedFilter.addCategory(Intent.CATEGORY_DEFAULT);

            registerReceiver(mServiceBroadcastReceiver, userDeletedFilter);
        }
        return super.onOptionsItemSelected(item);
    }

    public void toggleProgressBar() {
        TransitionManager.beginDelayedTransition(findViewById(R.id.main_activity_root));
        progressBarVisibility = !progressBarVisibility;
        findViewById(R.id.progressBar3).setVisibility(progressBarVisibility ? View.VISIBLE : View.GONE);
    }

    private class ServiceBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentType = intent.getAction();

            if (intentType.equals(TokenManager.ACTION_USER_DELETED)) {
                handleLogoutBroadcast(context, intent);
            } else {
            }
        }


        private void handleLogoutBroadcast(Context context, Intent intent) {
            finish();
            Intent activityIntent = new Intent(context, LauncherActivity.class);
            startActivity(activityIntent);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a ScheduleFragment (defined as a static inner class below).
            return ScheduleFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
