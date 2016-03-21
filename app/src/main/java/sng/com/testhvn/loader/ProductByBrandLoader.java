package sng.com.testhvn.loader;

import android.content.Context;

import sng.com.testhvn.service.apiRequestModel.ProductResult;
import sng.com.testhvn.service.impl.DefaultServiceFactory;

/**
 * Created by son.nguyen on 3/21/2016.
 */
public class ProductByBrandLoader extends BaseLoader<ProductResult> {
    private String mBrandID;

    public ProductByBrandLoader(Context context, String brandID) {
        super(context);
        mBrandID = brandID;
    }

    @Override
    protected ProductResult doLoadInBackground() throws Exception {
        return DefaultServiceFactory.getsInstance(getContext()).getProductService().getProductByBrand(getContext(), mBrandID);
    }
}
