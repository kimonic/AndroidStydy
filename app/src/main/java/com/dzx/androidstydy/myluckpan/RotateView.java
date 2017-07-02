package com.dzx.androidstydy.myluckpan;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dzx.androidstydy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 * 旋转盘--基本成功版
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

    private FinishRotateListener listener;
    private int[] images=new int[]{
            R.drawable.jiangpin01,
            R.drawable.jiangpin02,
            R.drawable.jiangpin03,
            R.drawable.jiangpin04,
            R.drawable.jiangpin05,
            R.drawable.jiangpin06

    };

    private  List<Bitmap> list;
    private Rect rect=new Rect();

    public void setListener(FinishRotateListener listener) {
        this.listener = listener;
    }

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
        bacPaint1=getPaint(Color.BLUE);
        bacPaint2=getPaint(Color.WHITE);
        textPaint1=getPaint(Color.RED);
        textPaint1.setStrokeWidth(5);

        textPaint2=getPaint(Color.YELLOW);
        textPaint2.setTextSize(60);


        list=new ArrayList<>();

        for (int  i:images) {
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),i,options);
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),i);
            list.add(bitmap);
        }


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
//            canvas.drawCircle((float) (circleX+radius*2/3*Math.cos(2*Math.PI/360*(i+rotateAngle+30))),
//                    (float)(circleY+radius*2/3*Math.sin(2*Math.PI/360*(i+rotateAngle+30))),radius/4,textPaint1);






        }
        for (int i = 0; i < 360; i=i+60) {
            // 设置图片的宽度为直径的1/8
            int imgWidth = radius / 8;

            float angle = (float) ((30 + i+rotateAngle) * (Math.PI / 180));

            int x = (int) (circleX + radius / 2  * Math.cos(angle));
            int y = (int) (circleY + radius / 2  * Math.sin(angle));

            // 确定绘制图片的位置
//            Rect rect = new Rect(x - imgWidth / 2-10, y - imgWidth / 2-10, 50+x + imgWidth
//                    / 2, 50+y + imgWidth / 2);
            rect.left=x - imgWidth / 2-10;
            rect.top=y - imgWidth / 2-10;
            rect.right=50+x + imgWidth/ 2;
            rect.bottom=50+y + imgWidth / 2;
            Bitmap bitmap=list.get(i/60);

            canvas.drawBitmap(bitmap, null, rect, null);

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
        if (listener!=null){
            listener.finish(getCurrentPosition());
        }
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

    public interface FinishRotateListener{
        void finish(int position);
    }
}
