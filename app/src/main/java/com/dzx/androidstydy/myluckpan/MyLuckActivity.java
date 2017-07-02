package com.dzx.androidstydy.myluckpan;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.tv_start)
    TextView start;

    private int angle = 1920;


    @Override
    public int setLayoutRes() {
        return R.layout.act_myluck;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.luckcustom:
//                ObjectAnimator.ofFloat(luckcustom, "rotation",0, 270).setDuration(500).start();

//                break;
            case R.id.bt_changangle:
                if (angle < angle + 360) {
                    angle += 60;
                }
                break;
            case R.id.tv_start:
                rotateview.startRotate(angle);
                start.setClickable(false);
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
        changeangle.setOnClickListener(this);
        start.setOnClickListener(this);
        rotateview.setListener(new RotateView.FinishRotateListener() {
            @Override
            public void finish(int position) {
                final StringBuilder builder=new StringBuilder();
                builder.append(""+position);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyLuckActivity.this, builder, Toast.LENGTH_SHORT).show();
                    }
                });
                start.setClickable(true);
            }
        });
    }

    @Override
    public void initLoad() {

    }


}
