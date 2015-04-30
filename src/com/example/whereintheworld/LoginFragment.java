package com.example.whereintheworld;

import java.util.Arrays;
import java.util.List;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
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
//	private UiLifecycleHelper uiHelper;
//	
//	private final List<String> permissions;

	public LoginFragment() {
		//permissions = Arrays.asList("user_status");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//uiHelper = new UiLifecycleHelper(getActivity(), callback);
		//uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_login, container, false);
//		fbLogin = (LoginButton) view.findViewById(R.id.fb_login_button);
//		//fbLogin.setFragment(this); 
//		fbLogin.setReadPermissions(permissions);
		return view;
	}

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
					getFragmentManager()
							.beginTransaction()
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
	

		
		
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
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
