package com.dzx.androidstydy.baidumapdemo;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.dzx.androidstydy.R;
import com.dzx.androidstydy.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/24.
 * 百度地图初级测试
 */

public class MapTestActivity extends BaseActivity {


    @BindView(R.id.tv_maptest)
    TextView tvMaptest;


    private LocationClient client;
    private String TAG="maptest";

    @Override
    public int setLayoutRes() {
        return R.layout.act_maptest;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initTransmitData() {

    }

    @Override
    public void requestInternetData() {

    }

    @Override
    public void initSDK() {
        SDKInitializer.initialize(getApplicationContext());
    }

    @Override
    public void initView() {

        client=new LocationClient(getApplicationContext());
        LocationClientOption option=new LocationClientOption();
//        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        client.setLocOption(option);


        client.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {

                final StringBuilder content=new StringBuilder();
                content.append("维度:").append(bdLocation.getLatitude())
                        .append("\n");
                content.append("经度:").append(bdLocation.getLongitude())
                        .append("\n");
                content.append("定位方式:");
                if (bdLocation.getLocType()==BDLocation.TypeGpsLocation){
                    content.append("GPS");
                }else if (bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                    content.append("网络");
                }
                content.append("\n");
                content.append("国家:").append(bdLocation.getCountry()).append("\n");
                content.append("省:").append(bdLocation.getProvince()).append("\n");
                content.append("市:").append(bdLocation.getCity()).append("\n");
                content.append("区:").append(bdLocation.getDistrict()).append("\n");
                content.append("街道:").append(bdLocation.getStreet()).append("\n");








                Log.e(TAG, "onReceiveLocation:--- "+content );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMaptest.setText(content);
                    }
                });



            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        });

        client.start();




    }

    @Override
    public void initViewClickListener() {

    }

    @Override
    public void initLoad() {

    }


}
