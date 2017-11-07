package com.story.thirdsdk;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * SDK基类
 */
public interface SDK {
    /**
     * Activity的onCreate时触发此调用
     * {@link Activity#onCreate(Bundle) Activity#onCreate}
     * @param activity
     */
    void onActivityCreate(Activity activity);

    /**
     * Application的onCreate时触发
     * {@link Application#onCreate() Application#onCreate}
     * @param application
     */
    void onApplicationCreate(Application application);

    /**
     * 用于第三方SDK的初始化
     * {@link Application#attachBaseContext(Context) Application#attachBaseContext}
     * @param context
     * @param application
     */
    void onAttachBaseContext(Context context, Application application);

    /**
     * 当配置发生改变的时候调用，比如屏幕旋转
     * @param configuration
     */
    void onConfigurationChanged(Configuration configuration);
}
