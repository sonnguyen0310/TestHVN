package sng.com.testhvn.service;

import android.content.Context;

import retrofit.client.Response;
import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.service.apiRequestModel.PostReview;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public interface ReviewService {
    CommentResult getAllComment(Context context,String appId, String apiKey);

    Response submitReview(Context context,String appId, String apiKey,PostReview postReview);
}
