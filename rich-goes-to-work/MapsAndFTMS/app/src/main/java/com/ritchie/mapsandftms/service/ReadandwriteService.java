package com.ritchie.mapsandftms.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.ritchie.mapsandftms.window.FloatingWindow;

public class ReadandwriteService extends Service {
    public ReadandwriteService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FloatingWindow floatingWindow = new FloatingWindow(this);
        floatingWindow.initWindow();
        floatingWindow.setAllViewOnClick();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
        //
    }
}