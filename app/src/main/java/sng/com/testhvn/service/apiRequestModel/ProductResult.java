package sng.com.testhvn.service.apiRequestModel;

/**
 * Created by son.nguyen on 3/19/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import sng.com.testhvn.model.Comment;
import sng.com.testhvn.model.Product;

public class ProductResult {

    @SerializedName("results")
    @Expose
    private List<Product> results = new ArrayList<Product>();
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = new ArrayList<Comment>();

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
}