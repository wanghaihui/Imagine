package retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp.RequestBody;
import okhttp.ResponseBody;

/**
 * Convert objects to and from their representation as HTTP bodies(将对象转换成Http Body). Register a converter with
 * Retrofit using {@link retrofit.Retrofit.Builder#addConverterFactory(Factory)}.
 *
 * 转换器
 */

public interface Converter<F, T> {

    T convert(F value) throws IOException;

    /**
     * 自动是public
     */
    abstract class Factory {
        /**
         * Create a {@link Converter} for converting an HTTP response body to {@code type} or null if it
         * cannot be handled by this factory.
         *
         * 通配符
         */
        public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
            return null;
        }

        /**
         * Create a {@link Converter} for converting {@code type} to an HTTP request body or null if it
         * cannot be handled by this factory.
         *
         * 通配符
         */
        public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
            return null;
        }
    }
}
