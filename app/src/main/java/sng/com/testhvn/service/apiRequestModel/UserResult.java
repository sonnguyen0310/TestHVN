package sng.com.testhvn.service.apiRequestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import sng.com.testhvn.model.user.User;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class UserResult {
    @SerializedName("results")
    @Expose
    private List<User> results = new ArrayList<User>();

    /**
     * @return The results
     */
    public List<User> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<User> results) {
        this.results = results;
    }

}