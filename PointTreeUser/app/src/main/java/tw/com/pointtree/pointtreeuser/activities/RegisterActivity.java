package tw.com.pointtree.pointtreeuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.api.ClientGenerator;
import tw.com.pointtree.pointtreeuser.api.PointTreeClient;
import tw.com.pointtree.pointtreeuser.api.response.RegisterResponse;
import tw.com.pointtree.pointtreeuser.views.NonSwipeViewPager;

public class RegisterActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private NonSwipeViewPager viewPager;
    private PointTreeClient apiClient = ClientGenerator.createService(PointTreeClient.class);

    private String mobileNumber;
    private String password;
    private String userName;
    private String sex;
    private String birthday;

    private Callback<RegisterResponse> registerCallback = new Callback<RegisterResponse>() {
        @Override
        public void onResponse(Call<RegisterResponse> call, final Response<RegisterResponse> response) {
            if (response.isSuccessful()) {
                String nonce = response.body().getNonce();
                toPhoneAuthActivity(nonce);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(RegisterActivity.this, "Register failed",
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<RegisterResponse> call, Throwable t) {
            // TODO: Implement this.
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (NonSwipeViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(currentItem - 1);
        }
    }

    private void toStep2(RegisterStep1Fragment srcFragment) {
        mobileNumber = srcFragment.mobileNumber.getText().toString();
        password = srcFragment.password.getText().toString();
        viewPager.setCurrentItem(1);
    }

    private void completeRegister(RegisterStep2Fragment srcFragment) {
        userName = srcFragment.name.getText().toString();
        birthday = srcFragment.birthday.getText().toString();
        sex = "male";
        apiClient.register(mobileNumber, password, userName, sex, birthday).enqueue(registerCallback);
    }

    private void toPhoneAuthActivity(String nonce) {
        Intent intent = new Intent(this, PhoneAuthActivity.class);
        intent.putExtra(PhoneAuthActivity.EXTRA_MOBILE_NUM, mobileNumber);
        intent.putExtra(PhoneAuthActivity.EXTRA_PASSWORD, password);
        intent.putExtra(PhoneAuthActivity.EXTRA_USER_NAME, userName);
        intent.putExtra(PhoneAuthActivity.EXTRA_SEX, sex);
        intent.putExtra(PhoneAuthActivity.EXTRA_BIRTHDAY, birthday);
        intent.putExtra(PhoneAuthActivity.EXTRA_NONCE, nonce);
        startActivity(intent);
        finish();
    }

    public static class RegisterStep1Fragment extends Fragment {
        private Button nextStepButton;
        private EditText mobileNumber;
        private EditText password;
        private EditText passwordConfirm;

        public RegisterStep1Fragment() {
        }

        public static RegisterStep1Fragment newInstance() {
            RegisterStep1Fragment fragment = new RegisterStep1Fragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final RegisterActivity registerActivity = (RegisterActivity) getActivity();
            View rootView = inflater.inflate(R.layout.fragment_register_step1, container, false);

            nextStepButton = (Button) rootView.findViewById(R.id.nextStepButton);
            nextStepButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateFields()) {
                        registerActivity.toStep2(RegisterStep1Fragment.this);
                    }
                }
            });
            mobileNumber = (EditText) rootView.findViewById(R.id.mobileNumber);
            password = (EditText) rootView.findViewById(R.id.password);
            passwordConfirm = (EditText) rootView.findViewById(R.id.passwordConfirm);

            return rootView;
        }

        private boolean validateFields() {
            return validateMobileNumber() && validatePassword();
        }

        private boolean validateMobileNumber() {
            if (mobileNumber.getText().toString().equals("")) {
                Toast toast = Toast.makeText(getActivity(), R.string.register_invalid_mobile_toast,
                        Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
            return true;
        }

        private boolean validatePassword() {
            if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
                Toast toast = Toast.makeText(getActivity(),
                        R.string.register_invalid_password_toast,
                        Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
            return true;
        }
    }

    public static class RegisterStep2Fragment extends Fragment {
        private EditText name;
        private EditText birthday;
        private EditText sex;

        public RegisterStep2Fragment() {
        }

        public static RegisterStep2Fragment newInstance() {
            RegisterStep2Fragment fragment = new RegisterStep2Fragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final RegisterActivity registerActivity = (RegisterActivity) getActivity();

            View rootView = inflater.inflate(R.layout.fragment_register_step2, container, false);
            name = (EditText) rootView.findViewById(R.id.user_name);
            birthday = (EditText) rootView.findViewById(R.id.user_birthday);
            // TODO: get user sex.
            Button register = (Button) rootView.findViewById(R.id.confirmRegister);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: prevent double click.
                    registerActivity.completeRegister(RegisterStep2Fragment.this);
                }
            });
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return RegisterStep1Fragment.newInstance();
                case 1:
                    return RegisterStep2Fragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
