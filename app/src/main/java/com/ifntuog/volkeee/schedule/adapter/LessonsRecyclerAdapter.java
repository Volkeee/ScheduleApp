package com.ifntuog.volkeee.schedule.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifntuog.volkeee.schedule.R;
import com.ifntuog.volkeee.schedule.model.Lesson;

import java.util.ArrayList;

/**
 * Created by volkeee on 12/3/17.
 */

public class LessonsRecyclerAdapter extends RecyclerView.Adapter<LessonsRecyclerAdapter.ViewHolder> {
    private ArrayList<Lesson> lessons;
    private OnItemClickListener mOnItemClickListener;

    public LessonsRecyclerAdapter(ArrayList<Lesson> lessons, OnItemClickListener onItemClickListener) {
        this.lessons = new ArrayList<>();
        swap(lessons);
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout viewHolder = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lessons_list_container, parent, false);

        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);

        holder.lessonAuditory.setText(lesson.getAuditory());
        holder.lessonName.setText(lesson.getName());
        holder.lessonOrder.setText(lesson.getPeriod().toString());
        holder.lessonTeacher.setText(lesson.getTeacher());
        holder.lessonType.setText(lesson.getType());

        holder.setOnItemClickListener(lessons.get(position), mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void swap(ArrayList<Lesson> datas)
    {
        if(datas == null || datas.size()==0)
            return;
        if (lessons != null && lessons.size()>0)
            lessons.clear();
        lessons.addAll(datas);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout rootConstraint;
        TextView lessonName;
        TextView lessonTeacher;
        TextView lessonAuditory;
        TextView lessonOrder;
        TextView lessonType;

        public ViewHolder(View itemView) {
            super(itemView);
            this.lessonName = itemView.findViewById(R.id.textView_name);
            this.lessonTeacher = itemView.findViewById(R.id.textView_teacher);
            this.lessonAuditory = itemView.findViewById(R.id.textView_auditory);
            this.lessonOrder = itemView.findViewById(R.id.textView_order);
            this.lessonType = itemView.findViewById(R.id.textView_type);

            this.rootConstraint = (ConstraintLayout) itemView.getRootView();
        }

        public void setOnItemClickListener(Lesson lesson, OnItemClickListener onItemClickListener) {
            rootConstraint.setOnClickListener(view -> {
                mOnItemClickListener.onItemClick(lesson);
            });
        }
    }
}
