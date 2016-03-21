package sng.com.testhvn.service.impl;

import android.content.Context;

import retrofit.client.Response;
import sng.com.testhvn.service.ReviewService;
import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.service.apiRequestModel.PostReview;
import sng.com.testhvn.service.dataprovider.ApiService;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public class CommentServiceImp implements ReviewService {
    @Override
    public CommentResult getAllComment(Context context) {
        return ApiService.getInstance(context).getAllComment();
    }

    @Override
    public Response submitReview(Context context, PostReview postReview) {
        return ApiService.getInstance(context).submitReview(postReview);
    }
}
