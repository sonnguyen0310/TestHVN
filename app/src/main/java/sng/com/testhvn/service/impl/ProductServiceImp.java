package sng.com.testhvn.service.impl;

import android.content.Context;

import com.google.gson.JsonObject;

import sng.com.testhvn.service.ProductService;
import sng.com.testhvn.service.dataprovider.ApiService;
import sng.com.testhvn.util.Utils;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public class ProductServiceImp implements ProductService {
    @Override
    public JsonObject getAllProduct(Context context) {
        return Utils.toJson(ApiService.getInstance(context).getAllProduct());
    }


}
