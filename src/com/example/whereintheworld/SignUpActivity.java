package com.example.whereintheworld;

import java.util.List;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {
	EditText f_name,l_name, email, password, cpassword;
	Button signup,cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		f_name = (EditText) findViewById(R.id.editTextFirstName);
		email = (EditText) findViewById(R.id.editTextEmail);
		password = (EditText) findViewById(R.id.editTextPassword);
		cpassword = (EditText) findViewById(R.id.editTextPasswordConfirm);
		
		signup = (Button) findViewById(R.id.buttonSignup);
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
					Toast.makeText(getApplicationContext(), "Enter the name", Toast.LENGTH_SHORT).show();
				}
				else if (inemail == null || inemail.length() == 0) {
					inputValid = false;
					Toast.makeText(getApplicationContext(), "Enter the email", Toast.LENGTH_SHORT).show();
				}
				else if (inpassword == null || inpassword.length() == 0) {
					inputValid = false;
					Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_SHORT).show();
				}
				else if (incpassword == null || incpassword.length() == 0) {
					inputValid = false;
					Toast.makeText(getApplicationContext(), "Confirm password", Toast.LENGTH_SHORT).show();
				}
				else if(!inpassword.equals(incpassword) )
				{
					inputValid = false;
					Toast.makeText(getApplicationContext(), "Passwords must match", Toast.LENGTH_SHORT).show();
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
								    		    	Toast.makeText(getApplicationContext(), "Login success... Redirecting to the apps page", Toast.LENGTH_SHORT).show();
								    		    	
													Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
													startActivity(intent);
													
								    		    } else {
								    		    	Toast.makeText(getApplicationContext(), "Login Failed.. Try logging in again", Toast.LENGTH_SHORT).show();
								    		    }
								    	
								    		  }
								    		});
								    } else {
								    	Toast.makeText(getApplicationContext(), "Signup Failed.. Try signing up again", Toast.LENGTH_SHORT).show();
								    	e.printStackTrace();
								    }
								  }
								});
					        }
					    } else {
					    	Toast.makeText(getApplicationContext(), "Email Id already exists...", Toast.LENGTH_SHORT).show();
					    }
					}
					});
					
				}
			} 
		});
		
		cancel = (Button) findViewById(R.id.buttonCancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
