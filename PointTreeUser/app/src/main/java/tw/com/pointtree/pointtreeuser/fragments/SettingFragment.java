package tw.com.pointtree.pointtreeuser.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.Session;
import tw.com.pointtree.pointtreeuser.activities.LoginActivity;
import tw.com.pointtree.pointtreeuser.api.ClientGenerator;
import tw.com.pointtree.pointtreeuser.api.PointTreeClient;
import tw.com.pointtree.pointtreeuser.api.models.Transaction;
import tw.com.pointtree.pointtreeuser.api.response.ProfileResponse;
import tw.com.pointtree.pointtreeuser.api.response.TransactionResponse;
import tw.com.pointtree.pointtreeuser.dialog.ChangeUserNameDialog;
import tw.com.pointtree.pointtreeuser.views.PointRecordListAdapter;

import static tw.com.pointtree.pointtreeuser.UriToFilePathUtils.getPath;

public class SettingFragment extends TitledFragment implements ChangeUserNameDialog.OnUserUpdatedListener {
    private static int PICK_IMAGE_REQUEST = 1928;
    private Session session;
    private ViewHolder viewHolder;

    private Callback<TransactionResponse> txCallback = new Callback<TransactionResponse>() {
        @Override
        public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
            if (response.isSuccessful()) {
                List<Transaction> transactions = response.body().getTransactions();
                viewHolder.getPointRecordListView().setAdapter(
                        new PointRecordListAdapter(getContext(), transactions, session.getUser()));
            }
        }

        @Override
        public void onFailure(Call<TransactionResponse> call, Throwable t) {

        }
    };

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingFragment.
     */
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @NonNull
    @Override
    public String getFragmentTitle() {
        return "帳戶設定";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        session = new Session(context);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        PointTreeClient client = session.getClient();
        client.getTransactions(session.getUserId()).enqueue(txCallback);

        viewHolder = new ViewHolder(view);
        setUserInfoFromSession();
        setButtonControl();
    }

    private void setUserInfoFromSession() {
        if (viewHolder != null) {
            TextView nameTextView = viewHolder.getNameTextView();
            nameTextView.setText(session.getUser().getName());

            TextView idTextView = viewHolder.getIdTextView();
            idTextView.setText(String.format("%d", session.getUser().getUserNumber()));

            ImageView userImageView = viewHolder.getUserImageView();
            OkHttpClient client = ClientGenerator.createAuthorizedOkHttpClient(session.getUserToken());
            Picasso picasso = new Picasso.Builder(getContext())
                    .downloader(new OkHttp3Downloader(client))
                    .build();

            String imageUrl = ClientGenerator.getApiBaseUrlString() + session.getUser().getImgUrl();
            picasso.load(imageUrl)
                    .placeholder(R.drawable.account) // TODO use real placeholder for this
                    .fit()
                    .centerCrop()
                    .into(userImageView);
        }
    }

    private void setButtonControl() {
        final Button pointRecordButton = viewHolder.getPointRecordButton();
        final Button accountSettingButton = viewHolder.getAccountSettingButton();
        final ListView pointRecordListView = viewHolder.getPointRecordListView();
        final ScrollView accountSettingScrollView = viewHolder.getAccountSettingScrollView();
        final LinearLayout logoutButton = viewHolder.getLogoutButton();
        final ImageButton changeNameButton = viewHolder.getChangeNameButton();
        final ImageView userImageClickView = viewHolder.getUserImageClickView();

        pointRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointRecordListView.setVisibility(View.VISIBLE);
                accountSettingScrollView.setVisibility(View.GONE);
                pointRecordButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightTeal));
                accountSettingButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.duckEggBlue));
                pointRecordButton.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                accountSettingButton.setTextColor(ContextCompat.getColor(getContext(), R.color.seafoamBlue));
            }
        });

        accountSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountSettingScrollView.setVisibility(View.VISIBLE);
                pointRecordListView.setVisibility(View.GONE);
                pointRecordButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.duckEggBlue));
                accountSettingButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightTeal));
                pointRecordButton.setTextColor(ContextCompat.getColor(getContext(), R.color.seafoamBlue));
                accountSettingButton.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session session = new Session(getActivity());
                session.clearSession();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        final Fragment fragment = this;
        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeUserNameDialog changeUserNameDialog = new ChangeUserNameDialog();
                changeUserNameDialog.setTargetFragment(fragment, 0);
                changeUserNameDialog.show(getFragmentManager(), "changeUserNameDialog");
            }
        });

        userImageClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Image Click", Toast.LENGTH_SHORT).show();
                showFileChooser();
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();

            File file = new File(getPath(getContext(), fileUri));

            RequestBody photo = RequestBody.create(MediaType.parse("application/image"), file);
            RequestBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", file.getName(), photo)
                    .build();

            session.getClient().updateProfile(session.getUserId(), body).enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    Log.d("RESPONSE", "wow");
                    if (response.isSuccessful()) {
                        session.setUser(response.body().getUser());
                        setUserInfoFromSession();
                    } else {
                        Toast toast = Toast.makeText(getContext(), "change user image failed", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onUserUpdated() {
        setUserInfoFromSession();
    }

    private class ViewHolder {
        TextView nameTextView;
        TextView idTextView;
        ImageView userImageView;
        ListView pointRecordListView;
        Button pointRecordButton;
        Button accountSettingButton;
        ScrollView accountSettingScrollView;
        LinearLayout logoutButton;
        ImageButton changeNameButton;
        ImageView userImageClickView;

        public ViewHolder(View view) {
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            idTextView = (TextView) view.findViewById(R.id.idTextView);
            userImageView = (ImageView) view.findViewById(R.id.userImageView);
            pointRecordListView = (ListView) view.findViewById(R.id.pointRecordListVIew);
            pointRecordButton = (Button) view.findViewById(R.id.pointRecordButton);
            accountSettingButton = (Button) view.findViewById(R.id.accountSettingButton);
            accountSettingScrollView = (ScrollView) view.findViewById(R.id.accountSettingScrollView);
            logoutButton = (LinearLayout) view.findViewById(R.id.logout);
            changeNameButton = (ImageButton) view.findViewById(R.id.changeNameButton);
            userImageClickView = (ImageView) view.findViewById(R.id.userImageClickView);
        }

        public ListView getPointRecordListView() {
            return pointRecordListView;
        }

        public ImageView getUserImageView() {
            return userImageView;
        }

        public TextView getIdTextView() {
            return idTextView;
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public Button getAccountSettingButton() {
            return accountSettingButton;
        }

        public Button getPointRecordButton() {
            return pointRecordButton;
        }

        public ImageButton getChangeNameButton() {
            return changeNameButton;
        }

        public LinearLayout getLogoutButton() {
            return logoutButton;
        }

        public ScrollView getAccountSettingScrollView() {
            return accountSettingScrollView;
        }

        public ImageView getUserImageClickView() {
            return userImageClickView;
        }
    }
}
