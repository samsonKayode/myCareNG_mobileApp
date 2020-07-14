package com.oxfordplus.hypen.mycareng;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.oxfordplus.hypen.alerts.DialogBoxes;
import com.oxfordplus.hypen.interfaces.ProfileInterface;
import com.oxfordplus.hypen.models.Profile;
import com.oxfordplus.hypen.models.ServerResponse;
import com.oxfordplus.hypen.sqlite.SqliteDatabase;
import com.oxfordplus.hypen.utils.ApiUtils;
import com.oxfordplus.hypen.utils.HintAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    SqliteDatabase db = new SqliteDatabase(this);

    private static final String TAG ="REGISTER" ;
    EditText email, e_firstname, e_lastname, e_phone, e_city, e_password, e_passkey;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar = Calendar.getInstance();

    private static final String TAG_STATUS = "status";
    private ProgressDialog pDialog;

    static EditText dob;
    Button button;

    String firstname, lastname, city, estate, phone, password, passkey, edob, esex, emailN;

    String[] sexList = {"Select Sex", "Male", "Female",""};

    String[] stateList = {"Select State of Residence",
            "Abia",
            "Adamawa",
            "Anambra",
            "Akwa Ibom",
            "Bauchi",
            "Bayelsa",
            "Benue",
            "Borno",
            "Cross River",
            "Delta",
            "Ebonyi",
            "Enugu",
            "Edo",
            "Ekiti",
            "FCT - Abuja",
            "Gombe",
            "Imo",
            "Jigawa",
            "Kaduna",
            "Kano",
            "Katsina",
            "Kebbi",
            "Kogi",
            "Kwara",
            "Lagos",
            "Nasarawa",
            "Niger",
            "Ogun",
            "Ondo",
            "Osun",
            "Oyo",
            "Plateau",
            "Rivers",
            "Sokoto",
            "Taraba",
            "Yobe",
            "Zamfara",""
    };

    ArrayAdapter<String> sexAdapter, stateAdapter;

    Spinner sex, state;
    TextView errorText;

    ProfileInterface profileInterface;
    DialogBoxes dbox = new DialogBoxes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dob = findViewById(R.id.dob);
        sex = findViewById(R.id.new_sex);

        state = findViewById(R.id.state);

        email = findViewById(R.id.email);

        e_firstname = findViewById(R.id.firstName);
        e_lastname = findViewById(R.id.lastName);

        e_city = findViewById(R.id.city);
        e_phone = findViewById(R.id.phone);

        e_password = findViewById(R.id.password);

        button = findViewById(R.id.button);

        profileInterface = ApiUtils.getAPIService();

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

        sexAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, sexList);
        //sex.setAdapter(sexAdapter);

        HintAdapter hintAdapter=new HintAdapter(this,android.R.layout.simple_list_item_1,sexList);
        sex.setAdapter(hintAdapter);
        sex.setSelection(0);


        stateAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, stateList);
        //state.setAdapter(stateAdapter);

        HintAdapter hintAdapter2=new HintAdapter(this,android.R.layout.simple_list_item_1,stateList);
        state.setAdapter(hintAdapter2);
        state.setSelection(0);
    }


    public void register(View v) {

        //Validate firstname..
         emailN = email.getText().toString();
         firstname = e_firstname.getText().toString();
         lastname = e_lastname.getText().toString();
         city = e_city.getText().toString();
         phone = e_phone.getText().toString();

         password = e_password.getText().toString();

         edob = dob.getText().toString();
         estate = state.getSelectedItem().toString();
         esex = sex.getSelectedItem().toString();

        if (!isValidEmail(emailN)) {
            email.setError("Invalid Email");
        }

        if (!validateTexts(edob)) {
            dob.setError("Select date of birth");
        }

        if (!validateTexts(password)) {
            e_password.setError("Invalid Password");
        }

        if (!validateTexts(firstname)) {
            e_firstname.setError("Invalid First Name");
        }

        if (!validateTexts(lastname)) {
            e_lastname.setError("Invalid Last Name");
        }

        if (!validateTexts(city)) {
            e_city.setError("Invalid City");
        }

        if (!validateTexts(phone)) {
            e_phone.setError("Invalid Phone Number");
        }

        if (!validateCombo(sex, "Invalid sex")) {
        }

        if (!validateCombo(state, "Invalid state of residence")) {
            //errorText.setError("Please Select Sex");
        }

    if(isValidEmail(emailN) && validateTexts(firstname) && validateTexts(lastname)&& validateTexts(city)
            && validateTexts(phone) && validateTexts(esex) && validateTexts(edob)&& validateTexts(estate)&&
            validateTexts(password)){


        Profile theProfile = new Profile();

        theProfile.setFirstName(firstname);
        theProfile.setLastName(lastname);
        theProfile.setCity(city);
        theProfile.setState(estate);
        theProfile.setDob(edob);
        theProfile.setPasskey("NA");
        theProfile.setPassword(password);
        theProfile.setSex(esex);
        theProfile.setEmail(emailN);
        theProfile.setPhone(phone);

        sendData(theProfile);

        button.setEnabled(false);



            //sendData(firstname, lastname, edob, esex, city, estate, emailN, phone, password, passkey);
    }else{
        //dbox.showMessageDialog(Register.this, getString(R.string.error_dialog), getString(R.string.fill_in_data),R.drawable.error_icon,getString(R.string.close));
    }




    }


    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Validate other text fields..
    private boolean validateTexts(String text) {

        if (text != null && text.length() > 2) {

            return true;
        }

        return false;
    }

    //Validate combobox..

    private boolean validateCombo(Spinner spinner, String error) {

        View selectedView = spinner.getSelectedView();

        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            if (spinner.getSelectedItemPosition() <= 0) {
                selectedTextView.setError(error);
                return false;
            }
        }
        return true;
    }

    public void loadDate(View v) {

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    //Update date label..

    public void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    /*

    public void sendData(String firstName, String lastName,
                          String dob,  String sex,
                          String city,  String state,
                          String email, String phone,  String password,
                          String passkey){

        profileInterface.saveProfile(firstName, lastName, dob, sex, city,
                state, email, phone, password, passkey)


     */

    public void sendData(final Profile theProfile){

        final ProgressDialog pg = dbox.showProgressDialog(this, "Registering User");

        pg.show();

        profileInterface.saveProfile(theProfile).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse myResponse = response.body();

                int responseCode = myResponse.getStatus();
                String message = myResponse.getMessage();

                if(response.isSuccessful()) {
                    pg.cancel();

                    if(responseCode==0){
                        //data saved.

                        String firstname = theProfile.getFirstName();
                        String lastname = theProfile.getLastName();
                        String city = theProfile.getCity();
                        String estate = theProfile.getState();
                        String edob = theProfile.getDob();
                        String passkey = "N-SET";
                        String password = theProfile.getPassword();
                        String esex = theProfile.getSex();
                        String emailN = theProfile.getEmail();
                        String phone = theProfile.getPhone();

                    String savetoPhone =db.insertProfile(firstname, lastname, edob, esex,
                            phone, city, estate, emailN, password, passkey );

                    if(savetoPhone.equals("OK")){
                        Log.e(TAG, "Data saved to sqlite database");
                    }



                    dbox.showMessageDialog(Register.this, getString(R.string.info_dialog), message,R.drawable.baseline_info_black_24, getString(R.string.close));
                    Log.i(TAG, "post submitted to API." + response.body().toString());

                    //send screen to dashboard!

                        Intent intent = new Intent(Register.this, Dashboard.class);
                        startActivity(intent);
                    }

                    else{
                        //data not saved..
                        dbox.showMessageDialog(Register.this, getString(R.string.error_dialog), message ,R.drawable.error_icon, getString(R.string.close));
                        button.setEnabled(true);
                    }

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                //ServerResponse serverResponse = new ServerResponse();

                //Log.e(TAG, serverResponse.getMessage());

                pg.cancel();

                dbox.showMessageDialog(Register.this, getString(R.string.error_dialog), getString(R.string.profile_error),R.drawable.error_icon, getString(R.string.close));
                Log.e(TAG, "Unable to submit post to API.");

                button.setEnabled(true);
            }
        });
    }

}
