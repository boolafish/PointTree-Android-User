package tw.com.pointtree.pointtreeuser.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.Session;
import tw.com.pointtree.pointtreeuser.activities.QRcodeActivity;
import tw.com.pointtree.pointtreeuser.activities.SearchActivity;
import tw.com.pointtree.pointtreeuser.activities.UserQueryActivity;
import tw.com.pointtree.pointtreeuser.api.models.UserTree;
import tw.com.pointtree.pointtreeuser.api.response.UpdateTreeResponse;
import tw.com.pointtree.pointtreeuser.api.response.UserTreeResponse;

public class OverviewFragment extends TitledFragment {
    private Session session;

    private EditText searchText;
    private Button sendPointButton;
    private Button redeemButton;
    private TextView scoreText;
    private ImageView treeImage;

    private Callback<UpdateTreeResponse> updateTreeCallback = new Callback<UpdateTreeResponse>() {
        @Override
        public void onResponse(Call<UpdateTreeResponse> call, Response<UpdateTreeResponse> response) {
            if (response.isSuccessful()) {
                if (response.body().getAction() == null) {
                    // show the tree image
                    UserTree userTree = response.body().getUserTree();
                    if (userTree != null) {
                        int treeLevel = userTree.getStages();
                        int treeId = userTree.getTree().getId();
                        String fileName = "tree_type" + treeId + "_" + treeLevel;
                        int id = getResources().getIdentifier(fileName, "drawable", getContext().getPackageName());
                        treeImage.setImageResource(id);
                    }

                    // retrieve all user trees to calculate the total score
                    session.getClient().getUserTrees(session.getUserId()).enqueue(userTreeCallback);
                } else if (response.body().getAction().equals("get_new_tree")) {
                    // TODO: should replace with customized dialog
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("New Tree");
                    dialog.setMessage("You got a new tree.");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            updateUserTrees();
                        }
                    });
                    dialog.show();
                } else if (response.body().getAction().equals("add_points")) {
                    // TODO: should replace with customized dialog
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("Add points");
                    dialog.setMessage("Your tree has become bigger.");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            updateUserTrees();
                        }
                    });
                    dialog.show();
                }
            }
        }

        @Override
        public void onFailure(Call<UpdateTreeResponse> call, Throwable t) {
            // TODO:
        }
    };

    private Callback<UserTreeResponse> userTreeCallback = new Callback<UserTreeResponse>() {
        @Override
        public void onResponse(Call<UserTreeResponse> call, Response<UserTreeResponse> response) {
            if (response.isSuccessful()) {
                List<UserTree> userTreeList = response.body().getUserTreeList();

                if (userTreeList.size() > 0) {
                    // calculate the total user tree score
                    int userScore = 0;
                    for (int i = 0; i < userTreeList.size(); i++) {
                        userScore += userTreeList.get(i).getStages();
                    }
                    scoreText.setText(Integer.toString(userScore));
                }
            }
        }

        @Override
        public void onFailure(Call<UserTreeResponse> call, Throwable t) {
            // TODO:
        }
    };

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
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setOverviewView(view);

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
                startActivity(intent);
            }
        });

        session = new Session(getActivity());
        // TODO: updating user tree should be done by notification in next version
        updateUserTrees();
    }

    private void updateUserTrees() {
        session.getClient().updateUserTrees(session.getUserId()).enqueue(updateTreeCallback);
    }

    private void setOverviewView(View view) {
        searchText = (EditText) view.findViewById(R.id.search_bar);
        sendPointButton = (Button) view.findViewById(R.id.send_button);
        redeemButton = (Button) view.findViewById(R.id.redeem_button);
        scoreText = (TextView) view.findViewById(R.id.score);
        treeImage = (ImageView) view.findViewById(R.id.tree_type);
    }
}
