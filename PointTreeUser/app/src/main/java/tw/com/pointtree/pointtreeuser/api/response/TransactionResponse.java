package tw.com.pointtree.pointtreeuser.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import tw.com.pointtree.pointtreeuser.api.models.Transaction;

public class TransactionResponse {
    @SerializedName("data")
    private List<Transaction> transactions;
}
