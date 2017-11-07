package retrofit.http;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Named pair(名字的对) for a form-encoded request.
 *
 * Values are converted to strings using {@link String#valueOf(Object)} and then form URL encoded.
 * {@code null} values are ignored. Passing a {@link java.util.List List} or array will result in a
 * field pair for each non-{@code null} item.
 *
 * Simple Example:
 * &#64;(@)FormUrlEncoded
 * &#64;(@)POST("/")
 * void example(@Field("name") String name, @Field("occupation") String occupation);
 * }
 *
 * Calling with {@code foo.example("Bob Smith", "President")} yields a request body of
 * {@code name=Bob+Smith&occupation=President}.
 *
 * Array Example:
 * &#64;(@)FormUrlEncoded
 * &#64;(@)POST("/list")
 * void example(@Field("name") String... names);
 *
 * Calling with {@code foo.example("Bob Smith", "Jane Doe")} yields a request body of
 * {@code name=Bob+Smith&name=Jane+Doe}.
 *
 * @see FormUrlEncoded
 * @see FieldMap
 */

@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    String value();

    /** Specifies whether the {@linkplain #value() name} and value are already URL encoded. */
    boolean encoded() default false;
}
