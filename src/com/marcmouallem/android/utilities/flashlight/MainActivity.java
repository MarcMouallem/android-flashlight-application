package com.marcmouallem.android.utilities.flashlight;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class MainActivity extends Activity {

/**********************************************************************************************************************
 * Member Variables BEGIN
 **********************************************************************************************************************/
	
    Context        applicationContext;
    PackageManager packageManager;    
    Window         applicationWindow;
    Camera         camera;
    
/**********************************************************************************************************************
 * Member Variables END & Lifecycle Methods BEGIN
 **********************************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        applicationContext = getApplicationContext();
        packageManager = applicationContext.getPackageManager();
        applicationWindow = getWindow();
        applicationWindow.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(LogTag.SYSTEM, "Activity started.");
    }

    @Override
    protected void onResume() {

        super.onResume();
        Log.v(LogTag.SYSTEM, "Activity resumed.");
        
        turnOnCameraFlash();
        Log.v(LogTag.SYSTEM, "Attempted to turn on camera flash.");

    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.v(LogTag.SYSTEM, "Activity paused.");

        turnOffCameraFlash();
        Log.v(LogTag.SYSTEM, "Attempted to turn off camera flash.");

    }

    @Override
    protected void onStop() {

        super.onStop();
        Log.v(LogTag.SYSTEM, "Activity stopped.");

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean screenIsOff = !powerManager.isScreenOn();
        if (screenIsOff) {
        	finish();
        	Log.v(LogTag.SYSTEM, "Attempted to close application.");
        }

    }
    
/**********************************************************************************************************************
 * Lifecycle Methods END
 **********************************************************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	
        super.onWindowFocusChanged(hasFocus);
        
        if (hasFocus) {
        	dimScreen();
        } else {
        	undimScreen();
        } 
        
    }

    private void turnOnCameraFlash() {
    	camera = Camera.open();
    	Camera.Parameters cameraParameters = camera.getParameters();	
        cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(cameraParameters);
        camera.startPreview();
    }

    private void turnOffCameraFlash() {
        camera.stopPreview();
        camera.release();
    }

    private void dimScreen() {    	
        WindowManager.LayoutParams layoutParams = applicationWindow.getAttributes();
        layoutParams.screenBrightness = 0.01f;
        applicationWindow.setAttributes(layoutParams);
        applicationWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }
    
    private void undimScreen() {
        WindowManager.LayoutParams layoutParams = applicationWindow.getAttributes();
        layoutParams.screenBrightness = LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        applicationWindow.setAttributes(layoutParams);    	
    }

}
