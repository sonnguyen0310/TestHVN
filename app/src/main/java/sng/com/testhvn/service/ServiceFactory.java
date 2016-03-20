package sng.com.testhvn.service;

/**
 * Created by son.nguyen on 3/18/2016.
 */
public interface ServiceFactory {
    ProductService getProductService();
    ReviewService getReviewService();
    BrandService getBrandService();
}
