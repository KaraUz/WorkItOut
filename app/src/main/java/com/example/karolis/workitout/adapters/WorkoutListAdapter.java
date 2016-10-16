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
 * Created by Karolis on 2016-10-16.
 */

public class WorkoutListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Workout> workoutList;

    public WorkoutListAdapter(Context context, List<Workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Exercise> exerciseList = workoutList.get(groupPosition).getExercises();
        return exerciseList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View view, ViewGroup parent) {

        Exercise exercise = (Exercise) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_items, null);
        }
        view.setBackgroundResource(R.color.white);
        TextView sequence = (TextView) view.findViewById(R.id.sequence);
        sequence.setText(exercise.getName()+" - ");
        TextView childItem = (TextView) view.findViewById(R.id.childItem);
        childItem.setText(""+exercise.getDifficulty());

        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<Exercise> exerciseList = workoutList.get(groupPosition).getExercises();
        return exerciseList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return workoutList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return workoutList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        Workout workout = (Workout) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.group_items, null);
        }
        if(isExpanded){
            view.setBackgroundResource(R.color.colorAccent);
        }else{
            view.setBackgroundResource(R.color.white);
        }
        TextView heading = (TextView) view.findViewById(R.id.heading);
        heading.setText(workout.getName().trim());

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
