package sng.com.testhvn.loader;

import android.content.Context;

import sng.com.testhvn.R;
import sng.com.testhvn.service.apiRequestModel.UserResult;
import sng.com.testhvn.service.impl.DefaultServiceFactory;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class UserLoader extends BaseLoader<UserResult> {
    public UserLoader(Context context) {
        super(context);
    }

    @Override
    protected UserResult doLoadInBackground() throws Exception {
        return DefaultServiceFactory.getsInstance(getContext()).getUserService().getAllUser(getContext(), getContext().getString(R.string.app_id), getContext().getString(R.string.api_key));
    }
}
