package sng.com.testhvn.loader;

import android.content.Context;

import sng.com.testhvn.parser.JsonParser;
import sng.com.testhvn.service.apiRequestModel.ProductResult;
import sng.com.testhvn.service.impl.DefaultServiceFactory;

/**
 * Created by son.nguyen on 3/19/2016.
 */
public class ProductLoader extends BaseLoader<ProductResult> {

    public ProductLoader(Context context) {
        super(context);
    }

    @Override
    protected ProductResult doLoadInBackground() throws Exception {
        return JsonParser.parserProductResult(DefaultServiceFactory.getsInstance(getContext()).getProductService().getAllProduct(getContext()));
    }
}

