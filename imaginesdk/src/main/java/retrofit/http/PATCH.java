package retrofit.http;

/** Make a PATCH request to a REST path relative to base URL. */

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PATCH方法是新引入的，是对PUT方法的补充，用来对已知资源进行局部更新
 * restful只是标准，标准的意思是如果在大家都依此行事的话，沟通成本会很低，开发效率就高
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PATCH {
    String value() default "";
}
