package sng.com.testhvn.loader;

import android.content.Context;

import sng.com.testhvn.service.apiRequestModel.UserResult;
import sng.com.testhvn.service.dataprovider.ApiService;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class UserLoader extends BaseLoader <UserResult>{
    public UserLoader(Context context) {
        super(context);
    }

    @Override
    protected UserResult doLoadInBackground() throws Exception {
        return ApiService.getInstance(getContext()).getAllUser();
    }
}
