package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Make a PUT request to a REST path relative to base URL. */

/**
 * 在HTTP中，PUT被定义为idempotent的方法，POST则不是，这是一个很重要的区别
 * 如果一个方法重复执行多次，产生的效果是一样的，那就是idempotent的
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PUT {
    String value() default "";
}
