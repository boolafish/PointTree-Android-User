package tw.com.pointtree.pointtreeuser.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import tw.com.pointtree.pointtreeuser.R;

public abstract class TitledFragment extends Fragment {
    private String fragmentTitle;

    /**
     * Subclass should return the title of this fragment by this method.
     * @return The title of the fragment.
     */
    @NonNull public abstract String getFragmentTitle();

    /**
     * Called to set the content view below the title.
     * @param inflater A layout inflater.
     * @param container The parent view to hold the view. The view should not add itself to the
     *                  parent.
     * @return The content view.
     */
    @NonNull public abstract View onCreateContentView(LayoutInflater inflater,
                                                      ViewGroup container);

    public TitledFragment() {
        // Required empty public constructor
        this.fragmentTitle = getFragmentTitle();
    }

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_titled, container, false);

        TextView fragmentTitleView = (TextView) view.findViewById(R.id.fragment_title);
        fragmentTitleView.setText(fragmentTitle);

        FrameLayout contentContainer = (FrameLayout) view.findViewById(R.id.content_container);
        View contentView = onCreateContentView(inflater, contentContainer);
        contentContainer.addView(contentView);
        return view;
    }
}
