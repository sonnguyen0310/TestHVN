package sng.com.testhvn.service.dataprovider;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import sng.com.testhvn.service.apiRequestModel.BrandResult;
import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.service.apiRequestModel.PostReview;
import sng.com.testhvn.service.apiRequestModel.ProductResult;
import sng.com.testhvn.service.apiRequestModel.UserResult;

/**
 * Created by son.nguyen on 3/19/2016.
 */
public interface ApiCall {
    // ==================================      Brand  ==================================
    @GET("/1/classes/Brand")
//    @Headers({
//            "X-Parse-Application-Id: MlR6vYpYvLRxfibxE5cg0e73jXojL6jWFqXU6F8L",
//            "X-Parse-REST-API-Key: 7BTXVX1qUXKUCnsngL8LxhpEHKQ8KKd798kKpD9W"
//    })
    BrandResult getAllBrand(@Header("X-Parse-Application-Id") String applicationID,
                            @Header("X-Parse-REST-API-Key") String apiKey);

    //==================================  Product     ==================================
    @GET("/1/classes/Product")
    Response getAllProduct(@Header("X-Parse-Application-Id") String applicationID,
                           @Header("X-Parse-REST-API-Key") String apiKey);

//    @GET("/1/classes/Product{services_query}")
//    @Headers({
//            "X-Parse-Application-Id: MlR6vYpYvLRxfibxE5cg0e73jXojL6jWFqXU6F8L",
//            "X-Parse-REST-API-Key: 7BTXVX1qUXKUCnsngL8LxhpEHKQ8KKd798kKpD9W"
//    })
//    ProductResult getProductByBrand(@Path(value ="services_query",encode = true) String services_query);

    @GET("/1/classes/Product")
    ProductResult getProductByBrand(@Header("X-Parse-Application-Id") String applicationID,
                                    @Header("X-Parse-REST-API-Key") String apiKey,
                                    @Query("where") String where,
                                    @Query("order") String order,
                                    @Query("limit") String limit);

    //==================================  Review     ==================================
    @GET("/1/classes/Review")
    CommentResult getAllComment(@Header("X-Parse-Application-Id") String applicationID,
                                @Header("X-Parse-REST-API-Key") String apiKey);

    @POST("/1/classes/Review")
    Response submitReview(@Header("X-Parse-Application-Id") String applicationID,
                          @Header("X-Parse-REST-API-Key") String apiKey,
                          @Body PostReview postReview);

    //==================================  User     ==================================
    @GET("/1/classes/User")
    UserResult getAllUser(@Header("X-Parse-Application-Id") String applicationID,
                          @Header("X-Parse-REST-API-Key") String apiKey);
}
