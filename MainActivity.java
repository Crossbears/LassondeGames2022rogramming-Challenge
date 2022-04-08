package com.luckman.oscstickerhunt;

import androidx.appcompat.app.AppCompatActivity; 
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore; 
import android.view.View;
import android.webkit.ValueCallback; 
import android.webkit.WebChromeClient; 
import android.webkit.WebSettings; 
import android.webkit.WebView;
import android.net.Uri;
import android.webkit.WebViewClient; 
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton; 

public class MainActivity extends AppCompatActivity {
//Camera Button 

FloatingActionButton camera;

//https://stackoverflow.com/questions/51704560/android-file-chooser-not-calling-fr om-android-webview
//File upload messages

public ValueCallback<Uri[]> uploadMessage; 
private ValueCallback<Uri> mUploadMessage;
public static final int REQUEST_SELECT_FILE = 100; 
private final static int FILECHOOSER_RESULTCODE = 1;

//https://stackoverflow.com/questions/5907369/file-upload-in-webview?noredirect=1& lq=1

protected WebView myWebView;	//The WebView client

//Activity Created

@Override
protected void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState); 
setContentView(R.layout.activity_main);

//Sets the WebView client and launches website 
myWebView = (WebView) findViewById(R.id.webview); 
myWebView.setWebChromeClient(new WebChromeClient());
myWebView.loadUrl("https://sites.google.com/my.yorku.ca/osc-sticker-hunt");

//Forces the ChromeClient to be enabled 


WebSettings webSettings = myWebView.getSettings();

webSettings.setSupportMultipleWindows(true);

//Use website settings, enable js and store website data on the client side like login info


webSettings.setJavaScriptEnabled(true); 
webSettings.setDomStorageEnabled(true);

//Allows us to access files and storage on device if we need to take pictures for the QR codes

myWebView.getSettings().setAllowContentAccess(true); 
myWebView.getSettings().setAllowFileAccess(true);


//https://stackoverflow.com/questions/14664363/webchromeclient-opens-link-in-brows er

myWebView.setWebChromeClient(new WebChromeClient() 
{ @Override
public void onReceivedTitle(WebView view, String title) 
{ getWindow().setTitle("OSC Sticker Hunt"); //Set Activity tile to page title.
	}
});

//When the URL is being loaded, check to make sure it is the OSC Treasure Hunt Site
//If so, open directly within WebChrome app, otherwise open in browser 

myWebView.setWebViewClient(new WebViewClient() {
@Override

public boolean shouldOverrideUrlLoading(WebView view, String url) {

if ((url.contains("yorku")) || (url.contains("sites.google.com")))
{

view.loadUrl(url); 
return false;
} else {
Intent browserIntent = new Intent(Intent.ACTION_VIEW,

Uri.parse(url));
startActivity(browserIntent); 
return true;
}
}
});

//https://www.youtube.com/watch?v=-GNEmmjQGB0


camera = (FloatingActionButton)findViewById(R.id.cam); 
camera.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v){
//https://stackoverflow.com/questions/8831050/android-how-to-read-qr-code-in-my-ap plication


try {

Intent intent = new Intent("com.google.zxing.client.android.SCAN");
intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE
for barcodes

		startActivityForResult(intent, 0);
	} catch (Exception e) 
	{ Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
	Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri); 
	startActivity(marketIntent);

	}

//To open camera, beta concept
/*try{

//https://stackoverflow.com/questions/13977245/android-open-camera-from-button Intent intent = new
Intent("android.media.action.IMAGE_CAPTURE");
startActivity(intent);
}
catch (Exception e){ e.printStackTrace();
}*/
}
});
}

//Set up the back key / gesture to go back a page instead of closing app (if there is a previous page, otherwise back key closes app)

@Override
public void onBackPressed() {

if (myWebView.canGoBack()) 
	myWebView.goBack();
else
super.onBackPressed();
}
}



















