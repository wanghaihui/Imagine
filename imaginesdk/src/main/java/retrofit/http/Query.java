package retrofit.http;

/**
 * Query parameter appended to the URL.(请求的参数附加到URL上)
 *
 * Values are converted to strings using {@link String#valueOf(Object)} and then URL encoded.
 * {@code null} values are ignored. Passing a {@link java.util.List List} or array will result in(产生) a
 * query parameter for each non-{@code null} item(对每一个非空项都将产生一个请求参数).
 *
 * Simple Example:
 * &#64;(@)GET("/list")
 * void list(@Query("page") int page);
 *
 * Calling with {@code foo.list(1)} yields(产出) {@code /list?page=1}.
 *
 * Example with {@code null}:
 * &#64;(@)GET("/list")
 * void list(@Query("category") String category);
 *
 * Calling with {@code foo.list(null)} yields {@code /list}.
 *
 * Array Example:
 * &#64;(@)GET("/list")
 * void list(@Query("category") String... categories);
 *
 * Calling with {@code foo.list("bar", "baz")} yields {@code /list?category=bar&category=baz}.
 *
 * Parameter names and values are URL encoded by default(参数名字和值默认都是需要URL编码). Specify {@link #encoded() encoded=true} to change this behavior.
 *
 * &#64;(@)GET("/search")
 * void list(@Query(value="foo", encoded=true) String foo);
 * </pre>
 * Calling with {@code foo.list("foo+bar"))} yields {@code /search?foo=foo+bar}.
 *
 * @see QueryMap
 */

public @interface Query {
    /** The query parameter name. */
    String value();

    /**
     * Specifies whether the parameter {@linkplain #value() name} and value are already URL encoded.
     */
    boolean encoded() default false;
}
