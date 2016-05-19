package tw.com.pointtree.pointtreeuser.api.response;

import com.google.gson.annotations.SerializedName;

import tw.com.pointtree.pointtreeuser.api.models.User;

public class ProfileResponse {
    @SerializedName("data")
    private User user;
}
