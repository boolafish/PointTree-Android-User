package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
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
import tw.com.pointtree.pointtreeuser.UserPreference;
import tw.com.pointtree.pointtreeuser.activities.LoginActivity;
import tw.com.pointtree.pointtreeuser.activities.QRcodeActivity;
import tw.com.pointtree.pointtreeuser.activities.SearchActivity;
import tw.com.pointtree.pointtreeuser.activities.UserQueryActivity;
import tw.com.pointtree.pointtreeuser.api.ClientGenerator;
import tw.com.pointtree.pointtreeuser.api.PointTreeClient;
import tw.com.pointtree.pointtreeuser.api.models.User;
import tw.com.pointtree.pointtreeuser.api.models.UserTree;
import tw.com.pointtree.pointtreeuser.api.response.UpdateTreeResponse;
import tw.com.pointtree.pointtreeuser.api.response.UserTreeResponse;

public class OverviewFragment extends TitledFragment {
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
            // TODO: updating user tree should be done by notification in next version
            updateUserTrees(userId, token);
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

    private void updateUserTrees(final String userId, final String userToken) {
        PointTreeClient client =
                ClientGenerator.createAuthorizedService(PointTreeClient.class, userToken);
        client.updateUserTrees(userId).enqueue(new Callback<UpdateTreeResponse>() {
            @Override
            public void onResponse(Call<UpdateTreeResponse> call, Response<UpdateTreeResponse> response) {
                if (response.code() == 200) {
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
                        fetchUserTrees(userId, userToken);
                    } else {
                        if (response.body().getAction().equals("get_new_tree")) {
                            // TODO: should replace with customized dialog
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            dialog.setTitle("New Tree");
                            dialog.setMessage("You got a new tree.");
                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    updateUserTrees(userId, userToken);
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
                                    updateUserTrees(userId, userToken);
                                }
                            });
                            dialog.show();
                        }
                    }
                } else if (response.code() == 403) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<UpdateTreeResponse> call, Throwable t) {
                // TODO:
            }
        });
    }

    private void fetchUserTrees(String userId, String userToken) {
        PointTreeClient client =
                ClientGenerator.createAuthorizedService(PointTreeClient.class, userToken);
        client.getUserTrees(userId).enqueue(new Callback<UserTreeResponse>() {
            @Override
            public void onResponse(Call<UserTreeResponse> call, Response<UserTreeResponse> response) {
                if (response.code() == 200) {
                    List<UserTree> userTreeList = response.body().getUserTreeList();

                    if (userTreeList.size() > 0) {
                        // calculate the total user tree score
                        int userScore = 0;
                        for (int i = 0; i < userTreeList.size(); i++) {
                            userScore += userTreeList.get(i).getStages();
                        }
                        scoreText.setText(Integer.toString(userScore));
                    }
                } else if (response.code() == 403) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<UserTreeResponse> call, Throwable t) {
                // TODO:
            }
        });
    }

    private void setOverviewView(View view) {
        searchText = (EditText) view.findViewById(R.id.search_bar);
        sendPointButton = (Button) view.findViewById(R.id.send_button);
        redeemButton = (Button) view.findViewById(R.id.redeem_button);
        scoreText = (TextView) view.findViewById(R.id.score);
        treeImage = (ImageView) view.findViewById(R.id.tree_type);
    }
}
