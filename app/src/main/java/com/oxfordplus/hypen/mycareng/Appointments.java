package com.oxfordplus.hypen.mycareng;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.oxfordplus.hypen.alerts.DialogBoxes;
import com.oxfordplus.hypen.interfaces.ProfileInterface;
import com.oxfordplus.hypen.models.AppointmentModel;
import com.oxfordplus.hypen.utils.ApiUtils;
import com.oxfordplus.hypen.utils.AppointmentAdapter;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Appointments extends AppCompatActivity {

    EditText searchText;

    ListView listView;

    List<ArrayList> theList;

    DialogBoxes dbox = new DialogBoxes();

    List<AppointmentModel> myResponse;

    String pattern = "dd-MMM-yyyy";
    String nDate;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    SimpleDateFormat originalDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    static ProfileInterface profileInterface;

    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //toolbar.setLogo(R.drawable.baseline_alarm_white_24);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.list);

        searchText = findViewById(R.id.search);

        loadAppointments(SignUp.userId);

        System.out.println("PROFILE ID>>>>>>>>>"+SignUp.userId);
    }

    public void newAppointment() {

        Intent i = new Intent(Appointments.this, NewAppointment.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appointments_menu, menu);
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

            Intent i = new Intent(Appointments.this, NewAppointment.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadAppointments(int profile_id){

        final ProgressDialog pg = dbox.showProgressDialog(this, "Loading your appointments...");

        pg.show();

        profileInterface = ApiUtils.getAPIService();

        profileInterface.getAppointmentData(profile_id).enqueue(new Callback<List<AppointmentModel>>() {
            @Override
            public void onResponse(Call<List<AppointmentModel>> call, Response<List<AppointmentModel>> response) {

                System.out.println("I GOT HERE<,,,,,,,,,");

                myResponse = response.body();

                if(response.isSuccessful()){
                    //response is successful..
                    pg.cancel();
                    System.out.println("RESPONSE SIZE====>>> "+myResponse.size());
                    System.out.println("MY RESPONSE>>>>>>"+myResponse);

                    for(int i=0; myResponse.size()>i; i++){

                        AppointmentModel am = myResponse.get(i);

                        try{

                            Date date = originalDateFormat.parse(am.getDate());

                            nDate = simpleDateFormat.format(date);

                            System.out.println("FORMATTED DATE >>>>"+nDate);

                            am.setDate(nDate);

                        }
                        catch (Exception nn){

                            System.out.println("Super Exception here >>>>>>>"+nn);

                        }

                        listView.setAdapter(new AppointmentAdapter(getApplicationContext(), myResponse));

                    }


                }

            }

            @Override
            public void onFailure(Call<List<AppointmentModel>> call, Throwable t) {

                pg.cancel();
                System.out.println("Error loading appointment data "+t.getMessage());

                t.getStackTrace();
                dbox.showMessageDialog(Appointments.this, getString(R.string.error_dialog), getString(R.string.appointment_error),R.drawable.error_icon, getString(R.string.close));
            }
        });


    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        View empty = findViewById(R.id.empty);
        ListView list = (ListView) findViewById(R.id.list);
        list.setEmptyView(empty);
    }
}
