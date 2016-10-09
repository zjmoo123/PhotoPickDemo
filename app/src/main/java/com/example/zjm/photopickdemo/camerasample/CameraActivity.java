package com.example.zjm.photopickdemo.camerasample;

import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zjm.photopickdemo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.data;

public class CameraActivity extends AppCompatActivity implements Camera.PictureCallback, View.OnClickListener {

    private static final String TAG = CameraActivity.class.getSimpleName();
    private SurfacePreview mCameraSurPreview;
    private ImageView mCaptureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        mCameraSurPreview = new SurfacePreview(this);
        preview.addView(mCameraSurPreview);

        mCaptureButton = (ImageView) findViewById(R.id.capture);
        mCaptureButton.setOnClickListener(this);
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(bytes);
            fos.close();
            Log.d(TAG, "picture degree:" + String.valueOf(getBitmapDegree(pictureFile.getPath())));
            //pictureFile.getPath()
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }

        // Restart the preview and re-enable the shutter button so that we can take another picture
        camera.startPreview();

        //See if need to enable or not
        mCaptureButton.setEnabled(true);
        Toast.makeText(this, "拍照成功", Toast.LENGTH_LONG).show();
    }

    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private File getOutputMediaFile() {
        //get the mobile Pictures directory
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //get the current time
        String timeStamp = new SimpleDateFormat("yyyy-MMdd-HHmmss").format(new Date());
        return new File(picDir.getPath() + File.separator + "zjm_" + timeStamp + ".jpg");
    }

    @Override
    public void onClick(View view) {
        mCaptureButton.setEnabled(false);
        // get an image from the camera
        mCameraSurPreview.takePicture(this);
    }
}
