package tw.com.pointtree.pointtreeuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.Session;
import tw.com.pointtree.pointtreeuser.api.ClientGenerator;
import tw.com.pointtree.pointtreeuser.api.PointTreeClient;
import tw.com.pointtree.pointtreeuser.api.models.User;
import tw.com.pointtree.pointtreeuser.api.response.ProfileResponse;
import tw.com.pointtree.pointtreeuser.fragments.CardCollectionFragment;
import tw.com.pointtree.pointtreeuser.fragments.OverviewFragment;
import tw.com.pointtree.pointtreeuser.fragments.SettingFragment;
import tw.com.pointtree.pointtreeuser.views.NonSwipeViewPager;
import tw.com.pointtree.pointtreeuser.views.TabLayout;

public class PointTreeActivity extends AppCompatActivity {
    private User currentUser;
    private Session session;

    private OverviewFragment overviewFragment;
    private CardCollectionFragment cardCollectionFragment;
    private SettingFragment settingFragment;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private NonSwipeViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (NonSwipeViewPager) findViewById(R.id.container);
        viewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        String userToken = session.getUserToken();
        if (userToken == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            String userId = session.getUserId();
            String token = session.getUserToken();
            fetchUserInfo(userId, token);
        }
    }

    private void fetchUserInfo(String userId, String userToken) {
        PointTreeClient client =
                ClientGenerator.createAuthorizedService(PointTreeClient.class, userToken);
        client.getProfile(userId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.code() == 200) {
                    currentUser = response.body().getUser();
                    // TODO: How to notify all fragment that user info is fetched?
                    if (settingFragment != null) {
                        settingFragment.setCurrentUser(currentUser);
                    }
                } else if (response.code() == 401) {
                    Intent intent = new Intent(PointTreeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // TODO:
            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public interface ImageTabAdapter {
        @DrawableRes
        int getPageImage(int position);
        @DrawableRes
        int getSelectedPageImage(int position);
    }

    /**
     * A FragmentPagerAdapter that returns a fragment corresponding to one of the tabs.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter implements ImageTabAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a RegisterStep1Fragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return OverviewFragment.newInstance();
                case 1:
                    return CardCollectionFragment.newInstance();
                case 4:
                    return SettingFragment.newInstance();
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        /**
         * Use instantiateItem to hook fragments to this activity.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            switch (position) {
                case 0:
                    overviewFragment = (OverviewFragment) fragment;
                    break;
                case 1:
                    cardCollectionFragment = (CardCollectionFragment) fragment;
                    break;
                case 4:
                    settingFragment = (SettingFragment) fragment;
                    settingFragment.setCurrentUser(currentUser);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public int getPageImage(int position) {
            switch (position) {
                case 0:
                    return R.drawable.tab_home;
                case 1:
                    return R.drawable.tab_card;
                case 2:
                    return R.drawable.tab_map;
                case 3:
                    return R.drawable.tab_dex;
                case 4:
                    return R.drawable.tab_setting;
            }
            return 0;
        }

        @Override
        public int getSelectedPageImage(int position) {
            switch (position) {
                case 0:
                    return R.drawable.tab_home_selected;
                case 1:
                    return R.drawable.tab_card_selected;
                case 2:
                    return R.drawable.tab_map_selected;
                case 3:
                    return R.drawable.tab_dex_selected;
                case 4:
                    return R.drawable.tab_setting_selected;
            }
            return 0;
        }
    }
}
