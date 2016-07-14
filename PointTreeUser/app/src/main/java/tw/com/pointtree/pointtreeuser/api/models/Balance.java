package tw.com.pointtree.pointtreeuser.api.models;

import java.util.ArrayList;

public class Balance {
    private Point point;
    private int balance;

    public Point getPoint() {
        return point;
    }

    public int getBalance() {
        return balance;
    }

    public static ArrayList<Balance> getSampleBalanceList() {
        ArrayList<Balance> balance_list = new ArrayList<>();
        Balance balance = new Balance();
        balance.point = Point.getSamplePoint("你好啊");
        balance.balance = 100;
        balance_list.add(balance);

        balance = new Balance();
        balance.point = Point.getSamplePoint("哇賽");
        balance.balance = 200;
        balance_list.add(balance);

        balance = new Balance();
        balance.point = Point.getSamplePoint("哭哭");
        balance.balance = 250;
        balance_list.add(balance);

        balance = new Balance();
        balance.point = Point.getSamplePoint("哇嗚");
        balance.balance = 1126;
        balance_list.add(balance);
        return balance_list;
    }
}
