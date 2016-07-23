package tw.com.pointtree.pointtreeuser.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.api.ClientGenerator;
import tw.com.pointtree.pointtreeuser.api.PointTreeClient;
import tw.com.pointtree.pointtreeuser.api.response.AuthResponse;
import tw.com.pointtree.pointtreeuser.api.response.RegisterResponse;

public class PhoneAuthActivity extends AppCompatActivity {
    public static final String EXTRA_MOBILE_NUM = "tw.com.pointtree.EXTRA_MOBILE_NUM";
    public static final String EXTRA_PASSWORD = "tw.com.pointtree.EXTRA_PASSWORD";
    public static final String EXTRA_USER_NAME = "tw.com.pointtree.EXTRA_USER_NAME";
    public static final String EXTRA_BIRTHDAY = "tw.com.pointtree.EXTRA_BIRTHDAY";
    public static final String EXTRA_SEX = "tw.com.pointtree.EXTRA_SEX";
    public static final String EXTRA_NONCE = "tw.com.pointtree.EXTRA_NONCE";

    private String nonce;
    private PointTreeClient apiClient = ClientGenerator.createService(PointTreeClient.class);

    private Callback<AuthResponse> authCallback = new Callback<AuthResponse>() {
        @Override
        public void onResponse(retrofit2.Call<AuthResponse> call, retrofit2.Response<AuthResponse> response) {
            if (response.isSuccessful()) {
                finish();
            } else {
                // TODO: Change failure UI.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(PhoneAuthActivity.this, "Auth failed",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(retrofit2.Call<AuthResponse> call, Throwable t) {
            // TODO: Change failure UI.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(PhoneAuthActivity.this, "Auth failed",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    };
    private Callback<RegisterResponse> registerCallback = new Callback<RegisterResponse>() {
        @Override
        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
            if (response.isSuccessful()) {
                nonce = response.body().getNonce();
            } else {
                // TODO: handle failure.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(PhoneAuthActivity.this, "Resend failed",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<RegisterResponse> call, Throwable t) {
            // TODO: handle failure.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(PhoneAuthActivity.this, "Resend failed",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        nonce = getIntent().getStringExtra(EXTRA_NONCE);
        final EditText authCode = (EditText) findViewById(R.id.auth_code);

        Button authButton = (Button) findViewById(R.id.auth_code_button);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int code = Integer.parseInt(authCode.getText().toString());
                apiClient.authorizeCode(nonce, code).enqueue(authCallback);
            }
        });

        TextView sendAgainText = (TextView) findViewById(R.id.send_again);
        sendAgainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobileNember = getIntent().getStringExtra(EXTRA_MOBILE_NUM);
                String password = getIntent().getStringExtra(EXTRA_PASSWORD);
                String name = getIntent().getStringExtra(EXTRA_USER_NAME);
                String sex = getIntent().getStringExtra(EXTRA_SEX);
                String birthday = getIntent().getStringExtra(EXTRA_BIRTHDAY);
                apiClient.register(mobileNember, password, name, sex, birthday)
                        .enqueue(registerCallback);
            }
        });
    }
}
