package com.project.andosyndicate;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TwitterWebviewActivity extends Activity {
    private Intent mIntent;

	@Override
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_webview);
       
        
        Log.i("TOKEN", "STARTED webactivity");
        
        mIntent = getIntent();
        String url = (String)mIntent.getExtras().get("URL");
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient( new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                if(url.contains( ConstantValues.TWITTER_CALLBACK_URL))
                {
                    Uri uri = Uri.parse( url );
                    String oauthVerifier = uri.getQueryParameter(ConstantValues.URL_PARAMETER_TWITTER_OAUTH_VERIFIER);
                    
                    Intent i=new Intent(getApplicationContext(), TwitterActivity.class);
                    i.putExtra("uri", oauthVerifier);
                    i.putExtra("start", 1);
                    startActivity(i);
                   finish();
                    return true;
                }
                return false;
            }
        });
        webView.loadUrl(url);
    }
}