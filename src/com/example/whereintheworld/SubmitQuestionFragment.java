package com.example.whereintheworld;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.parse.Parse;
import com.parse.ParseObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the
 * {@link SubmitQuestionFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link SubmitQuestionFragment#newInstance}
 * factory method to create an instance of this fragment.
 *
 */
public class SubmitQuestionFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private static View view;
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private String selectedOption;
	private Continent selectedContinent;
	private Country selectedCountry;
	
	String questionTyped, answerTyped;
	
	boolean next;
	boolean validated = false;
	boolean correctAnswer;
	
	int regionID;
    EditText newQuestion;
    EditText newAnswer;
    Button validateButton, getButton;
    Button submitButton;
    GoogleMap mMap;
    LatLng focus, lastFocus;
    MapFragment mf;
    Spinner spinner,spinner1,spinner2;
    TextView tv,tv1;
    List<Address> target = new ArrayList<Address>();
    boolean validateStarted = true;
    
    private static final LatLng[] regions = new LatLng[]{
        new LatLng(17.416993, -97.826123),  // Americas
        new LatLng(56.621964, 8.873096),    // Europe
        new LatLng(31.821470, 56.085151),   // Middle-East
        new LatLng(4.951796, 15.453137),    // Africa
        new LatLng(20.277519, 120.840514)   // Southeast Asia
};

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment SubmitQuestionFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static SubmitQuestionFragment newInstance(String param1,
			String param2) {
		SubmitQuestionFragment fragment = new SubmitQuestionFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public SubmitQuestionFragment() {
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
		if (view != null) {
	        ViewGroup parent = (ViewGroup) view.getParent();
	        if (parent != null)
	            parent.removeView(view);
	    }
	    try {
	        view = inflater.inflate(R.layout.fragment_submit_question, container, false);
	    } catch (InflateException e) {
	         //map is already there, just return view as it is 
	    }
	    return view;
		//return inflater.inflate(R.layout.fragment_submit_question, container, false);
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
			mListener = (OnFragmentInteractionListener) activity;
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
		
		newQuestion = (EditText) getActivity().findViewById(R.id.newQuestion);
        newAnswer = (EditText) getActivity().findViewById(R.id.newAnswer);
        validateButton = (Button) getActivity().findViewById(R.id.buttonValidate);
        getButton = (Button) getActivity().findViewById(R.id.buttonGet);
        submitButton = (Button) getActivity().findViewById(R.id.submitButton);
        mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map1);
		spinner = (Spinner) getActivity().findViewById(R.id.spinner1);
		spinner1 = (Spinner) getActivity().findViewById(R.id.spinner2);
		tv = (TextView) getActivity().findViewById(R.id.textViewQueryTitle2);
		spinner2 = (Spinner) getActivity().findViewById(R.id.spinner3);
		tv1 = (TextView) getActivity().findViewById(R.id.textViewQueryTitle3);
		
		correctAnswer = false;
		
		
		newQuestion.setVisibility(EditText.INVISIBLE);
		newAnswer.setVisibility(EditText.INVISIBLE);
		validateButton.setVisibility(Button.INVISIBLE);
		getButton.setVisibility(Button.INVISIBLE);
		submitButton.setVisibility(Button.INVISIBLE);
		spinner1.setVisibility(Spinner.INVISIBLE);
		tv.setVisibility(TextView.INVISIBLE);
		spinner2.setVisibility(Spinner.INVISIBLE);
		tv1.setVisibility(TextView.INVISIBLE);
		
		String[] items = new String[]{"Continent", "Country"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, items);
		spinner.setAdapter(adapter);
		
		

		spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				newQuestion.setVisibility(EditText.INVISIBLE);
				newAnswer.setVisibility(EditText.INVISIBLE);
				validateButton.setVisibility(Button.INVISIBLE);
				getButton.setVisibility(Button.INVISIBLE);
				submitButton.setVisibility(Button.INVISIBLE);
				spinner1.setVisibility(Spinner.INVISIBLE);
				tv.setVisibility(TextView.INVISIBLE);
				spinner2.setVisibility(Spinner.INVISIBLE);
				tv1.setVisibility(TextView.INVISIBLE);
				
				selectedOption = parent.getItemAtPosition(position).toString();
				
				selectedContinent = null;
				selectedCountry = null;
				
				DBQuery.populateContinentList((Context) mListener, spinner1);
				spinner1.setVisibility(Spinner.VISIBLE);
				tv.setVisibility(TextView.VISIBLE);
				addListenerOnSpinner1ItemSelection();				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub				
			}
		});
		
		
		
		
		setUpMapIfNeeded();

        regionID = 0;
        

        focus = new LatLng(0,0);
        lastFocus = new LatLng(0,0);

        newAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				
			}
		});
        
        newAnswer.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				getButton.setClickable(true);
				getButton.setVisibility(Button.VISIBLE);
				validateButton.setVisibility(Button.INVISIBLE);
				return false;
			}
		});
        
        newAnswer.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				getButton.setClickable(true);
				getButton.setVisibility(Button.VISIBLE);
				validateButton.setVisibility(Button.INVISIBLE);
				return false;
			}
		});
        
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	correctAnswer = false;
            	Log.d("demo", correctAnswer+"");
            	mMap.clear();
            	if (newAnswer.getText() != null && newAnswer.getText().toString().length() != 0) {
                    next = true;
                	new Validater().execute(newAnswer.getText().toString());
                }
            	else{
            		newAnswer.setError("Please enter an answer!");
            	}
                
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Log.d("demo", correctAnswer+"");
                if (newQuestion.getText().toString().length() != 0 && newAnswer.getText().toString().length() != 0 && correctAnswer) {
                    questionTyped = newQuestion.getText().toString();
                    answerTyped = newAnswer.getText().toString();
                	Log.d("output", selectedOption+"...."+selectedContinent+"..."+selectedCountry+"..."+newQuestion.getText().toString()+"...."+newAnswer.getText().toString());
                    
                    ParseObject questionObject = new ParseObject("Question");
                    questionObject.put("questionType", selectedOption);
                    questionObject.put("continent",selectedContinent.getContinentName());
                    if(selectedOption.equalsIgnoreCase("Country")){
                    	questionObject.put("country", selectedCountry.getCountryName());
                    }                   
                    questionObject.put("question",questionTyped );
                    questionObject.put("answer",answerTyped);             
                    questionObject.saveInBackground();
                    
                    newQuestion.setText("");
                    newAnswer.setText("");
                    mMap.clear();
                    
                    //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("submit")).commit();
                    getFragmentManager().popBackStack();
                
                } else {
                    if (newQuestion.getText().toString().length() == 0) {
                        newQuestion.setError("Please enter a question!");
                    }
                    if (newAnswer.getText().toString().length() == 0) {
                        newAnswer.setError("Please enter an answer!");
                    }
                    if(!correctAnswer){
                    	newAnswer.setError("Answer not validated!");
                    }
                }
            }
        });
	}

	
	
	public void addListenerOnSpinner1ItemSelection() {
		spinner1.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectedContinent = DBQuery.continentList.get(position);
				Log.d("demo", selectedContinent.getContinentName());
				
				if(selectedOption.equals("Continent")){
					newQuestion.setVisibility(EditText.VISIBLE);
					newAnswer.setVisibility(EditText.VISIBLE);
					//validateButton.setVisibility(Button.VISIBLE);
					getButton.setVisibility(Button.VISIBLE);
					submitButton.setVisibility(Button.VISIBLE);
					//mf.setUserVisibleHint(true);
				}
				
				if(selectedOption.equals("Country")){					
					DBQuery.populateCountryList((Context) mListener, spinner2, position);
					tv1.setVisibility(TextView.VISIBLE);
					addListenerOnSpinner2ItemSelection();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				Log.d("demo", "Nothing selected");
			}
		});
	  }
	
	
	public void addListenerOnSpinner2ItemSelection() {
		spinner2.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectedCountry = DBQuery.countryList.get(position);
				Log.d("demo", selectedCountry.getCountryName());
				
				newQuestion.setVisibility(EditText.VISIBLE);
				newAnswer.setVisibility(EditText.VISIBLE);
				//validateButton.setVisibility(Button.VISIBLE);
				getButton.setVisibility(Button.VISIBLE);
				submitButton.setVisibility(Button.VISIBLE);
				//mf.setUserVisibleHint(true);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});		
	  }
	
	
	@Override
	public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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

	    return (MapFragment) fm.findFragmentById(R.id.map1);
	}

    private void setUpMapIfNeeded() {
        if (mMap == null) {
        	mMap = getMapFragment().getMap();
            //mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map1)).getMap();
        }
        if(mMap != null) {
            UiSettings mapUI = mMap.getUiSettings();
            mapUI.setMapToolbarEnabled(false);
            mapUI.setZoomControlsEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getMinZoomLevel()));
        }
    }

    private void moveToFocus(String countryName, LatLng focus) {
        mMap.clear();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(focus)
                .zoom(2)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, null);
        mMap.addMarker(new MarkerOptions().position(focus).title(countryName));
    }

    private class Validater extends AsyncTask<String, Void, List<Address>>
    {
        int nearestRegionID;
        String countryName;
        LatLng targetLatLng;

        public Validater() {
            nearestRegionID = 0;
            countryName = "";
            targetLatLng = new LatLng(0,0);
        }

        @Override
        protected List<Address> doInBackground(String... params) {

            Geocoder gc = new Geocoder(getActivity());
            //List<Address> target;
            target.clear();
            try {
                target = gc.getFromLocationName(params[0], 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("demo", target.size()+"");
            return target;
        }

        @Override
        protected void onPostExecute(final List<Address> target) {
            
        	validateButton.setVisibility(Button.VISIBLE);
        	validateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	Log.d("demo", correctAnswer+"");
                	mMap.clear();
                	Log.d("validate", "validate");
                    if(target.size() > 0){
                    	validated = false;
                    	Address addr = target.remove(0);
                    	
                        int j = 0;
                        while(addr.getAddressLine(j) != null){
                        	Log.d("addr", addr.getAddressLine(j));
                        	if(selectedOption.equalsIgnoreCase("Country")){                   		
                        		if(addr.getAddressLine(j).contains(selectedCountry.getCountryName())){
                        			correctAnswer = true;
                        			validated = true;
                        			countryName = (addr.getCountryName());
                                    targetLatLng = new LatLng(addr.getLatitude(), addr.getLongitude());
                        			break;
                        		}
                        	}
                        	if(selectedOption.equalsIgnoreCase("Continent")){
                        		correctAnswer = true;
                        		validated = true;
                    			countryName = (addr.getCountryName());
                                targetLatLng = new LatLng(addr.getLatitude(), addr.getLongitude());
                                break;
                        	}
                        	j++;
                        }
                        if(validated){
                        	Toast.makeText(getActivity(), "Answer exists and validated", Toast.LENGTH_SHORT).show();
                        	focus = targetLatLng;
                            if(focus != lastFocus) {
                                moveToFocus(countryName, focus);
                                lastFocus = focus;
                            }
                        }
                        else{
                        	Toast.makeText(getActivity(), "Answer not validated. Click Validate to check the next address", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                    	Toast.makeText(getActivity(), "No more validations.. Change answer or click Get and then click Validate again", Toast.LENGTH_LONG).show();
                    }
                    
                    
                }
            });
        	
        	//newAnswer.setText(countryName);
            //regionID = nearestRegionID;
            
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
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
