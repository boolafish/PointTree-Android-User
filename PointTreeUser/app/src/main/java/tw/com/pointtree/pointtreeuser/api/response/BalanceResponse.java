package tw.com.pointtree.pointtreeuser.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tw.com.pointtree.pointtreeuser.api.models.Balance;

public class BalanceResponse {
    @SerializedName("data")
    private List<Balance> balances;

    public List<Balance> getBalances() {
        return balances;
    }
}
