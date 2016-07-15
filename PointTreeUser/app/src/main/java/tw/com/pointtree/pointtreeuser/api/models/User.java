package tw.com.pointtree.pointtreeuser.api.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    public static final String TYPE_ADMIN = "admin";
    public static final String TYPE_STORE = "store";
    public static final String TYPE_NORMAL = "normal";

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

    private User() {
    }

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
        user.mobileNumber = "+886900000000";
        user.imgUrl = "http://www.example.com/example.jpg";
        return user;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO use real equal compare
        if (obj != null && obj instanceof User) {
            User anotherUser = (User) obj;
            if (anotherUser.getName().equals(this.getName())) {
                return true;
            }
        }
        return false;
    }
}
