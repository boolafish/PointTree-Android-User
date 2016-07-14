package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.api.models.User;

public class ConfirmUserFragment extends Fragment {
    private User user;

    private Button confirmedButton;
    private TextView notThisUserTextView;
    private TextView confirmUserTextView;

    private OnUserConfirmedListener userConfirmedListener;

    public ConfirmUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConfirmUserFragment.
     */
    public static ConfirmUserFragment newInstance(User user) {
        ConfirmUserFragment fragment = new ConfirmUserFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_user_layout, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            userConfirmedListener = (OnUserConfirmedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnUserConfirmedListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setConfirmUserView(view);

        confirmUserTextView.setText(user.getName());

        confirmedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set confirmed user and move to next page
                userConfirmedListener.onUserConfirmed();
            }
        });

        notThisUserTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // cancel the activity due to wrong user
                getActivity().finish();
            }
        });
    }

    public void setConfirmUserView(View view) {
        confirmedButton = (Button) view.findViewById(R.id.confirm_user_button);
        notThisUserTextView = (TextView) view.findViewById(R.id.not_this_user);
        confirmUserTextView = (TextView) view.findViewById(R.id.confirm_user);
    }

    public interface OnUserConfirmedListener {
        void onUserConfirmed();
    }
}
