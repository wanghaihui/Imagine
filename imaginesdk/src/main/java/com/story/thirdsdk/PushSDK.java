package com.story.thirdsdk;

import android.content.Context;

/**
 * Created by tuyoo on 2017/11/7.
 */

public interface PushSDK extends SDK {
    /**
     * 启动Push服务
     * @param context
     */
    void startPush(Context context);

    /**
     * @return Push的名字
     */
    String getPushName();
}
