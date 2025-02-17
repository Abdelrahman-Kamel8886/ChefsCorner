package com.abdok.chefscorner.Utils.Helpers;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarHelper {

    public static void showCustomSnackBar(Activity activity, String message , int colorResId , int gravity){
        try {
            View view = activity.findViewById(android.R.id.content);

            if (view != null){
                Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);

                View snackbarView = snackbar.getView();
                int color = ContextCompat.getColor(activity, colorResId);
                snackbarView.setBackgroundTintList(ColorStateList.valueOf(color));

                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
                params.gravity = gravity;
                snackbarView.setLayoutParams(params);

                snackbar.show();
            }
            else{
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
