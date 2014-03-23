package com.project.andosyndicate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.auth.RequestToken;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.android.FacebookError;


@SuppressLint("NewApi")
public class SecondPage extends Activity {
	
	ImageView buttonLogin;
	
	private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";
	
	 private ImageView facebook;
	    private Session.StatusCallback statusCallback = new SessionStatusCallback();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_page);
		facebook = (ImageView)findViewById(R.id.facebook);
		
		

		Session session = Session.getActiveSession();
        if (session == null) {
           if (savedInstanceState != null) {
               session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
           }
           if (session == null) {
               session = new Session(this);
           }  
           
           Session.setActiveSession(session);
           if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
              session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        	  
           }
           if(session!= null && (session.isOpened()||session.isClosed()))
           {
        	   onSessionStateChange(session,session.getState(),null);
           }
       }
    	
		TextView mytext = (TextView) findViewById(R.id.skip);
		
		mytext.setOnClickListener(new OnClickListener() {
			
			@Override
			
			public void onClick(View v) {

                Intent intent = new Intent(SecondPage.this, ThirdPage.class);
                startActivity(intent);
			}
		});

		updateView();
       
       initializeComponent();
	}
	
	
	
	
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }
	
	
	 private void updateView() {
	        Session session = Session.getActiveSession();
	        if (session.isOpened()) {
	       
	          
	          
	           facebook.setOnClickListener(new OnClickListener() {
	                @Override
					public void onClick(View view) { onClickLogout();
	                }
	            });
	        } else {
	           
	            facebook.setRight(R.string.facebook);
	           
	            facebook.setOnClickListener(new OnClickListener() {
	                @Override
					public void onClick(View view) { onClickLogin(); }
	            });
	        }
	    }
	 
	 
	 private void onClickLogin() {
	        Session session = Session.getActiveSession();
	        if (!session.isOpened() && !session.isClosed()) {
	            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));     
	        } else {
	            Session.openActiveSession(this, true, statusCallback);
	        }
	    }

	    private void onClickLogout() {
	        Session session = Session.getActiveSession();
	        if (!session.isClosed()) {
	            session.closeAndClearTokenInformation();
	        }
	    }

	    private class SessionStatusCallback implements Session.StatusCallback {
	        @Override
	        public void call(Session session, SessionState state, Exception exception) {
	            onSessionStateChange(session, state, exception);
	     
	        }
	    }
	        
	    private void onSessionStateChange(Session session,SessionState state,Exception exception)
		{
	    	if(state.isOpened())
			{
				
	            Intent intent = new Intent(SecondPage.this, ThirdPage.class);
	            startActivity(intent);
	        }
			else if(state.isClosed())
			{
				session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
			}
		}
	    private void initializeComponent() {
	        buttonLogin = (ImageView) findViewById(R.id.twitter);

	        buttonLogin.setOnClickListener(buttonLoginOnClickListener);
	    }
	    
	    private View.OnClickListener buttonLoginOnClickListener = new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {

	            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	            if (!sharedPreferences.getBoolean(ConstantValues.PREFERENCE_TWITTER_IS_LOGGED_IN,false))
	            {
	                new TwitterAuthenticateTask().execute();
	            }
	            else
	            {
	                Intent intent = new Intent(SecondPage.this, TwitterActivity.class);
	                startActivity(intent);
	            }

	        }
	    };

	    class TwitterAuthenticateTask extends AsyncTask<String, String, RequestToken> {

	        @Override
	        protected void onPostExecute(RequestToken requestToken) {
	        	String url=requestToken.getAuthenticationURL().toString();
	            Intent intent = new Intent(SecondPage.this,TwitterWebviewActivity.class);
	            intent.putExtra("URL", url);
	            startActivity(intent);
	        }

	        @Override
	        protected RequestToken doInBackground(String... params) {
	            return TwitterUtil.getInstance().getRequestToken();
	        }
	    }

}
