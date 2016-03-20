package sng.com.testhvn.service.impl;

import android.content.Context;

import com.google.gson.JsonObject;

import sng.com.testhvn.service.ProductService;
import sng.com.testhvn.service.dataprovider.ApiService;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public class ProductServiceImp implements ProductService{
    @Override
    public JsonObject getAllProduct(Context context) {

        return ApiService.getInstance(context).getAllProduct(null);
    }
}
