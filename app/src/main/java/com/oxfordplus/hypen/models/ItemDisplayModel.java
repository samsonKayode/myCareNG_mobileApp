package com.oxfordplus.hypen.models;

import android.media.Image;

import com.oxfordplus.hypen.interfaces.ProfileInterface;
import com.oxfordplus.hypen.utils.ApiUtils;
import com.oxfordplus.hypen.utils.ItemDisplay;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDisplayModel {

    private int id;
    private String hospitalName;
    private String hospitalDesc;
    private byte[] hospitalLogo;

    private List<String> address;
    private List<String> speciality;

    static ProfileInterface profileInterface;

    static int hospitalId;

    static String hospitalName1, hospitalDesc1;
    static byte[] hospitalLogo1;
    static List<HospitalAddress> address1;
    static List<HospitalSpeciality> speciality1;

    static List<HospitalData> myResponse;

    static List<String> myAddress = new ArrayList<>();
    static List<String> mySpecs = new ArrayList<>();

    public ItemDisplayModel(){

    }

    //Constructor with fields..


    public ItemDisplayModel(int id, String hospitalName, String hospitalDesc, byte[] hospitalLogo,
                            List<String> address, List<String> speciality) {
        this.id = id;
        this.hospitalName = hospitalName;
        this.hospitalDesc = hospitalDesc;
        this.hospitalLogo = hospitalLogo;
        this.address = address;
        this.speciality = speciality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalDesc() {
        return hospitalDesc;
    }

    public void setHospitalDesc(String hospitalDesc) {
        this.hospitalDesc = hospitalDesc;
    }

    public byte[] getHospitalLogo() {
        return hospitalLogo;
    }

    public void setHospitalLogo(byte[] hospitalLogo) {
        this.hospitalLogo = hospitalLogo;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<String> getSpeciality() {
        return speciality;
    }

    public void setSpeciality(List<String> speciality) {
        this.speciality = speciality;
    }



    //retrieve data method..


}
