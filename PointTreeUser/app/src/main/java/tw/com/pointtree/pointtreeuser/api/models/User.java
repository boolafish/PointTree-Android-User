package tw.com.pointtree.pointtreeuser.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
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

    public static User getSampleUser() {
        User user = new User();
        user.id = "r04944037";
        user.name = "阿堯隊長";
        user.type = "normal";
        user.mobileNumber = "0911111111";
        user.imgUrl = "http://";

        return user;
    }
}
