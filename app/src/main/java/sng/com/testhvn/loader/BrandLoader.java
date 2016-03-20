package sng.com.testhvn.loader;

import android.content.Context;

import sng.com.testhvn.service.apiRequestModel.BrandResult;
import sng.com.testhvn.service.impl.DefaultServiceFactory;

/**
 * Created by son.nguyen on 3/19/2016.
 */
public class BrandLoader extends BaseLoader<BrandResult>{
    private Context mContext;
    public BrandLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected BrandResult doLoadInBackground() throws Exception {
        return DefaultServiceFactory.getsInstance(getContext()).getBrandService().getAllBrand(getContext());
    }
}