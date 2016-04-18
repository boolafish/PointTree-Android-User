package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import tw.com.pointtree.pointtreeuser.activities.PointTreeActivity.SectionsPagerAdapter;


public class TabLayout extends android.support.design.widget.TabLayout {
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
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);

        if (viewPager != null) {
            SectionsPagerAdapter adapter = (SectionsPagerAdapter) viewPager.getAdapter();
            if (adapter != null) {
                populateTabIcons(adapter);
            }
        }
    }

    private void populateTabIcons(SectionsPagerAdapter adapter) {
        for (int i = 0; i < this.getTabCount(); i++) {
            Tab t = this.getTabAt(i);
            t.setIcon(adapter.getPageImage(i));
        }
    }
}
