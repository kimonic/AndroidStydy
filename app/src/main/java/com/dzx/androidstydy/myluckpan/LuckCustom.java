package com.dzx.androidstydy.myluckpan;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 我的转盘抽奖view
 *
 *
 * Created by Administrator on 2017/6/27.
 */

public class LuckCustom  extends View{


    public LuckCustom(Context context) {
        this(context, null,0);
    }

    public LuckCustom(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LuckCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    @TargetApi(23)
    public LuckCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }


    private void initView() {

    }
}
