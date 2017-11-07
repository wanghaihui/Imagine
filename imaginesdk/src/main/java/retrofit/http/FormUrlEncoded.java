package retrofit.http;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes(指示) that the request body will use form URL encoding. Fields should be declared as
 * parameters and annotated with {@link Field @Field}.
 *
 * Requests made with this annotation will have {@code application/x-www-form-urlencoded} MIME
 * type.
 * Field names and values will be UTF-8 encoded before being URI-encoded in accordance to
 * <a href="http://tools.ietf.org/html/rfc3986">RFC-3986</a>.
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormUrlEncoded {
}
