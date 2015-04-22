package com.example.whereintheworld;

import com.example.whereintheworld.LoginFragment;
import com.example.whereintheworld.SplashScreenFragment;
import com.example.whereintheworld.ItemDetailsFragment.OnItemDetailsFragmentInteractionListener;
import com.example.whereintheworld.LoginFragment.OnLoginFragmentInteractionListener;
import com.example.whereintheworld.SignUpFragment.OnSignupFragmentInteractionListener;
import com.example.whereintheworld.ToDoFragment.OnToDoFragmentInteractionListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements OnLoginFragmentInteractionListener,OnSignupFragmentInteractionListener,OnToDoFragmentInteractionListener,OnItemDetailsFragmentInteractionListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, new SplashScreenFragment()).commit();

			Handler handlerTimer = new Handler();
			
			handlerTimer.postDelayed(new Runnable(){
		        public void run() {
		        	getFragmentManager().beginTransaction()
					.replace(R.id.container, new LoginFragment(), "login").commit();            
		      }}, 5000);
			
			
			
		}
	}

	@Override
	public void onItemDetailsFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onToDoFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignupFragmentInteraction(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoginFragmentInteraction(String email) {
		// TODO Auto-generated method stub
		
	}
}
