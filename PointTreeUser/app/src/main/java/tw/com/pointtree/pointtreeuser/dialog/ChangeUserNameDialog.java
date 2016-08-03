package tw.com.pointtree.pointtreeuser.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.Session;
import tw.com.pointtree.pointtreeuser.api.PointTreeClient;
import tw.com.pointtree.pointtreeuser.api.models.User;
import tw.com.pointtree.pointtreeuser.api.response.ProfileResponse;

public class ChangeUserNameDialog extends DialogFragment {
    private Context context;
    private Session session;
    private OnUserUpdatedListener onUserUpdatedListener;
    private Callback<ProfileResponse> changeNameCallback = new Callback<ProfileResponse>() {

        @Override
        public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
            if (response.isSuccessful()) {
                session.setUser(response.body().getUser());

                if (onUserUpdatedListener != null) {
                    onUserUpdatedListener.onUserUpdated();
                }
            } else {
                Toast toast = Toast.makeText(context, "change user name failed", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        @Override
        public void onFailure(Call<ProfileResponse> call, Throwable t) {

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        session = new Session(context);
        try {
            onUserUpdatedListener = (OnUserUpdatedListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement Callback interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.change_name_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText nameEditText = (EditText) view.findViewById(R.id.nameEditText);
                        String newName = nameEditText.getText().toString();
                        User currentUser = session.getUser();
                        PointTreeClient client = session.getClient();
                        client.updateProfile(currentUser.getId(), newName)
                                .enqueue(changeNameCallback);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        return builder.create();
    }


    public interface OnUserUpdatedListener {
        void onUserUpdated();
    }

}
