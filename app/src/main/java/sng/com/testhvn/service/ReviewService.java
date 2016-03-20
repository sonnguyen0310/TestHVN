package sng.com.testhvn.service;

import android.content.Context;

import sng.com.testhvn.service.apiRequestModel.CommentResult;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public interface ReviewService {
    CommentResult getAllComment(Context context);
}
