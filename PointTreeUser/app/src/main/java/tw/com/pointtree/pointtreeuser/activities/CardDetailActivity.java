package tw.com.pointtree.pointtreeuser.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.fragments.CardInfoFragment;
import tw.com.pointtree.pointtreeuser.fragments.StoreInfoFragment;
import tw.com.pointtree.pointtreeuser.views.SegmentControlLayout;

public class CardDetailActivity extends TitledActivity {
    private CardInfoFragment cardInfoFragment;
    private StoreInfoFragment storeInfoFragment;

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_card_detail, container, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCardDetailView();
    }

    @NonNull
    @Override
    public String getActivityTitle() {
        return "集點卡";
    }

    private void setCardDetailView() {
        initFragment();

        SegmentControlLayout segmentControlLayout = (SegmentControlLayout) findViewById(R.id.segment_control_group);
        segmentControlLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int viewId) {
                if (viewId == R.id.card_info) {
                    replaceFragment(cardInfoFragment);
                } else if (viewId == R.id.store_info) {
                    replaceFragment(storeInfoFragment);
                }
            }
        });
    }

    private void initFragment() {
        // initiate two fragments
        cardInfoFragment = CardInfoFragment.newInstance();
        storeInfoFragment = StoreInfoFragment.newInstance();

        // show card info fragment by default
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, cardInfoFragment).commit();
    }

    private void replaceFragment(Fragment newFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.commit();
    }
}
