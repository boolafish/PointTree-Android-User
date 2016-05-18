package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import tw.com.pointtree.pointtreeuser.R;

public class CollectionAdapter extends BaseAdapter {
    private static final int TOTAL_COLLECTION_NUMBER = 10;

    private Context context;
    private int collectionNum;

    public CollectionAdapter(Context context, int collectionNum) {
        this.context = context;
        this.collectionNum = collectionNum;
    }

    @Override
    public int getCount() {
        return TOTAL_COLLECTION_NUMBER;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }

        if (i < collectionNum) {
            // show collection image
            imageView.setImageResource(R.drawable.point_collected);
        } else {
            // show default image
            imageView.setImageResource(R.drawable.point_default);
        }

        return imageView;
    }
}
