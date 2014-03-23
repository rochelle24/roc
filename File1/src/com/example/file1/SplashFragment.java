package com.example.file1;


import java.util.Arrays;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;

public class SplashFragment extends Fragment {

		private static final String TAG = "MainFragment";
		private UiLifecycleHelper uiHelper;
		private ProfilePictureView profilePictureView;
		private TextView userNameView;
		@Override
		public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
		{
		    View view = inflater.inflate(R.layout.splash, container, false);
		    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		    authButton.setFragment(this);
		    authButton.setReadPermissions(Arrays.asList("uid"));
		 // Find the user's profile picture custom view
			 profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
			 profilePictureView.setCropped(true);

			 // Find the user's name view
			 userNameView = (TextView) view.findViewById(R.id.selection_user_name);

		    return view;
		}
		
		private void makeMeRequest(final Session session) {
		    // Make an API call to get user data and define a 
		    // new callback to handle the response.
		    Request request = Request.newMeRequest(session, 
		            new Request.GraphUserCallback() {
		        @Override
		        public void onCompleted(GraphUser user, Response response) {
		            // If the response is successful
		            if (session == Session.getActiveSession()) {
		                if (user != null) {
		                    // Set the id for the ProfilePictureView
		                    // view that in turn displays the profile picture.
		                    profilePictureView.setProfileId(user.getId());
		                    // Set the Textview's text to the user's name.
		                    userNameView.setText(user.getName());
		                    Log.d("Debug","Setttig profile pic");
		                }
		            }
		            if (response.getError() != null) {
		                // Handle errors, will do so later.
		            }
		        }
		    });
		    request.executeAsync();
		} 
		
		private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		    if (state.isOpened()) {
		        Log.i(TAG, "Logged in...");
		        makeMeRequest(session);
		    } else if (state.isClosed()) {
		        Log.i(TAG, "Logged out...");
		    }
		}
		private Session.StatusCallback callback = new Session.StatusCallback() {
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		        onSessionStateChange(session, state, exception);
		    }
		};
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    uiHelper = new UiLifecycleHelper(getActivity(), callback);
		    uiHelper.onCreate(savedInstanceState);
		}
		
		@Override
		public void onResume() {
		    super.onResume();
		
		    
		    // For scenarios where the main activity is launched and user
		    // session is not null, the session state change notification
		    // may not be triggered. Trigger it if it's open/closed.
		    Session session = Session.getActiveSession();
		    if (session != null &&
		           (session.isOpened() || session.isClosed()) ) {
		        onSessionStateChange(session, session.getState(), null);
		    }

		    uiHelper.onResume();
		}
		

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		    super.onActivityResult(requestCode, resultCode, data);
		    uiHelper.onActivityResult(requestCode, resultCode, data);
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
		public void onSaveInstanceState(Bundle outState) {
		    super.onSaveInstanceState(outState);
		    uiHelper.onSaveInstanceState(outState);
		}
		
		
}

