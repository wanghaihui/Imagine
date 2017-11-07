package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation on a service method param when you want to directly control the request body
 * of a POST/PUT request (instead of sending in as request parameters or form-style request
 * body).
 * The object will be serialized using the {@link retrofit.Retrofit Retrofit} instance
 * {@link retrofit.Converter Converter} and the result will be set directly as the
 * request body.
 *
 * Body parameters may not be {@code null}.(Body参数不能为null)
 */

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Body {
}
