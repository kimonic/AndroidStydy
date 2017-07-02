package com.dzx.androidstydy.myluckpan;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.view.WindowCallbackWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/2.
 * 旋转盘
 */

public class RotateView extends View {

    /**
     * 宽高
     */
    private int mWidth;
    private int mHeight;


    private int radius;
    private int circleX;
    private int circleY;

    private RectF arcRect;

    private Paint bacPaint1;
    private Paint bacPaint2;
    private Paint textPaint1;
    private Paint textPaint2;
    /**动画时的旋转角度*/
    private int rotateAngle=0;

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }


    public RotateView(Context context) {
        this(context, null, 0);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(23)
    public RotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
//        bacPaint=getPaint(Color.rgb(255, 133, 132));
        bacPaint1=getPaint(Color.BLUE);
        bacPaint2=getPaint(Color.WHITE);
        textPaint1=getPaint(Color.RED);
        textPaint1.setStrokeWidth(5);

        textPaint2=getPaint(Color.YELLOW);
        textPaint2.setTextSize(60);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();


        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

         radius = Math.min(width, height) / 2;//大圆的半径

         circleX = getWidth() / 2;//该view中心点坐标
         circleY = getHeight() / 2;


        arcRect = getRect();
        for (int i = 0; i < 360; i=i+60) {
            if (i/60%2==0){
                canvas.drawArc(arcRect,i+rotateAngle,60,true,bacPaint1);
            }else {
                canvas.drawArc(arcRect,i+rotateAngle,60,true,bacPaint2);
            }
            canvas.drawCircle((float) (circleX+radius*2/3*Math.cos(2*Math.PI/360*(i+rotateAngle+30))),
                    (float)(circleY+radius*2/3*Math.sin(2*Math.PI/360*(i+rotateAngle+30))),radius/4,textPaint1);
            canvas.drawText(""+(i/60+1),
                    (float) (circleX+radius*2/3*Math.cos(2*Math.PI/360*(i+rotateAngle+30))-15),
                    (float)(circleY+radius*2/3*Math.sin(2*Math.PI/360*(i+rotateAngle+30))+15),textPaint2);

        }

    }

    private RectF getRect() {
        return    new RectF(circleX - radius, circleY - radius, circleX + radius, circleY + radius);
    }

    public void  startRotate(final int angleRange){
        new Thread(new Runnable() {
            @Override
            public void run() {
               changeAngle(angleRange);

            }
        }).start();
    }
    /**角度变化值*/
    private void changeAngle(int angle){
        while (rotateAngle<angle){
            try {
                if (rotateAngle<170){
                    Thread.sleep(60);
                }else if (rotateAngle<1670){
                    Thread.sleep(20);
                }else if (rotateAngle<angle){
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rotateAngle=rotateAngle+10;
            long start=System.currentTimeMillis();
            RotateView.this.postInvalidate();
            Log.e("TAG", "changeAngle: -----"+(System.currentTimeMillis()-start) );
        }
        rotateAngle=rotateAngle%360;
        ((Activity)getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), ""+getCurrentPosition(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("TAG", "changeAngle:------------ "+ rotateAngle);
    }

    private int getCurrentPosition(){
        int temp;
        if (5-rotateAngle/60!=0){
            temp=5-rotateAngle/60;
        }else {
            temp=6;
        }
        return temp;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);


        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, height);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, mHeight);
        }

    }

    /**
     * 取得画笔实例
     */
    public Paint getPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }
}
