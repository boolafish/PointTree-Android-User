package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SegmentControlLayout extends RadioGroup {
    public SegmentControlLayout(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public SegmentControlLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    protected void onFinishInflate() {
        ((SegmentControl) getChildAt(0)).setChecked(true);
        super.onFinishInflate();
    }

    public static class SegmentControl extends RadioButton {
        private static final int[] STATE_FIRST = new int[] {android.R.attr.state_first};
        private static final int[] STATE_LAST = new int[] {android.R.attr.state_last};

        public SegmentControl(Context context, AttributeSet attrs) {
            super(context, attrs);
            setButtonDrawable(null);
        }

        @Override
        protected int[] onCreateDrawableState(int extraSpace) {
            ViewGroup parent = (ViewGroup) getParent();
            if (parent == null) {
                return super.onCreateDrawableState(extraSpace);
            }

            int count = parent.getChildCount();
            final boolean isFirst = (parent.getChildAt(0) == this);
            final boolean isLast = (parent.getChildAt(count - 1) == this);

            int[] states = super.onCreateDrawableState(extraSpace + 2);
            if (isFirst) {
                mergeDrawableStates(states, STATE_FIRST);
            } else if (isLast) {
                mergeDrawableStates(states, STATE_LAST);
            }

            return states;
        }
    }
}
