package com.oxfordplus.hypen.models;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HospitalData {

    private int id;
    private String hospitalName;
    private String hospitalDesc;
    private byte[] hospitalLogo;

    private List<HospitalAddress> hospitalAddress;
    private List<HospitalSpeciality> hospitalSpeciality;
    private List<HospitalDoctor> hospitalDoctor;

    public HospitalData(){


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

    public List<HospitalAddress> getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(List<HospitalAddress> hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public List<HospitalSpeciality> getHospitalSpeciality() {
        return hospitalSpeciality;
    }

    public void setHospitalSpeciality(List<HospitalSpeciality> hospitalSpeciality) {
        this.hospitalSpeciality = hospitalSpeciality;
    }

    public List<HospitalDoctor> getHospitalDoctor() {
        return hospitalDoctor;
    }

    public void setHospitalDoctor(List<HospitalDoctor> hospitalDoctor) {
        this.hospitalDoctor = hospitalDoctor;
    }

    @Override
    public String toString() {
        return "HospitalData{" +
                "id=" + id +
                ", hospitalName='" + hospitalName + '\'' +
                ", hospitalDesc='" + hospitalDesc + '\'' +
                ", hospitalLogo=" + Arrays.toString(hospitalLogo) +
                ", hospitalAddress=" + hospitalAddress +
                ", hospitalSpeciality=" + hospitalSpeciality +
                ", hospitalDoctor=" + hospitalDoctor +
                '}';
    }
}
