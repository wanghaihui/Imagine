package retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Call适配器--将一个Call适配成T
 * Adapts a {@link Call} into the type of {@code T}.
 */

public interface CallAdapter<T> {
    /**
     * Returns the value type(返回--值的类型) that this adapter uses when converting the HTTP response body to a Java
     * object. For example, the response type for {@code Call<Repo>} is {@code Repo}. This type
     * is used to prepare the {@code call} passed to {@code #adapt}.
     *
     * Note that this is typically not the same type as the {@code returnType} provided to
     * this call adapter's factory.
     */
    Type responseType();

    /** Returns an instance of the {@code T} which adapts the execution of {@code call}. */
    /**
     * 泛型方法--泛型参数列表置于返回值之前
     */
    <R> T adapt(Call<R> call);

    /**
     * 自动是public
     */
    interface Factory {
        /**
         * 通配符
         * Returns a call adapter for interface methods that return {@code returnType}, or null if this
         * factory doesn't adapt that type.
         */
        CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit);
    }
}
