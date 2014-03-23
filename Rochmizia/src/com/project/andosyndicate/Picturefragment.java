
package com.project.andosyndicate;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.project.andosyndicate.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Picturefragment extends Fragment {

	ImageButton profilePictureButton;
	TextView userNameView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.act4, container, false);
		profilePictureButton = (ImageButton) view.findViewById(R.id.selection_profile_pic);
		userNameView = (TextView) view.findViewById(R.id.selection_user_name);
		setUserData(Session.getActiveSession());
		return view;
	}

	public void onSessionStateChange(Session session, SessionState state, Exception exception) {
		setUserData(session);
	}

	private void setUserData(final Session session) {
		if (session == null) {
			Log.e("ProfileFragment", "session is null!!!!");
		}
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if (session == Session.getActiveSession()) {
					if (user != null && profilePictureButton != null && userNameView != null) {
						new ProfilePictureDownloadTask(profilePictureButton, getActivity().getResources()).execute(user.getId());
						userNameView.setText(user.getName());
					}
				}
				if (response != null && response.getError() != null) {
					//handleError(response.getError());
				}
			}
		});
		request.executeAsync();

	}
}
