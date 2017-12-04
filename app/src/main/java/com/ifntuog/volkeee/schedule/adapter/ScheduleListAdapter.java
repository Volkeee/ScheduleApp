package com.ifntuog.volkeee.schedule.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifntuog.volkeee.schedule.R;
import com.ifntuog.volkeee.schedule.model.Lesson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Created by volkeee on 12/2/17.
 */

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {
    private ArrayList<ViewHolder> mLessonLists;
    private ArrayList<Lesson> data;
    private TreeMap<Integer, String> days;
    private Integer mWeek;
    private Context mContext;

    public ScheduleListAdapter(ArrayList<Lesson> lessonsList, Context context, Integer week) {
        days = new TreeMap<>();
        data = new ArrayList<>();
        data.clear();
        data.addAll(lessonsList);
        mContext = context;
        mWeek = week;

        Calendar now = Calendar.getInstance(mContext.getResources().getConfiguration().locale);
        Map<String, Integer> displayNames = now.getDisplayNames(Calendar.DAY_OF_WEEK,
                Calendar.LONG, mContext.getResources().getConfiguration().locale);

        for (Map.Entry<String, Integer> e : displayNames.entrySet()) {
            if (e.getValue() != Calendar.SUNDAY && e.getValue() != Calendar.SATURDAY)
                days.put(e.getValue(), e.getKey());
        }

        LinkedHashMap<Integer, String> buffer = new LinkedHashMap<>();
        for (Lesson lesson :
                lessonsList) {
            Integer day = lesson.getDay() + 1;
            if (!buffer.containsKey(day)) {
                buffer.put(day, days.get(day));
            }
        }
        days.clear();
        TreeMap<Integer, String> copy = new TreeMap<>(buffer);
        days.clear();
        days.putAll(copy);

        mLessonLists = new ArrayList<>();
    }

    @Override
    public ScheduleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_list_container, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Integer viewsDay;
        switch (position) {
            case 0:
                viewsDay = 2;
                break;
            case 1:
                viewsDay = 3;
                break;
            case 2:
                viewsDay = 4;
                break;
            case 3:
                viewsDay = 5;
                break;
            case 4:
                viewsDay = 6;
                break;
            default:
                viewsDay = -1;
                break;
        }

        if (days.containsKey(viewsDay)) {
            String day = days.get(viewsDay);
            holder.textViewDay.setText(day);
            holder.setDay(viewsDay);
//            mLessonLists.add(holder);

            ArrayList<Lesson> sortedLessons = new ArrayList<>();
            for (Lesson lesson :
                    data) {
                if (Objects.equals(lesson.getDay() + 1, viewsDay)) {
                    sortedLessons.add(lesson);
                }
            }
            holder.setDataForList(sortedLessons);
        } else {
//            removeItem(holder.getAdapterPosition());
            holder.setVisibility(false);
        }
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    public void setDataForInnerList(ArrayList<Lesson> lessons) {
        for (int i = 1; i <= 5; i++) {
            ArrayList<Lesson> sortedLessons = new ArrayList<>();
            for (Lesson lesson :
                    lessons) {
                if (lesson.getDay() == i) {
                    sortedLessons.add(lesson);
                }
            }
            mLessonLists.get(i).setDataForList(sortedLessons);
        }
    }

    private void removeItem(int position) {
        days.remove(position+1);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LessonsRecyclerAdapter mAdapter;
        private ArrayList<Lesson> mLessons;
        private Context mContext;
        private CardView rootView;
        public Integer day;
        public TextView textViewDate;
        public TextView textViewDay;
        public RecyclerView lessonsRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
//            textViewDate = itemView.findViewById(R.id.date_text_view);
            textViewDay = itemView.findViewById(R.id.date_text_view);
            lessonsRecyclerView = itemView.findViewById(R.id.lessons_recycler_view);
            rootView = itemView.findViewById(R.id.schedule_card_view_container);
            mContext = itemView.getContext();
        }

        public void hideHolder() {
            rootView.setVisibility(View.GONE);
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public void setDataForList(ArrayList<Lesson> lessons) {
            mLessons = new ArrayList<>();
//            mLessons.addAll(lessons);
            mAdapter = new LessonsRecyclerAdapter(mLessons);

            lessonsRecyclerView.setAdapter(mAdapter);
            lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter.swap(lessons);
        }
        public void setVisibility(boolean isVisible){
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
            if (isVisible){
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            }else{
                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }
    }
}
