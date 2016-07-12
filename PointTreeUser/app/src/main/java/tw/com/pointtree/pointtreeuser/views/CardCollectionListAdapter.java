package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import tw.com.pointtree.pointtreeuser.api.models.Balance;


public class CardCollectionListAdapter extends BaseAdapter {
    public static final int TYPE_SEPARATOR = 0;
    public static final int TYPE_CARD = 1;
    private Context context;
    private ArrayList<Object> balanceList;

    public CardCollectionListAdapter(Context context, ArrayList<Balance> balanceList) {
        this.context = context;

        if (balanceList != null) {
            this.balanceList = new ArrayList<Object>(balanceList);
            this.balanceList.add(0, "集點卡");
        } else {
            this.balanceList = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return balanceList.size();
    }

    @Override
    public Object getItem(int i) {
        return balanceList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int i) {
        return isSeparator(balanceList.get(i)) ? TYPE_SEPARATOR : TYPE_CARD;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (balanceList == null) {
            //TODO handle empty case
            return null;
        }

        if (isSeparator(balanceList.get(i))) {
            CardCollectionSeparatorView separatorView;

            if (view == null) {
                separatorView = new CardCollectionSeparatorView(context);
            } else {
                separatorView = (CardCollectionSeparatorView) view;
            }

            separatorView.setCategory((String) balanceList.get(i));

            return separatorView;

        } else {
            CardCollectionView cardView;

            if (view == null) {
                cardView = new CardCollectionView(context);
            } else {
                cardView = (CardCollectionView) view;
            }

            cardView.setData((Balance) balanceList.get(i));

            return cardView;
        }
    }

    public boolean isSeparator(Object object) {
        return object instanceof String;
    }
}
