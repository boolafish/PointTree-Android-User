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

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.activities.CardDetailActivity;
import tw.com.pointtree.pointtreeuser.activities.SearchActivity;

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
        Button tempButton = (Button) view.findViewById(R.id.temp_button);

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        // TODO: should remove this in the future
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CardDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
