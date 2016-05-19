package tw.com.pointtree.pointtreeuser.api.models;

import com.google.gson.annotations.SerializedName;

public class Point {
    @SerializedName("point_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("member_control")
    private String memberControl;
    @SerializedName("img_url")
    private String imgUrl;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMemberControl() {
        return memberControl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
