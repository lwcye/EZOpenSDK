/* 
 * @ProjectName ezviz-openapi-android-demo
 * @Copyright HangZhou Hikvision System Technology Co.,Ltd. All Right Reserved
 * 
 * @FileName LoginSelectActivity.java
 * @Description 这里对文件进行描述
 * 
 * @author chenxingyf1
 * @data 2014-12-6
 * 
 * @note 这里写本文件的详细功能描述和注释
 * @note 历史记录
 * 
 * @warning 这里写本文件的相关警告
 */
package com.videogo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.videogo.bean.AccessTokenBean;
import com.videogo.ui.cameralist.EZCameraListActivity;
import com.videogo.ui.util.ActivityUtils;
import com.videogo.util.LocalInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import ezviz.ezopensdk.R;

/**
 * 登录选择演示
 *
 * @author xiaxingsuo
 * @data 2015-11-6
 */
public class LoginSelectActivity extends Activity implements OnClickListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        
        initData();
        initView();
    }
    
    private void initData() {
        
    }
    
    private void initView() {
        
    }
    
    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.web_login_btn:
                if (TextUtils.isEmpty(EzvizApplication.AppKey)) {
                    Toast.makeText(this, "Appkey为空", Toast.LENGTH_LONG).show();
                    return;
                }
                LogUtils.e(LocalInfo.getInstance().getAccessToken());
                if (TextUtils.isEmpty(LocalInfo.getInstance().getAccessToken())) {
                    requestAccessToken();
                } else {
                    ActivityUtils.goToLoginAgain(LoginSelectActivity.this);
                }
                break;
            case R.id.goto_cameralist_btn:
                openPlatformLoginDialog();
                break;
            default:
                break;
        }
    }
    
    /**
     * 请求密匙
     */
    private void requestAccessToken() {
        RequestParams params = new RequestParams("https://openauth.ys7.com/doLogin?t=1518367856867");
        params.addQueryStringParameter("response_type", "token");
        params.addQueryStringParameter("client_id", "eda0f5ff20c44defa956c5a0f674b214");
        params.addQueryStringParameter("redirect_uri", "default");
        params.addQueryStringParameter("scope", "xx");
        params.addQueryStringParameter("state", "xxx");
        params.addQueryStringParameter("v", "mobilezx");
        params.addQueryStringParameter("cname", "UEMtUEFOTE9ORw");
        params.addQueryStringParameter("timesnap", "1518367391468");
        params.addQueryStringParameter("account", "18008306535");
        params.addQueryStringParameter("password", "58835124b701fc93e6e8455fb1504f7d");
        params.addQueryStringParameter("from", "d1241784819647918671");
        params.addQueryStringParameter("r", "73846164573697210000");
        params.addQueryStringParameter("returnUrl", "default");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                AccessTokenBean accessTokenBean = new Gson().fromJson(result, AccessTokenBean.class);
                String[] andList = accessTokenBean.redirectUrl.split("&");
                if (andList.length > 0) {
                    String[] split = andList[0].split("=");
                    if (split.length > 1) {
                        LogUtils.e(split[1]);
                        LocalInfo.getInstance().setAccessToken(split[1]);
                    }
                }
            }
            
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            
            @Override
            public void onCancelled(CancelledException cex) {
            }
            
            @Override
            public void onFinished() {
                ActivityUtils.goToLoginAgain(LoginSelectActivity.this);
            }
        });
    }
    
    private void openPlatformLoginDialog() {
        final EditText editText = new EditText(this);
        new AlertDialog.Builder(this)
            .setTitle(R.string.please_input_platform_accesstoken_txt)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setView(editText)
            .setPositiveButton(R.string.certain, new DialogInterface.OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //String getAccessTokenSign = SignUtil.getGetAccessTokenSign();
                    
                    EzvizApplication.getOpenSDK().setAccessToken(editText.getText().toString());
                    Intent toIntent = new Intent(LoginSelectActivity.this, EZCameraListActivity.class);
                    toIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    LoginSelectActivity.this.startActivity(toIntent);
                }
                
            })
            .setNegativeButton(R.string.cancel, null)
            .show();
    }
}
