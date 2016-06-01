package tw.com.pointtree.pointtreeuser.api.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("user_type")
    private String type;
    @SerializedName("mobile_number")
    private String mobileNumber;
    @SerializedName("img_url")
    private String imgUrl;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
