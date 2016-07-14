package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.views.StoreListAdapter;

public class ChooseStoreFragment extends Fragment {
    private String chosenStore;

    private TextWatcher textWatcher;
    private EditText chosenStoreEditText;
    private ListView storeListView;
    private Button confirmStoreButton;

    private OnStoreChosenListener storeChosenListener;

    private StoreListAdapter storeListAdapter;

    public ChooseStoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChooseStoreFragment.
     */
    public static ChooseStoreFragment newInstance() {
        ChooseStoreFragment fragment = new ChooseStoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_store_layout, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            storeChosenListener = (OnStoreChosenListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStoreChosenListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setChooseStoreView(view);
        setTextWatcher();

        // add text watcher to EditText
        chosenStoreEditText.addTextChangedListener(textWatcher);
        chosenStoreEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeListView.setItemChecked(0, true);
            }
        });

        storeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // add selection background color
                storeListView.setSelector(R.color.colorStoreSelected);
                // enable button after selecting store
                confirmStoreButton.setEnabled(true);
                // set chosen store
                chosenStore = ((TextView) view).getText().toString();
            }
        });

        confirmStoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set chosen store and move to next page
                storeChosenListener.onStoreChosenListener(chosenStore);
            }
        });
    }

    public void setChooseStoreView(View view) {
        chosenStoreEditText = (EditText) view.findViewById(R.id.chosen_store);
        storeListView = (ListView) view.findViewById(R.id.store_list);
        confirmStoreButton = (Button) view.findViewById(R.id.confirm_store);
    }

    public void setTextWatcher() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                storeListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // remain empty
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // remove selection background color
                storeListView.setSelector(R.color.white);
                // disable button when typing
                confirmStoreButton.setEnabled(false);
            }
        };
    }

    public void fetchStoreList(ArrayList<String> storeList) {
        // set ListView adapter
        storeListAdapter = new StoreListAdapter(storeList, getActivity());
        storeListView.setAdapter(storeListAdapter);
    }

    public interface OnStoreChosenListener {
        void onStoreChosenListener(String store);
    }
}
