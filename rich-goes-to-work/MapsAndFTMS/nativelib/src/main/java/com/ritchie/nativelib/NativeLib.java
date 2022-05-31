package com.ritchie.nativelib;

import java.util.List;

public class NativeLib {

    // Used to load the 'nativelib' library on application startup.
    static {
        System.loadLibrary("nativelib");
    }

    /**
     * A native method that is implemented by the 'nativelib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native long[] searchMapsForlist(List<String[]> list);
    public native long[] searchMapsForStrings(String[] Strings);


}