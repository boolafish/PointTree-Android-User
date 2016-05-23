package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

import tw.com.pointtree.pointtreeuser.PointRecordListAdapter;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.models.Transaction;
import tw.com.pointtree.pointtreeuser.models.User;

public class SettingFragment extends TitledFragment {
    private static final String ARG_USER = "user";
    private ArrayList<Transaction> transactions;
    private User currentUser;


    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingFragment.
     * @param currentUser The logged in user.
     */
    public static SettingFragment newInstance(User currentUser) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, currentUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentUser = getArguments().getParcelable(ARG_USER);
        }
    }

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @NonNull
    @Override
    public String getFragmentTitle() {
        return "帳戶設定";
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ListView pointRecordListView = (ListView) view.findViewById(R.id.pointRecordListVIew);

        // TODO use real transactions
        transactions = Transaction.getSampleTransactions();
        pointRecordListView.setAdapter(new PointRecordListAdapter(getContext(), transactions, currentUser));

        setButtonControl(view);
    }

    private void setButtonControl(View view) {
        final Button pointRecordButton = (Button) view.findViewById(R.id.pointRecordButton);
        final Button accountSettingButton = (Button) view.findViewById(R.id.accountSettingButton);
        final ListView pointRecordListView = (ListView) view.findViewById(R.id.pointRecordListVIew);
        final ScrollView accountSettingScrollView = (ScrollView) view.findViewById(R.id.accountSettingScrollView);

        pointRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointRecordListView.setVisibility(View.VISIBLE);
                accountSettingScrollView.setVisibility(View.GONE);
                pointRecordButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightTeal));
                accountSettingButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.duckEggBlue));
                pointRecordButton.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                accountSettingButton.setTextColor(ContextCompat.getColor(getContext(), R.color.seafoamBlue));
            }
        });
        accountSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountSettingScrollView.setVisibility(View.VISIBLE);
                pointRecordListView.setVisibility(View.GONE);
                pointRecordButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.duckEggBlue));
                accountSettingButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightTeal));
                pointRecordButton.setTextColor(ContextCompat.getColor(getContext(), R.color.seafoamBlue));
                accountSettingButton.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }
        });
    }
}
