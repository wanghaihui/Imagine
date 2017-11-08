package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adds headers literally(逐个的) supplied in the {@code value}.
 *
 * &#64;(@)Headers("Cache-Control: max-age=640000")
 * &#64;(@)GET("/")
 * ...
 *
 * &#64;(@)Headers({
 *   "X-Foo: Bar",
 *   "X-Ping: Pong"
 * })
 * &#64;(@)GET("/")
 * ...
 *
 * Note: Headers do not overwrite each other(Headers不会覆盖重写). All headers with the same name will
 * be included in the request.
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Headers {
    String[] value();
}
