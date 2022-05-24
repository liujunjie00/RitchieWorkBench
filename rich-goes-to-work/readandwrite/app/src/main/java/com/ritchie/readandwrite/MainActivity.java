package com.ritchie.readandwrite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_main);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !Settings.canDrawOverlays(this)){
            getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(intent);

        }
        Intent intent = new Intent(this,Readandwrite.class);
        startService(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}