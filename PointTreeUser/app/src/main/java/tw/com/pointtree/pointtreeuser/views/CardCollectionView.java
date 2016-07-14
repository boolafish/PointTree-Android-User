package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.api.models.Balance;

public class CardCollectionView extends LinearLayout {
    private Context context;
    private ImageView cardImageView;
    private TextView storeNameTextView;
    private TextView currentPointAmountTextView;
    private TextView exchangeDueTextView;
    private LinearLayout cardInfoLinearLayout;

    public CardCollectionView(Context context) {
        super(context);
        this.context = context;
        initialView();
    }

    private void initialView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_collection_view, this);

        cardImageView = (ImageView) this.findViewById(R.id.card_image);
        storeNameTextView = (TextView) this.findViewById(R.id.store_name);
        currentPointAmountTextView = (TextView) this.findViewById(R.id.current_point_amount);
        exchangeDueTextView = (TextView) this.findViewById(R.id.exchange_due);
        cardInfoLinearLayout = (LinearLayout) this.findViewById(R.id.card_info);
    }


    public void setData(Balance balance) {
        storeNameTextView.setText(balance.getPoint().getName());
        storeNameTextView.setTypeface(null, Typeface.BOLD); // not working on xml so hardcode here
        String currentPointAmountString = "目前點數: " + Integer.toString(balance.getBalance());
        currentPointAmountTextView.setText(currentPointAmountString);
        Picasso.with(context).load(balance.getPoint().getImgUrl())
                .error(R.drawable.ic_delete).into(cardImageView);
        // TODO put real time into string
        String exchangeDueString = "兌換期限：";
        exchangeDueTextView.setText(exchangeDueString);

    }
}
