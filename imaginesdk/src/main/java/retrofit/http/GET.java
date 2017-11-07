package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Make a GET request(做一个GET请求) to a REST path(一个REST路径) relative to base URL(相对于基础的URL). */

/**
 * Documented--注解表明这个注解应该被 javadoc工具记录
 * Target--注解的作用目标
 * Retention--保留--这种类型的注解会被保留到那个阶段
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GET {
    String value() default "";
}
