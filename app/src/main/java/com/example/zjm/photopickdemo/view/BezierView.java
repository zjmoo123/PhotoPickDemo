package com.example.zjm.photopickdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by zjm on 16-10-10.
 */

public class BezierView extends View {
    private Paint mPaint;
    private Paint mPointPaint;
    private Paint mLinePaint;
    private Path mPath;
    private float blackMagic = 0.551915024494f;
    private PointF p1;
    private PointF p2;
    private PointF p3;
    private PointF p4;
    private PointF p5;
    private PointF p6;
    private PointF p7;
    private PointF p8;
    private PointF p9;
    private PointF p10;
    private PointF p11;
    private PointF p12;
    private float radius;
    private int width;
    private int height;
    private int centerX;
    private int centerY;

    private float mInterpolatedTime;

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFFfe626d);
        mPaint.setStrokeWidth(10);

        mPointPaint = new Paint();
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(Color.BLUE);
        mPointPaint.setStrokeWidth(15);
        mPointPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setAntiAlias(true);

        mPath = new Path();
        p1 = new PointF();
        p2 = new PointF();
        p3 = new PointF();
        p4 = new PointF();
        p5 = new PointF();
        p6 = new PointF();
        p7 = new PointF();
        p8 = new PointF();
        p9 = new PointF();
        p10 = new PointF();
        p11 = new PointF();
        p12 = new PointF();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
        centerX = width / 2;
        centerY = height / 2;
        radius = 250;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        //  canvas.translate(centerX, centerY);
//        p1.x = centerX - radius * blackMagic;
//        p1.y = centerY - radius;
//        p2.x = centerX;
//        p2.y = centerY - radius + 170;
//        p3.x = centerX + radius * blackMagic;
//        p3.y = centerY - radius;
//        p4.x = centerX + radius;
//        p4.y = centerY - radius * blackMagic;
//        p5.x = centerX + radius;
//        p5.y = centerY;
//        p6.x = centerX + radius - 30;
//        p6.y = centerY + radius * blackMagic;
//        p7.x = centerX + radius * blackMagic;
//        p7.y = centerY + radius - 125;
//        p8.x = centerX;
//        p8.y = centerY + radius + 30;
//        p9.x = centerX - radius * blackMagic;
//        p9.y = centerY + radius - 125;
//        p10.x = centerX - radius + 30;
//        p10.y = centerY + radius * blackMagic;
//        p11.x = centerX - radius;
//        p11.y = centerY;
//        p12.x = centerX - radius;
//        p12.y = centerY - radius * blackMagic;

        model0(mInterpolatedTime);

        mPath.moveTo(p2.x, p2.y);
        mPath.cubicTo(p3.x, p3.y, p4.x, p4.y, p5.x, p5.y);
        mPath.cubicTo(p6.x, p6.y, p7.x, p7.y, p8.x, p8.y);
        mPath.cubicTo(p9.x, p9.y, p10.x, p10.y, p11.x, p11.y);
        mPath.cubicTo(p12.x, p12.y, p1.x, p1.y, p2.x, p2.y);

        canvas.drawPath(mPath, mPaint);

//        canvas.drawPoint(p1.x, p1.y, mPointPaint);
//        canvas.drawPoint(p2.x, p2.y, mPointPaint);
//        canvas.drawPoint(p3.x, p3.y, mPointPaint);
//        canvas.drawPoint(p4.x, p4.y, mPointPaint);
//        canvas.drawPoint(p5.x, p5.y, mPointPaint);
//        canvas.drawPoint(p6.x, p6.y, mPointPaint);
//        canvas.drawPoint(p7.x, p7.y, mPointPaint);
//        canvas.drawPoint(p8.x, p8.y, mPointPaint);
//        canvas.drawPoint(p9.x, p9.y, mPointPaint);
//        canvas.drawPoint(p10.x, p10.y, mPointPaint);
//        canvas.drawPoint(p11.x, p11.y, mPointPaint);
//        canvas.drawPoint(p12.x, p12.y, mPointPaint);

//        canvas.drawText("p1",p1.x, p1.y, mPointPaint);
//        canvas.drawText("p2",p2.x, p2.y, mPointPaint);
//        canvas.drawText("p3",p3.x, p3.y, mPointPaint);
//        canvas.drawText("p4",p4.x, p4.y, mPointPaint);
//        canvas.drawText("p5",p5.x, p5.y, mPointPaint);
//        canvas.drawText("p6",p6.x, p6.y, mPointPaint);
//        canvas.drawText("p7",p7.x, p7.y, mPointPaint);
//        canvas.drawText("p8",p8.x, p8.y, mPointPaint);
//        canvas.drawText("p9",p9.x, p9.y, mPointPaint);
//        canvas.drawText("p10",p10.x, p10.y, mPointPaint);
//        canvas.drawText("p11",p11.x, p11.y, mPointPaint);
//        canvas.drawText("p12",p12.x, p12.y, mPointPaint);


//        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, mLinePaint);
//        canvas.drawLine(p2.x, p2.y, p3.x, p3.y, mLinePaint);
//        canvas.drawLine(p4.x, p4.y, p5.x, p5.y, mLinePaint);
//        canvas.drawLine(p5.x, p5.y, p6.x, p6.y, mLinePaint);
//        canvas.drawLine(p7.x, p7.y, p8.x, p8.y, mLinePaint);
//        canvas.drawLine(p8.x, p8.y, p9.x, p9.y, mLinePaint);
//        canvas.drawLine(p10.x, p10.y, p11.x, p11.y, mLinePaint);
//        canvas.drawLine(p11.x, p11.y, p12.x, p12.y, mLinePaint);


    }


    private void model0(float time) {
        p1.x = centerX - radius * blackMagic;
        p1.y = centerY - radius;
        p2.x = centerX;
        p2.y = centerY - radius + 150*time;
        p3.x = centerX + radius * blackMagic;
        p3.y = centerY - radius;
        p4.x = centerX + radius;
        p4.y = centerY - radius * blackMagic;
        p5.x = centerX + radius;
        p5.y = centerY;
        p6.x = centerX + radius - 30*time;
        p6.y = centerY + radius * blackMagic;
        p7.x = centerX + radius * blackMagic;
        p7.y = centerY + radius - 125*time;
        p8.x = centerX;
        p8.y = centerY + radius + 30*time;
        p9.x = centerX - radius * blackMagic;
        p9.y = centerY + radius - 125*time;
        p10.x = centerX - radius + 30*time;
        p10.y = centerY + radius * blackMagic;
        p11.x = centerX - radius;
        p11.y = centerY;
        p12.x = centerX - radius;
        p12.y = centerY - radius * blackMagic;

    }

    private class MoveAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mInterpolatedTime = interpolatedTime;
            invalidate();
        }
    }

    public void startAnimation() {
        mPath.reset();
        mInterpolatedTime = 0;
        MoveAnimation move = new MoveAnimation();
        move.setDuration(7000);
        move.setInterpolator(new AccelerateDecelerateInterpolator());
        //move.setRepeatCount(Animation.INFINITE);
        //move.setRepeatMode(Animation.REVERSE);
        startAnimation(move);
    }

    public BezierView(Context context) {
        this(context, null, 0);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
}
