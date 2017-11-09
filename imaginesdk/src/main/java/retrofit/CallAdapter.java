package retrofit;

/**
 * Call适配器
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
}
