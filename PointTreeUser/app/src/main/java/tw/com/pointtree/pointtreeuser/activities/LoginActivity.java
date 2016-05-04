package tw.com.pointtree.pointtreeuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import tw.com.pointtree.pointtreeuser.APIClient;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.models.APIObject;
import tw.com.pointtree.pointtreeuser.models.User;

public class LoginActivity extends AppCompatActivity {

    private Callback loginCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            // TODO: Implement this.
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                try {
                    User user = new User(response);
                    Intent intent = new Intent(LoginActivity.this, PointTreeActivity.class);
                    intent.putExtra(PointTreeActivity.EXTRA_USER, user);
                    startActivity(intent);
                    finish();
                } catch (APIObject.APIObjectException e) {
                    // TODO: Show error UI.
                    e.printStackTrace();
                }
            } else {
                // TODO: Change failure UI.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(LoginActivity.this, "Login failed",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }
    };

    private Callback registerCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            // TODO: Implement this.
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            // TODO: Use other UI.
            if (response.isSuccessful()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(LoginActivity.this, "Register succeed",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(LoginActivity.this, "Register failed",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                Log.d("", "onResponse: " + response.body().string());
                response.body().close();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView mobileNumberView = (TextView) findViewById(R.id.mobileNumber);
        final TextView passwordView = (TextView) findViewById(R.id.password);

        // TODO: prevent double clicking.
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = mobileNumberView.getText().toString();
                String password = passwordView.getText().toString();
                new APIClient().login(mobileNumber, password, loginCallback);
            }
        });

        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNumber = mobileNumberView.getText().toString();
                String password = passwordView.getText().toString();
                new APIClient().register(mobileNumber, password, registerCallback);
            }
        });
    }
}
