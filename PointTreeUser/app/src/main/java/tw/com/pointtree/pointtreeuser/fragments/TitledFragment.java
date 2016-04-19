package tw.com.pointtree.pointtreeuser.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import tw.com.pointtree.pointtreeuser.R;

public class TitledFragment extends Fragment {
    private static final String PARAM_FRAGMENT_TITLE = "fragmentTitle";
    private String fragmentTitle;

    public TitledFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param fragmentTitle The title to display for this fragment.
     * @return A new instance of fragment TitledFragment.
     */
    public static TitledFragment newInstance(String fragmentTitle) {
        TitledFragment fragment = new TitledFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_FRAGMENT_TITLE, fragmentTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragmentTitle = getArguments().getString(PARAM_FRAGMENT_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_titled, container, false);

        TextView fragmentTitleView = (TextView) view.findViewById(R.id.fragment_title);
        fragmentTitleView.setText(fragmentTitle);

        FrameLayout contentContainer = (FrameLayout) view.findViewById(R.id.content_container);
        View contentView = onCreateContentView(inflater, contentContainer);
        if (contentView != null) {
            contentContainer.addView(contentView);
        }
        return view;
    }

    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }
}
