package com.dzx.androidstydy.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.baidu.mapapi.SDKInitializer;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/24.
 * activity基类
 */

public abstract class BaseActivity extends AppCompatActivity implements
        View.OnClickListener,BaseMethod{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏显示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);//禁止截屏
        initSDK();
        setContentView(setLayoutRes());
        ButterKnife.bind(this);
        initTransmitData();
        initView();
        initViewClickListener();
        requestInternetData();
        initLoad();

    }

    public void initSDK() {

    }

    /**设置activity的布局文件id*/
    public abstract int setLayoutRes();



    /**启动下一个activity*/
    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }
    /**启动下一个activity*/
    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }

    /**关闭activity*/
    protected void closeActivity() {
        this.finish();
    }




}
