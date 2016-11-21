package com.example.karolis.workitout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.karolis.workitout.R;
import com.example.karolis.workitout.dataobjects.Exercise;
import com.example.karolis.workitout.dataobjects.Workout;

import java.util.List;

/**
 * Created by Karolis on 2016-11-17.
 */

public class ExerciseListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Exercise> exerciseList;

    public ExerciseListAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String description = exerciseList.get(groupPosition).getDescription();
        return description;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        Exercise exercise = (Exercise) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_exercise_description, null);
        }
        view.setBackgroundResource(R.color.white);
        TextView sequence = (TextView) view.findViewById(R.id.child_exercise_description);
        sequence.setText(exercise.getDescription());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return exerciseList.get(groupPosition).getDescription() == null || exerciseList.get(groupPosition).getDescription().equals("") ? 0 : 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return exerciseList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return exerciseList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        Exercise exercise = (Exercise) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.exercise_group_items, null);
        }
        TextView heading = (TextView) view.findViewById(R.id.exerciseHeading);
        if(isExpanded){
            view.setBackgroundResource(R.color.colorAccent);
            heading.setTextColor(view.getContext().getResources().getColor(R.color.white));
        }else{
            view.setBackgroundResource(R.color.white);
            heading.setTextColor(view.getContext().getResources().getColor(R.color.black));
        }

        heading.setText(exercise.getName().trim());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
