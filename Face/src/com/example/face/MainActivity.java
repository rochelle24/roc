package com.example.face;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.facebook.*;
import com.facebook.model.*;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView t = (TextView)findViewById(R.id.welcome);
		Button b = (Button)findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				facebookThing();
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}
	
	public void facebookThing()
	{
		 // start Facebook Login
		  Session.openActiveSession(this, true, new Session.StatusCallback() {
			  
		    // callback when session changes state
		    @SuppressWarnings("deprecation")
			@Override
		    public void call(Session session, SessionState state, Exception exception) {

		    	Log.d("lalala", "lalalal");
		    	Log.d("Session",session.toString());
		    	//Toast.makeText(this, session.toString(), Toast.LENGTH_LONG);
		    	if (session.isOpened()) {
		    		// make request to the /me API
		    		Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

		    		  // callback after Graph API response with user object
		    		  @Override
		    		  public void onCompleted(GraphUser user, Response response) {
		    			  if (user != null) {
		    				  TextView welcome = (TextView) findViewById(R.id.welcome);
		    				  welcome.setText("Hello " + user.getName() + "!");
		    				  
		    				}
		    		  }
		    		});
		    	}
		    }
		  });
	}



}
