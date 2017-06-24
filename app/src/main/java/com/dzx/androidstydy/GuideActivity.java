package com.dzx.androidstydy;

import android.view.View;
import android.widget.TextView;

import com.dzx.androidstydy.baidumapdemo.MapTestActivity;
import com.dzx.androidstydy.base.BaseActivity;

import butterknife.BindView;

public class GuideActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.sample_text)
    TextView sampleText;



    @Override
    public int setLayoutRes() {
        return R.layout.act_guide;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sample_text:
                openActivity(MapTestActivity.class);
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
        sampleText.setText(stringFromJNI());
    }

    @Override
    public void initViewClickListener() {
        sampleText.setOnClickListener(this);
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
