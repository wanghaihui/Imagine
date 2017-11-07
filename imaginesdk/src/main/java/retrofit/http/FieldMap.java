package retrofit.http;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Named key/value pairs for a form-encoded request.
 *
 * Field values may be {@code null} which will omit(忽略) them from the request body.
 *
 * Simple Example:
 * &#64;(@)FormUrlEncoded
 * &#64;(@)POST("/things")
 * void things(@FieldMap Map&lt;String, String&gt; fields);
 *
 * Calling with {@code foo.things(ImmutableMap.of("foo", "bar", "kit", "kat")} yields a request
 * body of {@code foo=bar&kit=kat}.
 *
 * @see FormUrlEncoded
 * @see Field
 */

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMap {
    /** Specifies whether the names and values are already URL encoded. */
    boolean encoded() default false;
}
