package sng.com.testhvn.model.product;

/**
 * Created by son.nguyen on 3/19/2016.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sng.com.testhvn.model.DateCreated;
import sng.com.testhvn.model.brand.BrandID;
import sng.com.testhvn.model.user.UserID;
public class Product implements Parcelable{

    @SerializedName("availabilityStatus")
    @Expose
    private String availabilityStatus;
    @SerializedName("brandID")
    @Expose
    private BrandID brandID;
    @SerializedName("colour")
    @Expose
    private String colour;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("dateCreated")
    @Expose
    private DateCreated dateCreated;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("productID")
    @Expose
    private ProductID productID;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("userID")
    @Expose
    private UserID userID;

    protected Product(Parcel in) {
        availabilityStatus = in.readString();
        colour = in.readString();
        createdAt = in.readString();
        description = in.readString();
        objectId = in.readString();
        productName = in.readString();
        updatedAt = in.readString();
        comment = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    /**
     *
     * @return
     * The availabilityStatus
     */
    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    /**
     *
     * @param availabilityStatus
     * The availabilityStatus
     */
    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    /**
     *
     * @return
     * The brandID
     */
    public BrandID getBrandID() {
        return brandID;
    }

    /**
     *
     * @param brandID
     * The brandID
     */
    public void setBrandID(BrandID brandID) {
        this.brandID = brandID;
    }

    /**
     *
     * @return
     * The colour
     */
    public String getColour() {
        return colour;
    }

    /**
     *
     * @param colour
     * The colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The dateCreated
     */
    public DateCreated getDateCreated() {
        return dateCreated;
    }

    /**
     *
     * @param dateCreated
     * The dateCreated
     */
    public void setDateCreated(DateCreated dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The objectId
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     *
     * @param objectId
     * The objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     *
     * @return
     * The price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     * The productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     * The productID
     */
    public ProductID getProductID() {
        return productID;
    }

    /**
     *
     * @param productID
     * The productID
     */
    public void setProductID(ProductID productID) {
        this.productID = productID;
    }

    /**
     *
     * @return
     * The rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The userID
     */
    public UserID getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     * The userID
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
        dest.writeString(availabilityStatus);
        dest.writeString(colour);
        dest.writeString(createdAt);
        dest.writeString(description);
        dest.writeString(objectId);
        dest.writeString(productName);
        dest.writeString(updatedAt);
        dest.writeString(comment);
    }
}