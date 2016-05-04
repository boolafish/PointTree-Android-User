package tw.com.pointtree.pointtreeuser.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import tw.com.pointtree.pointtreeuser.R;

public abstract class TitledActivity extends AppCompatActivity {
    private String activityTitle;

    /**
     * Subclass should return the title of this activity by this method.
     * @return The title of the fragment.
     */
    @NonNull public abstract String getActivityTitle();

    /**
     * Called to set the content view below the title.
     * @param inflater A layout inflater.
     * @param container The parent view to hold the view. The view should not add itself to the
     *                  parent.
     * @return The content view.
     */
    @NonNull public abstract View onCreateContentView(LayoutInflater inflater,
                                                      ViewGroup container);

    public TitledActivity() {
        // Required empty public constructor
        this.activityTitle = getActivityTitle();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_bar_layout);
        FrameLayout contentView = (FrameLayout) findViewById(R.id.content_container);
        contentView.addView(onCreateContentView(LayoutInflater.from(this), contentView));

        TextView titleTextView = (TextView) findViewById(R.id.title_text_view);
        titleTextView.setText(activityTitle);
    }
}
