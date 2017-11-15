package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Treat the response body on methods returning {@link okhttp.Response Response} as it is,
 * i.e.(即) without converting {@link okhttp.Response#body() body()} to {@code byte[]}.
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Streaming {

}
