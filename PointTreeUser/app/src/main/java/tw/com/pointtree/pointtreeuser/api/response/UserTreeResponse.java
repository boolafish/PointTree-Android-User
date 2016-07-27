package tw.com.pointtree.pointtreeuser.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tw.com.pointtree.pointtreeuser.api.models.UserTree;

public class UserTreeResponse {
    @SerializedName("data")
    private List<UserTree> userTreeList;

    public List<UserTree> getUserTreeList() {
        return userTreeList;
    }
}
