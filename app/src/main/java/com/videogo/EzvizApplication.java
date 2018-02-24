/* 
 * @ProjectName VideoGoJar
 * @Copyright HangZhou Hikvision System Technology Co.,Ltd. All Right Reserved
 * 
 * @FileName EzvizApplication.java
 * @Description 这里对文件进行描述
 * 
 * @author chenxingyf1
 * @data 2014-7-12
 * 
 * @note 这里写本文件的详细功能描述和注释
 * @note 历史记录
 * 
 * @warning 这里写本文件的相关警告
 */
package com.videogo;

import android.app.Application;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.videogo.openapi.EZOpenSDK;

import org.xutils.x;

/**
 * 自定义应用
 */
public class EzvizApplication extends Application {
    
    //开发者需要填入自己申请的appkey
    public static String AppKey = "eda0f5ff20c44defa956c5a0f674b214";
    
    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        initSDK();
        Utils.init(this);
        LogUtils.getConfig().setLogSwitch(true).setLog2FileSwitch(false).setGlobalTag("cqcity");
        CrashUtils.init();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
    
    private void initSDK() {
        {
            /**
             * sdk日志开关，正式发布需要去掉
             */
            EZOpenSDK.showSDKLog(true);
            
            /**
             * 设置是否支持P2P取流,详见api
             */
            EZOpenSDK.enableP2P(true);
            
            /**
             * APP_KEY请替换成自己申请的
             */
            EZOpenSDK.initLib(this, AppKey, "");
        }
    }
}
