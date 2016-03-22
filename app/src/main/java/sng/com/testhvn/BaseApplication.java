package sng.com.testhvn;

import sng.com.testhvn.service.impl.DefaultServiceFactory;

/**
 * Created by son.nguyen on 3/17/2016.
 */
public class BaseApplication extends android.app.Application {
    private static BaseApplication sInstance;
    private DefaultServiceFactory mServiceFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static BaseApplication getsInstance() {
        return sInstance;
    }


}
