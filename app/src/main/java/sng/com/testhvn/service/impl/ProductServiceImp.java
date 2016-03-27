package sng.com.testhvn.service.impl;

import android.content.Context;

import com.google.gson.JsonObject;

import sng.com.testhvn.R;
import sng.com.testhvn.service.ProductService;
import sng.com.testhvn.service.apiRequestModel.ProductResult;
import sng.com.testhvn.service.dataprovider.ApiService;
import sng.com.testhvn.util.Utils;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public class ProductServiceImp implements ProductService {
    @Override
    public JsonObject getAllProduct(Context context, String appId, String apiKey) {
        return Utils.toJson(ApiService.getInstance(context).getAllProduct(appId, apiKey));
    }

    @Override
    public ProductResult getProductByBrand(Context context, String appID, String apiKey, String brandId) {
        String query = context.getString(R.string.global_get_product_by_brand, brandId);
        return ApiService.getInstance(context).getProductByBrand(appID, brandId, query, null, null);
    }
}
