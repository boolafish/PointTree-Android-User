package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class PagerContainer extends FrameLayout {
    private ViewPager viewPager;

    public PagerContainer(Context context) {
        super(context);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // Disable clipping of children so non-selected pages are visible
        setClipChildren(false);

        // Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
        // You need to set this value here if using hardware acceleration in an
        // application targeted at these releases.
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            viewPager = (ViewPager) getChildAt(0);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
