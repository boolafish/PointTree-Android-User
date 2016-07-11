package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.UserPreference;
import tw.com.pointtree.pointtreeuser.activities.LoginActivity;
import tw.com.pointtree.pointtreeuser.activities.QRcodeActivity;
import tw.com.pointtree.pointtreeuser.activities.SearchActivity;
import tw.com.pointtree.pointtreeuser.activities.UserQueryActivity;
import tw.com.pointtree.pointtreeuser.api.models.User;

public class OverviewFragment extends TitledFragment {
    // TODO: should get these real data from server
    private int treeType = 15;
    private int treeLevel = 5;

    private User currentUser;
    private UserPreference userPreference;

    private EditText searchText;
    private Button sendPointButton;
    private Button redeemButton;
    private TextView scoreText;
    private ImageView treeImage;

    public OverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OverviewFragment.
     */
    public static OverviewFragment newInstance() {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPreference = new UserPreference(getActivity());
        String userToken = userPreference.getUserToken();
        if (userToken == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            String userId = userPreference.getUserId();
            String token = userPreference.getUserToken();
            // fetch user tree API
        }
    }

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @NonNull
    @Override
    public String getFragmentTitle() {
        return "首頁";
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
        super.onViewCreated(view, savedInstanceState);

        setOverviewView(view);

        String fileName = "tree_type" + treeType + "_" + treeLevel;
        // TODO: should show default error image if id is zero
        // TODO: all tree images should have different resolution
        int id = getResources().getIdentifier(fileName, "drawable", getContext().getPackageName());
        treeImage.setImageResource(id);

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        sendPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserQueryActivity.class);
                startActivity(intent);
            }
        });

        redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QRcodeActivity.class);
                intent.putExtra("user", currentUser);
                startActivity(intent);
            }
        });
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private void setOverviewView(View view) {
        searchText = (EditText) view.findViewById(R.id.search_bar);
        sendPointButton = (Button) view.findViewById(R.id.send_button);
        redeemButton = (Button) view.findViewById(R.id.redeem_button);
        scoreText = (TextView) view.findViewById(R.id.score);
        treeImage = (ImageView) view.findViewById(R.id.tree_type);
    }
}
