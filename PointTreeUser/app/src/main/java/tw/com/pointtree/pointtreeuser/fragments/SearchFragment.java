package tw.com.pointtree.pointtreeuser.fragments;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.com.pointtree.pointtreeuser.R;

public class SearchFragment extends TitledFragment {
    public SearchFragment() {
    }

    @NonNull
    @Override
    public String getFragmentTitle() {
        return "搜尋集點卡或店家";
    }

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
