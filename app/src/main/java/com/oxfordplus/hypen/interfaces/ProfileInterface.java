package com.oxfordplus.hypen.interfaces;

import com.oxfordplus.hypen.models.AppointmentModel;
import com.oxfordplus.hypen.models.HospitalAddress;
import com.oxfordplus.hypen.models.HospitalData;
import com.oxfordplus.hypen.models.HospitalDoctor;
import com.oxfordplus.hypen.models.HospitalSpeciality;
import com.oxfordplus.hypen.models.Profile;
import com.oxfordplus.hypen.models.ServerResponse;
import com.oxfordplus.hypen.mycareng.Appointments;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProfileInterface {

    //Save Profile Call

    @POST("rest/profiles")
    Call<ServerResponse> saveProfile(@Body Profile profile);

    //Confirm Registration call
    @GET("rest/profiles/{emailId}/{passwordId}")
    Call<ServerResponse> confirmLogin(@Path("emailId") String emailId, @Path("passwordId") String passwordId);

    @GET("hospitals/lists")
    Call<List<HospitalData>> getHospitalList();

    //Get all appointments..

    @GET("appointments/lists/{profile_id}")
    Call<List<AppointmentModel>> getAppointmentData(@Path("profile_id") int profile_id);

    //Get single hospital data..

    @GET("hospitals/lists/{hospital_id}")
    Call<List<HospitalData>> getHospitalDetail(@Path("hospital_id") int hospital_id);

    @GET("hospitals/lists/address/{hospital_id}")
    Call<List<HospitalAddress>> getHospitalAddress(@Path("hospital_id") int hospital_id);

    @GET("hospitals/lists/specs/{hospital_id}")
    Call<List<HospitalSpeciality>> getHospitalSpecialities(@Path("hospital_id") int hospital_id);


    @GET("hospitals/lists/docs/{hospital_id}/{speciality}")
    Call<List<HospitalDoctor>> getDoctors(@Path("hospital_id") int hospital_id, @Path("speciality") String speciality);

    //Save appointment..

    @POST("appointments/lists")
    Call<ServerResponse> saveAppointment(@Body AppointmentModel theAppointments);
}
