package tw.com.pointtree.pointtreeuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.UserPreference;
import tw.com.pointtree.pointtreeuser.api.ClientGenerator;
import tw.com.pointtree.pointtreeuser.api.PointTreeClient;
import tw.com.pointtree.pointtreeuser.api.response.LoginResponse;
import tw.com.pointtree.pointtreeuser.api.response.RegisterResponse;

public class LoginActivity extends AppCompatActivity {
    private PointTreeClient apiClient = ClientGenerator.createService(PointTreeClient.class);

    private Callback<LoginResponse> loginCallback = new Callback<LoginResponse>() {
        @Override
        public void onResponse(Call<LoginResponse> call, final Response<LoginResponse> response) {
            if (response.isSuccessful()) {
                UserPreference userPreference = new UserPreference(LoginActivity.this);
                userPreference.setUserId(response.body().getUser().getId());
                userPreference.setUserToken(response.body().getToken());
                Intent intent = new Intent(LoginActivity.this, PointTreeActivity.class);
                startActivity(intent);
                finish();
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

        @Override
        public void onFailure(Call<LoginResponse> call, Throwable t) {
            // TODO: Implement this.
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
                apiClient.login(mobileNumber, password).enqueue(loginCallback);
            }
        });

        TextView registerButton = (TextView) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: forget password
            }
        });
    }
}
