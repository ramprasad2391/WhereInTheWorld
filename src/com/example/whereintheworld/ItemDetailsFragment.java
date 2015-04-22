package com.example.whereintheworld;



import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ItemDetailsFragment extends Fragment {

    private static final String ARG_PARAM = "param";

    private String itemText;

    private OnItemDetailsFragmentInteractionListener mListener;

    public ItemDetailsFragment(String param) {
        /*ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);*/
    	this.itemText = param;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*if (getArguments() != null) {
            itemText = getArguments().getString(ARG_PARAM);
        }*/

        TextView tv = (TextView) getActivity().findViewById(R.id.textViewItemDetails);
        tv.setText(itemText);

        Button db = (Button) getActivity().findViewById(R.id.buttonDelete);

        db.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // code to call on ToDoList's delete goes here
            	
            }
        });
    }

    public ItemDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_details, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnItemDetailsFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnItemDetailsFragmentInteractionListener {
        public void onItemDetailsFragmentInteraction(Uri uri);
    }
}
