package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Replaces the header with the value of its target.
 *
 * &#64;(@)GET("/")
 * void foo(@Header("Accept-Language") String lang, Callback&lt;(<)Response&gt;(>) cb);
 *
 * Header parameters may be {@code null} which will omit them from the request. Passing a
 * {@link java.util.List List} or array will result in a header for each non-{@code null} item.
 *
 * Note: Headers do not overwrite each other. All headers with the same name will
 * be included in the request.
 */

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {
    String value();
}
