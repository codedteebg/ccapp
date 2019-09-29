package com.babbangona.barcodescannerproject.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


import com.babbangona.barcodescannerproject.model.syncHSFResponse;
import java.util.List;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/MyApi/public/api/v1/insert")
    Call<List<syncHSFResponse>> syncHSF(@Field("jsent") String jsent);
}
