//Ram Prasad Narayanaswamy
//Anusha Srivastava
//Aaron Maisto
package com.example.whereintheworld;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, "KyN0frL71NYWbnDZbrUj5Hmhlo6KMP3nxm0EGM3t", "xUWYj2Iy8jlYAKZp9F2eWBOnkBocR6gv5bktuYjP");
		ParseFacebookUtils.initialize("KyN0frL71NYWbnDZbrUj5Hmhlo6KMP3nxm0EGM3t");
		//ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);

	}
}
