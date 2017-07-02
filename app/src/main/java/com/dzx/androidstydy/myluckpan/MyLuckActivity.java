package com.dzx.androidstydy.myluckpan;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.dzx.androidstydy.R;
import com.dzx.androidstydy.base.BaseActivity;
import com.dzx.androidstydy.luckpan.view.RotatePan;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 展示我的新云抽奖view
 * <p>
 * Created by Administrator on 2017/6/27.
 */

public class MyLuckActivity extends BaseActivity {
//    @BindView(R.id.luckcustom)
//    LuckCustom luckcustom;
    @BindView(R.id.rv_mcustom)
    RotateView rotateview;

    @BindView(R.id.bt_changangle)
    Button changeangle;

    private int angle=1920;

    @Override
    public int setLayoutRes() {
        return R.layout.act_myluck;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.luckcustom:
//                ObjectAnimator.ofFloat(luckcustom, "rotation",0, 270).setDuration(500).start();

//                break;
            case R.id.bt_changangle:
                if (angle<angle+360){
                    angle+=60;
                }
                break;
            case R.id.rv_mcustom:
//                ObjectAnimator.ofFloat(rotateview, "rotation",0, 360,720,1080,1440).setDuration(5000).start();
//                ValueAnimator animtor = ValueAnimator.ofInt(0,3600);
//                animtor.setInterpolator(new AccelerateDecelerateInterpolator());
//                animtor.setDuration(5000);
//                animtor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        int updateValue = (int) animation.getAnimatedValue();
//
////                        ViewCompat.postInvalidateOnAnimation(rotateview);
//                        Log.e("TAG", "onAnimationUpdate: ------" +updateValue);
//                        Log.e("TAG", "onAnimationUpdate: ------" +Thread.currentThread().getName());
//                        rotateview.setRotation(updateValue);
//                        rotateview.postInvalidate();
//                    }
//                });
//                animtor.start();

                rotateview.startRotate(angle);

                break;
        }
    }

    @Override
    public void initTransmitData() {

    }

    @Override
    public void requestInternetData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initViewClickListener() {
//            luckcustom.setOnClickListener(this);
            rotateview.setOnClickListener(this);
        changeangle.setOnClickListener(this);
    }

    @Override
    public void initLoad() {

    }


}
