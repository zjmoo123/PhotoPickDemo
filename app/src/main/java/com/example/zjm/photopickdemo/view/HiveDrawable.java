package com.example.zjm.photopickdemo.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by zjm on 16-9-23.
 */

public class HiveDrawable extends Drawable {
    // 用于记录边界信息的Rect
    Rect mRect = new Rect();
    Paint mPaint;
    Path mPath;
    BitmapShader mShader;
    Bitmap mBitmap;

    public HiveDrawable() {
        this(null);
    }

    public HiveDrawable(Drawable drawable) {
        init();
        setBitmap(drawable);
    }

    private void init() {
        initPaint();
        initPath();
    }

    private void ensurePaint() {
        if (mPaint == null) {
            mPaint = new Paint();
        }
    }

    private void ensurePath() {
        if (mPath == null) {
            mPath = new Path();
        }
    }

    private void initPaint() {
        ensurePaint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    // 设置Bitmap的时候初始化shader，并设置给paint
    public void setBitmap(Drawable drawable) {
        mBitmap=centerSquareScaleBitmap(drawableToBitmap(drawable),300);//单位px
        if (mBitmap == null) {
            mShader = null;
        } else {
            mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
        }
    }

    // 初始化好Path要走的路径
    private void initPath() {
        ensurePath();
        float l = (float) (mRect.width() / 2);
        float h = (float) (Math.sqrt(3) * l);
        float top = (mRect.height() - h) / 2;
        mPath.reset();
        mPath.moveTo(l / 2, top);
        mPath.lineTo(0, h / 2 + top);
        mPath.lineTo(l / 2, h + top);
        mPath.lineTo((float) (l * 1.5), h + top);
        mPath.lineTo(2 * l, h / 2 + top);
        mPath.lineTo((float) (l * 1.5), top);
        mPath.lineTo(l / 2, top);
        mPath.close();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        if (mPaint != null) {
            mPaint.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (mPaint != null) {
            mPaint.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    // 设置边界信息
    @Override
        public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mRect.set(left, top, right, bottom);
        initPath();
    }

    @Override
    public int getIntrinsicWidth() {
        if (mBitmap != null) {
            return mBitmap.getWidth();
        } else {
            return super.getIntrinsicWidth();
        }
    }

    @Override
    public int getIntrinsicHeight() {
        if (mBitmap != null) {
            return mBitmap.getHeight();
        }
        return super.getIntrinsicHeight();
    }
    /**

     * @param bitmap      原图
     * @param edgeLength  希望得到的正方形部分的边长
     * @return  缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength)
    {
        if(null == bitmap || edgeLength <= 0)
        {
            return  null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if(widthOrg > edgeLength && heightOrg > edgeLength)
        {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try{
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }
            catch(Exception e){
                return null;
            }

            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try{
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            }
            catch(Exception e){
                return null;
            }
        }

        return result;
    }
    //格式转化drawableToBitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Log.i("TAG","drawableToBitmap");
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ?
                        Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}