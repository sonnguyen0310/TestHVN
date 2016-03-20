package sng.com.testhvn.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sng.com.testhvn.model.product.ProductID;
import sng.com.testhvn.model.user.UserID;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class Comment implements Parcelable {
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("productID")
    @Expose
    private ProductID productID;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("userID")
    @Expose
    private UserID userID;

    protected Comment(Parcel in) {
        comment = in.readString();
        createdAt = in.readString();
        objectId = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

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
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param objectId The objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comment);
        dest.writeString(createdAt);
        dest.writeString(objectId);
        dest.writeString(updatedAt);
    }
}