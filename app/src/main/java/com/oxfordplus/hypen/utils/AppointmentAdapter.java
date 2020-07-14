package com.oxfordplus.hypen.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oxfordplus.hypen.models.AppointmentModel;
import com.oxfordplus.hypen.mycareng.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentAdapter extends ArrayAdapter<AppointmentModel> {

    Context context;

    List<AppointmentModel> appointmentModel;

    public AppointmentAdapter(Context context, List<AppointmentModel> appointmentModel){
        super(context, 0, appointmentModel);

        this.context = context;
        this.appointmentModel = appointmentModel;
    }


    public class ViewHolder{

        TextView month;
        TextView day;
        TextView year;
        TextView time;
        TextView hospitalName;
        TextView hospitalAddress;
        TextView code;
        TextView status;
        TextView department;
        TextView hospitalDoctor;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        AppointmentModel item = getItem(position);

        ViewHolder viewHolder=null;

        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.appointment_cell, null);

            viewHolder = new ViewHolder();

            viewHolder.month = convertView.findViewById(R.id.month);
            viewHolder.day = convertView.findViewById(R.id.day);
            viewHolder.year = convertView.findViewById(R.id.year);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.hospitalName = convertView.findViewById(R.id.hospitalName);
            viewHolder.hospitalAddress = convertView.findViewById(R.id.hospitalAddress);
            viewHolder.code = convertView.findViewById(R.id.code);
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.hospitalDoctor = convertView.findViewById(R.id.hospitalDoctor);
            viewHolder.department = convertView.findViewById(R.id.hospitalDepartment);

            convertView.setTag(viewHolder);

        }else{

            viewHolder = (ViewHolder) convertView.getTag();
        }




        String[] rdate = item.getDate().split("-");


        viewHolder.month.setText(rdate[1]);
        viewHolder.day.setText(rdate[0]);
        viewHolder.year.setText(rdate[2]);

        viewHolder.time.setText(item.getTime());
        viewHolder.hospitalName.setText(item.getHospitalName());
        viewHolder.hospitalAddress.setText(item.getHospitalAddress());
        viewHolder.code.setText(item.getCode());
        viewHolder.status.setText(item.getStatus());
        viewHolder.hospitalDoctor.setText(item.getDoctorName());
        viewHolder.department.setText(item.getDepartment());


        return convertView;
    }
}
