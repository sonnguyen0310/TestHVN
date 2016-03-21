package sng.com.testhvn.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by son.nguyen on 3/20/2016.
 */
public class User implements Parcelable {

    @SerializedName("IsCustomer")
    @Expose
    private Boolean IsCustomer;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("dateOfBirth")
    @Expose
    private DateOfBirth dateOfBirth;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("objectId")
    @Expose
    private String objectId;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("userName")
    @Expose
    private String userName;

    protected User(Parcel in) {
        createdAt = in.readString();
        email = in.readString();
        objectId = in.readString();
        updatedAt = in.readString();
        userName = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    /**
     * @return The IsCustomer
     */
    public Boolean getIsCustomer() {
        return IsCustomer;
    }

    /**
     * @param IsCustomer The IsCustomer
     */
    public void setIsCustomer(Boolean IsCustomer) {
        this.IsCustomer = IsCustomer;
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
     * @return The dateOfBirth
     */
    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth The dateOfBirth
     */
    public void setDateOfBirth(DateOfBirth dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createdAt);
        dest.writeString(email);
        dest.writeString(objectId);
        dest.writeString(updatedAt);
        dest.writeString(userName);
    }
}