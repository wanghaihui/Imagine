package retrofit;

import okhttp.HttpUrl;

/**
 * The base URL of the remote service. (远程服务的基础URL)
 */

public interface BaseUrl {
    /**
     * The base URL.(基础URL)
     *
     * Consumers(消费者每次需要创建一个允许值随时间变化的请求时，都会调用这个方法) will call this method every time they need to create a request allowing values
     * to change over time.
     */
    HttpUrl url();
}
