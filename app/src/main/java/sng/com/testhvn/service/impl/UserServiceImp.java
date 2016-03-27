package sng.com.testhvn.service.impl;

import android.content.Context;

import sng.com.testhvn.service.UserService;
import sng.com.testhvn.service.apiRequestModel.UserResult;
import sng.com.testhvn.service.dataprovider.ApiService;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class UserServiceImp implements UserService {
    @Override
    public UserResult getAllUser(Context context,String appId, String apiKey) {
        return ApiService.getInstance(context).getAllUser(appId, apiKey);
    }
}
