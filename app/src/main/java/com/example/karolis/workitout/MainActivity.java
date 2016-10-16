package com.example.karolis.workitout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.karolis.workitout.adapters.WorkoutListAdapter;
import com.example.karolis.workitout.dao.Database;
import com.example.karolis.workitout.dataobjects.Workout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private WorkoutListAdapter listAdapter;
    private ExpandableListView expandableWorkoutList;
    private int selectedGroup = -1;

    private Database db;
    private List<Workout> workoutList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add data for displaying in expandable list view
        loadData();

        //get reference of the ExpandableListView
        expandableWorkoutList = (ExpandableListView) findViewById(R.id.expandable_workout_list);
        // create the adapter by passing your ArrayList data
        listAdapter = new WorkoutListAdapter(MainActivity.this,workoutList);
        // attach the adapter to the expandable list view
        expandableWorkoutList.setAdapter(listAdapter);

        //collapse all the Groups
        collapseAll();

        expandableWorkoutList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(groupPosition != selectedGroup || selectedGroup == -1) {
                   return false;
                }
                return true;
            }
        });
        //Responsible for expanding only one group.
        expandableWorkoutList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != selectedGroup) {
                    expandableWorkoutList.collapseGroup(selectedGroup);
                }
                selectedGroup = groupPosition;
            }
        });

    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableWorkoutList.expandGroup(i);
        }
    }

    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            expandableWorkoutList.collapseGroup(i);
        }
    }

    //load some initial data into out list
    private void loadData(){
        db = new Database(getApplication().openOrCreateDatabase("workout.db", Context.MODE_PRIVATE,null));
        workoutList = db.getAllWorkouts();
    }


    //Triggers when floating start button is clicked
    public void onClickStart(View view){
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
