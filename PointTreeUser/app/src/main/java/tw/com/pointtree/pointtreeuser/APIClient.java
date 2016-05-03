package tw.com.pointtree.pointtreeuser;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class APIClient {
    private static final String API_HTTP_SCHEME = "http";
    private static final String API_BASE_URL = "52.196.7.22";
    private static final int API_PORT = 8000;

    private final OkHttpClient client;
    private final HttpUrl apiBaseUrl;

    public APIClient() {
        client = new OkHttpClient.Builder().build();
        apiBaseUrl = new HttpUrl.Builder()
                .scheme(API_HTTP_SCHEME)
                .host(API_BASE_URL)
                .port(API_PORT)
                .build();
    }

    private HttpUrl buildUrlPath(String path, Map<String, String> query) {
        HttpUrl.Builder urlBuilder = apiBaseUrl.newBuilder();
        urlBuilder.addPathSegments(path);
        if (query != null) {
            for (Map.Entry<String, String> e : query.entrySet()) {
                urlBuilder.addQueryParameter(e.getKey(), e.getValue());
            }
        }
        return urlBuilder.build();
    }

    private RequestBody buildRequestBody(Map<String, String> data) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (data != null) {
            for (Map.Entry<String, String> e : data.entrySet()) {
                formBodyBuilder.add(e.getKey(), e.getValue());
            }
        }
        return formBodyBuilder.build();
    }

    /**
     * Send a get request.
     * @param path The path of URI.
     * @param query The queries to be appended after URI.
     * @param listener The listener of callback.
     */
    private void get(String path, Map<String, String> query, Callback listener) {
        HttpUrl url = buildUrlPath(path, query);
        Request request = new Request.Builder().get().url(url).build();
        client.newCall(request).enqueue(listener);
    }

    /**
     * Send a post request.
     * @param path The path of URI.
     * @param data The form data key/value pair.
     * @param listener The listener of callback.
     */
    private void post(String path, Map<String, String> data, Callback listener) {
        HttpUrl url = buildUrlPath(path, null);
        Request request = new Request.Builder().post(buildRequestBody(data)).url(url).build();
        client.newCall(request).enqueue(listener);
    }

    public void login(String mobileNumber, String password, Callback listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile_number", mobileNumber);
        map.put("password", password);
        post("api/v1/login", map, listener);
    }

    public void register(String mobileNumber, String password, Callback listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile_number", mobileNumber);
        map.put("password", password);
        post("api/v1/members", map, listener);
    }
}
