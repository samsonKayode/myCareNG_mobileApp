package com.oxfordplus.hypen.mycareng;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.oxfordplus.hypen.alerts.DialogBoxes;
import com.oxfordplus.hypen.sqlite.SqliteDatabase;

public class UnlockScreen extends AppCompatActivity {

    EditText code;
    SqliteDatabase db = new SqliteDatabase(this);
    DialogBoxes dbox = new DialogBoxes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_screen);

        code = findViewById(R.id.verification_code);
    }

    public void validateLogin(View v){

        String nCode = code.getText().toString();

        Cursor cr = db.validateCode(nCode);

        int result = cr.getCount();

        if(result>0){
            //go to dashboard..
        }else{

            //display error message..
            Toast.makeText(this, getString(R.string.invalid_code),Toast.LENGTH_LONG);
            dbox.showMessageDialog(this, getString(R.string.error_dialog), getString(R.string.invalid_code),R.drawable.error_icon, getString(R.string.close));
        }


    }

}
