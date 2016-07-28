package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.Session;
import tw.com.pointtree.pointtreeuser.api.models.Tree;
import tw.com.pointtree.pointtreeuser.api.models.UserTree;
import tw.com.pointtree.pointtreeuser.api.response.UserTreeResponse;

public class TreeDexFragment extends TitledFragment {
    private final static int DEFAULT_TREE_NUM = 16;

    private Session session;

    private GridView treeDexGridView;
    private TreeDexAdapter adapter;

    private Callback<UserTreeResponse> userTreeCallback = new Callback<UserTreeResponse>() {
        @Override
        public void onResponse(Call<UserTreeResponse> call, Response<UserTreeResponse> response) {
            if (response.isSuccessful()) {
                List<UserTree> userTreeList = response.body().getUserTreeList();
                adapter.setUserTrees(userTreeList);
            }
        }

        @Override
        public void onFailure(Call<UserTreeResponse> call, Throwable t) {
            // TODO:
        }
    };

    public TreeDexFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TreeDexFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TreeDexFragment newInstance() {
        TreeDexFragment fragment = new TreeDexFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_tree_dex, container, false);
    }

    @NonNull
    @Override
    public String getFragmentTitle() {
        return "圖鑑";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TreeDexAdapter(getContext());

        treeDexGridView = (GridView) view.findViewById(R.id.grid_view);
        treeDexGridView.setAdapter(adapter);

        session = new Session(getActivity());
        session.getClient().getUserTrees(session.getUserId()).enqueue(userTreeCallback);
    }

    private class TreeDexAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Tree> userTrees = new ArrayList<>(DEFAULT_TREE_NUM);

        public TreeDexAdapter(Context context) {
            this.context = context;
            for (int i = 0; i < DEFAULT_TREE_NUM; i++) {
                this.userTrees.add(new Tree());
            }
        }

        @Override
        public int getCount() {
            return DEFAULT_TREE_NUM;
        }

        @Override
        public Object getItem(int i) {
            return userTrees.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View gridView;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                gridView = inflater.inflate(R.layout.treedex_grid_view, viewGroup, false);
            } else {
                gridView = view;
            }

            ImageView treeImage = (ImageView) gridView.findViewById(R.id.tree_image);
            TextView treeName = (TextView) gridView.findViewById(R.id.tree_name);
            TextView treeId = (TextView) gridView.findViewById(R.id.tree_id);

            // set the tree id
            treeId.setText(String.format("%02d", i));

            if (userTrees.get(i).getName() != null) {
                // set the tree name
                treeName.setText(userTrees.get(i).getName());

                // set the tree image
                int treeLevel = userTrees.get(i).getStages();
                String fileName = "tree_type" + (i + 1) + "_" + treeLevel;
                int id = getResources().getIdentifier(fileName, "drawable", getContext().getPackageName());
                Picasso.with(context)
                        .load(id)
                        .placeholder(R.drawable.unknown_tree)
                        .into(treeImage);
            } else {
                // unknown tree
                treeName.setText("");
                Picasso.with(context)
                        .load(R.drawable.unknown_tree)
                        .into(treeImage);
            }

            return gridView;
        }

        public void setUserTrees(List<UserTree> userTreeList) {
            for (int i = 0; i < userTreeList.size(); i++) {
                if (userTreeList.get(i).getStatus().equals("completed")) {
                    // tree id starts from 1
                    int index = userTreeList.get(i).getTree().getId() - 1;
                    userTrees.set(index, userTreeList.get(i).getTree());
                }
            }
            this.notifyDataSetChanged();
        }
    }
}
