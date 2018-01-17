package com.ha.cjy.gradeconfigdmo;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

/**
 * Created by Administrator on 2018/1/16.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        //umeng初始化
        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,null);
    }
}
