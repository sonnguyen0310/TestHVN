package sng.com.testhvn.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class DateOfBirth {

    @SerializedName("__type")
    @Expose
    private String Type;
    @SerializedName("iso")
    @Expose
    private String iso;

    /**
     * @return The Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type The __type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * @return The iso
     */
    public String getIso() {
        return iso;
    }

    /**
     * @param iso The iso
     */
    public void setIso(String iso) {
        this.iso = iso;
    }

}