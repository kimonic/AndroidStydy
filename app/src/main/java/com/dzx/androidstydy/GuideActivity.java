package com.dzx.androidstydy;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.dzx.androidstydy.baidumapdemo.MapTestActivity;
import com.dzx.androidstydy.base.BaseActivity;
import com.dzx.androidstydy.demo_zhy_06_choujiangzhuanpan.MainActivity;
import com.dzx.androidstydy.luckpan.LuckActivity;
import com.dzx.androidstydy.myluckpan.MyLuckActivity;

import java.io.IOException;

import butterknife.BindView;

public class GuideActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.sample_text)
    TextView sampleText;

    @BindView(R.id.comtomluck)
    TextView myluck;

    @BindView(R.id.roottest)
    TextView rootTest;


    @Override
    public int setLayoutRes() {
        return R.layout.act_guide;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sample_text:
//                openActivity(MapTestActivity.class);
                Intent intent = new Intent();
                intent.setClass(this, LuckActivity.class);
                startActivity(intent);
                break;
            case R.id.comtomluck:
                openActivity(MyLuckActivity.class);
                break;
            case R.id.roottest:
                Intent intent3 = new Intent();
                intent3.setClass(this, MainActivity.class);
                startActivity(intent3);
//                ObjectAnimator.ofFloat(rootTest, "rotation",0, 360).setDuration(1000).start();

//                try {
//                    //请求超级用户权限
//                    Process process = Runtime.getRuntime().exec("su");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
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
//        sampleText.setText(stringFromJNI());
    }

    @Override
    public void initViewClickListener() {
        sampleText.setOnClickListener(this);
        myluck.setOnClickListener(this);
        rootTest.setOnClickListener(this);
    }

    @Override
    public void initLoad() {

    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
