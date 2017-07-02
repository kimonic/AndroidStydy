package com.dzx.androidstydy.myluckpan;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dzx.androidstydy.R;
import com.dzx.androidstydy.utils.ScreenUtils;

/**
 * Created by Administrator on 2017/7/2.
 * 外圈循环灯view
 */

public class LightView extends View implements Runnable {

    /**
     * 屏幕宽度
     */
    private int screenWidth;
//    /**
//     * 屏幕高度
//     */
//    private int screenHeight;

    /**
     * 最外圈大圆的画笔
     */
    private Paint bigCirclePaint;
//    /**
//     * 内圈大圆的画笔
//     */
//    private Paint smallCirclepaint1;
//    private Paint smallCirclepaint2;
    /**
     * 环绕小圆的画笔
     */
    private Paint manyCirclePaintYellow;
    private Paint manyCirclePaintWhite;
    /**
     * 环绕小圆变色标识
     */
    private boolean changeFlag = true;

    /**
     * 绘制文本画笔
     */
    Paint textPaint;

    int count = 0;
    private String[] strs = {"华为手机", "谢谢惠顾", "iPhone 6s", "mac book", "魅族手机", "小米手机"};
    private int[] images = new int[]{R.drawable.huawei, R.drawable.image_one, R.drawable.iphone, R.drawable.macbook, R.drawable.meizu, R.drawable.xiaomi};
    private int paint;

    public LightView(Context context) {
        this(context, null, 0);
    }

    public LightView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(23)
    public LightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView() {

        screenWidth = ScreenUtils.getScreenWidth(getContext());
//        screenHeight = ScreenUtils.getScreenHeight(getContext());

        bigCirclePaint = getPaint(Color.rgb(255, 92, 93));
//        smallCirclepaint1 = getPaint(Color.rgb(255, 133, 132));

        textPaint = getPaint(Color.WHITE);
        textPaint.setTextSize(ScreenUtils.dip2px(getContext(), 14));
//        smallCirclepaint2 = getPaint(Color.rgb(254, 104, 105));
        manyCirclePaintYellow = getPaint(Color.YELLOW);
        manyCirclePaintWhite = getPaint(Color.WHITE);

        new Thread(this).start();

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

    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();


        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int radius = Math.min(width, height) / 2;//大圆的半径

        int circleX = getWidth() / 2;//该view中心点坐标
        int circleY = getHeight() / 2;



        canvas.drawCircle(circleX, circleY, radius, bigCirclePaint);//外圈大圆


        for (int i = 0; i < 360; i = i + 18) {
            float cx;
            float cy;
            cx = (float) (circleX - (float) (radius - 50) * Math.cos(2 * Math.PI / 360 * i));
            cy = (float) (circleY - (float) (radius - 50) * Math.sin(2 * Math.PI / 360 * i));
            drawSmallCircle(i, cx, cy, canvas);
        }


    }

    /**
     * 绘制闪烁小圆
     */
    private void drawSmallCircle(int i, float cx, float cy, Canvas canvas) {
        int showColor;
        if (changeFlag) {
            showColor = (i / 18) % 2;
        } else {
            showColor = ((i + 18) / 18) % 2;
        }
        switch (showColor) {
            case 0:
                canvas.drawCircle(cx, cy, 20, manyCirclePaintYellow);
                break;
            case 1:
                canvas.drawCircle(cx, cy, 20, manyCirclePaintWhite);
                break;
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getMode(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(screenWidth, screenWidth);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(height, height);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, width);
        }


    }

    /**
     * 闪烁循环灯
     */
    @Override
    public void run() {
        while (!((AppCompatActivity) getContext()).isFinishing()) {

            Log.e("TAG", "run:--- " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            changeFlag=(count % 2 == 0);
            postInvalidate();
        }
    }
}
