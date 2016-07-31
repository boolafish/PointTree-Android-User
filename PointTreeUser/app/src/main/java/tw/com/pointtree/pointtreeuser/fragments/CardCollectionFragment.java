package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.activities.CardDetailActivity;
import tw.com.pointtree.pointtreeuser.activities.SearchActivity;
import tw.com.pointtree.pointtreeuser.api.models.Balance;
import tw.com.pointtree.pointtreeuser.views.CardCollectionListAdapter;

public class CardCollectionFragment extends TitledFragment {
    public CardCollectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CardCollectionFragment.
     */
    public static CardCollectionFragment newInstance() {
        CardCollectionFragment fragment = new CardCollectionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_collection, container, false);
    }

    @NonNull
    @Override
    public String getFragmentTitle() {
        return "集點卡列表";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        EditText searchText = (EditText) view.findViewById(R.id.search_bar);

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        SwipeMenuListView cardCollectionListView = (SwipeMenuListView) view.findViewById(R.id.card_collection_list_view);

        ArrayList<Balance> balanceList = getBalanceData();
        CardCollectionListAdapter cardCollectionListAdapter =
                new CardCollectionListAdapter(getContext(), balanceList);

        setupCardCollectionListView(cardCollectionListView, cardCollectionListAdapter);
    }

    private ArrayList<Balance> getBalanceData() {
        // TODO get real data from api
        return Balance.getSampleBalanceList();
    }

    private void setupCardCollectionListView(SwipeMenuListView cardCollectionListView, CardCollectionListAdapter adapter) {
        cardCollectionListView.setAdapter(adapter);
        cardCollectionListView.setMenuCreator(buildSwipeMenuCreator());
        cardCollectionListView.setOnItemClickListener(buildOnItemClickListener());
        cardCollectionListView.setOnMenuItemClickListener(buildOnMenuItemClickListener());
    }

    private SwipeMenuListView.OnMenuItemClickListener buildOnMenuItemClickListener() {
        return new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 1) {
                    // TODO implement delete
                } else if (index == 2) {
                    // TODO implement cancel favorite
                }
                return false;
            }
        };
    }

    private AdapterView.OnItemClickListener buildOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), CardDetailActivity.class);
                startActivity(intent);
            }
        };
    }

    private SwipeMenuCreator buildSwipeMenuCreator() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            final int width = 60;
            final int fontSize = 12;

            @Override
            public void create(SwipeMenu menu) {
                if (menu.getViewType() == CardCollectionListAdapter.TYPE_CARD) {
                    menu.addMenuItem(buildDeleteItem());
                    menu.addMenuItem(buildCancelFavoriteItem());
                }
            }

            private SwipeMenuItem buildDeleteItem() {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());

                deleteItem.setBackground(new ColorDrawable(Color.rgb(254, 56, 36)));
                deleteItem.setWidth(dp2px(width));
                deleteItem.setIcon(R.drawable.ic_delete);
                deleteItem.setTitle(R.string.delete);
                deleteItem.setTitleSize(fontSize);
                deleteItem.setTitleColor(Color.WHITE);
                return deleteItem;
            }

            private SwipeMenuItem buildCancelFavoriteItem() {
                SwipeMenuItem cancelFavoriteItem = new SwipeMenuItem(getContext());

                cancelFavoriteItem.setBackground(new ColorDrawable(Color.rgb(199, 199, 205)));
                cancelFavoriteItem.setWidth(dp2px(width));
                cancelFavoriteItem.setIcon(R.drawable.ic_star);
                cancelFavoriteItem.setTitle(R.string.cancel_favorite);
                cancelFavoriteItem.setTitleSize(fontSize);
                cancelFavoriteItem.setTitleColor(Color.WHITE);
                return cancelFavoriteItem;
            }
        };

        return creator;
    }

    private int dp2px(float dipValue) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
