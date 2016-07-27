package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

import tw.com.pointtree.pointtreeuser.PointRecordListAdapter;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.Session;
import tw.com.pointtree.pointtreeuser.activities.LoginActivity;
import tw.com.pointtree.pointtreeuser.api.ClientGenerator;
import tw.com.pointtree.pointtreeuser.api.PointTreeClient;
import tw.com.pointtree.pointtreeuser.api.models.Transaction;
import tw.com.pointtree.pointtreeuser.api.models.User;

public class SettingFragment extends TitledFragment {
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
     */
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
        setButtonControl(view);

        // TODO use real transactions
        transactions = Transaction.getSampleTransactions();
        pointRecordListView.setAdapter(
                new PointRecordListAdapter(getContext(), transactions, this.currentUser));
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private void setButtonControl(View view) {
        final Button pointRecordButton = (Button) view.findViewById(R.id.pointRecordButton);
        final Button accountSettingButton = (Button) view.findViewById(R.id.accountSettingButton);
        final ListView pointRecordListView = (ListView) view.findViewById(R.id.pointRecordListVIew);
        final ScrollView accountSettingScrollView = (ScrollView) view.findViewById(R.id.accountSettingScrollView);
        final LinearLayout logoutButton = (LinearLayout) view.findViewById(R.id.logout);

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
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session session = new Session(getActivity());
                session.clearSession();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
