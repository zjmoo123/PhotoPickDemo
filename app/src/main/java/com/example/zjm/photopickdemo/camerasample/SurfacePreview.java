package com.example.zjm.photopickdemo.camerasample;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by zjm on 16-10-8.
 */

public class SurfacePreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = SurfacePreview.class.getSimpleName();
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;

    public SurfacePreview(Context context) {
        super(context);

        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated() is called");
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Log.d(TAG, "surfaceChanged() is called");
        try {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    if (b) {
                        initCamera();
                        camera.cancelAutoFocus();
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    private void initCamera() {
        mParameters = mCamera.getParameters();
        mParameters.setPictureFormat(PixelFormat.JPEG);
        mParameters.setPictureSize(1080,1920);
        mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        setDisplay(mParameters,mCamera);
    }

    private void setDisplay(Camera.Parameters mParameters, Camera mCamera) {
        if (Integer.parseInt(Build.VERSION.SDK)>=8){
            setDisplayOrientation(mCamera,90);
        }else {
            mParameters.setRotation(90);
        }
    }

    private void setDisplayOrientation(Camera camera, int i) {
        Method downPolymorphic;
        try{
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if(downPolymorphic!=null) {
                downPolymorphic.invoke(camera, new Object[]{i});
            }
        }
        catch(Exception e){
            Log.e(TAG, "image error");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        Log.d(TAG, "surfaceDestroyed() is called");
    }
    public void takePicture(Camera.PictureCallback imageCallback) {
        mCamera.takePicture(null, null, imageCallback);
    }

}
