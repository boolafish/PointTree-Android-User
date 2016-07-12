package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonSwipeViewPager extends ViewPager {

    public NonSwipeViewPager(Context context) {
        super(context);
    }

    public NonSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // not allow ViewPager to swipe
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // not allow ViewPager to swipe
        return false;
    }
}
