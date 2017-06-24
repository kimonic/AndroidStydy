package com.dzx.androidstydy.base;

/**
 * Created by Administrator on 2017/6/24.
 * activity中基本方法接口
 */

public interface BaseMethod {

    /**初始化activity之间的传递数据*/
    void initTransmitData();
    /**加载网络数据*/
    void requestInternetData();
    /**初始化控件*/
    void initView();
    /**初始化控件监听*/
    void initViewClickListener();
    /**将网络数据加载到控件*/
    void initLoad();

}
