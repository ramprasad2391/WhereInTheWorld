package com.example.whereintheworld;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.android.gms.internal.mp;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.app.Fragment;
import android.content.DialogInterface;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
	private static final String ARG_PARAM3 = "param3";
	private static View view;

	// TODO: Rename and change types of parameters
	private Continent mParam1;
	private Country mParam2;
	private String mParam3;

	private String continentName = null;
	private String countryName = null;

	private OnQuizFragmentInteractionListener mListener;
	
	private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    public boolean hardmode;
    public boolean next = true;
    public int region;
    public String question;
    public String answerName;
    public LatLng answerLatLng;
    public int wrongAnswerCount;
    public List<Question> questionList = new ArrayList<Question>();
    public List<Question> questionListTest = new ArrayList<Question>();
    private TextView questionView;
    private TextView timerView;
    private Button nextButton;
    private CountDownTimer timer;
    private LatLngBounds worldBounds = new LatLngBounds(new LatLng(-85, -180), new LatLng(85, 180));
    public int score;

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
	public QuizFragment(Continent param1, Country param2, String param3) {
		
		Log.d("msg","Continent selected ...."+param1);
		Log.d("msg","Country selected ...."+param2);
		mParam1 = param1;
		mParam2 = param2;
		mParam3 = param3;
		/*Bundle args = new Bundle();
		args.putSerializable(ARG_PARAM1, param1);
		args.putSerializable(ARG_PARAM2, param2);
		args.putString(ARG_PARAM3, param3);
		fragment.setArguments(args);*/
		
	}

	public QuizFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*if (getArguments() != null) {
			mParam1 = (Continent) getArguments().getSerializable(ARG_PARAM1);
			mParam2 = (Country) getArguments().getSerializable(ARG_PARAM2);
			mParam3 = getArguments().getString(ARG_PARAM3);
		}*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	    }
	    try {
	        view = inflater.inflate(R.layout.fragment_quiz, container, false);
	    } catch (InflateException e) {
	        /* map is already there, just return view as it is */
	    }
	    return view;
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
	     nextButton = (Button) getActivity().findViewById(R.id.buttonNext);
     	//Log.d("demo","Continent selected ...."+mParam1.getContinentName());
		//Log.d("demo","Country selected ...."+mParam2.getCountryName());
		Log.d("demo","Option selected ...."+mParam3);
        
		
		if(mParam1 != null){
			continentName = mParam1.getContinentName();
		}
		if(mParam2 != null){
			countryName = mParam2.getCountryName();
		}
		
		if(mParam1 != null || mParam2 != null){
        	ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");
	        query.whereEqualTo("questionType", mParam3);
	        query.whereEqualTo("continent", continentName);
	        query.whereEqualTo("country", countryName);
	        query.findInBackground(new FindCallback<ParseObject>() {
	            public void done(List<ParseObject> parseObjectList, ParseException e) {
	                if (e == null) {
	                    questionList.clear();
	                	Log.d("demo", "Retrieved " + parseObjectList.size() + " questions");
	                    Iterator<ParseObject> itr = parseObjectList.iterator();
			            while(itr.hasNext()){
			            	ParseObject obj = itr.next();
			            	Question q = new Question(obj.getInt("questionId"),obj.getString("question"),obj.getString("answer"));
			            	Log.d("demo",q.toString());
			            	questionList.add(q);
			            }
			            if(questionList.size() > 0){
			            	if(mParam3.equals("Country")){
			            		new GetLatLong().execute(countryName);
			            	}
			            	else{
			            		populateQuestionAndMap();
			            	}			            	
			            }
	                } else {
	                    Log.d("demo", "Error: " + e.getMessage());
	                }
	            }
	            
	        });
        }
        else{
        	ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");
        	query.findInBackground(new FindCallback<ParseObject>() {
	            public void done(List<ParseObject> parseObjectList, ParseException e) {
	                if (e == null) {
	                	questionList.clear();
	                	questionListTest.clear();
	                    Log.d("demo", "Retrieved " + parseObjectList.size() + " questions");
	                    Iterator<ParseObject> itr = parseObjectList.iterator();
	                    while(itr.hasNext()){
			            	ParseObject obj = itr.next();
			            	Question q = new Question(obj.getInt("questionId"),obj.getString("question"),obj.getString("answer"));
			            	Log.d("demo",q.toString());
			            	questionListTest.add(q);
			            }
	                    Collections.shuffle(questionListTest);
	                    
			            for(int i=0;i<5;i++){
			            	questionList.add(questionListTest.get(i));
			            }
	                    
	                    if(questionList.size() > 0){
			            	populateQuestionAndMap();			            				            	
			            }
	                } else {
	                    Log.d("demo", "Error: " + e.getMessage());
	                }
	            }
	        });
	        
	        nextButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					next = true;
                    populateQuestionAndMap();
				}
			});
	        
        }        
	}
	
	private MapFragment getMapFragment() {
	    FragmentManager fm = null;

	    Log.d("tag", "sdk: " + Build.VERSION.SDK_INT);
	    Log.d("tag", "release: " + Build.VERSION.RELEASE);

	    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
	        Log.d("tag", "using getFragmentManager");
	        fm = getFragmentManager();
	    } else {
	        Log.d("tag", "using getChildFragmentManager");
	        fm = getChildFragmentManager();
	    }

	    return (MapFragment) fm.findFragmentById(R.id.map);
	}

	private void populateQuestionAndMap(){
    	wrongAnswerCount = 1;
		next = true;    	
		if( questionList.size() > 0 && next){
			Question q = questionList.remove(0);
    		hardmode = false;
 	        next = false;
    		region = 5;
 	       	question = q.getQuestion();
 	        answerName = q.getAnswer();
 	        questionView.setText(question);
 	        new GetLatLongFromCountry().execute(answerName);
		}
		else if(questionList.size()==0 && next)
		{
			ParseUser currentUser = ParseUser.getCurrentUser();
			currentUser.increment("score", score);
			currentUser.saveInBackground();
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
			builder.setTitle("Total Score")
			.setMessage("Your score is: "+score)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					//GameSettingsFragment.comingFromDifferentFragment = true;
					getFragmentManager().beginTransaction()
					.replace(R.id.container,new MenuFragment(),"menu")
					.commit();
				}
			});
			
			final AlertDialog alert = builder.create();
			alert.show();
		}
	}
	
	

	private void setUpMapIfNeeded(Continent selectedContinent, Country selectedCountry, String selectedOption) {
        // Do a null check to confirm that we have not already instantiated the map.
            // Try to obtain the map from the SupportMapFragment.
		mMap = getMapFragment().getMap();    
		//mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
       if(mMap != null) {
                UiSettings mapUI = mMap.getUiSettings();
                mapUI.setMapToolbarEnabled(false);
                mapUI.setZoomControlsEnabled(true);
                //mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getMinZoomLevel()));
            }
        if (mMap != null) {
                setUpMap(selectedContinent, selectedCountry, selectedOption);
            }
        
    }

    private void setUpMap(Continent selectedContinent, Country selectedCountry, String selectedOption) {

        if(hardmode)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if(selectedOption.equals("Continent")){
        	Log.d("latlng",selectedContinent.getLatLong()+"");
        	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedContinent.getLatLong(), 3));
        }
        if(selectedOption.equals("Country")){
        	mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedCountry.getLatLong(), 4));
        }
        if(selectedOption.equals("World")){
        	mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(worldBounds, 3));
        }
        
            timer = new CountDownTimer(51000, 1000) {

                public void onTick(long millisUntilFinished) {
                    timerView.setText(Long.toString(millisUntilFinished / 1000));
                }

                public void onFinish() {
                    timerView.setText("0");
                    Toast.makeText(getActivity(), "You ran out of time! Moving to next", Toast.LENGTH_LONG).show();
                    next = true;
                    populateQuestionAndMap();
                }
            }.start();
        
    }

    private void checkAnswer(String target) {

        if(target.contains(answerName)){
        	score++;
        	Toast.makeText(getActivity(), "Correct, the answer is "+answerName, Toast.LENGTH_LONG).show();
            moveToAnswer();
        }
        else
        {
        	Toast.makeText(getActivity(), "Wrong! Moving to next", Toast.LENGTH_SHORT).show();
        	 next = true;
             populateQuestionAndMap();
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
    
    private class GetCountryFromClick extends AsyncTask<Double, Void, String>
    {
        String targetName;
        StringBuffer sbr = new StringBuffer();

        public GetCountryFromClick() {
            targetName = "NOTHING";
        }

        @Override
        protected String doInBackground(Double... params) {

            Geocoder gc = new Geocoder(getActivity());
            List<Address> target;

            try {
                target = gc.getFromLocation(params[0], params[1], 1);
                if(!target.isEmpty()){
                    Address addr = target.get(0);
                	//targetName = (target.get(0).getCountryName());
                    int i = 0;
                	while(addr.getAddressLine(i) != null){
                		Log.d("add", addr.getAddressLine(i));
                		sbr.append(addr.getAddressLine(i));
                		i++;
                    }
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("Country Clicked", targetName);
            return sbr.toString();
        }

        @Override
        protected void onPostExecute(String targetName) {

            checkAnswer(targetName);
        }
    }
    
    private class GetLatLongFromCountry extends AsyncTask<String, Void, LatLng>
    {
        LatLng pos;

        public GetLatLongFromCountry() {
            pos = null;
        }

        @Override
        protected LatLng doInBackground(String... params) {

            Geocoder gc = new Geocoder(getActivity());
            List<Address> target;

            try {
                target = gc.getFromLocationName(params[0], 1);
                if(!target.isEmpty()){
                    Address a = target.get(0);
                    pos = new LatLng(a.getLatitude(),a.getLongitude());                	
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            
            return pos;
        }

        @Override
        protected void onPostExecute(LatLng pos) {
            //super.onPostExecute(pos);
            answerLatLng = pos;
            Log.d("demo", answerLatLng.toString());
 	        setUpMapIfNeeded(mParam1,mParam2,mParam3);
 	        
 	        nextButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					timer.cancel();
					next = true;
                   populateQuestionAndMap();
				}
			});
 	        
 	        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
				
				@Override
				public void onMapLongClick(LatLng point) {
					// TODO Auto-generated method stub
					timer.cancel();
					new GetCountryFromClick().execute(point.latitude, point.longitude);
				}
			});
        }
    }

    private class GetLatLong extends AsyncTask<String, Void, LatLng>
    {
        LatLng pos;

        public GetLatLong() {
            pos = null;
        }

        @Override
        protected LatLng doInBackground(String... params) {

            Geocoder gc = new Geocoder(getActivity());
            List<Address> target;

            try {
                target = gc.getFromLocationName(params[0], 1);
                if(!target.isEmpty()){
                    Address a = target.get(0);
                    pos = new LatLng(a.getLatitude(),a.getLongitude());                	
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("demo", pos.toString());
            return pos;
        }

        @Override
        protected void onPostExecute(LatLng pos) {
            //super.onPostExecute(pos);
            mParam2.setLatLong(pos);
            //Log.d("demo", answerLatLng.toString());
            populateQuestionAndMap();
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
