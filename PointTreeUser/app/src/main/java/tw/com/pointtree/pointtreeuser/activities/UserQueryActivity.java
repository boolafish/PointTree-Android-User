package tw.com.pointtree.pointtreeuser.activities;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.fragments.UserIdQueryFragment;
import tw.com.pointtree.pointtreeuser.fragments.UserMobileNumberQueryFragment;
import tw.com.pointtree.pointtreeuser.fragments.UserQRcodeQueryFragment;

public class UserQueryActivity extends TitledActivity {

    @NonNull
    @Override
    public String getActivityTitle() {
        return "送點數給朋友";
    }

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_user_query, container, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSendPointView();
    }

    public void setSendPointView() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("QRcode"));
        tabLayout.addTab(tabLayout.newTab().setText("朋友 ID"));
        tabLayout.addTab(tabLayout.newTab().setText("電話號碼"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentPagerAdapter fragmentPagerAdapter = new UserQueryPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // remain empty
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // remain empty
            }
        });
    }

    private static class UserQueryPagerAdapter extends FragmentPagerAdapter {
        private static int FRAGMENT_NUM = 3;

        public UserQueryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return UserQRcodeQueryFragment.newInstance();
                case 1:
                    return UserIdQueryFragment.newInstance();
                case 2:
                    return UserMobileNumberQueryFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return FRAGMENT_NUM;
        }
    }
}
