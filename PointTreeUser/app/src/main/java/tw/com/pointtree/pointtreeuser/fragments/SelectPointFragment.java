package tw.com.pointtree.pointtreeuser.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import tw.com.pointtree.pointtreeuser.R;

public class SelectPointFragment extends Fragment {
    private int selectedPoint;

    private TextView storeNameText;
    private TextView availableNumberText;
    private Spinner selectPointSpinner;
    private Button confirmButton;

    private OnPointSelectedListener pointSelectedListener;

    public SelectPointFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectPointFragment.
     */
    public static SelectPointFragment newInstance() {
        SelectPointFragment fragment = new SelectPointFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_point_layout, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            pointSelectedListener = (OnPointSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStoreChosenListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSelectPointView(view);

        selectPointSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                confirmButton.setEnabled(true);
                selectedPoint = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // remain empty
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pointSelectedListener.onPointSelectedListener(selectedPoint);
            }
        });
    }

    private void setSelectPointView(View view) {
        storeNameText = (TextView) view.findViewById(R.id.store_name);
        selectPointSpinner = (Spinner) view.findViewById(R.id.select_point_spinner);
        availableNumberText = (TextView) view.findViewById(R.id.available_number);
        confirmButton = (Button) view.findViewById(R.id.confirm_and_send);
    }

    public void fetchChosenStore(String store, int number) {
        // set the chosen store and available point number related view
        storeNameText.setText(store);
        availableNumberText.setText(String.valueOf(number));

        ArrayList<Integer> numberList = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            numberList.add(i);
        }
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, numberList);
        selectPointSpinner.setAdapter(spinnerAdapter);
    }

    public interface OnPointSelectedListener {
        void onPointSelectedListener(int number);
    }
}
