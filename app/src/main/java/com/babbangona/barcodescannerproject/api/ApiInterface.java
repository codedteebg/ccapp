package com.babbangona.barcodescannerproject.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


import com.babbangona.barcodescannerproject.model.driversResponse;
import com.babbangona.barcodescannerproject.model.msaResponseT;
import com.babbangona.barcodescannerproject.model.syncHSFResponse;
import com.babbangona.barcodescannerproject.model.syncTransportResponse;

import java.util.List;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/ccapp/public/api/v1/insertHSF")
    Call<List<syncHSFResponse>> syncHSF(@Field("jsent") String jsent);

  /*  @FormUrlEncoded
    @POST("/ccapp/public/api/v1/insertTransport")
    Call<List<syncHSFResponse>> syncTransport(@Field("transup") String transport);

    @GET("/ccapp/public/api/v1/getMSA/{dateP}")
    Call<List<msaResponseT>> getMSAs(@Path("dateP") String dateP);*/

    @GET("/ccapp/public/api/v1/getDriver/{dateT}")
    Call<List<driversResponse>> getDrivers(@Path("dateT") String dateT);
}
