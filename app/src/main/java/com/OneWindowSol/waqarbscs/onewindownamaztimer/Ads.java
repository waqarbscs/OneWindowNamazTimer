package com.OneWindowSol.waqarbscs.onewindownamaztimer;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.OneWindowSol.waqarbscs.onewindownamaztimer.Controllers.AdsTask;

public class Ads  {
    int resid;
    public Dialog dialog;
    public void showDialog(Activity activity,String name){
         dialog= new Dialog(activity);

       if(name.equals("splashnamaztimer")) {
           resid = R.drawable.background;
       }else {
           resid=R.drawable.menu_background;
       }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_ads);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ImageView imageView=(ImageView)dialog.findViewById(R.id.img);
        imageView.setImageResource(resid);
        ImageButton imageButton=(ImageButton)dialog.findViewById(R.id.imageView);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        }
    }

