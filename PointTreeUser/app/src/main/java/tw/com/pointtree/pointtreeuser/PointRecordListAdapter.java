package tw.com.pointtree.pointtreeuser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import tw.com.pointtree.pointtreeuser.api.models.Transaction;
import tw.com.pointtree.pointtreeuser.api.models.User;
import tw.com.pointtree.pointtreeuser.views.PointRecordView;

public class PointRecordListAdapter extends BaseAdapter {
    private ArrayList<Transaction> transactions;
    private Context context;
    private User currentUser;

    public PointRecordListAdapter(Context context, ArrayList<Transaction> transactions, User currentUser) {
        if (transactions != null) {
            this.transactions = new ArrayList<>(transactions);
            // sort from new to old
            Collections.sort(this.transactions, new Comparator<Transaction>() {
                @Override
                public int compare(Transaction tx1, Transaction tx2) {
                    return tx2.getTimestamp().compareTo(tx1.getTimestamp());
                }
            });
        } else {
            this.transactions = new ArrayList<>();
        }

        this.currentUser = currentUser;
        this.context = context;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Object getItem(int i) {
        return transactions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PointRecordView pointRecordView;

        if (transactions.size() == 0) {
            // TODO handle empty transactions
            return null;
        }

        if (view == null) {
            pointRecordView = new PointRecordView(context, currentUser);
        } else {
            pointRecordView = (PointRecordView) view;
        }

        if (i == 0 || !isSameDay(transactions.get(i).getTimestamp(), transactions.get(i - 1).getTimestamp())) {
            pointRecordView.setDateTextViewVisible(true);
        } else {
            pointRecordView.setDateTextViewVisible(false);
        }

        pointRecordView.setData(transactions.get(i));
        return pointRecordView;
    }

    private boolean isSameDay(String timeStamp1, String timeStamp2) {
        Date date1 = new Date(Long.parseLong(timeStamp1) * 1000);
        Date date2 = new Date(Long.parseLong(timeStamp2) * 1000);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
