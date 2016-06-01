package tw.com.pointtree.pointtreeuser.api.response;

public class RegisterResponse {
    private Data data;

    public String getNonce() {
        return data.nonce;
    }

    private class Data {
        private String nonce;
    }
}
