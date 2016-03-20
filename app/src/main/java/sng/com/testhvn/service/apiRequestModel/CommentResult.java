package sng.com.testhvn.service.apiRequestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import sng.com.testhvn.model.Comment;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class CommentResult {
    @SerializedName("results")
    @Expose
    private List<Comment> results = new ArrayList<Comment>();

    /**
     * @return The results
     */
    public List<Comment> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Comment> results) {
        this.results = results;
    }

}