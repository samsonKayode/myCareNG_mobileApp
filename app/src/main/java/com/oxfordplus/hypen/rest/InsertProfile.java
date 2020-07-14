package com.oxfordplus.hypen.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.oxfordplus.hypen.models.Profile;
import com.oxfordplus.hypen.mycareng.R;
import com.oxfordplus.hypen.mycareng.Register;

import java.util.List;

public class InsertProfile extends AsyncTask {

    private static final String TAG_STATUS = "status";
    private static String url_add_profile = "http://localhost:8080/mycareNG/rest/profiles/";
    private ProgressDialog pDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(null);
        pDialog.setMessage("Setting up profile..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        Profile theProfile = new Profile();

        return null;
    }
}
