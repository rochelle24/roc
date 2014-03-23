package com.project.andosyndicate;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TwitterActivity extends Activity {
	
	Button buttonUpdateStatus, buttonLogout;
	EditText editTextStatus;
	TextView textViewStatus, textViewUserName;


@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.twitter);
    
}
}