package tw.com.pointtree.pointtreeuser.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientGenerator {
    private static final String API_HTTP_SCHEME = "https";
    private static final String API_BASE_URL = "pointtree.com.tw";
    private static final int API_PORT = 443;

    private static final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    private static final HttpUrl apiBaseUrl =
            new HttpUrl.Builder()
                    .scheme(API_HTTP_SCHEME)
                    .host(API_BASE_URL)
                    .port(API_PORT)
                    .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(apiBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create());

    public static String getApiBaseUrlString() {
        return ClientGenerator.API_HTTP_SCHEME + "://" + ClientGenerator.API_BASE_URL;
    }

    public static OkHttpClient createAuthorizedOkHttpClient(final String token) {
        OkHttpClient client = httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .header("Authorization", "Token ".concat(token)).build();
                return chain.proceed(request);
            }
        }).build();
        return client;
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClientBuilder.build()).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createAuthorizedService(Class<S> serviceClass, final String token) {
        OkHttpClient httpClient = createAuthorizedOkHttpClient(token);
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
}
