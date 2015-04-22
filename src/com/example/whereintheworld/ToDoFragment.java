package com.example.whereintheworld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ToDoFragment.OnFragmentInteractionListener}
 * interface to handle interaction events.
 *
 */
public class ToDoFragment extends Fragment {

	private OnToDoFragmentInteractionListener mListener;
	String username;
	ProgressDialog dialog;
	ArrayList<String> items;

	public ToDoFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_to_do, container, false);
	}

	public void getUserName(String username) {
		this.username = username;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnToDoFragmentInteractionListener) activity;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		/*
		 * apps.clear();
		 * 
		 * AppAdapter adapter = new
		 * AppAdapter(AppsActivity.this,R.layout.row_item_layout,apps);
		 * //ArrayAdapter<Story> adapter = new
		 * ArrayAdapter<Story>(StoriesActivity
		 * .this,android.R.layout.simple_list_item_1,result);
		 * ll.setAdapter(adapter);
		 */
		items = new ArrayList<String>();
		final ListView ll = (ListView) getActivity().findViewById(
				R.id.listViewItems);
		if (ll.getChildCount() > 0) {
			ll.removeViews(0, ll.getChildCount());
		}

		dialog = new ProgressDialog(getActivity());
		dialog.setMessage("Loading Results...");
		dialog.show();

		ParseUser currentUser = ParseUser.getCurrentUser();
		String currentUserName = currentUser.getUsername();

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
		query.whereEqualTo("Username", currentUserName);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> list, ParseException e) {
				// TODO Auto-generated method stub
				if (e == null) {
					if (list != null && list.size() != 0) {
						Iterator<ParseObject> itr = list.iterator();
						while (itr.hasNext()) {
							ParseObject obj = itr.next();
							items.add(obj.getString("Title"));
						}
						if (dialog.isShowing()) {
							dialog.dismiss();
						}

						if (items != null) {

							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									getActivity(),
									android.R.layout.simple_list_item_1, items);
							adapter.setNotifyOnChange(true);
							ll.setAdapter(adapter);
							
							ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									// TODO Auto-generated method stub
									getFragmentManager().beginTransaction()
									.replace(R.id.container,new ItemDetailsFragment(items.get(position)),"details")
									.commit();
								}
							});
						} else {
							Toast.makeText(getActivity(), "No Items for this user",
									Toast.LENGTH_SHORT).show();
						}

					} else {
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
						Toast.makeText(getActivity(), "No Items for this user",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					if (dialog.isShowing()) {
						dialog.dismiss();
					}
					Log.d("favorites", "Error: " + e.getMessage());
				}
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
	public interface OnToDoFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onToDoFragmentInteraction(Uri uri);
	}

}
