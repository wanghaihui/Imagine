package retrofit;

import java.io.IOException;

/**
 * 调用--这个调用具备clone对象的功能
 * An invocation of a Retrofit method(一个Retrofit的方法调用) that sends a request to a webserver and returns a response.
 * Each call yields(产生) its own HTTP request and response pair. Use {@link #clone} to make multiple
 * calls with the same parameters to the same webserver; this may be used to implement polling(轮询) or
 * to retry a failed call(重试一个失败的调用).
 *
 * <p>Calls may be executed synchronously(同步的) with {@link #execute}, or asynchronously(异步的) with {@link
 * #enqueue}. In either case(无论发生何种情况) the call can be canceled at any time with {@link #cancel}. A call that
 * is busy writing its request or reading its response may receive a {@link IOException}; this is
 * working as designed(根据设计，这是可以工作的).
 */
public interface Call<T> extends Cloneable {
    // 同步--所以直接返回
    Response<T> execute() throws IOException;
    // 异步--所以传入回调
    void enqueue(CallBack<T> callBack);
    // 任何时候都可以取消
    void cancel();
    // 具备拷贝功能--调用的多次轮询以及失败后重试
    Call<T> clone();
}
