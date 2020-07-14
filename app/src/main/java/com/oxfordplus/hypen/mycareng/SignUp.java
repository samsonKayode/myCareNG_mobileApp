package com.oxfordplus.hypen.mycareng;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.oxfordplus.hypen.alerts.DialogBoxes;
import com.oxfordplus.hypen.interfaces.ProfileInterface;
import com.oxfordplus.hypen.models.Profile;
import com.oxfordplus.hypen.models.ServerResponse;
import com.oxfordplus.hypen.sqlite.SqliteDatabase;
import com.oxfordplus.hypen.utils.ApiUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUp extends AppCompatActivity {

    private  String TAG ="SIGN UP";
    static int r_id, userId;
    static String r_firstName, r_lastname, r_email;

    EditText username, password;
    SqliteDatabase db = new SqliteDatabase(this);
    DialogBoxes dbox = new DialogBoxes();

    ProfileInterface profileInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        profileInterface = ApiUtils.getAPIService();

    }

    private boolean validateText(String text) {

        if (text != null && text.length() > 2) {

            return true;
        }

        return false;
    }

    public void login(View view) {

        String uname, upass;

        uname = username.getText().toString();
        upass = password.getText().toString();

        if(!isValidEmail(uname)){
            username.setError("Please enter valid email");
        }
        if(!validateText(upass)){
            password.setError("Please enter password");
        }

        if(isValidEmail(uname) && validateText(upass)){

            //Confirm Login using Rest API

        confirmLogin(uname, upass);

        }


            /*

            int result = verifyLogin(uname, upass);
            if(result==0){
                //invalid username and password..
                dbox.showMessageDialog(SignUp.this, getString(R.string.error_dialog), getString(R.string.invalid_details),R.drawable.error_icon, getString(R.string.close));
            }
            else{
                //show Dashboard..

                Intent intent = new Intent(SignUp.this, Dashboard.class);
                startActivity(intent);

            }

        }else{

            System.out.println("NON");

        }

        */


    }

    private void confirmLogin(String uname, String upass) {


        final ProgressDialog pg = dbox.showProgressDialog(this, "Logging you in...");

        pg.show();

        profileInterface.confirmLogin(uname, upass).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse myResponse = response.body();

                System.out.println("MY RESPONSE====>>> "+myResponse);

                if(response.isSuccessful()) {

                    r_id = myResponse.getStatus();

                    Log.e(TAG, "gone through server with no issues");
                    pg.dismiss();

                    if(r_id==0){
                        //No user profile found..

                        dbox.showMessageDialog(SignUp.this, getString(R.string.error_dialog),
                                getString(R.string.invalid_details), R.drawable.error_icon, "Close");

                    }else{

                        String[] mStr = myResponse.getMessage().split("#");
                        userId = myResponse.getStatus();
                        r_firstName = mStr[0];
                        r_email = mStr[1];

                        System.out.println("Firstname ===>"+r_firstName);
                        System.out.println("Email  ====>> "+r_email);


                        Intent intent = new Intent(SignUp.this, Dashboard.class);
                        startActivity(intent);
                    }

                }
            }



            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                pg.cancel();

                String pfs = t.getMessage();
                System.out.println("PFS===>>> "+pfs);

                dbox.showMessageDialog(SignUp.this, getString(R.string.error_dialog),
                        getString(R.string.server_error), R.drawable.error_icon, "Close");

            }
        });

    }

    public void register(View view) {

        Intent intent = new Intent(SignUp.this, Register.class);
                startActivity(intent);
    }


    private int verifyLogin(String uname, String upass) {

        Cursor cursor = db.verifyLogin(uname, upass);

        int result = cursor.getCount();

        return result;

    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
