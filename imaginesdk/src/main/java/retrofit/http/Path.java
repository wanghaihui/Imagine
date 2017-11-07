package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Named replacement in the URL path(名字的替换在URL路径里). Values are converted to(转换成) string using
 * {@link String#valueOf(Object)} and URL encoded(URL编码).
 *
 * Simple example:
 * &#64;(@)GET("/image/{id}")
 * void example(@Path("id") int id);
 *
 * Calling with {@code foo.example(1)} yields(产出吧) {@code /image/1}.
 *
 * Values are URL encoded by default. Disable with {@code encode=false}.
 *
 * &#64;(@)GET("/user/{name}")
 * void encoded(@Path("name") String name);
 *
 * &#64;(@)GET("/user/{name}")
 * void notEncoded(@Path(value="name", encode=false) String name);
 *
 * Calling {@code foo.encoded("John+Doe")} yields {@code /user/John%2BDoe} whereas(然而)
 * {@code foo.notEncoded("John+Doe")} yields {@code /user/John+Doe}.
 *
 * Path parameters may not be {@code null}.(路径参数不可能为空)
 */

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Path {
    String value();

    /**
     * Specifies whether the argument value to the annotated method parameter is already URL encoded.
     */
    boolean encoded() default false;
}
