package com.example.whereintheworld;

import java.util.Arrays;
import java.util.List;




//import com.example.facebookapp.R;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link LoginFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 *
 */
public class LoginFragment extends Fragment {

	private OnLoginFragmentInteractionListener mListener;
	EditText email, password;
	String inemail;
	Button createAccount, loginButton;
	LoginButton fbLogin;
	


//	private static final String TAG = LoginFragment.class.getSimpleName();
//
	private UiLifecycleHelper uiHelper;

	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

	public LoginFragment() {
		//permissions = Arrays.asList("user_status");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		
		//View view = (LinearLayout) inflater.inflate(R.layout.activity_facebook, container, false);
		LoginButton authButton = (LoginButton) view.findViewById(R.id.fb_login_button);
		//authButton.setFragment(this);
		authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"/*, "publish_actions"*/));
		
		return view;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        Log.d("fb", "Logged in...");
			
	    } else if (state.isClosed()) {
	        Log.d("fb", "Logged out...");
			
	    }
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnLoginFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		
		
		if (ParseUser.getCurrentUser() == null) {
			email = (EditText) getActivity().findViewById(R.id.editTextEmail);
			password = (EditText) getActivity().findViewById(
					R.id.editTextPassword);

			createAccount = (Button) getActivity().findViewById(
					R.id.buttonCreateNewAccount);
			createAccount.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					getFragmentManager().beginTransaction()
							.replace(R.id.container, new SignUpFragment(),
									"signup").commit();
				}
			});

			loginButton = (Button) getActivity().findViewById(R.id.buttonLogin);
			loginButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					inemail = email.getText().toString();
					String inpassword = password.getText().toString();

					ParseUser.logInInBackground(inemail, inpassword,
							new LogInCallback() {

								@Override
								public void done(ParseUser user,
										ParseException e) {
									if (user != null) {
										getFragmentManager()
												.beginTransaction()
												.replace(R.id.container,
														new MenuFragment(),
														"menu").commit();

										
									} else {
										Toast.makeText(getActivity(),
												"Cannot login",
												Toast.LENGTH_SHORT).show();
									}
								}
							});
					// TODO Auto-generated method stub

				}
			});
		} else {
			Log.d("User", ParseUser.getCurrentUser().getUsername());
			inemail = ParseUser.getCurrentUser().getUsername();

			

			getFragmentManager().beginTransaction()
					.replace(R.id.container, new MenuFragment(), "menu")
					.commit();

			
		}
		
		/*fbLogin = (LoginButton) getActivity().findViewById(R.id.fb_login_button);
		fbLogin.setReadPermissions(Arrays.asList("email"));
		fbLogin.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					Log.d("fb","You are currently logged in as " + user.getName());
				} else {
					Log.d("fb","You are not logged in.");
				}
			}
		});*/

		
		
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data,new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}
	

	@Override
	public void onResume() {
		super.onResume();
		Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }

	    uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnLoginFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onLoginFragmentInteraction(String email);
	}

}
