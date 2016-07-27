package tw.com.pointtree.pointtreeuser;

import android.content.Context;
import android.content.SharedPreferences;

import tw.com.pointtree.pointtreeuser.api.ClientGenerator;
import tw.com.pointtree.pointtreeuser.api.PointTreeClient;
import tw.com.pointtree.pointtreeuser.api.models.User;

public class Session {
    private static String USER_PREF = "USER_PREF";
    private static String USER_TOKEN = "USER_TOKEN";
    private static String USER_ID = "USER_ID";
    private static String USER_NAME = "USER_NAME";
    private static String USER_TYPE = "USER_TYPE";
    private static String USER_MOBILE_NUMBER = "USER_MOBILE_NUMBER";
    private static String USER_IMG_URL = "USER_IMG_URL";
    private static String USER_NUMBER = "USER_NUMBER";

    private SharedPreferences preferences;
    private User user;

    public Session(Context context) {
        this.preferences = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
    }

    /**
     * Get the user token from user preference.
     * @return The user token or null if doesn't exist.
     */
    public String getUserToken() {
        return this.preferences.getString(USER_TOKEN, null);
    }

    public void setUserToken(String userToken) {
        this.preferences.edit().putString(USER_TOKEN, userToken).apply();
    }

    /**
     * Get the user id from user preference.
     * @return The user id or null if doesn't exist.
     */
    public String getUserId() {
        return this.preferences.getString(USER_ID, null);
    }

    public void setUserId(String userId) {
        this.preferences.edit().putString(USER_ID, userId).apply();
    }

    public void clearSession() {
        this.preferences.edit().clear().apply();
    }

    public User getUser() {
        String id = preferences.getString(USER_ID, null);
        String name = preferences.getString(USER_NAME, null);
        String type = preferences.getString(USER_TYPE, null);
        String mobileNumber = preferences.getString(USER_MOBILE_NUMBER, null);
        String imgUrl = preferences.getString(USER_IMG_URL, null);
        int userNumber  = preferences.getInt(USER_NUMBER, -1);

        return new User(id, name, type, mobileNumber, imgUrl, userNumber);
    }

    public void setUser(User user) {
        this.preferences.edit()
                .putString(USER_ID, user.getId())
                .putString(USER_NAME, user.getName())
                .putString(USER_TYPE, user.getType())
                .putString(USER_MOBILE_NUMBER, user.getMobileNumber())
                .putString(USER_IMG_URL, user.getImgUrl())
                .putInt(USER_NUMBER, user.getUserNumber())
                .apply();
    }

    public PointTreeClient getClient() {
        PointTreeClient client ;
        String userToken = getUserToken();
        if (userToken == null) {
            client = ClientGenerator.createService(PointTreeClient.class);
        } else {
            client = ClientGenerator.createAuthorizedService(PointTreeClient.class, userToken);
        }
        return client;
    }
}
