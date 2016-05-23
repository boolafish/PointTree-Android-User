package tw.com.pointtree.pointtreeuser.models;

import java.util.ArrayList;

public class Transaction {
    private String id;
    private String type;
    private Point point;
    private User sender;
    private User receiver;
    private int amount;
    private String timestamp;
    private String comment;

    static public ArrayList<Transaction> getSampleTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        User store1 = new User("id", "Store1", User.TYPE_STORE, "0911111111", "imgUrl", "accessToken");
        User bun = new User("id", "+886910189422", User.TYPE_NORMAL, "0911111111", "imgUrl", "accessToken");
        User stanley = new User("id", "Stanley", User.TYPE_NORMAL, "0911111111", "imgUrl", "accessToken");
        Point rabbitPoint = new Point("123456789", "兔子兔子茶飲專賣店-內湖店-第二行");

        Transaction tempTx = new Transaction("id", "normal", rabbitPoint, store1, bun, 1, "1459947327", "comment");
        transactions.add(tempTx);

        tempTx = new Transaction("id", "normal", rabbitPoint, bun, stanley, 2, "1459947327", "comment");
        transactions.add(tempTx);

        tempTx = new Transaction("id", "normal", rabbitPoint, stanley, bun, 3, "1462176044", "comment");
        transactions.add(tempTx);

        tempTx = new Transaction("id", "normal", rabbitPoint, bun, stanley, 4, "1462176044", "comment");
        transactions.add(tempTx);

        return transactions;
    }

    public Transaction(String id, String type, Point point,
                       User sender, User receiver, int amount,
                       String timestamp, String comment) {
        this.id = id;
        this.type = type;
        this.point = point;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = timestamp;
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public User getReceiver() {
        return receiver;
    }

    public User getSender() {
        return sender;
    }

    public Point getPoint() {
        return point;
    }
}
