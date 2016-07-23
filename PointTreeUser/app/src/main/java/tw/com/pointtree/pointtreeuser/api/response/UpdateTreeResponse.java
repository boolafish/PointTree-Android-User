package tw.com.pointtree.pointtreeuser.api.response;

import com.google.gson.annotations.SerializedName;

import tw.com.pointtree.pointtreeuser.api.models.UserTree;

public class UpdateTreeResponse {
    @SerializedName("data")
    private Data data;

    public String getAction() {
        return data.action;
    }

    public int getAmount() {
        return data.amount;
    }

    public UserTree getUserTree() {
        return data.userTree;
    }

    private class Data {
        @SerializedName("action")
        private String action;

        @SerializedName("amount")
        private int amount;

        @SerializedName("member_tree")
        private UserTree userTree;
    }
}
