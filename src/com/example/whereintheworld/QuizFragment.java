package com.example.whereintheworld;


import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link QuizFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link QuizFragment#newInstance} factory method to create an instance of this
 * fragment.

 */
public class QuizFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;


	private OnQuizFragmentInteractionListener mListener;
	
	private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    public boolean hardmode;
    public int region;
    public String question;
    public String answerName;
    public LatLng answerLatLng;
    public int wrongAnswerCount;

    private TextView questionView;
    private TextView timerView;
    private CountDownTimer timer;

    private static final LatLng AMERICAS = new LatLng(17.416993, -97.826123);
    private static final LatLng EUROPE = new LatLng(56.621964, 8.873096);
    private static final LatLng MIDDLE_EAST = new LatLng(31.821470, 56.085151);
    private static final LatLng AFRICA = new LatLng(4.951796, 15.453137);
    private static final LatLng SOUTHEAST_ASIA = new LatLng(20.277519, 120.840514);


	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.

	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment QuizFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static QuizFragment newInstance(String param1, String param2) {
		QuizFragment fragment = new QuizFragment();
		Log.d("msg","Continent selected ...."+param1);
		Log.d("msg","Country selected ...."+param2);
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public QuizFragment() {
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
		return inflater.inflate(R.layout.fragment_quiz, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnQuizFragmentInteractionListener) activity;

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
		 questionView = (TextView) getActivity().findViewById(R.id.questionText);
	        timerView = (TextView) getActivity().findViewById(R.id.timerText);

	        hardmode = false;
	        region = 5;
	        question = "What is the most populous country in the world?";
	        answerName = "China";
	        answerLatLng = new LatLng(35, 103);

	        questionView.setText(question);

	        setUpMapIfNeeded();

	        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
				
				@Override
				public void onMapLongClick(LatLng point) {
					// TODO Auto-generated method stub
					new GetCountryFromClick().execute(point.latitude, point.longitude);
				}
			});
	}


	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        if(hardmode)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        switch (region) {
            case 0 :
                break;
            case 1 : mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AMERICAS, 0));
                break;
            case 2 : mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EUROPE, 3));
                break;
            case 3 : mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MIDDLE_EAST, 3));
                break;
            case 4 : mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AFRICA, 3));
                break;
            case 5 : mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SOUTHEAST_ASIA, 2));
                break;
            default :
                break;
        }

        if(timer == null) {
            timer = new CountDownTimer(31000, 1000) {

                public void onTick(long millisUntilFinished) {
                    timerView.setText(Long.toString(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    timerView.setText("0");
                    Toast.makeText(getActivity(), "You ran out of time!", Toast.LENGTH_LONG).show();
                }
            }.start();
        }
    }

    private void checkAnswer(String target) {

        if(answerName.equals(target)){
            Toast.makeText(getActivity(), "Correct, the answer was China!", Toast.LENGTH_LONG).show();
            moveToAnswer();
        }
        else
        {
            wrongAnswerCount++;

            if(wrongAnswerCount < 3)
            {
                Toast.makeText(getActivity(), "Wrong!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(), "Sorry, you ran out of guesses!", Toast.LENGTH_LONG).show();
                moveToAnswer();
            }
        }
    }

    private void moveToAnswer() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(answerLatLng)      // Sets the center of the map to answer coordinates
                .zoom(4)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.addMarker(new MarkerOptions()
                .position(answerLatLng)
                .title("Answer: " + answerName));
    }
    
    private class GetCountryFromClick extends AsyncTask<Double, Void, Void>
    {
        String targetName;

        public GetCountryFromClick() {
            targetName = "NOTHING";
        }

        @Override
        protected Void doInBackground(Double... params) {

            Geocoder gc = new Geocoder(getActivity());
            List<Address> target;

            try {
                target = gc.getFromLocation(params[0], params[1], 1);
                if(!target.isEmpty()){
                    targetName = (target.get(0).getCountryName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("Country Clicked", targetName);
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);

            checkAnswer(targetName);
        }
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

	public interface OnQuizFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}


}
