package sng.com.testhvn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class UserID {

    @SerializedName("__type")
    @Expose
    private String Type;
    @SerializedName("className")
    @Expose
    private String className;
    @SerializedName("objectId")
    @Expose
    private String objectId;

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
     * @return The className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className The className
     */
    public void setClassName(String className) {
        this.className = className;
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

}