package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.activities.SendPointActivity;
import tw.com.pointtree.pointtreeuser.api.models.User;

public class UserQRcodeQueryFragment extends Fragment {

    public UserQRcodeQueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserQRcodeQueryFragment.
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final UserQRcodeQueryFragment userQRcodeQueryFragment = this;

        Button scanButton = (Button) view.findViewById(R.id.scan_qrcode);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // scan qrcode
                IntentIntegrator.forSupportFragment(userQRcodeQueryFragment)
                        .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                        .setBeepEnabled(false)
                        .setBarcodeImageEnabled(true)
                        .initiateScan();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            if (scanResult.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + scanResult.getContents(), Toast.LENGTH_LONG).show();
                // TODO: should call API to query user info with this decoded message
                User user = User.getSampleUser();

                Intent intent = new Intent(getActivity(), SendPointActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
