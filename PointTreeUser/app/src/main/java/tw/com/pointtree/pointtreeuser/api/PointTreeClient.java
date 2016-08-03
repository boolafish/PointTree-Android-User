package tw.com.pointtree.pointtreeuser.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import tw.com.pointtree.pointtreeuser.api.models.Transaction;
import tw.com.pointtree.pointtreeuser.api.response.AuthResponse;
import tw.com.pointtree.pointtreeuser.api.response.BalanceResponse;
import tw.com.pointtree.pointtreeuser.api.response.LoginResponse;
import tw.com.pointtree.pointtreeuser.api.response.ProfileResponse;
import tw.com.pointtree.pointtreeuser.api.response.RegisterResponse;
import tw.com.pointtree.pointtreeuser.api.response.TransactionResponse;
import tw.com.pointtree.pointtreeuser.api.response.UpdateTreeResponse;
import tw.com.pointtree.pointtreeuser.api.response.UserTreeResponse;

public interface PointTreeClient {
    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<LoginResponse> login(
            @Field("mobile_number") String mobileNumber,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/api/v1/members")
    Call<RegisterResponse> register(
            @Field("mobile_number") String mobileNumber,
            @Field("password") String password,
            @Field("name") String name,
            @Field("sex") String sex,
            @Field("birthday") String birthday
    );

    @GET("/api/v1/members/{user_id}/profile")
    Call<ProfileResponse> getProfile(
            @Path("user_id") String userId
    );

    @FormUrlEncoded
    @POST("/api/v1/members/{user_id}/profile")
    Call<ProfileResponse> updateProfile(
            @Path("user_id") String userId,
            @Field("name") String name
    );

    @POST("/api/v1/members/{user_id}/profile")
    Call<ProfileResponse> updateProfile(
            @Path("user_id") String userId,
            @Body RequestBody image
    );

    @GET("/api/v1/members/{user_id}/balance")
    Call<BalanceResponse> getBalances(
            @Path("user_id") String userId
    );

    @GET("/api/v1/members/{user_id}/transactions")
    Call<TransactionResponse> getTransactions(
            @Path("user_id") String userId
    );

    @FormUrlEncoded
    @POST("/api/v1/points/{point_id}/transfer")
    Call<Transaction> transferPoint(
            @Path("point_id") String pointId,
            @Field("amount") int amount,
            @Field("comment") String comment
    );

    @FormUrlEncoded
    @POST("/api/v1/messages/{nonce}")
    Call<AuthResponse> authorizeCode(
            @Path("nonce") String nonce,
            @Field("code") int code
    );

    @GET("/api/v1/members/{user_id}/trees")
    Call<UserTreeResponse> getUserTrees(
            @Path("user_id") String userId
    );

    @POST("/api/v1/members/{user_id}/trees")
    Call<UpdateTreeResponse> updateUserTrees(
            @Path("user_id") String userId
    );
}
