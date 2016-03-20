package sng.com.testhvn.service.impl;

import android.content.Context;

import sng.com.testhvn.service.ReviewService;
import sng.com.testhvn.service.apiRequestModel.CommentResult;
import sng.com.testhvn.service.dataprovider.ApiService;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public class CommentServiceImp implements ReviewService {
    @Override
    public CommentResult getAllComment(Context context) {
        return ApiService.getInstance(context).getAllComment();
    }
}
