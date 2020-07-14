package com.oxfordplus.hypen.alerts;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;

import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.oxfordplus.hypen.mycareng.R;
import com.oxfordplus.hypen.utils.FontConstsant;

public class DialogBoxes {


    /*

    public void showMessageDialog(Context context, String title, String content, int icon)
    {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(content);
        alert.setIcon(icon);
        AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    */


    public void showMessageDialog(Context context, String title, String content, int icon, String ooc)
    {

        iOSDialogBuilder alert = new iOSDialogBuilder(context);

        alert.setTitle(title);
        alert.setSubtitle(content);
        alert.setBoldPositiveLabel(true);

        alert.setCancelable(true);

        //alert.setFont(FontConstsant.getfontBold(context));

        alert.setPositiveListener(ooc,new iOSDialogClickListener() {
        @Override
        public void onClick(iOSDialog dialog) {
            dialog.dismiss();

        }
    });

        alert.build().show();

        //AlertDialog alertDialog = alert.create();
        //alertDialog.setCanceledOnTouchOutside(true);
        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //alertDialog.show();

    }

    public ProgressDialog showProgressDialog(Context context, String title){

        ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage(title);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        return pDialog;
    }

}
