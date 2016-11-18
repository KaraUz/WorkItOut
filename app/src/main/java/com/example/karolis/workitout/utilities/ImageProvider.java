package com.example.karolis.workitout.utilities;

import android.media.Image;
import android.support.annotation.DrawableRes;

import com.example.karolis.workitout.R;

/**
 * Created by Dziugas on 11/18/2016.
 */

public class ImageProvider {
    private @DrawableRes int squatImageID;
    private @DrawableRes int jumpImageID;

    public ImageProvider(){
        squatImageID = R.drawable.squat_image;
        jumpImageID = R.drawable.jump_image;
    }

    public @DrawableRes int getImage(String exerciseName){
        return exerciseName.toLowerCase().contains("jump") ? jumpImageID : squatImageID;
    }
}
