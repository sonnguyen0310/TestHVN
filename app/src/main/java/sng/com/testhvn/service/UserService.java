package sng.com.testhvn.service;

import android.content.Context;

import sng.com.testhvn.service.apiRequestModel.UserResult;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public interface UserService {
    UserResult getAllUser(Context context,String appId, String apiKey);
}
