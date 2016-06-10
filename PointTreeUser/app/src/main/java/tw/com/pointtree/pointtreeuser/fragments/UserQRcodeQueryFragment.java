package tw.com.pointtree.pointtreeuser.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.com.pointtree.pointtreeuser.R;

public class UserQRcodeQueryFragment extends Fragment {

    public UserQRcodeQueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChooseFriendWithQRcode.
     */
    public static UserQRcodeQueryFragment newInstance() {
        UserQRcodeQueryFragment fragment = new UserQRcodeQueryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_qrcode_query, container, false);
    }
}
