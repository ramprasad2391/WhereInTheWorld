package com.example.whereintheworld;

import com.example.whereintheworld.GameSettingsFragment.OnGameSettingsFragmentInteractionListener;
import com.example.whereintheworld.HighScoreFragment.OnHighScoreFragmentInteractionListener;
import com.example.whereintheworld.MenuFragment.OnMenuFragmentInteractionListener;
import com.example.whereintheworld.MyProfileFragment.OnMyProfileFragmentInteractionListener;
import com.example.whereintheworld.QuizFragment.OnQuizFragmentInteractionListener;
import com.example.whereintheworld.SubmitQuestionFragment.OnFragmentInteractionListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends Activity implements OnHighScoreFragmentInteractionListener, OnMyProfileFragmentInteractionListener,OnFragmentInteractionListener,OnMenuFragmentInteractionListener,OnGameSettingsFragmentInteractionListener,OnQuizFragmentInteractionListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
				.add(R.id.container, new MenuFragment()).commit();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
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

	@Override
	public void onGameSettingsFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnMyProfileFragmentInteractionListener(Uri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnHighScoreFragmentInteractionListener(Uri uri) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(getFragmentManager().getBackStackEntryCount() > 0){
			getFragmentManager().popBackStack();
		}
		else{
			super.onBackPressed();
		}
		
	}
	
	
}
