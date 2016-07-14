package tw.com.pointtree.pointtreeuser.fragments;

public interface SwipeableFragment {
    void setOnSwipeListener(OnSwipeListener onSwipeListener);

    interface OnSwipeListener {
        void onSwipeStart();

        void onSwipeEnd();
    }
}
