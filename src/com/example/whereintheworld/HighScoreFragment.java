package com.example.whereintheworld;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link HighScoreFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link HighScoreFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class HighScoreFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	TextView user1,user2,user3,score1,score2,score3;
	Button backButton;

	private OnHighScoreFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment HighScoreFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static HighScoreFragment newInstance(String param1, String param2) {
		HighScoreFragment fragment = new HighScoreFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public HighScoreFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_high_score, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.OnHighScoreFragmentInteractionListener(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnHighScoreFragmentInteractionListener) activity;
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

	
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		user1 = (TextView) getActivity().findViewById(R.id.textViewUser1);
		user2 = (TextView) getActivity().findViewById(R.id.textViewUser2);
		user3 = (TextView) getActivity().findViewById(R.id.textViewUser3);
		score1 = (TextView) getActivity().findViewById(R.id.textViewScore1);
		score2 = (TextView) getActivity().findViewById(R.id.textViewScore2);
		score3 = (TextView) getActivity().findViewById(R.id.textViewScore3);
		backButton = (Button) getActivity().findViewById(R.id.buttonBackScore);
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				getFragmentManager().popBackStack();
			}
		});
		
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.orderByDescending("score");
		query.findInBackground(new FindCallback<ParseUser>() {
		  public void done(List<ParseUser> arg0, ParseException e) {
		    if (e == null) {
		        // The query was successful.
		    	ParseUser Puser1 = arg0.get(0);
	            ParseUser Puser2 = arg0.get(1);
	            ParseUser Puser3 = arg0.get(2);
	            user1.setText(Puser1.getString("FirstName"));
	            user2.setText(Puser2.getString("FirstName"));
	            user3.setText(Puser3.getString("FirstName"));
	            score1.setText(Puser1.getInt("score")+"");
	            score2.setText(Puser2.getInt("score")+"");
	            score3.setText(Puser3.getInt("score")+"");  
		    } else {
		        // Something went wrong.
		    }
		  }
		});
		
		
		
		
	}



	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnHighScoreFragmentInteractionListener {
		// TODO: Update argument type and name
		public void OnHighScoreFragmentInteractionListener(Uri uri);
	}

}
