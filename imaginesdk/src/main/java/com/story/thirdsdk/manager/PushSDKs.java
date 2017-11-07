package com.story.thirdsdk.manager;

import com.story.thirdsdk.PushSDK;

import java.util.List;

/**
 * Created by tuyoo on 2017/11/7.
 */

public class PushSDKs extends SDKs<PushSDK> {

    private static PushSDKs instance = null;

    private PushSDKs() {
        super();
    }

    public static PushSDKs getInstance() {
        if (null == instance) {
            instance = new PushSDKs();
        }
        return instance;
    }

    public List<PushSDK> getAll() {
        return super.getAll();
    }
}
