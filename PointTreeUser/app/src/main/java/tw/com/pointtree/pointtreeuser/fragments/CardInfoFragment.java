package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.views.CollectionAdapter;

public class CardInfoFragment extends Fragment {
    private static final int COLLECTION_PER_FRAGMENT = 10;
    private int COLLECTION_NUM = 15;    // TODO: this value should be passed from server

    public CardInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CardInfoFragment.
     */
    public static CardInfoFragment newInstance() {
        CardInfoFragment fragment = new CardInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // maintain an ArrayList to record the collection number in each GridView
        ArrayList<Integer> collectionNum = new ArrayList<>();

        int totalCollectionNum = COLLECTION_NUM;
        while (totalCollectionNum > 0) {
            collectionNum.add(Math.min(COLLECTION_PER_FRAGMENT, totalCollectionNum));
            totalCollectionNum -= 10;
        }

        // set viewpager and its indicator
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.collection_view_pager);
        viewPager.setAdapter(new CollectionPagerAdapter(getActivity(), collectionNum));
        CirclePageIndicator circleIndicator = (CirclePageIndicator) view.findViewById(R.id.circle_indicator);
        circleIndicator.setViewPager(viewPager);
    }

    private static class CollectionPagerAdapter extends PagerAdapter {
        private Context context;
        private ArrayList<Integer> collectionNum;

        public CollectionPagerAdapter(Context context, ArrayList<Integer> collectionNum) {
            this.context = context;
            this.collectionNum = collectionNum;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            int collectionNum = this.collectionNum.get(position);

            LayoutInflater inflater = LayoutInflater.from(context);
            GridView gridView = (GridView) inflater.inflate(R.layout.collection_view, collection, false);
            gridView.setAdapter(new CollectionAdapter(context, collectionNum));
            collection.addView(gridView);

            return gridView;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return collectionNum.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
