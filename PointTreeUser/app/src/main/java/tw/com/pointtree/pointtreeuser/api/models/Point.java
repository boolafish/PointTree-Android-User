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

    public static Point getSamplePoint(String name) {
        Point point = new Point();
        point.id = "id";
        point.name = name;
        point.memberControl = "memberControl";
        double randomNum = Math.random();
        if (randomNum < 0.333)
            point.imgUrl = "https://images.gamme.com.tw/news2/2016/50/39/qp_Xn6SVl6WdrqQ-270x180.jpg";
        else if (randomNum < 0.666)
            point.imgUrl = "http://pic.qiantucdn.com/58pic/14/57/44/26G58PICtZP_1024.jpg";
        else
            point.imgUrl = "http://images.gamme.com.tw/news2/2015/50/00/p56ZoaWej6CW.jpg";
        return point;
    }
}
