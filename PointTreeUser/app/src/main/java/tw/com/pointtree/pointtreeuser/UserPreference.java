package tw.com.pointtree.pointtreeuser;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private SharedPreferences preferences;

    private static String USER_PREF = "USER_PREF";
    private static String USER_TOKEN = "USER_TOKEN";
    private static String USER_ID = "USER_ID";

    public UserPreference(Context context) {
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

    public void setUserId(String userToken) {
        this.preferences.edit().putString(USER_ID, userToken).apply();
    }
}
