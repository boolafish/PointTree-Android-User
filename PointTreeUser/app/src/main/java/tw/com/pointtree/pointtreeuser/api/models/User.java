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
    @SerializedName("user_number")
    private int userNumber;

    private User() {
    }

    public User(String id, String name, String type, String mobileNumber, String imgUrl, int userNumber) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.mobileNumber = mobileNumber;
        this.imgUrl = imgUrl;
        this.userNumber = userNumber;
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

    public int getUserNumber() {
        return userNumber;
    }

    public static User getSampleUser() {
        User user = new User();
        user.id = "r04944037";
        user.name = "阿堯隊長";
        user.type = "normal";
        user.mobileNumber = "+886900000000";
        user.imgUrl = "http://www.example.com/example.jpg";
        user.userNumber = 10001;
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
