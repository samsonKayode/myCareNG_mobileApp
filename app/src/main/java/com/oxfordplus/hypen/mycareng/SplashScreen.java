package com.oxfordplus.hypen.mycareng;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.oxfordplus.hypen.sqlite.SqliteDatabase;

public class SplashScreen extends AppCompatActivity {

    SqliteDatabase db = new SqliteDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final ImageView iv = findViewById(R.id.imageView);

        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade);

        iv.startAnimation(an);


        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                finish();

                int confirmData = confirmNow();

                if(confirmData==0){

                    //go to sign up page..
                    startActivity(new Intent(getBaseContext(), SignUp.class));

                }else{

                    //go to code page..
                    startActivity(new Intent(getBaseContext(), UnlockScreen.class));

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    //confirm Existence of Data..

    public int confirmNow(){

        Cursor res = db.confirmData();

        int x = res.getCount();

        return x;
    }

}
