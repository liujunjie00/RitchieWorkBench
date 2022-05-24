package com.liqi.nativelib2;

public class NativeLib {

    // Used to load the 'nativelib2' library on application startup.
    static {
        System.loadLibrary("nativelib2");
    }

    /**
     * A native method that is implemented by the 'nativelib2' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}