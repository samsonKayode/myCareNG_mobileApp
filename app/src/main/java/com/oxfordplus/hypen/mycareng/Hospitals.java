package com.oxfordplus.hypen.mycareng;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.oxfordplus.hypen.alerts.DialogBoxes;
import com.oxfordplus.hypen.interfaces.ProfileInterface;
import com.oxfordplus.hypen.models.HospitalAddress;
import com.oxfordplus.hypen.models.HospitalData;
import com.oxfordplus.hypen.models.HospitalSpeciality;
import com.oxfordplus.hypen.models.ItemDisplayModel;
import com.oxfordplus.hypen.utils.ApiUtils;
import com.oxfordplus.hypen.utils.CellFoldingAdapter;
import com.oxfordplus.hypen.utils.FoldingCellListAdapter;
import com.oxfordplus.hypen.utils.Item;
import com.oxfordplus.hypen.utils.ItemDisplay;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Hospitals extends AppCompatActivity {

    //CellFoldingAdapter adapter;

    static ProfileInterface profileInterface;

    List<HospitalData> theList;

    static int hospitalId;

    static String hospitalName, hospitalDesc;
    static byte[] hospitalLogo;
    static List<HospitalAddress> address;
    static List<HospitalSpeciality> speciality;

    public static List<HospitalData> myResponse;

    static String STS="NULL";

    DialogBoxes dbox = new DialogBoxes();

    public static ListView theListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.round_local_hospital_white_24);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadItems();

        //theListView.setEmptyView(R.id.);

        if(STS.equals("NULL")){
            System.out.println("REST API==>>NULL");
        }

        if(STS.equals("OK")){

            System.out.println("REST API==>>OK");
        }

        if(STS.equals("ERROR")){

            System.out.println("REST API==>>ERROR");
        }

        theListView = findViewById(R.id.mainListView);

        // prepare elements to display
        theList = myResponse;

        final CellFoldingAdapter adapter = new CellFoldingAdapter(this, theList);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

    }

    //load hospitals..

    public void loadItems(){

        final ProgressDialog pg = dbox.showProgressDialog(this, "Loading partner hospitals...");

        pg.show();

        profileInterface = ApiUtils.getAPIService();

        profileInterface.getHospitalList().enqueue(new Callback<List<HospitalData>>() {
            @Override
            public void onResponse(Call<List<HospitalData>> call, Response<List<HospitalData>> response) {

                myResponse = (List<HospitalData>) response.body();
                System.out.println("RESPONSE====>>"+response);
                System.out.println("MY RESPONSE===>> "+myResponse);

                if(response.isSuccessful()){

                    STS="OK";

                    pg.cancel();

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
                            //mySpecs.add(fullSpec);
                        }


                        System.out.println("");
                        //loop address..

                        for(int addy =0; address.size()>addy; addy++){
                            HospitalAddress hospitalAddress = address.get(addy);

                            System.out.println(hospitalAddress.getAddress()+",  "+
                                    hospitalAddress.getCity()+", "+hospitalAddress.getState());

                            String fullAddress = hospitalAddress.getAddress()+",  "+
                                    hospitalAddress.getCity()+", "+hospitalAddress.getState();

                            //myAddress.add(fullAddress);
                        }

                        theListView.setAdapter(new CellFoldingAdapter(getApplicationContext(), myResponse));

                    }

                }

            }

            @Override
            public void onFailure(Call<List<HospitalData>> call, Throwable t) {

                pg.cancel();
                System.out.println("Error loading hospital data" +t);
                t.getStackTrace();
                dbox.showMessageDialog(Hospitals.this, getString(R.string.error_dialog), getString(R.string.hospital_error),R.drawable.error_icon, getString(R.string.close));
                //dbox.showMessageDialog(this.getContext(), "Error", "Error loading hospital data", R.drawable.error_icon, "close");

                STS="ERROR";

            }
        });
    }


}
