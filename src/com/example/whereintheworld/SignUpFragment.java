package com.example.whereintheworld;

import java.util.List;


import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link SignUpFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 *
 */
public class SignUpFragment extends Fragment {

	private OnSignupFragmentInteractionListener mListener;
	EditText f_name,l_name, email, password, cpassword;
	Button signup,cancel;
	public SignUpFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_sign_up, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnSignupFragmentInteractionListener) activity;
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
		
		f_name = (EditText) getActivity().findViewById(R.id.editTextFirstName);
		email = (EditText) getActivity().findViewById(R.id.editTextEmail);
		password = (EditText) getActivity().findViewById(R.id.editTextPassword);
		cpassword = (EditText) getActivity().findViewById(R.id.editTextPasswordConfirm);
		
		signup = (Button) getActivity().findViewById(R.id.buttonSignup);
		signup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String inFirstName = f_name.getText().toString();
				final String inemail = email.getText().toString();
				final String inpassword = password.getText().toString();
				String incpassword = cpassword.getText().toString();
				
				boolean inputValid = false;
				
				if(inFirstName == null || inFirstName.length() == 0)
				{
					inputValid = false;
					Toast.makeText(getActivity(), "Enter the name", Toast.LENGTH_SHORT).show();
				}
				else if (inemail == null || inemail.length() == 0) {
					inputValid = false;
					Toast.makeText(getActivity(), "Enter the email", Toast.LENGTH_SHORT).show();
				}
				else if (inpassword == null || inpassword.length() == 0) {
					inputValid = false;
					Toast.makeText(getActivity(), "Enter a password", Toast.LENGTH_SHORT).show();
				}
				else if (incpassword == null || incpassword.length() == 0) {
					inputValid = false;
					Toast.makeText(getActivity(), "Confirm password", Toast.LENGTH_SHORT).show();
				}
				else if(!inpassword.equals(incpassword) )
				{
					inputValid = false;
					Toast.makeText(getActivity(), "Passwords must match", Toast.LENGTH_SHORT).show();
				}
				else
				{
					inputValid = true;
				}
				
				if(inputValid)
				{
					ParseQuery<ParseUser> query = ParseUser.getQuery();
					query.whereEqualTo("email", inemail);
					query.findInBackground(new FindCallback<ParseUser>() {					 
					@Override
					public void done(List<ParseUser> users, ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
					        if((users == null) || (users != null && users.size() == 0) ){
					        	ParseUser user = new ParseUser();
								user.setUsername(inemail);
								user.setPassword(inpassword);
								user.setEmail(inemail);
								user.put("FirstName", inFirstName);
								
								user.signUpInBackground(new SignUpCallback() {
								  public void done(ParseException e) {
								    if (e == null) {
								    	
								    	ParseUser.logInInBackground(inemail, inpassword, new LogInCallback() {
								    		  public void done(ParseUser user, ParseException e) {
								    		    if (user != null) {
								    		    	Toast.makeText(getActivity(), "Login success... Redirecting to the apps page", Toast.LENGTH_SHORT).show();
								    		    	
													
													getFragmentManager().beginTransaction()
													.replace(R.id.container,new ToDoFragment(),"todo")
													.commit();
													
													
								    		    } else {
								    		    	Toast.makeText(getActivity(), "Login Failed.. Try logging in again", Toast.LENGTH_SHORT).show();
								    		    }
								    	
								    		  }
								    		});
								    } else {
								    	Toast.makeText(getActivity(), "Signup Failed.. Try signing up again", Toast.LENGTH_SHORT).show();
								    	e.printStackTrace();
								    }
								  }
								});
					        }
					    } else {
					    	Toast.makeText(getActivity(), "Email Id already exists...", Toast.LENGTH_SHORT).show();
					    }
					}
					});
					
				}
			} 
		});
		
		cancel = (Button) getActivity().findViewById(R.id.buttonCancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager().beginTransaction()
				.replace(R.id.container,new LoginFragment(),"login")
				.commit();
				
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
	public interface OnSignupFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onSignupFragmentInteraction(String username);
	}

}
