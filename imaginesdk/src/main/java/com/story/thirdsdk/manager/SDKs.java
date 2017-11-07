package com.story.thirdsdk.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class SDKs<T> {

    private final HashMap<String, T> SDKMap;

    protected SDKs() {
        SDKMap = new HashMap<>();
    }

    public void regist(String key, T sdk) {
        synchronized (SDKMap) {
            SDKMap.put(key, sdk);
        }
    }

    public T getSDK(String key) {
        synchronized (SDKMap) {
            return SDKMap.get(key);
        }
    }

    protected List<T> getAll() {
        synchronized (SDKMap) {
            if (SDKMap.size() > 0) {
                List<T> allSDK = new ArrayList<>();
                allSDK.addAll(SDKMap.values());
                return allSDK;
            }
            return null;
        }
    }

    protected List<String> getKeys() {
        synchronized (SDKMap) {
            if (SDKMap.size() > 0) {
                Iterator<String> iterator = SDKMap.keySet().iterator();
                List<String> keys = new ArrayList<>(SDKMap.size());
                while (iterator.hasNext()) {
                    keys.add(iterator.next());
                }
                return keys;
            }
            return null;
        }
    }
}
