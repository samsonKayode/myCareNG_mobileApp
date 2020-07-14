package com.oxfordplus.hypen.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oxfordplus.hypen.models.HospitalAddress;
import com.oxfordplus.hypen.models.HospitalData;
import com.oxfordplus.hypen.models.HospitalSpeciality;
import com.oxfordplus.hypen.models.ItemDisplayModel;
import com.oxfordplus.hypen.mycareng.R;
import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CellFoldingAdapter extends ArrayAdapter<HospitalData> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    Context context;
    List<HospitalData> hospitalData;

    public CellFoldingAdapter(Context context, List<HospitalData> hospitalData){

        super(context, 0,hospitalData);
        this.context=context;
        this.hospitalData=hospitalData;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


        System.out.println("CellFoldingAdapter Page BABY!!!!!!!");

        HospitalData item = getItem(position);

        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;

        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell_hospital, parent, false);
            viewHolder.hospitalName = cell.findViewById(R.id.hospitalName);
            viewHolder.hospitalLocations = cell.findViewById(R.id.hospitalLocations);
            viewHolder.hospitalDesc = cell.findViewById(R.id.hospitalDesc);
            viewHolder.hospitalLogo = cell.findViewById(R.id.hospitalLogo);

            viewHolder.c_hospitalLocations = cell.findViewById(R.id.c_hospitalLocations);

            viewHolder.c_hospitalLogo = cell.findViewById(R.id.c_hospitalLogo);
            viewHolder.c_hospitalName = cell.findViewById(R.id.c_hospitalName);
            viewHolder.c_hospitalDesc = cell.findViewById(R.id.c_hospitalDesc);

            viewHolder.hospitalId = cell.findViewById(R.id.hospital_Id);
            viewHolder.hospital_name = cell.findViewById(R.id.hospital_name);
            viewHolder.hospitalSpeciality = cell.findViewById(R.id.hospitalSpeciality);

            cell.setTag(viewHolder);
        }
        else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        //Bind data here..

        System.out.println("PAGE: HOSPITAL NAME===>> "+item.getHospitalName());

        viewHolder.hospitalName.setText(item.getHospitalName());

        viewHolder.hospital_name.setText(item.getHospitalName());

        viewHolder.c_hospitalName.setText(item.getHospitalName());

        viewHolder.hospitalDesc.setText(item.getHospitalDesc());
        viewHolder.c_hospitalDesc.setText(item.getHospitalDesc());

        byte[] logo = item.getHospitalLogo();


        if(logo==null){
            viewHolder.hospitalLogo.setImageResource(R.drawable.no_img_available);
            viewHolder.c_hospitalLogo.setImageResource(R.drawable.no_img_available);
        }else{
            Bitmap bmp= BitmapFactory.decodeByteArray(logo,0,logo.length);
            viewHolder.hospitalLogo.setImageBitmap(bmp);

            viewHolder.c_hospitalLogo.setImageBitmap(bmp);
        }

        List<HospitalAddress> lAddress = item.getHospitalAddress();

        StringBuilder str = new StringBuilder();
        StringBuilder c_str = new StringBuilder();


        for(int i = 0; lAddress.size()>i; i++){

            HospitalAddress hospitalAddress = lAddress.get(i);

            str.append(hospitalAddress.getCity()+" . ");
            c_str.append(hospitalAddress.getAddress()+", "+hospitalAddress.getCity()+", "+hospitalAddress.getState()+"\n");
        }

        viewHolder.hospitalLocations.setText(str.substring(0));
        viewHolder.c_hospitalLocations.setText(c_str.substring(0));


        //Speciality..

        List<HospitalSpeciality> spec = item.getHospitalSpeciality();

        StringBuilder str1 = new StringBuilder();

        for(int i = 0; spec.size()>i; i++){

            HospitalSpeciality speciality = spec.get(i);

            str1.append(speciality.getSpeciality()+" | ");
        }

        viewHolder.hospitalSpeciality.setText(str1.substring(0));


        viewHolder.hospitalId.setText(String.valueOf(item.getId()));



        //viewHolder.hospitalSpeciality.setText(item.getAddress().toString());


        //end of data binding..

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    //View Holder class..

    private class ViewHolder {
        TextView hospitalName;
        TextView hospitalLocations;
        TextView c_hospitalLocations;
        TextView hospitalDesc;
        ImageView hospitalLogo;

        TextView hospitalId;
        TextView hospital_name;
        TextView hospitalSpeciality;
        TextView c_hospitalName;
        ImageView c_hospitalLogo;

        TextView c_hospitalDesc;
    }
}

