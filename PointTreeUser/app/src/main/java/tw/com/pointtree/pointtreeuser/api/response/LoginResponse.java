package tw.com.pointtree.pointtreeuser.api.response;

import tw.com.pointtree.pointtreeuser.api.models.User;

public class LoginResponse {
    private Data data;

    public User getUser() {
        return data.member;
    }

    public String getToken() {
        return data.token;
    }

    private class Data {
        private User member;
        private String token;
    }
}
