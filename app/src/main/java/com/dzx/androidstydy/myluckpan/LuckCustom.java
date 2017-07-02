package com.dzx.androidstydy.myluckpan;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.dzx.androidstydy.R;
import com.dzx.androidstydy.base.BaseActivity;
import com.dzx.androidstydy.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的转盘抽奖view
 * <p>
 * <p>
 * Created by Administrator on 2017/6/27.
 */

public class LuckCustom extends View implements Runnable {
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 屏幕高度
     */
    private int screenHeight;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 最外圈大圆的画笔
     */
    private Paint bigCirclePaint;
    /**
     * 内圈大圆的画笔
     */
    private Paint smallCirclepaint1;
    private Paint smallCirclepaint2;
    /**
     * 环绕小圆的画笔
     */
    private Paint manyCirclePaintYellow;
    private Paint manyCirclePaintWhite;
    /**
     * 环绕小圆变色标识
     */
    private boolean changeFlag = true;

    /**绘制文本画笔*/
    Paint textPaint;

    int count = 0;
    private String[] strs = {"华为手机","谢谢惠顾","iPhone 6s","mac book","魅族手机","小米手机"};
    private int[] images = new int[]{R.drawable.huawei,R.drawable.image_one,R.drawable.iphone,R.drawable.macbook,R.drawable.meizu,R.drawable.xiaomi};

    public LuckCustom(Context context) {
        this(context, null, 0);
    }

    public LuckCustom(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuckCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    @TargetApi(23)
    public LuckCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView() {

        screenWidth = ScreenUtils.getScreenWidth(context);
        screenHeight = ScreenUtils.getScreenHeight(context);
        bigCirclePaint = new Paint();
        bigCirclePaint.setAntiAlias(true);
        bigCirclePaint.setColor(Color.rgb(255, 92, 93));
        bigCirclePaint.setStyle(Paint.Style.FILL);

        smallCirclepaint1 = new Paint();
        smallCirclepaint1.setAntiAlias(true);
        smallCirclepaint1.setColor(Color.rgb(255, 133, 132));
        smallCirclepaint1.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(ScreenUtils.dip2px(context,14));


        smallCirclepaint2 = new Paint();
        smallCirclepaint2.setAntiAlias(true);
        smallCirclepaint2.setColor(Color.rgb(254,104,105));
        smallCirclepaint2.setStyle(Paint.Style.FILL);


        manyCirclePaintYellow = new Paint();
        manyCirclePaintYellow.setAntiAlias(true);
        manyCirclePaintYellow.setColor(Color.YELLOW);
        manyCirclePaintYellow.setStyle(Paint.Style.FILL);

        manyCirclePaintWhite = new Paint();
        manyCirclePaintWhite.setAntiAlias(true);
        manyCirclePaintWhite.setColor(Color.WHITE);
        manyCirclePaintWhite.setStyle(Paint.Style.FILL);



        new Thread(this).start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        if (paddingLeft != paddingRight) {
            int paddingLR = Math.min(paddingLeft, paddingRight);
            paddingLeft = paddingLR;
            paddingRight = paddingLR;
        } else if (paddingRight == 0 || paddingLeft == 0) {
            paddingLeft = 30;
            paddingRight = 30;
        }

        if (paddingTop != paddingBottom) {
            int paddingLR = Math.min(paddingTop, paddingBottom);
            paddingTop = paddingLR;
            paddingBottom = paddingLR;
        } else if (paddingTop == 0 || paddingBottom == 0) {
            paddingTop = 30;
            paddingBottom = 30;
        }

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int radius = Math.min(width, height) / 2;//大圆的半径


        int circleX = getWidth() / 2;//该view中心点坐标
        int circleY = getHeight() / 2;


        canvas.drawCircle(circleX, circleY, radius, bigCirclePaint);//外圈大圆
        canvas.drawCircle(circleX, circleY, radius - 100, smallCirclepaint1);//内圈小圆

        RectF  rect=new RectF((float)(circleX-radius+100),(float)(circleY-radius+100),
                (float)(circleX+radius-100),(float)(circleY+radius-100));

        RectF  rect1=new RectF((float)(circleX-radius+100+100),(float)(circleY-radius+100+100),
                (float)(circleX+radius-100-100),(float)(circleY+radius-100-100));




        for (int i = 0; i < 360; i=i+60) {
            if ((i/60)%2==0){
                canvas.drawArc(rect,i,60,true,smallCirclepaint1);
            }else {
                canvas.drawArc(rect,i,60,true,smallCirclepaint2);
            }
        }




        for (int i = 0; i < 360; i = i + 18) {
            float cx;
            float cy;
            cx = (float) (circleX - (float) (radius - 50) * Math.cos(2 * Math.PI / 360 * i));
            cy = (float) (circleY - (float) (radius - 50) * Math.sin(2 * Math.PI / 360 * i));
            if (changeFlag){
                if ((i / 18) % 2 == 0 ) {
                    canvas.drawCircle(cx, cy, 20, manyCirclePaintYellow);
                } else {
                    canvas.drawCircle(cx, cy, 20, manyCirclePaintWhite);
                }
            }else {
                if ((i / 18) % 2 == 0 ) {
                    canvas.drawCircle(cx, cy, 20, manyCirclePaintWhite);
                } else {
                    canvas.drawCircle(cx, cy, 20, manyCirclePaintYellow);
                }
            }
        }

        drawText(rect1,canvas,(float) (radius - 100-100*Math.sqrt(2)));
        drawIcon(circleX,circleY,(int) (radius - 100-100*Math.sqrt(2)-30),0,0,canvas);



    }

    private void drawIcon(int xx,int yy,int mRadius,float startAngle, int i,Canvas mCanvas)
    {

        List<Bitmap>  bitmaps=new ArrayList<>();
        for (int j = 0; j < images.length; j++) {
            bitmaps.add(BitmapFactory.decodeResource(getResources(),images[j]));
        }

        int imgWidth = mRadius / 4;

        float angle = (float) Math.toRadians(60 +startAngle);

        float x = (float) (xx + mRadius / 2 * Math.cos(angle));
        float y = (float) (yy + mRadius / 2  * Math.sin(angle));

//         确定绘制图片的位置
        RectF rect = new RectF(x - imgWidth *3/ 4, y - imgWidth*3 / 4, x + imgWidth
                *3/ 4, y + imgWidth*3/4);

        for (int j = 10; j < 360; j=j+60) {
            RectF  rect1=new RectF((float)(xx-mRadius*Math.cos(2*Math.PI/360*30)),
                    (float)(yy-mRadius*Math.sin(2*Math.PI/360*30)),
                    (float)(xx),(float)(yy));
            Bitmap bitmap = bitmaps.get(0);
            mCanvas.drawBitmap(bitmap, null, rect1, null);

        RectF  rect2=new RectF((float)(xx-mRadius*Math.cos(2*Math.PI/360*75)),
                (float)(yy-mRadius*Math.sin(2*Math.PI/360*75)),
                (float)(xx),(float)(yy));
        Bitmap bitmap1 = bitmaps.get(1);
        mCanvas.drawBitmap(bitmap1, null, rect2, null);


        RectF  rect3=new RectF((float)(xx-mRadius*Math.cos(2*Math.PI/360*135)),
                (float)(yy-mRadius*Math.sin(2*Math.PI/360*135)),
                (float)(xx),(float)(yy));
        Bitmap bitmap2 = bitmaps.get(2);
        mCanvas.drawBitmap(bitmap2, null, rect3, null);









        }

    }

    private void drawText(RectF rect1,Canvas canvas,float mRadius) {
        for (int i = 0; i < 360; i=i+60) {
            Path path=new Path();
            path.addArc(rect1,i,60);

            float textWidth = textPaint.measureText(strs[i/60]);

            float hOffset = (float) (mRadius * Math.PI / 6  - textWidth / 2);
            float vOffset = mRadius / 2 /4;
            canvas.drawTextOnPath(strs[i/60],path,hOffset,0,textPaint);
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
            setMeasuredDimension(screenWidth, height);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, screenWidth);
        }


    }

    @Override
    public void run() {
        while (!((AppCompatActivity) context).isFinishing()) {

            Log.e("TAG", "run:--- " +Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            if (count % 2 == 0) {
                changeFlag = true;
            } else {
                changeFlag = false;
            }

            postInvalidate();

        }

    }
}

