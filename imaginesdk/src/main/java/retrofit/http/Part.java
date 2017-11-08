package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import retrofit.Converter;

/**
 * Denotes a single part(一个单个的part) of a multi-part request.
 *
 * The parameter type on which this annotation exists will be processed in one of three ways:
 *
 * If the type is {@link String} the value will also be used directly with a {@code text/plain}
 * content type.
 *
 * Other object types will be converted to an appropriate representation by using
 * {@linkplain Converter a converter}.
 *
 * Values may be {@code null} which will omit them from the request body.
 *
 * &#64;(@)Multipart
 * &#64;(@)POST("/")
 * void example(&#64;(@)Part("description") String description,
 *              &#64;(@)Part("image") TypedFile image,
 *              ...
 * );
 *
 * Part parameters may not be {@code null}.
 */

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Part {
    String value();

    /** The {@code Content-Transfer-Encoding} of this part. */
    String encoding() default "binary";
}
