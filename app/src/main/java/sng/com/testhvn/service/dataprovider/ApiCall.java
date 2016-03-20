package sng.com.testhvn.service.dataprovider;

import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import sng.com.testhvn.service.apiRequestModel.BrandResult;
/**
 * Created by son.nguyen on 3/19/2016.
 */
public interface ApiCall {
// ==================================      Brand  ==================================
    @GET("/1/classes/Brand")
    @Headers({
            "X-Parse-Application-Id: MlR6vYpYvLRxfibxE5cg0e73jXojL6jWFqXU6F8L",
            "X-Parse-REST-API-Key: 7BTXVX1qUXKUCnsngL8LxhpEHKQ8KKd798kKpD9W"
    })
    BrandResult getAllBrand();
//==================================  Product     ==================================
    @GET("/1/classes/Product")
    @Headers({
            "X-Parse-Application-Id: MlR6vYpYvLRxfibxE5cg0e73jXojL6jWFqXU6F8L",
            "X-Parse-REST-API-Key: 7BTXVX1qUXKUCnsngL8LxhpEHKQ8KKd798kKpD9W"
    })
    JsonObject getAllProduct(Callback<Response> cb);
}
