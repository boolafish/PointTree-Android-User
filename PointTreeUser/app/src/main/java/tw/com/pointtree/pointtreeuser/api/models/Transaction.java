package tw.com.pointtree.pointtreeuser.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Transaction {
    @SerializedName("tx_id")
    private String id;
    @SerializedName("type")
    private String type;
    @SerializedName("point")
    private Point point;
    @SerializedName("sender")
    private User sender;
    @SerializedName("receiver")
    private User receiver;
    @SerializedName("amount")
    private int amount;
    @SerializedName("time")
    private String timestamp;
    @SerializedName("comment")
    private String comment;

    private Transaction() {}

    static public class SimpleUserData {
        @SerializedName("name")
        private String name;
        @SerializedName("user_type")
        private String type;

        private SimpleUserData() {}

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        static public SimpleUserData getSampleSimpleUserData() {
            SimpleUserData userData = new SimpleUserData();
            userData.name = "name1";
            userData.type = "type";
            return userData;
        }
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Point getPoint() {
        return point;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public int getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getComment() {
        return comment;
    }

    static public Transaction getSampleTransaction() {
        Transaction tx = new Transaction();
        tx.id = "deadbeef";
        tx.type = "type";
        tx.point = Point.getSamplePoint("Point");
        tx.sender = User.getSampleUser();
        tx.receiver = User.getSampleUser();
        tx.amount = 1;
        tx.timestamp = "0";
        tx.comment = "";
        return tx;
    }

    static public ArrayList<Transaction> getSampleTransactions() {
        ArrayList<Transaction> txList = new ArrayList<>();
        txList.add(Transaction.getSampleTransaction());
        txList.add(Transaction.getSampleTransaction());
        return txList;
    }
}
