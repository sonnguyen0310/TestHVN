package sng.com.testhvn.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.product.Product;
import sng.com.testhvn.service.apiRequestModel.ProductResult;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class JsonParser {
    public static ProductResult parserProductResult(JsonObject object) {
        ProductResult productResult = new ProductResult();
        JsonArray jsonArray = object.getAsJsonArray("results");
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject object1 = jsonArray.get(i).getAsJsonObject();
            if (null != object1.get("comment")) {
                Comment comment = gson.fromJson(object1, Comment.class);
                productResult.getComment().add(comment);
            } else {
                Product product = gson.fromJson(object1, Product.class);
                productResult.getResults().add(product);
            }
        }
        return productResult;
    }
}
