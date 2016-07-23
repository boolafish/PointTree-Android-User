package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.activities.SendPointActivity;
import tw.com.pointtree.pointtreeuser.api.models.User;

public class UserMobileNumberQueryFragment extends Fragment {

    public UserMobileNumberQueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserMobileNumberQueryFragment.
     */
    public static UserMobileNumberQueryFragment newInstance() {
        UserMobileNumberQueryFragment fragment = new UserMobileNumberQueryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_mobile_number_query, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText userMobileNumber = (EditText) view.findViewById(R.id.user_mobile_number);
        userMobileNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                if (action == EditorInfo.IME_ACTION_SEND) {
                    // TODO: should call API to query user info with user mobile number
                    User user = User.getSampleUser();

                    Intent intent = new Intent(getActivity(), SendPointActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}
