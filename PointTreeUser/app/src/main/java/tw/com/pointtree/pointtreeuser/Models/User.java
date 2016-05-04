package tw.com.pointtree.pointtreeuser.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

public class User extends APIObject implements Parcelable {
    private String id;
    private String name;
    private String type;
    private String mobileNumber;
    private String imgUrl;
    private String accessToken;

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel in) {
        id = in.readString();
        name = in.readString();
        type = in.readString();
        mobileNumber = in.readString();
        imgUrl = in.readString();
        accessToken = in.readString();
    }

    public User(Response response) throws APIObjectException {
        super(response);
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

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    protected void instantiateFromJsonObject(JSONObject json) throws JSONException {
        JSONObject data = json.getJSONObject("data");
        JSONObject member = data.getJSONObject("member");
        this.id = member.getString("user_id");
        this.name = member.getString("name");
        this.type = member.getString("user_type");
        this.mobileNumber = member.getString("mobile_number");
        // TODO: add it back.
        // this.imgUrl = member.getString("img_uri");
        this.accessToken = data.getString("token");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(mobileNumber);
        parcel.writeString(imgUrl);
        parcel.writeString(accessToken);
    }
}
