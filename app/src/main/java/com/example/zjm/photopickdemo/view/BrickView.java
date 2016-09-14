package com.example.zjm.photopickdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.zjm.photopickdemo.R;

/**
 * Created by zjm on 16-9-14.
 */
public class BrickView extends View {
    private Paint mFillPaint, mStrokePaint;
    private BitmapShader bitmapShader;
    private float posX,posY;

    public BrickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mStrokePaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        mStrokePaint.setColor(0xFF000000);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(5);

        mFillPaint=new Paint();
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.target);
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mFillPaint.setShader(bitmapShader);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*
         * 手指移动时获取触摸点坐标并刷新视图
         */
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            posX = event.getX();
            posY = event.getY();

            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 设置画笔背景色
        canvas.drawColor(Color.DKGRAY);

        /*
         * 绘制圆和描边
         */
        canvas.drawCircle(posX, posY, 300, mFillPaint);
        canvas.drawCircle(posX, posY, 300, mStrokePaint);
    }
}
