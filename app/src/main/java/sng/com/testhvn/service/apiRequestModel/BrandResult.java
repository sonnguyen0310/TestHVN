package sng.com.testhvn.service.apiRequestModel;

/**
 * Created by son.nguyen on 3/19/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import sng.com.testhvn.model.Brand;

public class BrandResult {

    @SerializedName("results")
    @Expose
    private List<Brand> results = new ArrayList<Brand>();

    /**
     *
     * @return
     * The results
     */
    public List<Brand> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Brand> results) {
        this.results = results;
    }

}