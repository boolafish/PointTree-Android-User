package tw.com.pointtree.pointtreeuser.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import tw.com.pointtree.pointtreeuser.R;

public class StoreInfoFragment extends Fragment {
    public StoreInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StoreInfoFragment.
     */
    public static StoreInfoFragment newInstance() {
        StoreInfoFragment fragment = new StoreInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addStorePreference(view);
    }

    private void addStorePreference(View view) {
        // TODO: should use real data in the future
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.last_content_layout);

        TextView storePreference = new TextView(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 0, 0, 0);
        storePreference.setLayoutParams(layoutParams);
        storePreference.setBackgroundResource(R.drawable.store_preference);
        storePreference.setText("滿200可外送");

        linearLayout.addView(storePreference);
    }
}
