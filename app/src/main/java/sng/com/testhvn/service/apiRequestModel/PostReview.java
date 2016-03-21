package sng.com.testhvn.service.apiRequestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sng.com.testhvn.model.product.ProductID;
import sng.com.testhvn.model.user.UserID;

/**
 * Created by son.nguyen on 3/21/2016.
 */
public class PostReview {
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("productID")
    @Expose
    private ProductID productID;
    @SerializedName("userID")
    @Expose
    private UserID userID;

    /**
     * @return The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return The rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * @param rating The rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * @return The productID
     */
    public ProductID getProductID() {
        return productID;
    }

    /**
     * @param productID The productID
     */
    public void setProductID(ProductID productID) {
        this.productID = productID;
    }

    /**
     * @return The userID
     */
    public UserID getUserID() {
        return userID;
    }

    /**
     * @param userID The userID
     */
    public void setUserID(UserID userID) {
        this.userID = userID;
    }

}
