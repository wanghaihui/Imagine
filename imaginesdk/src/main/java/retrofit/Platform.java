package retrofit;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * 支持不同的平台，比如Android，Java8
 */

class Platform {

    private static final Platform PLATFORM = findPlatform();

    static Platform get() {
        return PLATFORM;
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                return new Android();
            }
        } catch (ClassNotFoundException ignored) {

        }

        return new Platform();
    }

    boolean isDefaultMethod(Method method) {
        return false;
    }

    /**
     * 缺省的Call适配器工厂
     * @param callbackExecutor
     * @return
     */
    CallAdapter.Factory defaultCallAdapterFactory(Executor callbackExecutor) {
        if (callbackExecutor != null) {
            // 带回调的生产CallAdapter的工厂
            return new ExecutorCallAdapterFactory(callbackExecutor);
        }
        return DefaultCallAdapter.FACTORY;
    }

    static class Android extends Platform {
        @Override
        CallAdapter.Factory defaultCallAdapterFactory(Executor callbackExecutor) {
            if (callbackExecutor == null) {
                callbackExecutor = new MainThreadExecutor();
            }

            return new ExecutorCallAdapterFactory(callbackExecutor);
        }

        static class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable r) {
                handler.post(r);
            }
        }

    }
}
