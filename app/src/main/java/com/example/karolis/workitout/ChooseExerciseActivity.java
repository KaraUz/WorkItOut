package com.example.karolis.workitout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.karolis.workitout.adapters.ExerciseListAdapter;
import com.example.karolis.workitout.dao.Database;
import com.example.karolis.workitout.dataobjects.Exercise;

import java.util.List;

public class ChooseExerciseActivity extends AppCompatActivity {
    private ExerciseListAdapter listAdapter;
    private ExpandableListView expandableExerciseList;
    private int selectedGroup = -1;
    FloatingActionButton fab;
    private List<Exercise> exerciseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Choose an Exercise");
        setSupportActionBar(toolbar);

        loadData();
        fab = (FloatingActionButton) findViewById(R.id.choose_exercise_fab);
        //get reference of the ExpandableListView
        expandableExerciseList = (ExpandableListView) findViewById(R.id.expandable_exercise_list);
        // create the adapter by passing your ArrayList data
        listAdapter = new ExerciseListAdapter(this,exerciseList);
        // attach the adapter to the expandable list view
        expandableExerciseList.setAdapter(listAdapter);

        //collapse all the Groups
        collapseAll();

        expandableExerciseList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(groupPosition != selectedGroup || selectedGroup == -1) {
                    return false;
                }
                return true;
            }
        });
        //Responsible for expanding only one group.
        expandableExerciseList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(View.GONE==fab.getVisibility()) fab.show();
                if(groupPosition != selectedGroup) {
                    expandableExerciseList.collapseGroup(selectedGroup);
                }
                selectedGroup = groupPosition;
            }
        });

    }

    private void loadData() {
        Database db = new Database(getApplication().openOrCreateDatabase("workout.db", Context.MODE_PRIVATE,null));
        exerciseList = db.getAllExercises();
    }


    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableExerciseList.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableExerciseList.collapseGroup(i);
        }
    }

    public void onClickConfirm(View view) {
        if(selectedGroup <0) {
            Toast.makeText(this, "Please select an exercise!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent returnIntent = new Intent();
        Exercise exercise = exerciseList.get(selectedGroup);
        SeekBar difficultyBar = (SeekBar) findViewById(R.id.difficultyBar);
        exercise.setDifficulty(difficultyBar.getProgress()+1);
        returnIntent.putExtra("exercise",exercise);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
