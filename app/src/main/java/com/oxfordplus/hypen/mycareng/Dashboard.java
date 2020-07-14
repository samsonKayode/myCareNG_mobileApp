package com.oxfordplus.hypen.mycareng;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    TextView email, fullname;
    int UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);

        fullname.setText(SignUp.r_firstName);
        email.setText(SignUp.r_email);
    }

    public void gotoHospital(View view) {

        Intent intent = new Intent(this, Hospitals.class);
        startActivity(intent);
    }

    public void gotoAppointment(View view) {

        Intent intent = new Intent(this, Appointments.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void gotoSetting(View view) {

        
    }
}
