package com.babbangona.barcodescannerproject.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


import com.babbangona.barcodescannerproject.model.syncHSFResponse;
import com.babbangona.barcodescannerproject.model.syncTransportResponse;

import java.util.List;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/ccapi/public/api/v1/insertHSF")
    Call<List<syncHSFResponse>> syncHSF(@Field("jsent") String jsent);

    @FormUrlEncoded
    @POST("/ccapi/public/api/v1/insertTransport")
    Call<List<syncHSFResponse>> syncTransport(@Field("transup") String transport);
}
