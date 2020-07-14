package com.oxfordplus.hypen.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.oxfordplus.hypen.alerts.DialogBoxes;
import com.oxfordplus.hypen.interfaces.ProfileInterface;
import com.oxfordplus.hypen.models.HospitalAddress;
import com.oxfordplus.hypen.models.HospitalData;
import com.oxfordplus.hypen.models.HospitalSpeciality;
import com.oxfordplus.hypen.models.ItemDisplayModel;
import com.oxfordplus.hypen.mycareng.Hospitals;
import com.oxfordplus.hypen.mycareng.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDisplay {

    DialogBoxes dbox = new DialogBoxes();

    static ProfileInterface profileInterface;

    static int hospitalId;

    static String hospitalName, hospitalDesc;
    static byte[] hospitalLogo;
    static List<HospitalAddress> address;
    static List<HospitalSpeciality> speciality;

    public static List<HospitalData> myResponse;

    static List<String> myAddress = new ArrayList<>();
    static List<String> mySpecs = new ArrayList<>();

    static ArrayList<ItemDisplayModel> theList;

    public static String STS="NULL";

    public ItemDisplay(){

    }

    public static void loadItems(){

        profileInterface = ApiUtils.getAPIService();

        profileInterface.getHospitalList().enqueue(new Callback<List<HospitalData>>() {
            @Override
            public void onResponse(Call<List<HospitalData>> call, Response<List<HospitalData>> response) {

                myResponse = (List<HospitalData>) response.body();
                System.out.println("RESPONSE====>>"+response);
                System.out.println("MY RESPONSE===>> "+myResponse);

                if(response.isSuccessful()){

                    STS="OK";

                    for(int i=0; myResponse.size()>i; i++) {

                            HospitalData myData = myResponse.get(i);
                            hospitalId = myData.getId();
                            System.out.println("Hospital ID===>" + hospitalId);
                            hospitalName = myData.getHospitalName();
                            hospitalDesc = myData.getHospitalDesc();
                            hospitalLogo = myData.getHospitalLogo();

                            address = myData.getHospitalAddress();
                            speciality = myData.getHospitalSpeciality();

                            System.out.println("Hospital Name====>" + hospitalName);

                            for(int spec =0; speciality.size()>spec; spec++){
                                HospitalSpeciality hospitalSpeciality = speciality.get(spec);
                                //System.out.println("SPEIALITY ID "+hospitalSpeciality.getId());
                                System.out.print(hospitalSpeciality.getSpeciality()+" | ");

                                String fullSpec = hospitalSpeciality.getSpeciality()+" | ";
                                mySpecs.add(fullSpec);
                            }


                            System.out.println("");
                            //loop address..

                        for(int addy =0; address.size()>addy; addy++){
                            HospitalAddress hospitalAddress = address.get(addy);

                            System.out.println(hospitalAddress.getAddress()+",  "+
                                    hospitalAddress.getCity()+", "+hospitalAddress.getState());

                            String fullAddress = hospitalAddress.getAddress()+",  "+
                                    hospitalAddress.getCity()+", "+hospitalAddress.getState();

                            myAddress.add(fullAddress);
                        }

                    }

                }

                System.out.println("THE SIZE OF theLis====>>>"+ theList.size());
            }

            @Override
            public void onFailure(Call<List<HospitalData>> call, Throwable t) {

                System.out.println("Error loading hospital data");
                STS="ERROR";

            }
        });
    }

}
