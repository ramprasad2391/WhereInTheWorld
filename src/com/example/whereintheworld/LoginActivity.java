package com.example.whereintheworld;

import java.util.Arrays;
import java.util.List;





import com.facebook.FacebookException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
//import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	EditText email, password;
	String inemail;
	Button createAccount, loginButton;
	LoginButton fbLogin;

	
	private UiLifecycleHelper uiHelper;
	private Context mActivity = this;

	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		callFacebookLogout(this);
		if(ParseUser.getCurrentUser() != null){
			ParseUser.logOut();
		}
		
		LoginButton authButton = (LoginButton) findViewById(R.id.fb_login_button);
		authButton.setReadPermissions(Arrays.asList("email"));
		/*ParseFacebookUtils.logIn(PERMISSIONS, this, new LogInCallback() {
			  @Override
			  public void done(ParseUser user, ParseException err) {
			    if (user == null) {
			      Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
			    } else if (user.isNew()) {
			      Log.d("MyApp", "User signed up and logged in through Facebook!");
			    } else {
			      Log.d("MyApp", "User logged in through Facebook!");
			    }
			  }
		});
		*/
		
		
		
		authButton.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(final GraphUser user) {
				if (user != null) {
					Log.d("fb", "Logged in..."+user.getName() + "...." + user.getUsername() + "....." + user.getProperty("email")
							);
					
					ParseQuery<ParseUser> query = ParseUser.getQuery();
					query.whereEqualTo("username", (String) user.getProperty("email"));
					query.findInBackground(new FindCallback<ParseUser>() {					 
					@Override
					public void done(List<ParseUser> users, ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
							Log.d("log", "Entering the parse loop");
					        if((users == null) || (users != null && users.size() == 0) ){
					        	Log.d("log", "Entering the new user loop");
					        	ParseUser Puser = new ParseUser();
					        	Puser.setUsername((String) user.getProperty("email"));
					        	Puser.setPassword("");
					        	Puser.setEmail((String) user.getProperty("email"));
					        	Puser.put("FirstName", user.getFirstName());
					        	Puser.put("LastName", user.getLastName());
					        	Puser.put("score", 0);
								
					        	Puser.signUpInBackground(new SignUpCallback() {
								  public void done(ParseException e) {
								    if (e == null) {
								    	
								    	ParseUser.logInInBackground((String) user.getProperty("email"), "", new LogInCallback() {
								    		  public void done(ParseUser user, ParseException e) {
								    		    if (user != null) {
								    		    	Toast.makeText(getApplicationContext(), "Login success... Redirecting to the apps page", Toast.LENGTH_SHORT).show();
								    		    	Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
								    		    	finish();								    		    	
								    		    	startActivity(intent);
								    		    } else {
								    		    	Toast.makeText(getApplicationContext(), "Login Failed.. Try logging in again", Toast.LENGTH_SHORT).show();
								    		    }
								    	
								    		  }
								    		});
								    } else {
								    	//Toast.makeText(getApplicationContext(), "Signup Failed.. Try signing up again", Toast.LENGTH_SHORT).show();
								    	//e.printStackTrace();
								    }
								  }
								});
					        }
					        else{
					        	ParseUser.logInInBackground((String) user.getProperty("email"), "", new LogInCallback() {
						    		  public void done(ParseUser user, ParseException e) {
						    		    if (user != null) {
						    		    	Toast.makeText(getApplicationContext(), "Login success... Redirecting to the apps page", Toast.LENGTH_SHORT).show();
						    		    	Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
						    		    	finish();								    		    	
						    		    	startActivity(intent);
						    		    } else {
						    		    	Toast.makeText(getApplicationContext(), "Login Failed.. Try logging in again", Toast.LENGTH_SHORT).show();
						    		    }
						    	
						    		  }
						    		});
					        }
					    } else {
					    	Log.d("log", "Entering the existing user loop");
					    	
					    }
					}
					});
				} else {
					Log.d("fb", "Logged out...");
					
				}
			}
		});
		
		
		if (ParseUser.getCurrentUser() == null) {
			email = (EditText) findViewById(R.id.editTextEmail);
			password = (EditText) findViewById(
					R.id.editTextPassword);

			createAccount = (Button) findViewById(
					R.id.buttonCreateNewAccount);
			createAccount.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
					startActivity(intent);
				}
			});

			loginButton = (Button) findViewById(R.id.buttonLogin);
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
										Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
										startActivity(intent);

										
									} else {
										Toast.makeText(mActivity,
												"Cannot login",
												Toast.LENGTH_SHORT).show();
									}
								}
							});
					// TODO Auto-generated method stub

				}
			});
		} 
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
	
	/**
	 * Logout From Facebook 
	 */
	public static void callFacebookLogout(Context context) {
	    Session session = Session.getActiveSession();
	    if (session != null) {

	        if (!session.isClosed()) {
	            session.closeAndClearTokenInformation();
	            //clear your preferences if saved
	        }
	    } /*else {

	        session = new Session(context);
	        Session.setActiveSession(session);

	        session.closeAndClearTokenInformation();
	            //clear your preferences if saved

	    }*/

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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
