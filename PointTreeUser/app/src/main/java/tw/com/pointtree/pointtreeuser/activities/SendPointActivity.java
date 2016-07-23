package tw.com.pointtree.pointtreeuser.activities;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.api.models.User;
import tw.com.pointtree.pointtreeuser.fragments.ChooseStoreFragment;
import tw.com.pointtree.pointtreeuser.fragments.ConfirmUserFragment;
import tw.com.pointtree.pointtreeuser.fragments.SelectPointFragment;
import tw.com.pointtree.pointtreeuser.views.PagerContainer;

public class SendPointActivity extends TitledActivity implements
        ConfirmUserFragment.OnUserConfirmedListener,
        ChooseStoreFragment.OnStoreChosenListener,
        SelectPointFragment.OnPointSelectedListener {
    private User user;
    private String store;

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;

    @NonNull
    @Override
    public String getActivityTitle() {
        return "送點數給朋友";
    }

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_send_point, container, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // TODO: should handle if failed when passing user?
            user = (User) extras.getSerializable("user");
        }

        setView();
    }

    public void setView() {
        PagerContainer pagerContainer = (PagerContainer) findViewById(R.id.pager_container);
        viewPager = pagerContainer.getViewPager();

        fragmentPagerAdapter = new SendPointPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentPagerAdapter.getCount());
        viewPager.setPageMargin(40);
    }

    private ArrayList fetchUserStoreList() {
        // TODO: should use confirmed user to query store list
        ArrayList<String> storeList = new ArrayList<>();
        storeList.add("City Milk 城市首選 公館店");
        storeList.add("City Milk 城市首選 逢甲店");
        storeList.add("布萊恩紅茶 新北中和店");
        storeList.add("布萊恩紅茶 台中逢甲店");
        storeList.add("茶湯會 安居店");
        storeList.add("茶湯會 公館店");
        storeList.add("City Drink");

        return storeList;
    }

    private int fetchStoreAvailablePoint() {
        // TODO: should use chosen store to query available point number
        return 5;
    }

    @Override
    public void onUserConfirmed() {
        // pass the store list to ChooseStoreFragment
        ChooseStoreFragment chooseStoreFragment = (ChooseStoreFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.view_pager + ":1");
        chooseStoreFragment.fetchStoreList(fetchUserStoreList());

        viewPager.setCurrentItem(1);
    }

    @Override
    public void onStoreChosenListener(String store) {
        // pass the chosen store and the max available number to SelectPointFragment
        this.store = store;
        SelectPointFragment selectPointFragment = (SelectPointFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.view_pager + ":2");
        selectPointFragment.fetchChosenStore(store, fetchStoreAvailablePoint());

        viewPager.setCurrentItem(2);
    }

    @Override
    public void onPointSelectedListener(int number) {
        // TODO: perform sending transaction
    }

    public class SendPointPagerAdapter extends FragmentPagerAdapter {
        private static final int FRAGMENT_NUM = 3;

        public SendPointPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ConfirmUserFragment.newInstance(user);
                case 1:
                    return ChooseStoreFragment.newInstance();
                case 2:
                    return SelectPointFragment.newInstance();
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
