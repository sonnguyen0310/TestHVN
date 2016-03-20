package sng.com.testhvn.service.impl;

import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.client.Response;
import sng.com.testhvn.service.ProductService;
import sng.com.testhvn.service.dataprovider.ApiService;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public class ProductServiceImp implements ProductService {
    @Override
    public JsonObject getAllProduct(Context context) {
        return toJson(ApiService.getInstance(context).getAllProduct());
    }

    private JsonObject toJson(Response result) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return (new JsonParser()).parse(sb.toString()).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
