package sng.com.testhvn.service.impl;

import android.content.Context;

import sng.com.testhvn.service.BrandService;
import sng.com.testhvn.service.ProductService;
import sng.com.testhvn.service.ReviewService;
import sng.com.testhvn.service.ServiceFactory;
import sng.com.testhvn.service.UserService;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public class DefaultServiceFactory implements ServiceFactory {
    private ProductServiceImp mProductServiceImp;
    private CommentServiceImp mReviewServiceImp;
    private BrandServiceImp mBrandServiceImp;
    private UserServiceImp mUserServiceImp;
    private static DefaultServiceFactory sInstance;

    public DefaultServiceFactory(Context context) {
        mProductServiceImp = new ProductServiceImp();
        mReviewServiceImp = new CommentServiceImp();
        mBrandServiceImp = new BrandServiceImp();
        mUserServiceImp = new UserServiceImp();
    }

    public static DefaultServiceFactory getsInstance(Context context) {
        if (null == sInstance) {
            sInstance = new DefaultServiceFactory(context);
        }
        return sInstance;
    }

    @Override
    public ProductService getProductService() {
        return mProductServiceImp;
    }

    @Override
    public ReviewService getReviewService() {
        return mReviewServiceImp;
    }

    @Override
    public BrandService getBrandService() {
        return mBrandServiceImp;
    }

    @Override
    public UserService getUserService() {
        return mUserServiceImp;
    }

}
