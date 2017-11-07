package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Query parameter keys and values appended to the URL.
 *
 * Both keys and values are converted to strings using {@link String#valueOf(Object)}. Values are
 * URL encoded and {@code null} will not include the query parameter in the URL. {@code null} keys
 * are not allowed.
 *
 * Simple Example:
 * &#64;(@)GET("/search")
 * void list(@QueryMap Map&lt;(<)String, String&gt;(>) filters);
 *
 * Calling with {@code foo.list(ImmutableMap.of("foo", "bar", "kit", "kat"))} yields
 * {@code /search?foo=bar&kit=kat}.
 *
 * Map keys and values representing parameter values are URL encoded by default. Specify
 * {@link #encoded() encoded=true} to change this behavior.
 *
 * &#64;(@)GET("/search")
 * void list(@QueryMap(encoded=true) Map&lt;String, String&gt; filters);
 *
 * Calling with {@code foo.list(ImmutableMap.of("foo", "foo+foo"))} yields
 * {@code /search?foo=foo%2Bbar}.
 *
 * @see Query
 */

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryMap {
    /** Specifies whether parameter names and values are already URL encoded. */
    boolean encoded() default false;
}
