package com.project.andosyndicate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.Animation;
import android.widget.ImageView;

public class FirstPage extends Activity {
	ImageView animImg;
	Animation anim;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_page);
			animImg = (ImageView)findViewById(R.id.applogo);
	        animImg.setBackgroundResource(R.animator.animation);
	        AnimationDrawable frameAnimation = (AnimationDrawable) animImg.getBackground();
	        frameAnimation.start();
		 Thread t=new Thread()
	        {
	            @Override
				public void run()
	            {   
	                try {
	                    sleep(5000);
	                    Intent cv=new Intent("com.project.andosyndicate.SecondPage");
	                    startActivity(cv);
	                } 
	                catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                finally{
	                	finish();
	                }
	            }
	        };
	        t.start();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
