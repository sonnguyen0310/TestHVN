package sng.com.testhvn.service.dataprovider;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import sng.com.testhvn.service.apiRequestModel.BrandResult;
import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.service.apiRequestModel.PostReview;
import sng.com.testhvn.service.apiRequestModel.UserResult;

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
    Response getAllProduct();

    //==================================  Review     ==================================
    @GET("/1/classes/Review")
    @Headers({
            "X-Parse-Application-Id: MlR6vYpYvLRxfibxE5cg0e73jXojL6jWFqXU6F8L",
            "X-Parse-REST-API-Key: 7BTXVX1qUXKUCnsngL8LxhpEHKQ8KKd798kKpD9W"
    })
    CommentResult getAllComment();

    @POST("/1/classes/Review")
    @Headers({
            "X-Parse-Application-Id: MlR6vYpYvLRxfibxE5cg0e73jXojL6jWFqXU6F8L",
            "X-Parse-REST-API-Key: 7BTXVX1qUXKUCnsngL8LxhpEHKQ8KKd798kKpD9W"
    })
    Response submitReview( @Body PostReview postReview);

    //==================================  User     ==================================
    @GET("/1/classes/User")
    @Headers({
            "X-Parse-Application-Id: MlR6vYpYvLRxfibxE5cg0e73jXojL6jWFqXU6F8L",
            "X-Parse-REST-API-Key: 7BTXVX1qUXKUCnsngL8LxhpEHKQ8KKd798kKpD9W"
    })
    UserResult getAllUser();
}
