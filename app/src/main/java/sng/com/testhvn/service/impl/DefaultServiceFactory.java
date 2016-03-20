package sng.com.testhvn.service.impl;

import android.content.Context;

import retrofit.RestAdapter;
import sng.com.testhvn.service.BrandService;
import sng.com.testhvn.service.ProductService;
import sng.com.testhvn.service.ReviewService;
import sng.com.testhvn.service.ServiceFactory;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public class DefaultServiceFactory implements ServiceFactory {
    private ProductServiceImp mProductService;
    private ReviewServiceImp mReviewService;
    private BrandServiceImp mBrandServiceImp;
    private RestAdapter mRetrofit;
    private static DefaultServiceFactory sInstance;
    public DefaultServiceFactory(Context context) {
        mProductService = new ProductServiceImp();
        mReviewService = new ReviewServiceImp();
        mBrandServiceImp = new BrandServiceImp();
    }
    public static DefaultServiceFactory getsInstance(Context context){
        if (null == sInstance){
            sInstance = new DefaultServiceFactory(context);
        }
        return sInstance;
    }
    @Override
    public ProductService getProductService() {
        return mProductService;
    }

    @Override
    public ReviewService getReviewService() {
        return mReviewService;
    }

    @Override
    public BrandService getBrandService() {
        return mBrandServiceImp;
    }
}
