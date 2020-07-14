package com.oxfordplus.hypen.mycareng;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.oxfordplus.hypen.alerts.DialogBoxes;
import com.oxfordplus.hypen.interfaces.ProfileInterface;
import com.oxfordplus.hypen.models.AppointmentModel;
import com.oxfordplus.hypen.models.HospitalAddress;
import com.oxfordplus.hypen.models.HospitalData;
import com.oxfordplus.hypen.models.HospitalDoctor;
import com.oxfordplus.hypen.models.HospitalSpeciality;
import com.oxfordplus.hypen.models.ServerResponse;
import com.oxfordplus.hypen.utils.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAppointment extends AppCompatActivity {

    static String selectedHospitalName="", selectedAddress="", selectedSpec="";

    List<HospitalData> myResponse, hospitalDetail;

    String hospitalName="";

    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();

    static EditText appointmentDate, appointmentTime;
    String appointmentDateText;

    String am_pm="";

    DialogBoxes dbox = new DialogBoxes();

    ProfileInterface profileInterface;

    Spinner hospital_name, hospital_branch, hospital_speciality, hospital_doctor;

    ArrayAdapter name_adapter;
    ArrayList<String> hName;

    ArrayList<String> hospitalBranch, hospitalSpec, hospitalDoc;

    int hospitalID=0;

    List<HospitalAddress> hospitalAddresses;
    List<HospitalSpeciality> hospitalSpeciality;
    List<HospitalDoctor> hospitalDoctor;


    String serverResponse="";
    int responseCode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //toolbar.setLogo(R.drawable.baseline_alarm_white_24);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hospital_name = (Spinner) findViewById(R.id.hospital_name);
        hospital_branch = (Spinner) findViewById(R.id.hospital_location);
        hospital_speciality = (Spinner) findViewById(R.id.hospital_department);
        hospital_doctor = findViewById(R.id.doctor);

        hName = new ArrayList<>();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        appointmentDate = (EditText) findViewById(R.id.date);
        appointmentTime = (EditText) findViewById(R.id.time);

        //calling loadHospitals()
        loadHospitals();

        //add action to hospital name spinner.

        hospital_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHospitalName = parent.getItemAtPosition(position).toString();

                if(selectedHospitalName.length()<2){

                }else{

                    String[] abc = selectedHospitalName.split("#");

                    hospitalID = Integer.parseInt(abc[1]);

                    System.out.println("HOSPITAL NAME>>>>>>>>>>"+selectedHospitalName);
                    System.out.println("HOSPITAL ID>>>>>>>>>>"+hospitalID);

                    hospitalAddress(hospitalID);
                }



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //hospital branch action...

        hospital_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAddress = parent.getItemAtPosition(position).toString();

                if(selectedAddress.length()<2 || selectedHospitalName.length()<2){

                }else{

                    hospitalSpec(hospitalID);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        hospital_speciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpec = parent.getItemAtPosition(position).toString();

                if(selectedAddress.length()<2 || selectedHospitalName.length()<2||selectedSpec.length()<2){

                }else{

                    hospitalDOC(hospitalID, selectedSpec);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void loadDate(View view) {

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        appointmentDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void saveAppointment(View view) {

        String name="";

        AppointmentModel theAppointments = new AppointmentModel();

        String date = appointmentDate.getText().toString();
        String time = appointmentTime.getText().toString();
        String[] namex = hospital_name.getSelectedItem().toString().split("#");
        try{
             name = namex[0];
        }catch(Exception vv){

        }
        String dept = hospital_speciality.getSelectedItem().toString();
        String doc = hospital_doctor.getSelectedItem().toString();
        String addr = hospital_branch.getSelectedItem().toString();
        //String hospital_id = hospitalID;

        theAppointments.setDate(date);
        theAppointments.setTime(time);
        theAppointments.setHospitalName(name);
        theAppointments.setDepartment(dept);
        theAppointments.setDoctorName(doc);
        theAppointments.setHospitalAddress(addr);
        theAppointments.setProfile_id(String.valueOf(SignUp.userId));
        theAppointments.setStatus("PENDING");

        if(date.length()<=2||time.length()<=2||name.length()<=2||dept.length()<=2||doc.length()<=2||addr.length()<=2){

            dbox.showMessageDialog(NewAppointment.this, getString(R.string.error_dialog),
                    getString(R.string.fill_in_data),R.drawable.error_icon, getString(R.string.close));

        }else{

            saveAppointmentData(theAppointments);
        }

    }

    public void loadTime(View view) {

       final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        final int ampm = mcurrentTime.get(Calendar.AM_PM);


        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(NewAppointment.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                /*

                if (mcurrentTime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (mcurrentTime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";
                */

                appointmentTime.setText( selectedHour + ":" + selectedMinute+" "+ampm);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void loadHospitals(){

        final ProgressDialog pg = dbox.showProgressDialog(this, "Loading available hospitals...");

        pg.show();

        profileInterface = ApiUtils.getAPIService();

        profileInterface.getHospitalList().enqueue(new Callback<List<HospitalData>>() {
            @Override
            public void onResponse(Call<List<HospitalData>> call, Response<List<HospitalData>> response) {

                myResponse = response.body();

                if(response.isSuccessful()){

                    hName.add("");

                    pg.cancel();

                    for(int i=0; myResponse.size()>i; i++){

                        HospitalData myData = myResponse.get(i);

                        String a1 = myData.getHospitalName();
                        int a2 = myData.getId();

                        hName.add(a1+"#"+a2);
                    }

                    hospital_name.setAdapter(new ArrayAdapter<String>
                            (NewAppointment.this, android.R.layout.simple_spinner_dropdown_item, hName));
                }
            }

            @Override
            public void onFailure(Call<List<HospitalData>> call, Throwable t) {

                pg.cancel();

                dbox.showMessageDialog(NewAppointment.this, getString(R.string.error_dialog), getString(R.string.hospital_error),R.drawable.error_icon, getString(R.string.close));

            }
        });

    }

    public void hospitalAddress(int id){

        final ProgressDialog pg = dbox.showProgressDialog(this, "Loading hospital locations...");

        pg.show();

        hospitalBranch = new ArrayList<>();

        profileInterface = ApiUtils.getAPIService();

        profileInterface.getHospitalAddress(id).enqueue(new Callback<List<HospitalAddress>>() {
            @Override
            public void onResponse(Call<List<HospitalAddress>> call, Response<List<HospitalAddress>> response) {

                hospitalAddresses = response.body();

                if(response.isSuccessful()){

                    hospitalBranch.add("");

                    pg.cancel();

                        for(int j =0; hospitalAddresses.size()>j; j++){

                            HospitalAddress ha = hospitalAddresses.get(j);

                            String address = ha.getAddress();
                            String city = ha.getCity();
                            String state = ha.getState();

                            String fullAddress = address+", "+city+", "+state;

                            System.out.println("HOSPITAL ADDRESS>>>>>"+fullAddress);

                            hospitalBranch.add(fullAddress);

                        }

                    }

                    hospital_branch.setAdapter(new ArrayAdapter<String>
                            (NewAppointment.this, android.R.layout.simple_spinner_dropdown_item, hospitalBranch));
                }

            @Override
            public void onFailure(Call<List<HospitalAddress>> call, Throwable t) {

                pg.cancel();

                t.printStackTrace();
                System.out.println("ERROR MESSAGE >>>>>>>>>>>"+t.getMessage());

                dbox.showMessageDialog(NewAppointment.this, getString(R.string.error_dialog), getString(R.string.hospital_error_detail),R.drawable.error_icon, getString(R.string.close));

            }
        });
    }


    //getting specs..

    public void hospitalSpec(int id){

        final ProgressDialog pg = dbox.showProgressDialog(this, "Loading hospital specialities...");

        pg.show();

        hospitalSpeciality = new ArrayList<>();
        hospitalSpec = new ArrayList<>();

        profileInterface = ApiUtils.getAPIService();

        profileInterface.getHospitalSpecialities(id).enqueue(new Callback<List<HospitalSpeciality>>() {
            @Override
            public void onResponse(Call<List<HospitalSpeciality>> call, Response<List<HospitalSpeciality>> response) {

                hospitalSpeciality = response.body();

                if(response.isSuccessful()){

                    hospitalSpec.add("");

                    pg.cancel();

                    for(int k =0; hospitalSpeciality.size()>k; k++){

                        HospitalSpeciality hs = hospitalSpeciality.get(k);

                        String spec = hs.getSpeciality();

                        System.out.println("HOSPITAL SPECIALITY>>>>>>"+spec);

                        hospitalSpec.add(spec);

                    }

                    hospital_speciality.setAdapter(new ArrayAdapter<>
                            (NewAppointment.this, android.R.layout.simple_spinner_dropdown_item, hospitalSpec));
                }
            }

            @Override
            public void onFailure(Call<List<HospitalSpeciality>> call, Throwable t) {

                pg.cancel();

                t.printStackTrace();

                dbox.showMessageDialog(NewAppointment.this, getString(R.string.error_dialog), getString(R.string.hospital_error_detail),R.drawable.error_icon, getString(R.string.close));

            }
        });
    }

    //get Doctors..

    public void hospitalDOC(int id, String spec){

        final ProgressDialog pg = dbox.showProgressDialog(this, "Loading hospital doctors...");
        pg.show();

        hospitalDoctor = new ArrayList<>();
        hospitalDoc = new ArrayList<>();

        profileInterface = ApiUtils.getAPIService();

        profileInterface.getDoctors(id, spec).enqueue(new Callback<List<HospitalDoctor>>() {
            @Override
            public void onResponse(Call<List<HospitalDoctor>> call, Response<List<HospitalDoctor>> response) {

                hospitalDoctor = response.body();

                if(response.isSuccessful()){

                    hospitalDoc.add("");

                    pg.cancel();

                    for(int k =0; hospitalDoctor.size()>k; k++){

                        HospitalDoctor hd = hospitalDoctor.get(k);

                        String doc = hd.getDoctor_name();

                        System.out.println("HOSPITAL DOCTOR>>>>>>"+doc);

                        hospitalDoc.add(doc);

                    }

                    hospital_doctor.setAdapter(new ArrayAdapter<>
                            (NewAppointment.this, android.R.layout.simple_spinner_dropdown_item, hospitalDoc));
                }
            }

            @Override
            public void onFailure(Call<List<HospitalDoctor>> call, Throwable t) {

                pg.cancel();

                t.printStackTrace();

                dbox.showMessageDialog(NewAppointment.this, getString(R.string.error_dialog), getString(R.string.hospital_error_detail),R.drawable.error_icon, getString(R.string.close));

            }
        });
    }

    //Save appointments..

    public void saveAppointmentData(AppointmentModel appointments){

        profileInterface = ApiUtils.getAPIService();
        final ProgressDialog pg = dbox.showProgressDialog(this, "Scheduling appointment...");
        pg.show();

        profileInterface.saveAppointment(appointments).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse myResponse = response.body();

                if(response.isSuccessful()){

                    serverResponse = myResponse.getMessage();
                    responseCode = myResponse.getStatus();
                }

                pg.cancel();
                //dbox.showMessageDialog(NewAppointment.this,
                // getString(R.string.info_dialog), serverResponse ,R.drawable.baseline_info_black_24, getString(R.string.close));

                Intent i = new Intent(NewAppointment.this, Appointments.class);
                startActivity(i);

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                pg.cancel();
                t.printStackTrace();
                dbox.showMessageDialog(NewAppointment.this, getString(R.string.error_dialog), getString(R.string.error_saving_appointment),R.drawable.error_icon, getString(R.string.close));


            }
        });


    }

}
