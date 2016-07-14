package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class StoreListAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ValueFilter valueFilter;
    private ArrayList<String> storeList;
    private ArrayList<String> filteredStoreList;

    public StoreListAdapter(ArrayList<String> storeList, Context context) {
        this.storeList = storeList;
        this.filteredStoreList = storeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return storeList.size();
    }

    @Override
    public Object getItem(int i) {
        return storeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        if (view == null) {
            textView = new TextView(context);
            textView.setTextSize(20);
            textView.setPadding(16, 20, 16, 20);
        } else {
            textView = (TextView) view;
        }

        textView.setText(storeList.get(i));

        return textView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            if (charSequence != null && charSequence.length() > 0) {
                ArrayList<String> filterList = new ArrayList<>();
                for (int i = 0; i < filteredStoreList.size(); i++){
                    if (filteredStoreList.get(i).contains(charSequence)) {
                        filterList.add(filteredStoreList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = filteredStoreList.size();
                results.values = filteredStoreList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            storeList = (ArrayList<String>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
