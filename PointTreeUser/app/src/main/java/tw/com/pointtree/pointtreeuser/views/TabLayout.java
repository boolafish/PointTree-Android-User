package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import tw.com.pointtree.pointtreeuser.activities.PointTreeActivity.SectionsPagerAdapter;


public class TabLayout extends android.support.design.widget.TabLayout {
    private ViewPager viewPager;

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setupWithViewPager(@Nullable final ViewPager viewPager) {
        super.setupWithViewPager(viewPager);
        this.viewPager = viewPager;

        populateTabIcons();

        // Change icon when selected.
        if (viewPager != null && viewPager.getAdapter() != null) {
            final SectionsPagerAdapter adapter = (SectionsPagerAdapter) viewPager.getAdapter();
            addOnTabSelectedListener(new OnTabSelectedListener() {
                @Override
                public void onTabSelected(Tab tab) {
                    tab.setIcon(adapter.getSelectedPageImage(tab.getPosition()));
                }

                @Override
                public void onTabUnselected(Tab tab) {
                    tab.setIcon(adapter.getPageImage(tab.getPosition()));
                }

                @Override
                public void onTabReselected(Tab tab) {
                }
            });
        }
    }

    private void populateTabIcons() {
        if (viewPager != null && viewPager.getAdapter() != null) {
            SectionsPagerAdapter adapter = (SectionsPagerAdapter) viewPager.getAdapter();
            for (int i = 0; i < this.getTabCount(); i++) {
                Tab t = this.getTabAt(i);
                if (viewPager.getCurrentItem() == i) {
                    t.setIcon(adapter.getSelectedPageImage(i));
                } else {
                    t.setIcon(adapter.getPageImage(i));
                }
            }
        }
    }
}
