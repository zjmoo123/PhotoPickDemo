package com.example.zjm.photopickdemo.jniTest;

/**
 * Created by zjm on 16-11-18.
 */

public class JniUtils {
    static {
        System.loadLibrary("hello");
    }
    public native String getStringFromC();
    public native String setString();
}
