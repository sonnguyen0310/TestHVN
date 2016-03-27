package sng.com.testhvn.service;

import android.content.Context;

import com.google.gson.JsonObject;

import sng.com.testhvn.service.apiRequestModel.ProductResult;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public interface ProductService {
   JsonObject getAllProduct(Context context,String appId, String apiKey);
   ProductResult getProductByBrand(Context context,String appId, String apiKey,String brandID);
}
