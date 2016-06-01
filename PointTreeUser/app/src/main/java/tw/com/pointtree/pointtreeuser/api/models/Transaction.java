package tw.com.pointtree.pointtreeuser.api.models;

import com.google.gson.annotations.SerializedName;

public class Transaction {
    @SerializedName("tx_id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("point")
    private Point point;
    @SerializedName("sender")
    private SimpleUserData sender;
    @SerializedName("receiver")
    private SimpleUserData receiver;
    @SerializedName("amount")
    private int amount;
    @SerializedName("timestamp")
    private String timestamp;
    @SerializedName("comment")
    private String comment;

    private class SimpleUserData {
        @SerializedName("name")
        private String name;
        @SerializedName("user_type")
        private String type;
    }
}
