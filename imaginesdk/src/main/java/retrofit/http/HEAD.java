package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Make a HEAD request to a REST path relative to base URL.
 * Note: HEAD requests(HEAD请求) must use {@link Void} as the response type(回复类型) since there is no body content(没有body内容).
 * 只请求页面的首部
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HEAD {
    String value() default "";
}
