package sng.com.testhvn.service.apiRequestModel;

/**
 * Created by son.nguyen on 3/19/2016.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;
import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.product.Product;

@AutoParcel
public class ProductResult implements Parcelable {

    @SerializedName("results")
    @Expose
    private List<Product> results = new ArrayList<Product>();
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = new ArrayList<Comment>();

    public ProductResult(Parcel in) {
    }
    public ProductResult(){}
    public static final Creator<ProductResult> CREATOR = new Creator<ProductResult>() {
        @Override
        public ProductResult createFromParcel(Parcel in) {
            return new ProductResult(in);
        }

        @Override
        public ProductResult[] newArray(int size) {
            return new ProductResult[size];
        }
    };

    /**
     * @return The results
     */
    public List<Product> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Product> results) {
        this.results = results;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(this.getComment().toArray());
        dest.writeArray(this.getResults().toArray());
    }
}