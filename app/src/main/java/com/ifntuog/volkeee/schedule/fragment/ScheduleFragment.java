package com.ifntuog.volkeee.schedule.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifntuog.volkeee.schedule.R;
import com.ifntuog.volkeee.schedule.activity.MainActivity;
import com.ifntuog.volkeee.schedule.adapter.ScheduleListAdapter;
import com.ifntuog.volkeee.schedule.model.Lesson;
import com.ifntuog.volkeee.schedule.tools.manager.ConnectionManager;

import java.util.ArrayList;

/**
 * Created by volkeee on 12/4/17.
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class ScheduleFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ScheduleListAdapter mAdapter;
    private Integer mFragmentsWeek;
    private FragmentBroadcastReceiver mBroadcastReceiver;
    private ConnectionManager mConnectionManager;

    private boolean listInitialized = false;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ScheduleFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ScheduleFragment newInstance(int sectionNumber) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
//        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start, container, false);
        mBroadcastReceiver = new FragmentBroadcastReceiver();
        mConnectionManager = new ConnectionManager(getContext());

        mFragmentsWeek = getArguments().getInt(ARG_SECTION_NUMBER);

        mRecyclerView = rootView.findViewById(R.id.schedule_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mConnectionManager.requestSchedule(MainActivity.mGroup, mFragmentsWeek);

        IntentFilter lessonsFilter = new IntentFilter(ConnectionManager.ACTION_RETURN_LESSONS + mFragmentsWeek);
        lessonsFilter.addCategory(Intent.CATEGORY_DEFAULT);

        getActivity().registerReceiver(mBroadcastReceiver, lessonsFilter);

        return rootView;
    }

    private class FragmentBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentType = intent.getAction();

            if (intentType.equals(ConnectionManager.ACTION_RETURN_LESSONS + mFragmentsWeek)) {
                handleLessonsBroadcast(context, intent);
            }
        }

        private void handleLessonsBroadcast(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectionManager.ACTION_RETURN_LESSONS + mFragmentsWeek)) {
                mAdapter = new ScheduleListAdapter((ArrayList<Lesson>) intent.getSerializableExtra("lessons"), getContext(), mFragmentsWeek);
                mRecyclerView.setAdapter(mAdapter);
                listInitialized = !listInitialized;

            }

//            mAdapter.setDataForInnerList();
        }
    }
}
