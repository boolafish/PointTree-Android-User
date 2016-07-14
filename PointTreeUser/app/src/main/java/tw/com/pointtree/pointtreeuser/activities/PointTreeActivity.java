package tw.com.pointtree.pointtreeuser.activities;

import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.fragments.CardCollectionFragment;
import tw.com.pointtree.pointtreeuser.fragments.OverviewFragment;
import tw.com.pointtree.pointtreeuser.fragments.SettingFragment;
import tw.com.pointtree.pointtreeuser.fragments.SwipeableFragment;
import tw.com.pointtree.pointtreeuser.models.User;
import tw.com.pointtree.pointtreeuser.views.NonSwipeViewPager;
import tw.com.pointtree.pointtreeuser.views.TabLayout;

public class PointTreeActivity extends AppCompatActivity {
    public final static String EXTRA_USER = "tw.com.pointtree.pointtreeuser.USER";

    private User currentUser;

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

        currentUser = getIntent().getParcelableExtra(EXTRA_USER);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (NonSwipeViewPager) findViewById(R.id.container);
        viewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return OverviewFragment.newInstance();
                case 1:
                    return CardCollectionFragment.newInstance();
                case 4:
                    return SettingFragment.newInstance(currentUser);
            }
            return PlaceholderFragment.newInstance(position + 1);
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
                    return R.mipmap.ic_launcher;
                case 1:
                    return R.mipmap.ic_launcher;
                case 2:
                    return R.mipmap.ic_launcher;
                case 3:
                    return R.mipmap.ic_launcher;
                case 4:
                    return R.mipmap.ic_launcher;
            }
            return 0;
        }
    }
}
