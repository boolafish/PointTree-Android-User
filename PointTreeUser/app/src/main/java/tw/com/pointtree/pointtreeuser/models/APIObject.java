package tw.com.pointtree.pointtreeuser.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class APIObject {
    public APIObject(Response response) throws APIObjectException {
        try {
            JSONObject json = toJsonObject(response.body());
            instantiateFromJsonObject(json);
        } catch (Exception e) {
            e.printStackTrace();
            throw new APIObjectException("Unable to instantiate from API response.");
        }
    }

    protected APIObject() {
    }

    final protected JSONObject toJsonObject(ResponseBody body) throws IOException, JSONException {
        JSONObject json = new JSONObject(body.string());
        body.close();
        return json;
    }

    protected abstract void instantiateFromJsonObject(JSONObject json) throws JSONException;

    public class APIObjectException extends Exception {
        public APIObjectException(String detailMessage) {
            super(detailMessage);
        }
    }
}
