package com.example.zjm.photopickdemo.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.icu.util.MeasureUnit;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.example.zjm.photopickdemo.R;

/**
 * Created by zjm on 16-9-14.
 */
public class ShaderView extends View {
    private static final int RECT_SIZE = 400;
    private Paint mPaint;
    private int left, top, right, bottom;
    private int width, height;

    private int screenX;
    private int screenY;


    public ShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getSrcreenSize(context);
        //计算屏幕中心坐标
        screenX = width / 2;
        screenY = height / 2;
        //计算左右上下坐标值
        left = screenX - RECT_SIZE;
        top = screenY - RECT_SIZE;
        right = screenX + RECT_SIZE;
        bottom = screenY + RECT_SIZE;


        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);
     //   mPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);


        // 实例一个矩阵对象
        Matrix matrix = new Matrix();

        // 设置矩阵变换
        matrix.setTranslate(500, 500);

        // 设置Shader的变换矩阵
        bitmapShader.setLocalMatrix(matrix);

        // 设置着色器
        mPaint.setShader(bitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // canvas.drawRect(0, 0, screenX * 2, screenY * 2, mPaint);
         canvas.drawRect(left, top, right, bottom, mPaint);
    }

    /**
     * 获取屏幕宽高
     *
     * @param context
     */
    private void getSrcreenSize(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }


}
