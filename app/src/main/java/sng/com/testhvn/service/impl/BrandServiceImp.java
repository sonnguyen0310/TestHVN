package sng.com.testhvn.service.impl;

import android.content.Context;

import sng.com.testhvn.service.BrandService;
import sng.com.testhvn.service.apiRequestModel.BrandResult;
import sng.com.testhvn.service.dataprovider.ApiService;

/**
 * Created by son.nguyen on 3/19/2016.
 */
public class BrandServiceImp implements BrandService {
    @Override
    public BrandResult getAllBrand(Context context,String appId, String apiKey) {
        return ApiService.getInstance(context).getAllBrand(appId,apiKey);
    }
}
