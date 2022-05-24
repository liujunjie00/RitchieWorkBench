package com.liqi.nativelib;

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

    //获取到一个值
    public native int getIn();

    // 添加 int 类型返回
    public native int add(int ssr);

    //添加s拼接String 返回
    public native String addString();

    public native int count(int ss,int ss2);

    public native int ModifyMemory();

    public native int ModifyMemory2(int pid);

    public native int ModifyMemory3(int javaPid,long javaStar,long javaEnd);

    public native int ModifyMemory4(int javaPid,long javaAddrStatr,long javaAddrEnd,int i);

    public native int printfI();

    public native int testIntAddr(int addr);

    public native int test1arry(int[] ints);

}