package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import tw.com.pointtree.pointtreeuser.R;


public class CardCollectionSeparatorView extends LinearLayout {
    private Context context;
    private TextView categoryTextView;

    public CardCollectionSeparatorView(Context context) {
        super(context);
        this.context = context;
        initialView();
    }

    private void initialView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_collection_seperator_view, this);

        categoryTextView = (TextView) this.findViewById(R.id.category);
    }

    public void setCategory(String categoryString) {
        categoryTextView.setText(categoryString);
        categoryTextView.setTypeface(null, Typeface.BOLD); // not working on xml so hardcode here
    }
}
