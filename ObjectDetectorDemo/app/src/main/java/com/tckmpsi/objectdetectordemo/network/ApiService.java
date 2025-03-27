package com.tckmpsi.objectdetectordemo.network;

import com.tckmpsi.objectdetectordemo.models.Disease;
import com.tckmpsi.objectdetectordemo.models.ImageData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("predict")  // Replace "your-endpoint" with your actual server endpoint
    Call<Disease> sendImageData(@Body ImageData imageData);
    Call<Disease> getDisease();
}
